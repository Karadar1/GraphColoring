package graphfx.graphcoloring.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ScoreDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/graphdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Tutuguli123zxc";

    public void insertScore(int userId, int score, int timeElapsed) {
        String query = "INSERT INTO scores (user_id, score, time_elapsed) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            statement.setInt(2, score);
            statement.setInt(3, timeElapsed);

            statement.executeUpdate();
            System.out.println("Score inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}