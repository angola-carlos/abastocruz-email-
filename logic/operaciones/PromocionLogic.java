package logic.operaciones;

import data.operaciones.DetallePromocionData;
import data.inventario.ProductoData;
import data.operaciones.PromocionData;
import model.DetallePromocion;
import model.Promocion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PromocionLogic {
    private PromocionData promocionData;
    private DetallePromocionData detallePromocionData;
    private ProductoData productoData;

    public PromocionLogic(Connection connection) {
        this.promocionData = new PromocionData(connection);
        this.detallePromocionData = new DetallePromocionData(connection);
        this.productoData = new ProductoData(connection);
    }

    public Promocion registrarPromocion(Promocion promocion) throws SQLException {
        long idGenerado = promocionData.insertar(promocion);
        promocion.setIdPromocion(idGenerado);

        for (DetallePromocion detalle : promocion.getDetallePromocion()) {
            detalle.setIdPromocion(idGenerado);
            detallePromocionData.insertar(detalle);
        }

        return promocion;
    }

    public Promocion buscarPorId(int idPromocion) throws SQLException {
        Promocion promocion = promocionData.buscarPorId(idPromocion);

        if (promocion == null) {
            return null;
        }
        List<DetallePromocion> detalles = detallePromocionData.listarPorIdPromocion(idPromocion);

        promocion.setDetallePromocion(detalles);

        return promocion;
    }

    public List<Promocion> listarTodos() throws SQLException {
        return promocionData.listarTodos();
    }
}
