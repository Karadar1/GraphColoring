package graphfx.graphcoloring.test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LeaderboardController {

    @FXML
    private TableView<LeaderboardEntry> leaderboardTable;

    @FXML
    private TableColumn<LeaderboardEntry, String> usernameColumn;

    @FXML
    private TableColumn<LeaderboardEntry, Integer> scoreColumn;

    @FXML
    private TableColumn<LeaderboardEntry, Integer> timeColumn;

    @FXML
    private TableColumn<LeaderboardEntry, String> dateColumn;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/graphdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Tutuguli123zxc";

    @FXML
    public void initialize() {
        // Set up table columns
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeElapsed"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Load data into the table
        leaderboardTable.setItems(getLeaderboardData());
    }
    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            // Load the previous scene (e.g., main.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(root));
            stage.setTitle("Main Menu");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<LeaderboardEntry> getLeaderboardData() {
        ObservableList<LeaderboardEntry> leaderboardData = FXCollections.observableArrayList();

        String query = "SELECT users.username, scores.score, scores.time_elapsed, scores.game_date " +
                "FROM scores " +
                "INNER JOIN users ON scores.user_id = users.id " +
                "ORDER BY scores.score DESC, scores.time_elapsed ASC LIMIT 10";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int score = resultSet.getInt("score");
                int timeElapsed = resultSet.getInt("time_elapsed");
                String date = resultSet.getString("game_date");

                leaderboardData.add(new LeaderboardEntry(username, score, timeElapsed, date));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return leaderboardData;
    }
}
