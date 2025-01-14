package graphfx.graphcoloring.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/graphdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Tutuguli123zxc";

    @FXML
    public void handleRegister(javafx.event.ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Input validation
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        if (registerUser(username, password)) {
            try {
                // Load first.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("first.fxml"));
                Parent root = loader.load();

                // Get the current stage
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene
                stage.setScene(new Scene(root));
                stage.setTitle("Welcome");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                errorLabel.setText("Error loading welcome screen.");
            }
        } else {
            errorLabel.setText("Username already exists.");
        }
    }

    private boolean registerUser(String username, String password) {
        String queryCheck = "SELECT * FROM users WHERE username = ?";
        String queryInsert = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement checkStmt = connection.prepareStatement(queryCheck);
             PreparedStatement insertStmt = connection.prepareStatement(queryInsert)) {

            // Check if username already exists
            checkStmt.setString(1, username);
            try (ResultSet resultSet = checkStmt.executeQuery()) {
                if (resultSet.next()) {
                    return false; // Username exists
                }
            }

            // Insert the new user
            insertStmt.setString(1, username);
            insertStmt.setString(2, password); // Store passwords securely in production!
            insertStmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("Database connection error.");
            return false;
        }
    }
}
