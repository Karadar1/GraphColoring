package graphfx.graphcoloring.test;

import exceptions.InvalidRandomGraphException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RandomController {
    Input input = new Input();

    @FXML
    public TextField nodeInput;
    public TextField edgesInput;





    public void handleStartClick(ActionEvent event) {
        try {
            // Validate the input for nodes
            String nodesText = nodeInput.getText();
            if (nodesText == null || nodesText.trim().isEmpty()) {
                throw new IllegalArgumentException("Node input cannot be empty.");
            }
            int nodes = Integer.parseInt(nodesText);
            if (nodes <= 0) {
                throw new IllegalArgumentException("Number of nodes must be greater than 0.");
            }

            // Validate the input for edges
            String edgesText = edgesInput.getText();
            if (edgesText == null || edgesText.trim().isEmpty()) {
                throw new IllegalArgumentException("Edge input cannot be empty.");
            }
            int edges = Integer.parseInt(edgesText);
            if (edges < 0) {
                throw new IllegalArgumentException("Number of edges cannot be negative.");
            }

            int maxEdges = nodes * (nodes - 1) / 2; // Maximum possible edges for a simple graph
            if (edges > maxEdges) {
                throw new InvalidRandomGraphException("Cannot have more than " + maxEdges + " edges for " + nodes + " nodes.");
            }

            // Generate the random graph
            Graph randomGraph = input.generateRandomGraph(nodes, edges);
            randomGraph.print();

            // Load the game screen
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            // Update the scene
            currentStage.setScene(scene);
            currentStage.show();

            // Pass the graph to the game controller
            GameController gameController = loader.getController();
            gameController.drawInitialGraph(randomGraph);
            gameController.setGraph(randomGraph);

            // Log the chromatic number
            System.out.println("Chromatic Number: " + GraphColoring.findChromaticNumberParallel(randomGraph.getAdjacencyList()));

        } catch (NumberFormatException e) {
            System.err.println("Invalid number format. Please enter valid integers for nodes and edges.");
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        } catch (InvalidRandomGraphException e) {
            System.err.println("Graph generation error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error loading the game screen: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
