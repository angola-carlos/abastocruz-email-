package data.inventario;

import model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoData {
    private Connection connection;


    public ProductoData(Connection connection) {
        this.connection = connection;
    }


    public long insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO Producto (nombre, descripcion, precio, costo, stockActual, estado, idCategoria) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING idProducto";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, producto.getNombre());
        ps.setString(2, producto.getDescripcion());
        ps.setBigDecimal(3, producto.getPrecio());
        ps.setBigDecimal(4, producto.getCosto());
        ps.setInt(5, producto.getStockActual());
        ps.setString(6, producto.getEstado());
        ps.setLong(7, producto.getIdCategoria());
        int filasAfectadas = ps.executeUpdate();
        if (filasAfectadas > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    producto.setIdProducto(rs.getLong(1));
                }
            }
        }
    
        return producto.getIdProducto();
    }

    public boolean actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE Producto SET nombre=?, descripcion=?, precio=?, costo=?, stockActual=?, estado=?, idCategoria=? WHERE idProducto=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, producto.getNombre());
        ps.setString(2, producto.getDescripcion());
        ps.setBigDecimal(3, producto.getPrecio());
        ps.setBigDecimal(4, producto.getCosto());
        ps.setInt(5, producto.getStockActual());
        ps.setString(6, producto.getEstado());
        ps.setLong(7, producto.getIdCategoria());
        ps.setLong(8, producto.getIdProducto());
        return ps.executeUpdate() > 0;
    }


    public boolean eliminar(int idProducto) throws SQLException {
        String sql = "DELETE FROM Producto WHERE idProducto=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idProducto);
        return ps.executeUpdate() > 0;
    }

    public Producto buscarPorId(long idProducto) throws SQLException {
        String sql = "SELECT * FROM Producto WHERE idProducto=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, idProducto);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return null;


        Producto producto = new Producto();
        producto.setIdProducto(rs.getLong("idProducto"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getBigDecimal("precio"));
        producto.setCosto(rs.getBigDecimal("costo"));
        producto.setStockActual(rs.getInt("stockActual"));
        producto.setEstado(rs.getString("estado"));
        producto.setIdCategoria(rs.getLong("idCategoria"));
        return producto;
    }

    public List<Producto> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Producto ORDER BY idProducto";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Producto> productos = new ArrayList<>();
        while (rs.next()) {
            Producto producto = new Producto();
            producto.setIdProducto(rs.getLong("idProducto"));
            producto.setNombre(rs.getString("nombre"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setPrecio(rs.getBigDecimal("precio"));
            producto.setCosto(rs.getBigDecimal("costo"));
            producto.setStockActual(rs.getInt("stockActual"));
            producto.setEstado(rs.getString("estado"));
            producto.setIdCategoria(rs.getLong("idCategoria"));
            productos.add(producto);
        }
        return productos;
    }

    public List<Producto> listarPorCategoria(int idCategoria) throws SQLException {
        String sql = "SELECT * FROM Producto WHERE idCategoria=? ORDER BY idProducto";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idCategoria);
        ResultSet rs = ps.executeQuery();
        List<Producto> productosCategoria = new ArrayList<>();
        while (rs.next()) {
            Producto producto = new Producto();
            producto.setIdProducto(rs.getLong("idProducto"));
            producto.setNombre(rs.getString("nombre"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setPrecio(rs.getBigDecimal("precio"));
            producto.setCosto(rs.getBigDecimal("costo"));
            producto.setStockActual(rs.getInt("stockActual"));
            producto.setEstado(rs.getString("estado"));
            producto.setIdCategoria(rs.getLong("idCategoria"));
            productosCategoria.add(producto);
        }
        return productosCategoria;
    }

    public void actualizarStock(long idProducto, int nuevoStock) throws SQLException {
        String sql = "UPDATE producto SET stockActual = ? WHERE idproducto = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, nuevoStock);
            ps.setLong(2, idProducto);
            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException("No se actualizó el stock. Producto no encontrado con ID: " + idProducto);
            }
        }
    }
}
