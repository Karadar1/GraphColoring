package graphfx.graphcoloring.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class FileController {

    private void onNodeClick(){
        System.out.println("onNodeClick");
    }

    @FXML
    private void handleOpenFile(ActionEvent event) {
        try {
            // Set up the file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");

            // Show file chooser dialog in the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            File file = fileChooser.showOpenDialog(currentStage);

            // Process the file if selected
            if (file != null) {
                // Initialize Graph and read from file
                Graph graph = new Graph();
                Input inputController = new Input();
                inputController.readFileAndBuildGraph(file, graph);

                // Print adjacency list for debugging
                graph.print();

                // Load the FXML file and set up the controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
                Parent root = loader.load();

                // Set up the new scene
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.show();

                // Pass the graph to GameController for initial drawing
                GameController gameController = loader.getController();
                gameController.drawInitialGraph(graph);
                gameController.setGraph(graph);
                System.out.println(GraphColoring.findChromaticNumberParallel(graph.getAdjacencyList()));
            }
        } catch (IOException e) {
            // Handle any I/O errors that occur (e.g., file loading or FXML errors)
            e.printStackTrace();
            showErrorAlert("Error", "Unable to load file or scene. Please try again.");
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Helper method to display an error alert
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
