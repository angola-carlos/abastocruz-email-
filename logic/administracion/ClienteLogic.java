package logic.administracion;

import data.administracion.ClienteData;
import model.Cliente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClienteLogic {
    private ClienteData clienteData;

    public ClienteLogic(Connection connection) {
        this.clienteData =  new ClienteData(connection);
    }

    public long registrarCliente(Cliente cliente) throws SQLException {
        return clienteData.insertar(cliente);
    }


    public boolean actualizarCliente(Cliente cliente) throws SQLException {
        return clienteData.actualizar(cliente);
    }


    public boolean eliminarCliente(int idCliente) throws SQLException {
        return clienteData.eliminar(idCliente);
    }


    public Cliente buscarPorId(int idCliente) throws SQLException {
        return clienteData.buscarPorId(idCliente);
    }


    public List<Cliente> listarTodos() throws SQLException {
        return clienteData.listarTodos();
    }
}
