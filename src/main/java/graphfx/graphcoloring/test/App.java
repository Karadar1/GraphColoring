package graphfx.graphcoloring.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Retrieve command-line arguments
        Parameters params = getParameters();
        String sceneToLoad = params.getRaw().isEmpty() ? "" : params.getRaw().get(0);

        // Initialize the scene based on the argument
        if ("random".equalsIgnoreCase(sceneToLoad)) {
            loadScene1(primaryStage);
        } else if ("file".equalsIgnoreCase(sceneToLoad)) {
            loadScene2(primaryStage);
        } else {
            System.out.println("Invalid scene argument. Loading default scene.");
            loadDefaultScene(primaryStage);
        }
    }

    private void loadScene1(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("random-graph.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Random Graph");
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            System.err.println("Failed to load Scene 1: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadScene2(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("file-graph.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("File Graph");
            stage.setFullScreen(true);
            stage.show();

        } catch (Exception e) {
            System.err.println("Failed to load Scene 2: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadDefaultScene(Stage stage) {
        try {
            // If you have a default FXML, load it here. Otherwise, a simple scene or alert can be shown.
            Parent root = FXMLLoader.load(getClass().getResource("first.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Default Scene");
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            System.err.println("Failed to load the default scene: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);// Pass command-line arguments here
    }
}
