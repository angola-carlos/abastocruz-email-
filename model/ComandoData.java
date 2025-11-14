package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComandoData {
    private Connection connection;

    public ComandoData(Connection connection) {
        this.connection = connection;
    }

    public List<Comando> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Comando";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Comando> lista = new ArrayList<>();
        while (rs.next()) {
            Comando comando = new Comando();
            comando.setIdComando(rs.getLong("idComando"));
            comando.setComando(rs.getString("comando"));
            comando.setDescripcion(rs.getString("descripcion"));
            comando.setFormato(rs.getString("formato"));
            lista.add(comando);
        }
        return lista;
    }
}
