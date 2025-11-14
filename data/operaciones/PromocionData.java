package data.operaciones;

import model.Promocion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromocionData {
    private Connection conn;


    public PromocionData(Connection conn) {
        this.conn = conn;
    }

    public long insertar(Promocion p) throws SQLException {
        String sql = "INSERT INTO Promocion(nombre, descripcion, fechaInicio, fechaFin, estado) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDate(3, Date.valueOf(p.getFechaInicio()));
            ps.setDate(4, Date.valueOf(p.getFechaFin()));
            ps.setString(5, p.getEstado());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        p.setIdPromocion(rs.getLong(1));
                    }
                }
            }
        }
        return p.getIdPromocion();
    }


    public void actualizar(Promocion p) throws SQLException {
        String sql = "UPDATE Promocion SET nombre=?, descripcion=?, fechaInicio=?, fechaFin=?, estado=? WHERE idPromocion=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDate(3, Date.valueOf(p.getFechaInicio()));
            ps.setDate(4, Date.valueOf(p.getFechaFin()));
            ps.setString(5, p.getEstado());
            ps.setLong(6, p.getIdPromocion());
            ps.executeUpdate();
        }
    }


    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Promocion WHERE idPromocion=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Promocion buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Promocion WHERE idPromocion = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Promocion p = new Promocion();
                p.setIdPromocion(rs.getLong("idPromocion"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setFechaInicio(rs.getDate("fechaInicio").toLocalDate());
                p.setFechaFin(rs.getDate("fechaFin").toLocalDate());
                p.setEstado(rs.getString("estado"));
                return p;
            }
            return null;
        }
    }


    public List<Promocion> listarTodos() throws SQLException {
        List<Promocion> list = new ArrayList<>();
        String sql = "SELECT * FROM Promocion ORDER BY fechaInicio DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Promocion p = new Promocion();
                p.setIdPromocion(rs.getLong("idPromocion"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setFechaInicio(rs.getDate("fechaInicio").toLocalDate());
                p.setFechaFin(rs.getDate("fechaFin").toLocalDate());
                p.setEstado(rs.getString("estado"));
                list.add(p);
            }
        }
        return list;
    }

    public List<Promocion> listarActivas() throws SQLException {
        List<Promocion> list = new ArrayList<>();
        String sql = "SELECT * FROM Promocion WHERE estado='ACTIVA' AND CURRENT_DATE BETWEEN fechaInicio AND fechaFin";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Promocion p = new Promocion();
                p.setIdPromocion(rs.getLong("idPromocion"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setFechaInicio(rs.getDate("fechaInicio").toLocalDate());
                p.setFechaFin(rs.getDate("fechaFin").toLocalDate());
                p.setEstado(rs.getString("estado"));
                list.add(p);
            }
        }
        return list;
    }
}
