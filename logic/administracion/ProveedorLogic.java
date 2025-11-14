package logic.administracion;

import data.administracion.ProveedorData;
import model.Proveedor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProveedorLogic {
    private ProveedorData proveedorData;

    public ProveedorLogic(Connection connection) {
        this.proveedorData = new ProveedorData(connection);
    }

    public long registrarProveedor(Proveedor proveedor) throws SQLException {
        return proveedorData.crear(proveedor);
    }


    public boolean actualizarProveedor(Proveedor proveedor) throws SQLException {
        return proveedorData.actualizar(proveedor);
    }


    public boolean eliminarProveedor(int idProveedor) throws SQLException {
        return proveedorData.eliminar(idProveedor);
    }


    public Proveedor obtenerPorId(int idProveedor) throws SQLException {
        return proveedorData.buscarPorId(idProveedor);
    }


    public List<Proveedor> listarProveedores() throws SQLException {
        return proveedorData.listarTodos();
    }


}
