package data.operaciones;

import model.DetalleVenta;

import java.sql.*;
import java.util.*;

public class DetalleVentaData {
    private Connection connection;


    public DetalleVentaData(Connection connection) {
        this.connection = connection;
    }

    public void insertar(DetalleVenta d) throws SQLException {
        String sql = "INSERT INTO DetalleVenta (idVenta, idProducto, cantidad, precioUnitario, subTotal, descuento) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1, d.getIdVenta());
            ps.setLong(2, d.getIdProducto());
            ps.setLong(3, d.getCantidad());
            ps.setBigDecimal(4, d.getPrecioUnitario());
            ps.setBigDecimal(5, d.getSubTotal());
            ps.setBigDecimal(6, d.getDescuento());
            ps.executeUpdate();
        }

    }


    public List<DetalleVenta> listarPorVenta(int idVenta) throws SQLException {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM DetalleVenta WHERE idVenta = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idVenta);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            DetalleVenta d = new DetalleVenta();
            d.setIdVenta(rs.getLong("idVenta"));
            d.setIdProducto(rs.getLong("idProducto"));
            d.setCantidad(rs.getInt("cantidad"));
            d.setPrecioUnitario(rs.getBigDecimal("precioUnitario"));
            d.setSubTotal(rs.getBigDecimal("subTotal"));
            d.setDescuento(rs.getBigDecimal("descuento"));
            lista.add(d);
        }
        return lista;
    }
}
