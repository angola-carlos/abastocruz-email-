package controller.administracion;

import logic.administracion.ProveedorLogic;
import model.Proveedor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProveedorController {
    private ProveedorLogic proveedorLogic;

    public ProveedorController(Connection connection) {
        this.proveedorLogic = new ProveedorLogic(connection);
    }

    public String registrarProveedor(String[] parametros) throws SQLException {
        if (parametros.length < 6) {
            return "Parámetros insuficientes para REGISTRAR_PROVEEDOR. Se requieren: [nombre, razonsocial, nit, telefono, direccion, email]";
        }

        try {
            Proveedor proveedor = new Proveedor();
            proveedor.setNombre(parametros[0].replace("\"", "").trim());
            proveedor.setRazonSocial(parametros[1].replace("\"", "").trim());
            proveedor.setNit(parametros[2].replace("\"", "").trim());
            proveedor.setTelefono(parametros[3].replace("\"", "").trim());
            proveedor.setDireccion(parametros[4].replace("\"", "").trim());
            proveedor.setEmail(parametros[5].replace("\"", "").trim());

            long idGenerado = proveedorLogic.registrarProveedor(proveedor);
            return "Proveedor registrado exitosamente con ID: " + idGenerado;
        } catch (SQLException e) {
            return "Error SQL al registrar el proveedor: " + e.getMessage();
        }
    }

    public String actualizarProveedor(String[] parametros) throws SQLException {
        if (parametros.length < 7) {
            return "Parámetros insuficientes para ACTUALIZAR_PROVEEDOR. Se requieren: [idproveedor, nombre, razonsocial, nit, telefono, direccion, email]";
        }

        try {
            long idProveedor = Integer.parseInt(parametros[0].replace("\"", "").trim());

            Proveedor proveedor = new Proveedor();
            proveedor.setIdProveedor(idProveedor); // Asignamos el ID
            proveedor.setNombre(parametros[1].replace("\"", "").trim());
            proveedor.setRazonSocial(parametros[2].replace("\"", "").trim());
            proveedor.setNit(parametros[3].replace("\"", "").trim());
            proveedor.setTelefono(parametros[4].replace("\"", "").trim());
            proveedor.setDireccion(parametros[5].replace("\"", "").trim());
            proveedor.setEmail(parametros[6].replace("\"", "").trim());

            boolean actualizado = proveedorLogic.actualizarProveedor(proveedor);
            return actualizado ? "Proveedor ID " + idProveedor + " actualizado exitosamente" : "No se encontró o no se pudo actualizar el proveedor ID " + idProveedor;

        } catch (NumberFormatException e) {
            return "El primer parámetro (ID) es un número inválido.";
        } catch (SQLException e) {
            return "Error SQL al actualizar el proveedor: " + e.getMessage();
        }
    }

    public String eliminarProveedor(String[] parametros) throws SQLException {
        if (parametros.length < 1) {
            return "Parámetros insuficientes para ELIMINAR_PROVEEDOR. Se requiere: [idproveedor]";
        }

        try {
            int idProveedor = Integer.parseInt(parametros[0].replace("\"", "").trim());
            boolean eliminado = proveedorLogic.eliminarProveedor(idProveedor);

            return eliminado ? "Proveedor ID " + idProveedor + " eliminado exitosamente" : "No se encontró o no se pudo eliminar el proveedor ID " + idProveedor;
        } catch (NumberFormatException e) {
            return "El ID de proveedor proporcionado es inválido";
        } catch (SQLException e) {
            return "Error SQL al eliminar el proveedor: " + e.getMessage();
        }
    }

    public String obtenerPorId(String[] parametros) throws SQLException {
        if (parametros.length < 1) {
            return "Parámetros insuficientes para BUSCAR_PROVEEDOR. Se requiere: [idproveedor]";
        }

        try {
            int idProveedor = Integer.parseInt(parametros[0].replace("\"", "").trim());
            Proveedor proveedor = proveedorLogic.obtenerPorId(idProveedor);

            if (proveedor != null) {
                return "Proveedor encontrado: ID=" + proveedor.getIdProveedor() +
                        ", Nombre=" + proveedor.getNombre() +
                        ", Razón Social=" + proveedor.getRazonSocial() +
                        ", NIT=" + proveedor.getNit();
            } else {
                return "Proveedor no encontrado con ID: " + idProveedor;
            }
        } catch (NumberFormatException e) {
            return "El ID de proveedor proporcionado es inválido";
        } catch (SQLException e) {
            return "Error SQL al buscar el proveedor: " + e.getMessage();
        }
    }

    public String listarTodos() throws SQLException {
        try {
            List<Proveedor> proveedores = proveedorLogic.listarProveedores();

            if (proveedores.isEmpty()) {
                return "No se encontraron proveedores registrados.";
            }

            StringBuilder sb = new StringBuilder("Lista de Proveedores:\n");
            for (Proveedor p : proveedores) {
                sb.append("ID=").append(p.getIdProveedor())
                        .append(", Nombre=").append(p.getNombre())
                        .append(", Razón Social=").append(p.getRazonSocial())
                        .append(", NIT=").append(p.getNit()).append("\n");
            }
            return sb.toString();
        } catch (SQLException e) {
            return "Error SQL al listar los proveedores: " + e.getMessage();
        }
    }
}
