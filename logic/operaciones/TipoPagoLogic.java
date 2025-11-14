package logic.operaciones;

import data.operaciones.TipoPagoData;
import model.TipoPago;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TipoPagoLogic {
    private TipoPagoData tipoPago;

    public TipoPagoLogic(Connection connection) {
        this.tipoPago =  new TipoPagoData(connection);
    }

    public long registrarTipoPago(TipoPago pago) throws SQLException {
        return tipoPago.insertar(pago);
    }

    public TipoPago buscarPorId(int idCliente) throws SQLException {
        return tipoPago.buscarPorId(idCliente);
    }


    public List<TipoPago> listarTodos() throws SQLException {
        return tipoPago.listarTodos();
    }
}
