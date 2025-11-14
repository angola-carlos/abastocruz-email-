package data.operaciones;

import model.Credito;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreditoData {
    private final Connection connection;


    public CreditoData(Connection connection) {
        this.connection = connection;
    }


    public long insertar(Credito credito) throws SQLException {
        LocalDateTime fechaApertura = credito.getFechaApertura();
        if (fechaApertura == null) {
            fechaApertura = LocalDateTime.now();
            credito.setFechaApertura(fechaApertura);
        }

        LocalDate fechaVencimiento = credito.getFechaVencimiento();
        if (fechaVencimiento == null) {
            fechaVencimiento = LocalDate.now();
            credito.setFechaVencimiento(fechaVencimiento);
        }

        String sql = "INSERT INTO Credito (fechaApertura, montoTotalCredito, montoPagado, estadoCredito, fechaVencimiento, idVenta, idCliente) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING idCredito";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(fechaApertura));
        ps.setBigDecimal(2, credito.getMontoTotalCredito());
        ps.setBigDecimal(3, credito.getMontoPagado());
        ps.setString(4, credito.getEstadoCredito());
        ps.setDate(5, Date.valueOf(fechaVencimiento));
        ps.setLong(6, credito.getIdVenta());
        ps.setLong(7, credito.getIdCliente());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1);
        return 0;
    }



        public boolean actualizar(Credito credito) throws SQLException {
        String sql = "UPDATE Credito SET fechaApertura=?, montoTotalCredito=?, montoPagado=?, estadoCredito=?, fechaVencimiento=?, idVenta=?, idCliente=? WHERE idCredito=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(credito.getFechaApertura()));
        ps.setBigDecimal(2, credito.getMontoTotalCredito());
        ps.setBigDecimal(3, credito.getMontoPagado());
        ps.setString(4, credito.getEstadoCredito());
        ps.setDate(5, Date.valueOf(credito.getFechaVencimiento()));
        ps.setLong(6, credito.getIdVenta());
        ps.setLong(7, credito.getIdCliente());
        ps.setLong(8, credito.getIdCredito());
        return ps.executeUpdate() > 0;
    }

    public boolean eliminar(int idCredito) throws SQLException {
        String sql = "DELETE FROM Credito WHERE idCredito=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idCredito);
        return ps.executeUpdate() > 0;
    }


    public Credito buscarPorId(int idCredito) throws SQLException {
        String sql = "SELECT * FROM Credito WHERE idCredito=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idCredito);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return null;


        Credito credito = new Credito();
        credito.setIdCredito(rs.getLong("idCredito"));
        credito.setFechaApertura(rs.getTimestamp("fechaApertura").toLocalDateTime());
        credito.setMontoTotalCredito(rs.getBigDecimal("montoTotalCredito"));
        credito.setMontoPagado(rs.getBigDecimal("montoPagado"));
        credito.setEstadoCredito(rs.getString("estadoCredito"));
        credito.setFechaVencimiento(rs.getDate("fechaVencimiento").toLocalDate());
        credito.setIdVenta(rs.getLong("idVenta"));
        credito.setIdCliente(rs.getLong("idCliente"));
        return credito;
    }

    public List<Credito> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Credito ORDER BY idCredito";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Credito> lista = new ArrayList<>();
        while (rs.next()) {
            Credito credito = new Credito();
            credito.setIdCredito(rs.getLong("idCredito"));
            credito.setFechaApertura(rs.getTimestamp("fechaApertura").toLocalDateTime());
            credito.setMontoTotalCredito(rs.getBigDecimal("montoTotalCredito"));
            credito.setMontoPagado(rs.getBigDecimal("montoPagado"));
            credito.setEstadoCredito(rs.getString("estadoCredito"));
            credito.setFechaVencimiento(rs.getDate("fechaVencimiento").toLocalDate());
            credito.setIdVenta(rs.getLong("idVenta"));
            credito.setIdCliente(rs.getLong("idCliente"));
            lista.add(credito);
        }
        return lista;
    }
}
