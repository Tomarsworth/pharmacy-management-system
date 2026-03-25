package app.storage.jdbc;

import app.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionProvider{
    private JdbcConnectionProvider() {}

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(
                DatabaseConfig.URL,
                DatabaseConfig.USER,
                DatabaseConfig.PASSWORD
        );
    }

    /*
    public static void main(String[] args){
        try (Connection c = JdbcConnectionProvider.getConnection()) {
            System.out.println("Ок: " + !c.isClosed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    */

}
