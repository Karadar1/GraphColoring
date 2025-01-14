package graphfx.graphcoloring.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/graphdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Tutuguli123zxc"; // Replace with your MySQL root password

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}