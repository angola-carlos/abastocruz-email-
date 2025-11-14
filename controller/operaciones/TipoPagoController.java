package controller.operaciones;

import logic.operaciones.TipoPagoLogic;
import model.TipoPago;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TipoPagoController {
    private TipoPagoLogic tipoPagoLogic;

    public TipoPagoController(Connection connection){
        this.tipoPagoLogic = new TipoPagoLogic(connection);
    }

    public String registrarTipoPago(String[] parametros) throws SQLException {
        if (parametros.length < 1) return "Parámetros insuficientes para REGISTRAR_TIPOPAGO";
        try {
            TipoPago tipoPago = new TipoPago();

            tipoPago.setNombre(parametros[0].replace("\"", "").trim());

            long idGenerado = tipoPagoLogic.registrarTipoPago(tipoPago);
            return "Tipo pago registrado exitosamente con ID: " + idGenerado;

        } catch (SQLException e) {
            return "Error SQL al registrar el producto: " + e.getMessage();
        }
    }

    public String listarTodos() throws SQLException {
        try {
            List<TipoPago> tipoPago = tipoPagoLogic.listarTodos();

            if (tipoPago.isEmpty()) {
                return "No se encontraron productos registrados.";
            }

            StringBuilder sb = new StringBuilder("Lista de Tipo Pago:\n");
            for (TipoPago p : tipoPago) {
                sb.append("ID=").append(p.getIdTipoPago())
                        .append(", Stock=").append(p.getNombre()).append("\n");
            }
            return sb.toString();
        } catch (SQLException e) {
            return "Error SQL al listar los productos: " + e.getMessage();
        }
    }
}
