package data.operaciones;

import model.Pago;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoData {
    private final Connection conn;


    public PagoData(Connection conn) {
        this.conn = conn;
    }

    public long insertar(Pago pago) throws SQLException {
        String sql = "INSERT INTO Pago(fechaPago, montoPago, idVenta, idTipoPago) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, Timestamp.valueOf(pago.getFechaPago()));
            ps.setBigDecimal(2, pago.getMontoPago());
            ps.setLong(3, pago.getIdVenta());
            ps.setLong(4, pago.getIdTipoPago());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        pago.setIdPago(rs.getLong(1));
                    }
                }
            }
        }
        return pago.getIdPago();
    }
    
    public List<Pago> buscarPorVenta(int idVenta) throws SQLException {
        List<Pago> list = new ArrayList<>();
        String sql = "SELECT * FROM Pago WHERE idVenta=? ORDER BY fechaPago DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pago pago = new Pago();
                pago.setIdPago(rs.getLong("idPago"));
                pago.setFechaPago(rs.getTimestamp("fechaPago").toLocalDateTime());
                pago.setMontoPago(rs.getBigDecimal("montoPago"));
                pago.setIdVenta(rs.getLong("idVenta"));
                pago.setIdTipoPago(rs.getLong("idTipoPago"));
                list.add(pago);
            }
        }
        return list;
    }
    
    public Pago buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Pago WHERE idPago=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Pago pago = new Pago();
                pago.setIdPago(rs.getLong("idPago"));
                pago.setFechaPago(rs.getTimestamp("fechaPago").toLocalDateTime());
                pago.setMontoPago(rs.getBigDecimal("montoPago"));
                pago.setIdVenta(rs.getLong("idVenta"));
                pago.setIdTipoPago(rs.getLong("idTipoPago"));
                return pago;
            }
            return null;
        }
    }
}
