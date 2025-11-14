package controller.administracion;

import logic.administracion.ClienteLogic;
import model.Cliente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClienteController {
    private ClienteLogic clienteLogic;

    public ClienteController(Connection connection) {
        this.clienteLogic = new ClienteLogic(connection);
    }

    public String registrarCliente(String[] parametros) throws SQLException {
        // Se esperan 6 parámetros
        if (parametros.length < 6) {
            return "Parámetros insuficientes para REGISTRAR_CLIENTE. Se requieren: [nombre, apellido, ci, telefono, direccion, email]";
        }

        try {
            Cliente cliente = new Cliente();
            // Limpiamos y asignamos los parámetros al objeto Cliente
            cliente.setNombre(parametros[0].replace("\"", "").trim());
            cliente.setApellido(parametros[1].replace("\"", "").trim());
            cliente.setCi(parametros[2].replace("\"", "").trim());
            cliente.setTelefono(parametros[3].replace("\"", "").trim());
            cliente.setDireccion(parametros[4].replace("\"", "").trim());
            cliente.setEmail(parametros[5].replace("\"", "").trim());

            long idGenerado = clienteLogic.registrarCliente(cliente);
            return "Cliente registrado exitosamente con ID: " + idGenerado;
        } catch (SQLException e) {
            return "Error SQL al registrar el cliente: " + e.getMessage();
        }
    }

    public String actualizarCliente(String[] parametros) throws SQLException {
        // Se esperan 7 parámetros (id + 6 campos)
        if (parametros.length < 7) {
            return "Parámetros insuficientes para ACTUALIZAR_CLIENTE. Se requieren: [idCliente, nombre, apellido, ci, telefono, direccion, email]";
        }

        try {
            long idCliente = Integer.parseInt(parametros[0].replace("\"", "").trim());

            Cliente cliente = new Cliente();
            cliente.setIdCliente(idCliente); // Asignamos el ID
            cliente.setNombre(parametros[1].replace("\"", "").trim());
            cliente.setApellido(parametros[2].replace("\"", "").trim());
            cliente.setCi(parametros[3].replace("\"", "").trim());
            cliente.setTelefono(parametros[4].replace("\"", "").trim());
            cliente.setDireccion(parametros[5].replace("\"", "").trim());
            cliente.setEmail(parametros[6].replace("\"", "").trim());

            boolean actualizado = clienteLogic.actualizarCliente(cliente);
            return actualizado ? "Cliente ID " + idCliente + " actualizado exitosamente" : "No se encontró o no se pudo actualizar el cliente ID " + idCliente;

        } catch (NumberFormatException e) {
            return "El primer parámetro (ID) es un número inválido.";
        } catch (SQLException e) {
            return "Error SQL al actualizar el cliente: " + e.getMessage();
        }
    }

    public String eliminarCliente(String[] parametros) throws SQLException {
        if (parametros.length < 1) {
            return "Parámetros insuficientes para ELIMINAR_CLIENTE. Se requiere: [idCliente]";
        }

        try {
            int idCliente = Integer.parseInt(parametros[0].replace("\"", "").trim());
            boolean eliminado = clienteLogic.eliminarCliente(idCliente);

            return eliminado ? "Cliente ID " + idCliente + " eliminado exitosamente" : "No se encontró o no se pudo eliminar el cliente ID " + idCliente;
        } catch (NumberFormatException e) {
            return "El ID de cliente proporcionado es inválido";
        } catch (SQLException e) {
            return "Error SQL al eliminar el cliente: " + e.getMessage();
        }
    }

    public String buscarPorId(String[] parametros) throws SQLException {
        if (parametros.length < 1) {
            return "Parámetros insuficientes para BUSCAR_CLIENTE. Se requiere: [idCliente]";
        }

        try {
            int idCliente = Integer.parseInt(parametros[0].replace("\"", "").trim());
            Cliente cliente = clienteLogic.buscarPorId(idCliente);

            if (cliente != null) {
                // Formateamos la salida de los datos del cliente
                return "Cliente encontrado: ID=" + cliente.getIdCliente() +
                        ", Nombre=" + cliente.getNombre() +
                        ", CI=" + cliente.getCi() +
                        ", Email=" + cliente.getEmail();
            } else {
                return "Cliente no encontrado con ID: " + idCliente;
            }
        } catch (NumberFormatException e) {
            return "El ID de cliente proporcionado es inválido";
        } catch (SQLException e) {
            return "Error SQL al buscar el cliente: " + e.getMessage();
        }
    }

    public String listarTodos() throws SQLException {
        try {
            List<Cliente> clientes = clienteLogic.listarTodos();

            if (clientes.isEmpty()) {
                return "No se encontraron clientes registrados.";
            }

            StringBuilder sb = new StringBuilder("Lista de Clientes:\n");
            for (Cliente c : clientes) {
                sb.append("ID=").append(c.getIdCliente())
                        .append(", Nombre=").append(c.getNombre())
                        .append(", Apellido=").append(c.getApellido())
                        .append(", CI=").append(c.getCi()).append("\n");
            }
            return sb.toString();
        } catch (SQLException e) {
            return "Error SQL al listar los clientes: " + e.getMessage();
        }
    }
}
