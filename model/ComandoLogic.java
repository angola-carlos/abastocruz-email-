package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ComandoLogic {
    private ComandoData comandoData;


    public ComandoLogic(Connection connection) {
        this.comandoData = new ComandoData(connection);
    }

    public List<Comando> listarTodos() throws SQLException {
        return comandoData.listarTodos();
    }
}
