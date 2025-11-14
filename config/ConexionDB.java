package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    /*private String url = "jdbc:postgresql://localhost:5432/db_abastocruz";
    private String usuario = "postgres";
    private String pass = "";*/
    private String url = "jdbc:postgresql://www.tecnoweb.org.bo/db_grupo18sa";
    private String usuario = "grupo18sa";
    private String pass = "grup018grup018*";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, usuario, pass);
    }
}
