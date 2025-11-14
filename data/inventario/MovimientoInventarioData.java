package data.inventario;

import model.MovimientoInventario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimientoInventarioData {
    private Connection connection;


    public MovimientoInventarioData(Connection connection) {
        this.connection = connection;
    }

    public long insertar(MovimientoInventario m) throws SQLException {
        String sql = "INSERT INTO MovimientoInventario(fechaMovimiento, tipoMovimiento, cantidad, idProducto) VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, Timestamp.valueOf(m.getFechaMovimiento()));
            ps.setString(2, m.getTipoMovimiento());
            ps.setInt(3, m.getCantidad());
            ps.setLong(4, m.getIdProducto());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        m.setIdMovimiento(rs.getLong(1));
                    }
                }
            }
        }
        return m.getIdMovimiento();
    }

    public MovimientoInventario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM MovimientoInventario WHERE idMovimiento=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                MovimientoInventario m = new MovimientoInventario();
                m.setIdMovimiento(rs.getLong("idMovimiento"));
                m.setFechaMovimiento(rs.getTimestamp("fechaMovimiento").toLocalDateTime());
                m.setTipoMovimiento(rs.getString("tipoMovimiento"));
                m.setCantidad(rs.getInt("cantidad"));
                m.setIdProducto(rs.getLong("idProducto"));
                return m;
            }
            return null;
        }
    }


    public List<MovimientoInventario> buscarPorProducto(int idProducto) throws SQLException {
        List<MovimientoInventario> list = new ArrayList<>();
        String sql = "SELECT * FROM MovimientoInventario WHERE idProducto=? ORDER BY fechaMovimiento DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MovimientoInventario m = new MovimientoInventario();
                m.setIdMovimiento(rs.getLong("idMovimiento"));
                m.setFechaMovimiento(rs.getTimestamp("fechaMovimiento").toLocalDateTime());
                m.setTipoMovimiento(rs.getString("tipoMovimiento"));
                m.setCantidad(rs.getInt("cantidad"));
                m.setIdProducto(rs.getLong("idProducto"));
                list.add(m);
            }
        }
        return list;
    }



}
