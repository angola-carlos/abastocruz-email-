package data.operaciones;

import model.DetallePromocion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetallePromocionData {
    private Connection conn;


    public DetallePromocionData(Connection conn) {
        this.conn = conn;
    }

    public List<DetallePromocion> buscarPorPromocion(int idPromocion) throws SQLException {
        List<DetallePromocion> list = new ArrayList<>();
        String sql = "SELECT * FROM DetallePromocion WHERE idPromocion=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPromocion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetallePromocion dp = new DetallePromocion();
                dp.setIdPromocion(rs.getLong("idPromocion"));
                dp.setIdProducto(rs.getLong("idProducto"));
                dp.setTipoDescuento(rs.getString("tipoDescuento"));
                dp.setValorDescuento(rs.getBigDecimal("valorDescuento"));
                list.add(dp);
            }
        }
        return list;
    }

    public List<DetallePromocion> buscarPorProduco(int idProducto) throws SQLException {
        List<DetallePromocion> list = new ArrayList<>();
        String sql = "SELECT * FROM DetallePromocion WHERE idProducto=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetallePromocion dp = new DetallePromocion();
                dp.setIdPromocion(rs.getLong("idPromocion"));
                dp.setIdProducto(rs.getLong("idProducto"));
                dp.setTipoDescuento(rs.getString("tipoDescuento"));
                dp.setValorDescuento(rs.getBigDecimal("valorDescuento"));
                list.add(dp);
            }
        }
        return list;
    }

    public void insertar(DetallePromocion dp) throws SQLException {
        String sql = "INSERT INTO DetallePromocion(idPromocion, idProducto, tipoDescuento, valorDescuento) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, dp.getIdPromocion());
            ps.setLong(2, dp.getIdProducto());
            ps.setString(3, dp.getTipoDescuento());
            ps.setBigDecimal(4, dp.getValorDescuento());
            ps.executeUpdate();
        }
    }


    public void eliminar(int idPromocion, int idProducto) throws SQLException {
        String sql = "DELETE FROM DetallePromocion WHERE idPromocion=? AND idProducto=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPromocion);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        }
    }

    public List<DetallePromocion> listarPorIdPromocion(int idPromocion) throws SQLException {
        List<DetallePromocion> detalles = new ArrayList<>();
        String sql = "SELECT idpromocion, idproducto, tipodescuento, valordescuento FROM detallepromocion WHERE idpromocion = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPromocion);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DetallePromocion dp = new DetallePromocion();
                    dp.setIdPromocion(rs.getLong("idpromocion"));
                    dp.setIdProducto(rs.getLong("idproducto"));
                    dp.setTipoDescuento(rs.getString("tipodescuento"));
                    dp.setValorDescuento(rs.getBigDecimal("valordescuento")); // Asumiendo BigDecimal para el valor
                    detalles.add(dp);
                }
            }
        } // Los recursos PreparedStatement y ResultSet se cierran automáticamente

        return detalles;
    }
}
