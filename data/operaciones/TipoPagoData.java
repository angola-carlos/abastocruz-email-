package data.operaciones;

import model.TipoPago;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoPagoData {
    private Connection conn;


    public TipoPagoData(Connection conn) {
        this.conn = conn;
    }

    public long insertar(TipoPago t) throws SQLException {
        String sql = "INSERT INTO TipoPago(nombre) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getNombre());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        t.setIdTipoPago(rs.getLong(1));
                    }
                }
            }
        }
        return t.getIdTipoPago();
    }

    public TipoPago buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM TipoPago WHERE idTipoPago=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TipoPago t = new TipoPago();
                t.setIdTipoPago(rs.getLong("idTipoPago"));
                t.setNombre(rs.getString("nombre"));
                return t;
            }
            return null;
        }
    }


    public List<TipoPago> listarTodos() throws SQLException {
        List<TipoPago> list = new ArrayList<>();
        String sql = "SELECT * FROM TipoPago ORDER BY nombre";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TipoPago t = new TipoPago();
                t.setIdTipoPago(rs.getLong("idTipoPago"));
                t.setNombre(rs.getString("nombre"));
                list.add(t);
            }
        }
        return list;
    }
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM TipoPago WHERE idTipoPago=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

}
