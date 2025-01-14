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

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/graphdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Tutuguli123zxc";

    @FXML
    public void handleLogin(javafx.event.ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        if (authenticate(username, password)) {
            try {
                // Load main.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
                Parent root = loader.load();

                // Get the current stage
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene
                stage.setScene(new Scene(root));
                stage.setTitle("Main Menu");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                errorLabel.setText("Error loading main screen.");
            }
        } else {
            errorLabel.setText("Invalid username or password.");
        }
    }

    private boolean authenticate(String username, String password) {
        String query = "SELECT id, username FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Store user ID and username in the session
                    Session.getInstance().setUserId(resultSet.getInt("id"));
                    Session.getInstance().setUsername(resultSet.getString("username"));
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("Database connection error.");
        }
        return false;
    }
}