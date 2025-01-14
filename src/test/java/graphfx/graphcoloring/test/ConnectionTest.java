package graphfx.graphcoloring.test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn != null) {
                System.out.println("Connected to MySQL database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}