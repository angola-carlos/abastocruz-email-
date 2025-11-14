package controller;

import model.Comando;
import model.ComandoLogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ComandoController {
    private ComandoLogic comandoLogic;

    public ComandoController(Connection connection){
        this.comandoLogic = new ComandoLogic(connection);
    }

    public String listarTodos() throws SQLException {
        try {
            List<Comando> comandos = comandoLogic.listarTodos();

            if (comandos.isEmpty()) {
                return "No se encontraron comandos registradas.";
            }

            StringBuilder sb = new StringBuilder("Lista de comandos:\n");
            for (Comando c : comandos) {
                sb.append("ID=").append(c.getIdComando())
                        .append(", Comando=").append(c.getComando())
                        .append(", Descripcion=").append(c.getDescripcion())
                        .append(", Formato=").append(c.getFormato()).append("\n");
            }
            return sb.toString();
        } catch (SQLException e) {
            return "Error SQL al listar los comandos: " + e.getMessage();
        }
    }
}
