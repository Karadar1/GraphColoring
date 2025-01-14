package graphfx.graphcoloring.test;

import exceptions.InvalidColoringError;
import graphfx.graphcoloring.test.Game;
import graphfx.graphcoloring.test.Graph;
import graphfx.graphcoloring.test.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    public void testGameConstructor() {
        // Create a Game instance with a sample start string
        String startString = "Welcome to the Graph Coloring Game!";
        Game game = new Game(startString);

        // Verify the start string is set correctly
        assertEquals(startString, game.getStartString(), "Start string should be correctly initialized.");

        // Verify that the list of used colors is initialized and contains the default colors
        assertNotNull(game.getUsedColors(), "Used colors list should not be null.");
        assertTrue(game.getUsedColors().contains(javafx.scene.paint.Color.RED), "Used colors list should contain RED.");
        assertTrue(game.getUsedColors().contains(javafx.scene.paint.Color.GREEN), "Used colors list should contain GREEN.");
        assertTrue(game.getUsedColors().contains(javafx.scene.paint.Color.BLUE), "Used colors list should contain BLUE.");
        assertTrue(game.getUsedColors().contains(javafx.scene.paint.Color.YELLOW), "Used colors list should contain YELLOW.");
        assertTrue(game.getUsedColors().contains(javafx.scene.paint.Color.MAGENTA), "Used colors list should contain MAGENTA.");
    }

    @Test
    public void testValidMove() {
        Graph graph = new Graph();
        graph.addNode(1);
        graph.addNode(2);
        graph.addEdge(1, 2);

        Node node1 = new Node(1, 50.0, 50.0);
        Node node2 = new Node(2, 100.0, 100.0);
        node1.colorCircle(javafx.scene.paint.Color.RED);

        Game game = new Game("Test Game");
        assertDoesNotThrow(() -> game.validMove(graph, node2, javafx.scene.paint.Color.BLUE));
        assertThrows(InvalidColoringError.class, () -> game.validMove(graph, node2, javafx.scene.paint.Color.RED));
    }
}

