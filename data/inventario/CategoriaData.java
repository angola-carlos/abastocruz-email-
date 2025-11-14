package data.inventario;

import model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaData {
    private Connection connection;


    public CategoriaData(Connection connection) {
        this.connection = connection;
    }


    public long insertar(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO Categoria (nombre, descripcion) VALUES (?, ?) RETURNING idCategoria";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, categoria.getNombre());
        ps.setString(2, categoria.getDescripcion());
        int filasAfectadas = ps.executeUpdate();
        if (filasAfectadas > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    categoria.setIdCategoria(rs.getLong(1));
                }
            }
        }
    
        return categoria.getIdCategoria();
    }

    public boolean actualizar(Categoria categoria) throws SQLException {
        String sql = "UPDATE Categoria SET nombre=?, descripcion=? WHERE idCategoria=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, categoria.getNombre());
        ps.setString(2, categoria.getDescripcion());
        ps.setLong(3, categoria.getIdCategoria());
        return ps.executeUpdate() > 0;
    }


    public boolean eliminar(int idCategoria) throws SQLException {
        String sql = "DELETE FROM Categoria WHERE idCategoria=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idCategoria);
        return ps.executeUpdate() > 0;
    }

    public Categoria buscarPorId(int idCategoria) throws SQLException {
        String sql = "SELECT * FROM Categoria WHERE idCategoria=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idCategoria);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return null;


        Categoria categoria = new Categoria();
        categoria.setIdCategoria(rs.getLong("idCategoria"));
        categoria.setNombre(rs.getString("nombre"));
        categoria.setDescripcion(rs.getString("descripcion"));
        return categoria;
    }


    public List<Categoria> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Categoria ORDER BY idCategoria";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Categoria> lista = new ArrayList<>();
        while (rs.next()) {
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getLong("idCategoria"));
            categoria.setNombre(rs.getString("nombre"));
            categoria.setDescripcion(rs.getString("descripcion"));
            lista.add(categoria);
        }
        return lista;
    }
}
