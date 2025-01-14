package graphfx.graphcoloring.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminController {

    @FXML
    private TextField levelNameField;

    @FXML
    private TextArea levelDescriptionField;

    @FXML
    private ChoiceBox<String> difficultyChoiceBox;

    @FXML
    private Label fileLabel;

    private File selectedFile;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/graphdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Tutuguli123zxc";

    @FXML
    public void handleChooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Graph File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            fileLabel.setText(selectedFile.getName());
        } else {
            fileLabel.setText("No file selected");
        }
    }

    @FXML
    public void handleCreateLevel() {
        String levelName = levelNameField.getText();
        String levelDescription = levelDescriptionField.getText();
        String difficultyText = difficultyChoiceBox.getValue();

        if (levelName.isEmpty() || levelDescription.isEmpty() || difficultyText == null || selectedFile == null) {
            showAlert("Error", "Please fill in all fields and select a file.");
            return;
        }

        int difficulty = switch (difficultyText) {
            case "Easy" -> 1;
            case "Medium" -> 2;
            case "Hard" -> 3;
            default -> 0;
        };

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Insert level information
            String insertLevelQuery = "INSERT INTO levels (name, description, difficulty, created_by) VALUES (?, ?, ?, ?)";
            PreparedStatement levelStatement = connection.prepareStatement(insertLevelQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            levelStatement.setString(1, levelName);
            levelStatement.setString(2, levelDescription);
            levelStatement.setInt(3, difficulty);
            levelStatement.setInt(4, Session.getInstance().getUserId());
            levelStatement.executeUpdate();

            // Get the generated level ID
            int levelId;
            try (var generatedKeys = levelStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    levelId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve level ID.");
                }
            }

            // Insert graph data
            if (!insertGraphData(levelId, connection)) {
                throw new SQLException("Failed to insert graph data.");
            }

            showAlert("Success", "Level created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to create level: " + e.getMessage());
        }
    }

    private boolean insertGraphData(int levelId, Connection connection) {
        String insertGraphQuery = "INSERT INTO graph_data (level_id, node1, node2) VALUES (?, ?, ?)";

        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
             PreparedStatement graphStatement = connection.prepareStatement(insertGraphQuery)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length != 2) continue;

                int node1 = Integer.parseInt(parts[0]);
                int node2 = Integer.parseInt(parts[1]);

                graphStatement.setInt(1, levelId);
                graphStatement.setInt(2, node1);
                graphStatement.setInt(3, node2);
                graphStatement.addBatch();
            }

            graphStatement.executeBatch();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handleBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) levelNameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

