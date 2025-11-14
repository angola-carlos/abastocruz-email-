package logic.inventario;

import data.inventario.ProductoData;
import model.Producto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductoLogic {
    private ProductoData productoData;


    public ProductoLogic(Connection connection) {
        this.productoData = new ProductoData(connection);
    }


    public long registrarProducto(Producto p) throws SQLException {
        return productoData.insertar(p);
    }


    public boolean actualizarProducto(Producto p) throws SQLException {
        return productoData.actualizar(p);
    }


    public boolean eliminarProducto(int idProducto) throws SQLException {
        return productoData.eliminar(idProducto);
    }


    public Producto obtenerPorId(int idProducto) throws SQLException {
        return productoData.buscarPorId(idProducto);
    }


    public List<Producto> listarTodos() throws SQLException {
        return productoData.listarTodos();
    }


    public List<Producto> listarPorCategoria(int idCategoria) throws SQLException {
        return productoData.listarPorCategoria(idCategoria);
    }
}
