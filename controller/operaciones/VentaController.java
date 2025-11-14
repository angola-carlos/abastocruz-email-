package controller.operaciones;

import logic.operaciones.VentaLogic;
import model.Venta;
import model.DetalleVenta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VentaController {
    private VentaLogic ventaLogic;

    public VentaController(Connection connection){
        this.ventaLogic = new VentaLogic(connection);
    }

    public String registrarVenta(String[] parametros) throws SQLException {
        if (parametros.length < 4) {
            return "Parámetros insuficientes para REGISTRAR_VENTA. Se requiere: [idUsuario, idCliente, tipoVenta, DETALLE[...]]";
        }

        try {
            System.out.println("PARAMETROS VENTA: " + Arrays.toString(parametros));

            Venta venta = new Venta();
            venta.setIdUsuario(Long.parseLong(parametros[0].trim()));
            venta.setIdCliente(Long.parseLong(parametros[1].trim()));
            venta.setEstadoVenta(parametros[2].trim());

            // --- Extraer todo lo que empiece con DETALLE[
            StringBuilder detalleBuilder = new StringBuilder();
            for (int i = 3; i < parametros.length; i++) {
                detalleBuilder.append(parametros[i]).append(" ");
            }
            String detalleString = detalleBuilder.toString().trim();
            System.out.println("DETALLE STRING CRUDO: " + detalleString);

            List<DetalleVenta> detalles = parsearDetallesVenta(detalleString);

            if (detalles.isEmpty()) {
                return "No se especificaron detalles para la venta.";
            }

            venta.setDetalles(detalles);

            Venta ventaRegistrada = ventaLogic.registrarVenta(venta);

            return "Venta registrada con ID: " + ventaRegistrada.getIdVenta() +
                    " y " + detalles.size() + " detalle(s).";

        } catch (NumberFormatException e) {
            return "Error de formato en los datos numéricos (ID o cantidad).";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al registrar la venta: " + e.getMessage();
        }
    }

    private List<DetalleVenta> parsearDetallesVenta(String detalleString) throws NumberFormatException {
        List<DetalleVenta> detalles = new ArrayList<>();
        if (detalleString == null || detalleString.isEmpty()) {
            return detalles;
        }

        // Permite múltiples DETALLE[...];DETALLE[...] si algún día lo amplías
        Pattern patron = Pattern.compile("DETALLE\\[(.*?)\\]");
        Matcher matcher = patron.matcher(detalleString);

        while (matcher.find()) {
            String contenido = matcher.group(1).trim(); // ej: "5, 3"
            String[] partes = contenido.split(",");
            if (partes.length == 2) {
                DetalleVenta dv = new DetalleVenta();
                dv.setIdProducto(Long.parseLong(partes[0].trim()));
                dv.setCantidad(Integer.parseInt(partes[1].trim()));
                detalles.add(dv);
            }
        }

        System.out.println("DETALLES DE VENTA PARSEADOS: " + detalles.size());
        return detalles;
    }

    public String listarVentas ()throws SQLException{
        try{
            List<Venta> ventas = ventaLogic.listarTodo();
            if (ventas.isEmpty()){
                return "no se encontraron ventas";
            }

            StringBuilder sb = new StringBuilder("Lista de Ventas:\n");
            for (Venta v : ventas){
                sb.append("ID=").append(v.getIdVenta())
                        .append(", Fecha=").append(v.getFechaVenta())
                        .append(", Total=").append(v.getTotalVenta())
                        .append(", TipoVenta=").append(v.getEstadoVenta()).append("\n");
            }
            return sb.toString();

        } catch (SQLException e) {
            return "Error SQL al listar las promociones: " + e.getMessage();
        }

    }

}
