package data.operaciones;

import model.CuotaCredito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuotaCreditoData {
    private Connection connection;


    public CuotaCreditoData(Connection connection) {
        this.connection = connection;
    }


    public int insertar(CuotaCredito c) throws SQLException {
        String sql = "INSERT INTO CuotaCredito (fechaCuota, montoCuota, esMora, idCredito) VALUES (?, ?, ?, ?) RETURNING idCuota";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(c.getFechaCuota()));
        ps.setBigDecimal(2, c.getMontoCuota());
        ps.setString(3, c.getEsMora());
        ps.setLong(4, c.getIdCredito());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1);
        return 0;
    }

    public List<CuotaCredito> listarPorCredito(int idCredito) throws SQLException {
        String sql = "SELECT * FROM CuotaCredito WHERE idCredito=? ORDER BY idCuota";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idCredito);
        ResultSet rs = ps.executeQuery();
        List<CuotaCredito> lista = new ArrayList<>();
        while (rs.next()) {
            CuotaCredito c = new CuotaCredito();
            c.setIdCuota(rs.getLong("idCuota"));
            c.setFechaCuota(rs.getTimestamp("fechaCuota").toLocalDateTime());
            c.setMontoCuota(rs.getBigDecimal("montoCuota"));
            c.setEsMora(rs.getString("esMora"));
            c.setIdCredito(rs.getLong("idCredito"));
            lista.add(c);
        }
        return lista;
    }
}
