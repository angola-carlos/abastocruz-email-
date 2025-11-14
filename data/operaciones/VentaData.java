package data.operaciones;

import model.Venta;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class VentaData {
    private Connection connection;

    public VentaData(Connection connection){
        this.connection = connection;
    }

    public long insertar(Venta venta) throws SQLException {
        // Validación básica: si fechaVenta es null, asigna ahora (o lanza excepción si prefieres)
        LocalDateTime fecha = venta.getFechaVenta();
        if (fecha == null) {
            fecha = LocalDateTime.now();  // Valor por defecto para evitar null
            venta.setFechaVenta(fecha);    // Opcional: actualiza el objeto Venta
        }

        String sql = "INSERT INTO Venta (fechaVenta, totalVenta, impuesto, descuentoAplicado, estadoVenta, idCliente, idUsuario) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setTimestamp(1, Timestamp.valueOf(fecha));  // Ahora usa 'fecha' en lugar de venta.getFechaVenta()
        ps.setBigDecimal(2, venta.getTotalVenta());
        ps.setBigDecimal(3, venta.getImpuesto());
        ps.setBigDecimal(4, venta.getDescuentoAplicado());
        ps.setString(5, venta.getEstadoVenta());
        ps.setLong(6, venta.getIdCliente());
        ps.setLong(7, venta.getIdUsuario());
        int filasAfectadas = ps.executeUpdate();
        if (filasAfectadas > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    venta.setIdVenta(rs.getLong(1));
                }
            }
        }
        return venta.getIdVenta();
    }

    public Venta buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Venta WHERE idVenta = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();


        if (rs.next()) {
            Venta venta = new Venta();
            venta.setIdVenta(rs.getLong("idVenta"));
            venta.setFechaVenta(rs.getTimestamp("fechaVenta").toLocalDateTime());
            venta.setTotalVenta(rs.getBigDecimal("totalVenta"));
            venta.setImpuesto(rs.getBigDecimal("impuesto"));
            venta.setDescuentoAplicado(rs.getBigDecimal("descuentoAplicado"));
            venta.setEstadoVenta(rs.getString("estadoVenta"));
            venta.setIdCliente(rs.getLong("idCliente"));
            venta.setIdUsuario(rs.getLong("idUsuario"));
            return venta;
        }
        return null;
    }


    public List<Venta> listarTodos() throws SQLException {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM Venta";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);


        while (rs.next()) {
            Venta venta = new Venta();
            venta.setIdVenta(rs.getLong("idVenta"));
            venta.setFechaVenta(rs.getTimestamp("fechaVenta").toLocalDateTime());
            venta.setTotalVenta(rs.getBigDecimal("totalVenta"));
            venta.setImpuesto(rs.getBigDecimal("impuesto"));
            venta.setDescuentoAplicado(rs.getBigDecimal("descuentoAplicado"));
            venta.setEstadoVenta(rs.getString("estadoVenta"));
            venta.setIdCliente(rs.getLong("idCliente"));
            venta.setIdUsuario(rs.getLong("idUsuario"));
            lista.add(venta);
        }
        return lista;
    }

    public void actualizarTotalVenta(long idVenta, BigDecimal totalVenta) throws SQLException {
        String sql = "UPDATE venta SET totalventa = ? WHERE idventa = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBigDecimal(1, totalVenta);
            ps.setLong(2, idVenta);
            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException("No se pudo actualizar el total de la venta con ID: " + idVenta);
            }
        }
    }
}
