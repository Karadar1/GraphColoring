package graphfx.graphcoloring.test;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.canvas.Canvas;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GameController {
    @FXML
    public Label errorLabel;
    @FXML
    Pane borderPane;
    @FXML
    private VBox colorsBox;
    @FXML
    private Label timerLabel;
    @FXML
    private Label movesLabel;
    @FXML
    private Canvas gameCanvas;

    private Graph gameGraph;

    private int movesMade = 0;
    private int secondsElapsed = 0; // Track elapsed time in seconds
    private Timeline timeline;

    Game game = new Game("Start it");
    public void showError(String errorMessage) {
        errorLabel.setText(errorMessage); // Set the error message text
    }

    public void initialize() {

        // Set callback for game completion
        game.setOnGameComplete(() -> {
            stopTimer(); // Stop the timer
            showGameCompleteAlert(); // Show the alert with score and time
            saveScore(Session.getInstance().getUserId(), calculateScore(), secondsElapsed);
        });
    }

    public void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsElapsed++;
            timerLabel.setText("Time: " + secondsElapsed + "s");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        timeline.play(); // Start the timer
    }

    public void stopTimer() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    public void saveScore(int userId, int score, int timeElapsed) {
        ScoreDAO scoreDAO = new ScoreDAO();
        scoreDAO.insertScore(userId, score, timeElapsed);
    }

    public void incrementMoves() {
        movesMade++;
        movesLabel.setText("Moves: " + movesMade);
    }

    public int calculateScore() {
        int baseScore = 1000;
        int timePenalty = secondsElapsed * 5;
        int movePenalty = movesMade * 10;

        return Math.max(baseScore - timePenalty - movePenalty, 0);
    }

    private void showGameCompleteAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Graph Fully Colored");
        alert.setHeaderText(null);
        alert.setContentText("Congratulations! The graph is fully colored.\n" +
                "Time: " + secondsElapsed + "s\n" +
                "Score: " + calculateScore());
        alert.showAndWait();
    }


    public void drawInitialGraph(Graph graph) {
        graph.initializeNodes(borderPane);
        graph.initializeEdges();
        graph.drawGraph(borderPane);
        System.out.println("Graph drawn");
        game.drawColorCircles(colorsBox);
        game.setEventClick(graph);
        startTimer();
    }
    public void setGraph(Graph graph) {
        this.gameGraph = graph;
    }


    private static final Map<Color, String> COLOR_NAMES = new HashMap<>();

    static {
        COLOR_NAMES.put(Color.RED, "Red");
        COLOR_NAMES.put(Color.GREEN, "Green");
        COLOR_NAMES.put(Color.BLUE, "Blue");
        COLOR_NAMES.put(Color.BLACK, "Black");
        COLOR_NAMES.put(Color.WHITE, "White");
        COLOR_NAMES.put(Color.YELLOW, "Yellow");
        COLOR_NAMES.put(Color.CYAN, "Cyan");
        COLOR_NAMES.put(Color.MAGENTA, "Magenta");
        // Add more colors if needed
    }

    private String getColorNameFromColor(Color color) {
        for (Map.Entry<Color, String> entry : COLOR_NAMES.entrySet()) {
            if (entry.getKey().equals(color)) {
                return entry.getValue(); // Return the name if a match is found
            }
        }
        return "Unknown Color"; // If no match is found
    }

    public void handleHint() {
        try {
            Node hintNode = findUncoloredNode(gameGraph);
            if (hintNode == null) {
                showAlert("Hint", "All nodes are already colored!");
                return;
            }

            Color suggestedColor = findValidColor(gameGraph, hintNode);
            if (suggestedColor != null) {
                String colorName = getColorNameFromColor(suggestedColor); // Directly get the color name
                showAlert("Hint", "You can color Node " + hintNode.getId() + " with " + colorName);
            } else {
                showAlert("Hint", "No valid color found for Node " + hintNode.getId());
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred while generating the hint.");
            e.printStackTrace();
        }
    }

    private Node findUncoloredNode(Graph graph) {
        for (Node node : graph.getNodes().values()) {
            if (node.getColor() == null || node.getColor().equals(Color.WHITE)) {
                return node; // Return the first uncolored node
            }
        }
        return null; // All nodes are colored
    }

    private Color findValidColor(Graph graph, Node node) {
        Set<Integer> neighbors = graph.getAdjacencyList().get(node.getId());
        Set<Color> usedColors = neighbors.stream()
                .map(neighborId -> graph.getNodes().get(neighborId).getColor())
                .filter(color -> color != null) // Exclude uncolored nodes
                .collect(Collectors.toSet());

        // Predefined list of available colors
        for (Color color : getAvailableColors()) {
            if (!usedColors.contains(color)) {
                return color; // Return the first valid color
            }
        }
        return null; // No valid color found
    }

    private Set<Color> getAvailableColors() {
        return Set.of(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //Handling level logic
    public void loadLevel(Level level) {
        // Load the graph data for the selected level
        this.gameGraph = loadGraphForLevel(level);

        // Display the graph on the game screen
        this.drawInitialGraph(gameGraph);
    }

    private Graph loadGraphForLevel(Level level) {
        Graph graph = new Graph();
        String query = "SELECT * FROM graph_data WHERE level_id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/graphdb", "root", "Tutuguli123zxc");
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, level.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int node1 = resultSet.getInt("node1");
                int node2 = resultSet.getInt("node2");

                graph.addNode(node1);
                graph.addNode(node2);
                graph.addEdge(node1, node2);
            }

            normalizeNodePositions(graph);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return graph;
    }

    private void normalizeNodePositions(Graph graph) {
        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;

        // Find the bounding box of the graph
        for (Node node : graph.getNodes().values()) {
            minX = Math.min(minX, node.getX());
            maxX = Math.max(maxX, node.getX());
            minY = Math.min(minY, node.getY());
            maxY = Math.max(maxY, node.getY());
        }

        double graphWidth = maxX - minX;
        double graphHeight = maxY - minY;

        // Calculate the offsets needed to center the graph
        double canvasCenterX = gameCanvas.getWidth() / 2;
        double canvasCenterY = gameCanvas.getHeight() / 2;

        double offsetX = canvasCenterX - (graphWidth / 2 + minX);
        double offsetY = canvasCenterY - (graphHeight / 2 + minY);

        // Apply the offsets to all nodes
        for (Node node : graph.getNodes().values()) {
            node.setX(node.getX() + offsetX);
            node.setY(node.getY() + offsetY);
        }
    }

}

