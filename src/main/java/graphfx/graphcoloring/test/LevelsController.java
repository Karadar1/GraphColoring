package graphfx.graphcoloring.test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LevelsController {

    @FXML
    private TableView<Level> levelsTable;

    @FXML
    private TableColumn<Level, String> nameColumn;

    @FXML
    private TableColumn<Level, String> descriptionColumn;

    @FXML
    private TableColumn<Level, Integer> difficultyColumn;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/graphdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Tutuguli123zxc";

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

        loadLevels();

        levelsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click
                Level selectedLevel = levelsTable.getSelectionModel().getSelectedItem();
                if (selectedLevel != null) {
                    openGameForLevel(selectedLevel);
                }
            }
        });
    }

    private void loadLevels() {
        ObservableList<Level> levels = FXCollections.observableArrayList();

        String query = "SELECT * FROM levels";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                levels.add(new Level(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("difficulty"),
                        resultSet.getInt("created_by")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        levelsTable.setItems(levels);
    }

    private void openGameForLevel(Level level) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
            Parent root = loader.load();

            GameController gameController = loader.getController();
            gameController.loadLevel(level); // Pass the selected level to the game

            Stage stage = (Stage) levelsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Graph Coloring - " + level.getName());
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load the game screen.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}