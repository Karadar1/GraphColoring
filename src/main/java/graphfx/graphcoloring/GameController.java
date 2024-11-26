package graphfx.graphcoloring;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Game;
import models.Graph;

import java.awt.*;
import java.util.Arrays;

public class GameController {
    @FXML
    public Label errorLabel;
    @FXML
    Pane anchorPane;
    @FXML
    private VBox colorsBox;

    Game game = new Game("Start it");
    public void showError(String errorMessage) {
        errorLabel.setText(errorMessage); // Set the error message text
    }


    public void drawInitialGraph(Graph graph) {
        graph.initializeNodes();
        graph.initializeEdges();
        graph.drawGraph(anchorPane);
        System.out.println("Graph drawn");
        game.drawColorCircles(colorsBox);
        game.setEventClick(graph);
    }



}

