package controller;

import controller.administracion.ClienteController;
import controller.administracion.ProveedorController;
import controller.inventario.CategoriaController;
import controller.inventario.ProductoController;
import controller.operaciones.PromocionController;
import controller.operaciones.VentaController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComandoEmailController {

    private final ComandoController comandoController;
    private final CategoriaController categoriaController;
    private final ClienteController clienteController;
    private final ProductoController productoController;
    private final PromocionController promocionController;
    private final VentaController ventaController;
    private final ProveedorController proveedorController;

    public ComandoEmailController(Connection connection) {
        this.comandoController = new ComandoController(connection);
        this.categoriaController = new CategoriaController(connection);
        this.clienteController = new ClienteController(connection);
        this.productoController = new ProductoController(connection);
        this.promocionController = new PromocionController(connection);
        this.ventaController = new VentaController(connection);
        this.proveedorController = new ProveedorController(connection);
    }

    public String procesarComando(String subject) {
        try {
            String comando = subject.split("\\[")[0].trim().toUpperCase();

            String[] parametros;

            if (subject.contains("[") && subject.contains("]")) {
                String paramsString = subject.substring(subject.indexOf('[') + 1, subject.lastIndexOf(']')).trim();
                List<String> paramList = dividirParametros(paramsString);
                parametros = paramList.toArray(new String[0]);
            } else {
                parametros = new String[]{};
            }

            switch (comando) {
                case "HELP":
                    return comandoController.listarTodos();

                // --- CATEGORIA COMMANDS
                case "REGISTRAR_CATEGORIA":
                    return categoriaController.registrarCategoria(parametros);
                case "ACTUALIZAR_CATEGORIA":
                    return categoriaController.actualizarCategoria(parametros);
                case "ELIMINAR_CATEGORIA":
                    return categoriaController.eliminarCategoria(parametros);
                case "BUSCAR_CATEGORIA":
                    return categoriaController.buscarPorId(parametros);
                case "LISTAR_CATEGORIAS":
                    return categoriaController.listarTodos();

                // --- CLIENTE COMMANDS
                case "REGISTRAR_CLIENTE":
                    return clienteController.registrarCliente(parametros);
                case "ACTUALIZAR_CLIENTE":
                    return clienteController.actualizarCliente(parametros);
                case "ELIMINAR_CLIENTE":
                    return clienteController.eliminarCliente(parametros);
                case "BUSCAR_CLIENTE":
                    return clienteController.buscarPorId(parametros);
                case "LISTAR_CLIENTES":
                    return clienteController.listarTodos();

                // --- PRODUCTO COMMANDS
                case "REGISTRAR_PRODUCTO":
                    return productoController.registrarProducto(parametros);
                case "ACTUALIZAR_PRODUCTO":
                    return productoController.actualizarProducto(parametros);
                case "ELIMINAR_PRODUCTO":
                    return productoController.eliminarProducto(parametros);
                case "BUSCAR_PRODUCTO":
                    return productoController.obtenerPorId(parametros);
                case "LISTAR_PRODUCTOS":
                    return productoController.listarTodos();
                case "LISTAR_POR_CATEGORIA":
                    return productoController.listarPorCategoria(parametros);
                // --- PROVEEDOR COMMANDS
                case "REGISTRAR_PROVEEDOR":
                    return proveedorController.registrarProveedor(parametros);
                case "ACTUALIZAR_PROVEEDOR":
                    return proveedorController.actualizarProveedor(parametros);
                case "ELIMINAR_PROVEEDOR":
                    return proveedorController.eliminarProveedor(parametros);
                case "BUSCAR_PROVEEDOR":
                    return proveedorController.obtenerPorId(parametros);
                case "LISTAR_PROVEEDOR":
                    return proveedorController.listarTodos();
                // --- USUARIO COMMANDS
                // --- PROMOCION COMMANDS
                case "REGISTRAR_PROMOCION":
                    // El último parámetro es la cadena de detalles, que PromocionController procesará
                    return promocionController.registrarPromocion(parametros);
                case "BUSCAR_PROMOCION":
                    return promocionController.obtenerPorId(parametros);
                case "LISTAR_PROMOCIONES":
                    return promocionController.listarPromociones();
                case "REGISTRAR_VENTA":
                    return ventaController.registrarVenta(parametros);
                case "LISTAR_VENTAS":
                    return  ventaController.listarVentas();

                default:
                    return "Comando no reconocido: " + comando;
            }
        } catch (SQLException e) {
            return "Error de base de datos al ejecutar el comando: " + e.getMessage();
        } catch (Exception e) {
            return "Error inesperado al procesar el comando: " + e.getMessage();
        }
    }


    private List<String> dividirParametros(String input) {
        List<String> result = new ArrayList<>();
        StringBuilder actual = new StringBuilder();
        int nivel = 0;

        for (char c : input.toCharArray()) {
            if (c == '[') nivel++;
            else if (c == ']') nivel--;

            if (c == ',' && nivel == 0) {
                result.add(actual.toString().trim());
                actual.setLength(0);
            } else {
                actual.append(c);
            }
        }
        if (actual.length() > 0) result.add(actual.toString().trim());
        return result;
    }
}