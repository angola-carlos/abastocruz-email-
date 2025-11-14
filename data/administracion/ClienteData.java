package data.administracion;

import model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteData {
    private Connection conn;


    public ClienteData(Connection conn) {
        this.conn = conn;
    }

    public long insertar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nombre, apellido, ci, telefono, direccion, email) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getCi());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getDireccion());
            ps.setString(6, cliente.getEmail());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        cliente.setIdCliente(rs.getLong(1));
                    }
                }
            }
        }
        return cliente.getIdCliente();
    }


    public Cliente buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE idCliente=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getLong("idCliente"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setApellido(rs.getString("apellido"));
                    cliente.setCi(rs.getString("ci"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setDireccion(rs.getString("direccion"));
                    cliente.setEmail(rs.getString("email"));
                    return cliente;
                }
                return null;
            }
        }
    }

    public boolean eliminar(long idCliente) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idCliente);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0; // Retorna true si se eliminó algo
        }
    }

    public boolean actualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nombre = ?, telefono = ?, direccion = ?, email = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3,cliente.getDireccion());
            ps.setString(4, cliente.getEmail());
            ps.setLong(5, cliente.getIdCliente());
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0; // Retorna true si se actualizó algo
        }
    }

    public List<Cliente> listarTodos() throws SQLException {
        String sql = "SELECT idCliente, nombre, apellido, ci, telefono, direccion, email FROM cliente";
        List<Cliente> clientes = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getLong("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setCi(rs.getString("ci"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setEmail(rs.getString("email"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }
}
