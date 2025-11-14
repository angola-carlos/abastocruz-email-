package logic.operaciones;

import data.administracion.ClienteData;
import data.administracion.UsuarioData;
import data.inventario.ProductoData;
import data.operaciones.*;
import data.inventario.MovimientoInventarioData;
import model.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class VentaLogic {
    private Connection connection;
    private UsuarioData usuarioData;
    private ClienteData clienteData;
    private VentaData ventaData;
    private ProductoData productoData;
    private DetalleVentaData detalleVentaData;
    private MovimientoInventarioData movimientoInventarioData;
    private PagoData pagoData;
    private CreditoData creditoData;
    private TipoPagoData tipoPagoData;

    public VentaLogic(Connection connection) {
        this.connection = connection;
        this.ventaData = new VentaData(connection);
        this.detalleVentaData = new DetalleVentaData(connection);
        this.usuarioData = new UsuarioData(connection);
        this.clienteData = new ClienteData(connection);
        this.productoData = new ProductoData(connection);
        this.pagoData = new PagoData(connection);
        this.creditoData = new CreditoData(connection);
        this.tipoPagoData = new TipoPagoData(connection);
    }

    public Venta registrarVenta(Venta venta) throws SQLException {
        try {
            connection.setAutoCommit(false);

            // 1. Validar existencia de usuario y cliente
            Usuario u = usuarioData.buscarPorId(venta.getIdUsuario());
            Cliente c = clienteData.buscarPorId(venta.getIdCliente());
            if (u == null || c == null) throw new SQLException("Usuario o cliente no existe.");

            // 2. Insertar cabecera
            long idVenta = ventaData.insertar(venta);
            venta.setIdVenta(idVenta);

            BigDecimal total = BigDecimal.ZERO;

            // 3. Procesar los detalles
            for (DetalleVenta d : venta.getDetalles()) {
                d.setIdVenta(venta.getIdVenta());
                Producto p = productoData.buscarPorId(d.getIdProducto());
                if (p == null) throw new SQLException("Producto no encontrado: " + d.getIdProducto());
                if (p.getStockActual() < d.getCantidad()) throw new SQLException("Stock insuficiente para " + p.getNombre());

                d.setPrecioUnitario(p.getPrecio());
                d.setSubTotal(p.getPrecio().multiply(BigDecimal.valueOf(d.getCantidad())));
                total = total.add(d.getSubTotal());

                detalleVentaData.insertar(d);
                productoData.actualizarStock(p.getIdProducto(), p.getStockActual() - d.getCantidad());
            }

            venta.setTotalVenta(total);
            ventaData.actualizarTotalVenta(idVenta, total);

            // 4. Registrar pago o crédito según tipo
            if (venta.getEstadoVenta().equalsIgnoreCase("EFECTIVO")) {
                long i = 1;
                Pago pago = new Pago();
                pago.setIdVenta(idVenta);
                pago.setMontoPago(total);
                pago.setFechaPago(LocalDate.now().atStartOfDay());
                pago.setIdTipoPago(i); // ejemplo: efectivo
                pagoData.insertar(pago);
            } else if (venta.getEstadoVenta().equalsIgnoreCase("CREDITO")) {
                Credito credito = new Credito();
                credito.setIdVenta(idVenta);
                credito.setIdCliente(venta.getIdCliente());
                credito.setMontoTotalCredito(total);
                credito.setMontoPagado(BigDecimal.ZERO);
                credito.setFechaApertura(LocalDate.now().atStartOfDay());
                credito.setEstadoCredito("PENDIENTE");
                creditoData.insertar(credito);
            }

            connection.commit();
            return venta;

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }



    private Long obtenerUltimoIdVenta() throws SQLException {
        String sql = "SELECT currval(pg_get_serial_sequence('venta','idVenta'))";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()) return rs.getLong(1);
        return null;
    }

    public List<Venta> listarTodo() throws SQLException {
        return ventaData.listarTodos();
    }
}
