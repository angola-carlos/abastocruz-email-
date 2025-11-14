package logic.inventario;

import data.inventario.CategoriaData;
import model.Categoria;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CategoriaLogic {
    private CategoriaData categoriaData;


    public CategoriaLogic(Connection connection) {
        this.categoriaData = new CategoriaData(connection);
    }


    public long registrarCategoria(Categoria c) throws SQLException {
        return categoriaData.insertar(c);
    }


    public boolean actualizarCategoria(Categoria c) throws SQLException {
        return categoriaData.actualizar(c);
    }


    public boolean eliminarCategoria(int idCategoria) throws SQLException {
        return categoriaData.eliminar(idCategoria);
    }


    public Categoria buscarPorId(int idCategoria) throws SQLException {
        return categoriaData.buscarPorId(idCategoria);
    }


    public List<Categoria> listarTodos() throws SQLException {
        return categoriaData.listarTodos();
    }
}
