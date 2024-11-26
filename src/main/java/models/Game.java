//Class A

package models;


import exceptions.InvalidColoringError;
import interfaces.Printable;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class Game implements Printable {
    String startString;
    private Color selectedColor;
    private ArrayList<Color> usedColors;

    public Game(String startString ) {
        this.startString = startString;
        this.usedColors = new ArrayList<Color>(Arrays.asList(
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW,
                Color.MAGENTA

        ));
    }

    public void drawColorCircles(VBox colorsBox) {

        for (Color color : this.usedColors) {
            Circle circle = new Circle(30, color);  // Circle with radius 30 and specific color
            circle.setStroke(Color.BLACK);          // Optional: add a black border
            circle.setStrokeWidth(2);
            circle.setOnMouseClicked(event -> {
                selectedColor = color;  // Store the color of the clicked circle
                System.out.println("Selected color: " + selectedColor);  // Display selected color in console
                highlightSelectedCircle(circle,colorsBox);  // Optional: highlight the selected circle
            });
            // Add circle to VBox
            colorsBox.getChildren().add(circle);

        }
    }
    private void highlightSelectedCircle(Circle selectedCircle, VBox colorsBox) {
        // Reset stroke width for all circles in colorsBox
        for (var node : colorsBox.getChildren()) {
            if (node instanceof Circle) {
                ((Circle) node).setStrokeWidth(2);  // Reset to default width
            }
        }
        // Increase stroke width for the selected circle
        selectedCircle.setStrokeWidth(4);
    }

    public boolean validMove(Graph graph, Node node, Color color) throws InvalidColoringError {
        int nodePosition = node.getId();
        boolean validColor = true;
        Set<Integer> neighbours = graph.getAdjacencyList().get(nodePosition);

        for(int neighbour : neighbours) {
            if(graph.getNodes().get(neighbour).getColor() == color) {
                validColor = false;
                throw new InvalidColoringError("Invalid coloring since node "+ neighbour + " and " + nodePosition+ " can't have the same collor"  );
            }
        }
        return true;
    }

    public void checkGraphFullyColored(Graph graph) {
        boolean isFullyColored = true;

        // Check if all nodes in the graph are colored
        for (Node node : graph.getNodes().values()) {
            if (node.getColor() == null || node.getColor().equals(Color.WHITE)) {
                isFullyColored = false; // Found an uncolored node
                break;
            }
        }

        // If the graph is fully colored, show a success message
        if (isFullyColored) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Graph Fully Colored");
            alert.setHeaderText(null);
            alert.setContentText("Congratulations! The graph is fully colored.");
            alert.showAndWait();
        }
    }

    public void setEventClick(Graph graph){
        for (Map.Entry<Integer, Node> entry : graph.getNodes().entrySet()){
            Node node = entry.getValue();
            node.getCircle().setOnMouseClicked(mouseEvent -> {
                try {
                    if(validMove(graph, node, selectedColor)){
                        node.getCircle().setFill(selectedColor);
                        checkGraphFullyColored(graph);
                    }
                } catch (InvalidColoringError e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    // Getter method for the selected color
    public Color getSelectedColor() {
        return selectedColor;
    }

    @Override
    public void print() {
        System.out.println(startString);
    }
}
