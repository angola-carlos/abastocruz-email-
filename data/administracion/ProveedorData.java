package data.administracion;

import model.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorData {
    private Connection connection;

    public ProveedorData(Connection connection) {
        this.connection = connection;
    }

    public long crear(Proveedor proveedor) throws SQLException {
        String sql = "INSERT INTO proveedor (nombre, razonSocial, nit, telefono, direccion, email) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getRazonSocial());
            ps.setString(3, proveedor.getNit());
            ps.setString(4, proveedor.getTelefono());
            ps.setString(5, proveedor.getDireccion());
            ps.setString(6, proveedor.getEmail());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        proveedor.setIdProveedor(rs.getLong(1));
                    }
                }
            }
        }
        return proveedor.getIdProveedor();
    }


    public Proveedor buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM proveedor WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Proveedor proveedor = new Proveedor();
                    proveedor.setIdProveedor(rs.getLong("idProveedor"));
                    proveedor.setNombre(rs.getString("nombre"));
                    proveedor.setRazonSocial(rs.getString("razonSocial"));
                    proveedor.setNit(rs.getString("nit"));
                    proveedor.setTelefono(rs.getString("telefono"));
                    proveedor.setDireccion(rs.getString("direccion"));
                    proveedor.setEmail(rs.getString("email"));
                    return proveedor;
                }
                return null;
            }
        }
    }

    public boolean eliminar(long idProveedor) throws SQLException {
        String sql = "DELETE FROM proveedor WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idProveedor);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    public boolean actualizar(Proveedor proveedor) throws SQLException {
        String sql = "UPDATE proveedor SET nombre = ?, telefono = ?, direccion, email = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getTelefono());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getEmail());
            ps.setLong(5, proveedor.getIdProveedor());
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    public List<Proveedor> listarTodos() throws SQLException {
        String sql = "SELECT idProveedor, nombre, razonSocial, nit, telefono, direccion, email FROM proveedor";
        List<Proveedor> proveedores = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setIdProveedor(rs.getLong("idProveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setRazonSocial(rs.getString("razonSocial"));
                proveedor.setNit(rs.getString("nit"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedor.setEmail(rs.getString("email"));
                proveedores.add(proveedor);
            }
        }
        return proveedores;
    }
}
