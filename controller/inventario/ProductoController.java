package controller.inventario;

import logic.inventario.ProductoLogic;
import model.Producto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;

public class ProductoController {
    private ProductoLogic productoLogic;

    public ProductoController(Connection connection) {
        this.productoLogic = new ProductoLogic(connection);
    }

    public String registrarProducto(String[] parametros) throws SQLException {
        if (parametros.length < 7) {
            return "Parámetros insuficientes para REGISTRAR_PRODUCTO. Se requieren: [nombre, descripcion, precio, costo, stockactual, estado, idcategoria]";
        }

        try {
            Producto producto = new Producto();

            producto.setNombre(parametros[0].replace("\"", "").trim());
            producto.setDescripcion(parametros[1].replace("\"", "").trim());
            producto.setEstado(parametros[5].replace("\"", "").trim());

            producto.setPrecio(new BigDecimal(parametros[2].replace("\"", "").trim()));
            producto.setCosto(new BigDecimal(parametros[3].replace("\"", "").trim()));
            producto.setStockActual(Integer.parseInt(parametros[4].replace("\"", "").trim()));
            producto.setIdCategoria(Long.parseLong(parametros[6].replace("\"", "").trim()));

            long idGenerado = productoLogic.registrarProducto(producto);
            return "Producto registrado exitosamente con ID: " + idGenerado;

        } catch (NumberFormatException e) {
            return "Error de formato: Asegúrate de que precio, costo, stockactual e idcategoria sean números válidos. Error: " + e.getMessage();
        } catch (SQLException e) {
            return "Error SQL al registrar el producto: " + e.getMessage();
        }
    }

    public String actualizarProducto(String[] parametros) throws SQLException {
        if (parametros.length < 8) {
            return "Parámetros insuficientes para ACTUALIZAR_PRODUCTO. Se requieren: [idproducto, nombre, descripcion, precio, costo, stockactual, estado, idcategoria]";
        }

        try {
            Producto producto = new Producto();

            long idProducto = Integer.parseInt(parametros[0].replace("\"", "").trim());
            producto.setIdProducto(idProducto);

            producto.setNombre(parametros[1].replace("\"", "").trim());
            producto.setDescripcion(parametros[2].replace("\"", "").trim());
            producto.setEstado(parametros[6].replace("\"", "").trim());

            producto.setPrecio(new BigDecimal(parametros[3].replace("\"", "").trim()));
            producto.setCosto(new BigDecimal(parametros[4].replace("\"", "").trim()));
            producto.setStockActual(Integer.parseInt(parametros[5].replace("\"", "").trim()));
            producto.setIdCategoria(Long.parseLong(parametros[7].replace("\"", "").trim()));

            boolean actualizado = productoLogic.actualizarProducto(producto);
            return actualizado ? "Producto ID " + idProducto + " actualizado exitosamente" : "No se encontró o no se pudo actualizar el producto ID " + idProducto;

        } catch (NumberFormatException e) {
            return "Error de formato: El ID, precio, costo, stock o idcategoria son números inválidos. Error: " + e.getMessage();
        } catch (SQLException e) {
            return "Error SQL al actualizar el producto: " + e.getMessage();
        }
    }

    public String eliminarProducto(String[] parametros) throws SQLException {
        if (parametros.length < 1) {
            return "Parámetros insuficientes para ELIMINAR_PRODUCTO. Se requiere: [idproducto]";
        }

        try {
            int idProducto = Integer.parseInt(parametros[0].replace("\"", "").trim());
            boolean eliminado = productoLogic.eliminarProducto(idProducto);

            return eliminado ? "Producto ID " + idProducto + " eliminado exitosamente" : "No se encontró o no se pudo eliminar el producto ID " + idProducto;
        } catch (NumberFormatException e) {
            return "El ID de producto proporcionado es inválido";
        } catch (SQLException e) {
            return "Error SQL al eliminar el producto: " + e.getMessage();
        }
    }

    public String obtenerPorId(String[] parametros) throws SQLException {
        if (parametros.length < 1) {
            return "Parámetros insuficientes para BUSCAR_PRODUCTO. Se requiere: [idproducto]";
        }

        try {
            int idProducto = Integer.parseInt(parametros[0].replace("\"", "").trim());
            Producto producto = productoLogic.obtenerPorId(idProducto);

            if (producto != null) {
                return "Producto encontrado: ID=" + producto.getIdProducto() +
                        ", Nombre=" + producto.getNombre() +
                        ", Precio=" + producto.getPrecio() +
                        ", Stock=" + producto.getStockActual() +
                        ", Categoria ID=" + producto.getIdCategoria();
            } else {
                return "Producto no encontrado con ID: " + idProducto;
            }
        } catch (NumberFormatException e) {
            return "El ID de producto proporcionado es inválido";
        } catch (SQLException e) {
            return "Error SQL al buscar el producto: " + e.getMessage();
        }
    }

    public String listarPorCategoria(String[] parametros) throws SQLException {
        if (parametros.length < 1) {
            return "Parámetros insuficientes para LISTAR_POR_CATEGORIA. Se requiere: [idCategoria]";
        }

        try {
            int idCategoria = Integer.parseInt(parametros[0].replace("\"", "").trim());
            List<Producto> productos = productoLogic.listarPorCategoria(idCategoria);

            if (productos.isEmpty()) {
                return "No se encontraron productos para la Categoría ID: " + idCategoria;
            }

            StringBuilder sb = new StringBuilder("Lista de Productos para Categoría ID " + idCategoria + ":\n");
            for (Producto p : productos) {
                sb.append("ID=").append(p.getIdProducto())
                        .append(", Nombre=").append(p.getNombre())
                        .append(", Precio=").append(p.getPrecio())
                        .append(", Stock=").append(p.getStockActual()).append("\n");
            }
            return sb.toString();
        } catch (NumberFormatException e) {
            return "El ID de categoría proporcionado es inválido";
        } catch (SQLException e) {
            return "Error SQL al listar productos por categoría: " + e.getMessage();
        }
    }

    public String listarTodos() throws SQLException {
        try {
            List<Producto> productos = productoLogic.listarTodos();

            if (productos.isEmpty()) {
                return "No se encontraron productos registrados.";
            }

            StringBuilder sb = new StringBuilder("Lista de Productos:\n");
            for (Producto p : productos) {
                sb.append("ID=").append(p.getIdProducto())
                        .append(", Nombre=").append(p.getNombre())
                        .append(", Precio=").append(p.getPrecio())
                        .append(", Stock=").append(p.getStockActual()).append("\n");
            }
            return sb.toString();
        } catch (SQLException e) {
            return "Error SQL al listar los productos: " + e.getMessage();
        }
    }
}
