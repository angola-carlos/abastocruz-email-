package controller.inventario;

import logic.inventario.CategoriaLogic;
import model.Categoria;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CategoriaController {
    private CategoriaLogic categoriaLogic;

    public CategoriaController(Connection connection) {
        this.categoriaLogic = new CategoriaLogic(connection);
    }

    public String registrarCategoria(String [] parametros) throws SQLException {
        if(parametros.length < 2) return "Parámetros insuficientes para REGISTRAR_CATEGORIA";
        try {
            Categoria categoria = new Categoria();
            categoria.setNombre(parametros[0].replace("\"", "").trim());
            categoria.setDescripcion(parametros[1].replace("\"", "").trim());

            long idGenerado = categoriaLogic.registrarCategoria(categoria);
            return "Categoria registrada exitosamente con ID: " + idGenerado;
        } catch (SQLException e) {
            return "Error SQL al registrar la categoría: " + e.getMessage();
        }
    }

    public String actualizarCategoria(String[] parametros) throws SQLException {
        if (parametros.length < 3) {
            return "Parámetros insuficientes para ACTUALIZAR_CATEGORIA. Se requiere: [id, nombre, descripcion]";
        }

        try {
            long idCategoria = Integer.parseInt(parametros[0].replace("\"", "").trim());
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(idCategoria);
            categoria.setNombre(parametros[1].replace("\"", "").trim());
            categoria.setDescripcion(parametros[2].replace("\"", "").trim());

            boolean actualizado = categoriaLogic.actualizarCategoria(categoria);
            return actualizado ? "Categoria ID " + idCategoria + " actualizada exitosamente" : "No se encontró o no se pudo actualizar la categoría ID " + idCategoria;

        } catch (NumberFormatException e) {
            return "El primer parámetro (ID) es un número inválido.";
        } catch (SQLException e) {
            return "Error SQL al actualizar la categoría: " + e.getMessage();
        }
    }

    public String eliminarCategoria(String[] parametros) throws SQLException {
        if (parametros.length < 1) {
            return "Parámetros insuficientes para ELIMINAR_CATEGORIA. Se requiere: [idCategoria]";
        }

        try {
            int idCategoria = Integer.parseInt(parametros[0].replace("\"", "").trim());
            boolean eliminado = categoriaLogic.eliminarCategoria(idCategoria);

            return eliminado ? "Categoria eliminada exitosamente" : "No se encontró o no se pudo eliminar la categoria ID " + idCategoria;
        } catch (NumberFormatException e) {
            return "El ID de categoria proporcionado es inválido";
        } catch (SQLException e) {
            return "Error SQL al eliminar la categoría: " + e.getMessage();
        }
    }

    public String buscarPorId(String[] parametros) throws SQLException {
        if (parametros.length < 1) {
            return "Parámetros insuficientes para BUSCAR_CATEGORIA. Se requiere: [idCategoria]";
        }

        try {
            int idCategoria = Integer.parseInt(parametros[0].replace("\"", "").trim());
            Categoria categoria = categoriaLogic.buscarPorId(idCategoria);

            if (categoria != null) {
                return "Categoria encontrada: ID=" + categoria.getIdCategoria() + ", Nombre=" + categoria.getNombre() + ", Descripcion=" + categoria.getDescripcion();
            } else {
                return "Categoria no encontrada con ID: " + idCategoria;
            }
        } catch (NumberFormatException e) {
            return "El ID de categoria proporcionado es inválido";
        } catch (SQLException e) {
            return "Error SQL al buscar la categoría: " + e.getMessage();
        }
    }

    public String listarTodos() throws SQLException {
        try {
            List<Categoria> categorias = categoriaLogic.listarTodos();

            if (categorias.isEmpty()) {
                return "No se encontraron categorías registradas.";
            }

            StringBuilder sb = new StringBuilder("Lista de Categorías:\n");
            for (Categoria c : categorias) {
                sb.append("ID=").append(c.getIdCategoria())
                        .append(", Nombre=").append(c.getNombre())
                        .append(", Descripcion=").append(c.getDescripcion()).append("\n");
            }
            return sb.toString();
        } catch (SQLException e) {
            return "Error SQL al listar las categorías: " + e.getMessage();
        }
    }
}
