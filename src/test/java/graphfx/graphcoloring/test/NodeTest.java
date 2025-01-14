package graphfx.graphcoloring.test;

import graphfx.graphcoloring.test.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    @Test
    public void testNodeConstructor() {
        // Arrange & Act
        Node node = new Node(1, 50.0, 50.0);

        // Assert
        assertNotNull(node, "Node instance should not be null.");
        assertEquals(1, node.getId(), "Node ID should be correctly set.");
        assertEquals(50.0, node.getX(), "Node X coordinate should be correctly set.");
        assertEquals(50.0, node.getY(), "Node Y coordinate should be correctly set.");
    }

    @Test
    public void testDraw() {
        // Arrange
        Node node = new Node(1, 100.0, 200.0);
        Pane pane = new Pane();

        // Act
        node.draw(pane);

        // Assert
        assertEquals(2, pane.getChildren().size(), "Pane should have two children after drawing the node (circle and text).");
        assertTrue(pane.getChildren().get(0) instanceof javafx.scene.shape.Circle, "First child should be a Circle.");
        assertTrue(pane.getChildren().get(1) instanceof javafx.scene.text.Text, "Second child should be Text.");
    }

    @Test
    public void testColorCircle() {
        // Arrange
        Node node = new Node(1, 50.0, 50.0);
        node.draw(new Pane()); // Ensure the circle is initialized

        // Act
        node.colorCircle(Color.RED);

        // Assert
        assertEquals(Color.RED, node.getColor(), "Circle color should be set to RED.");
    }

    @Test
    public void testGetColor() {
        // Arrange
        Node node = new Node(1, 50.0, 50.0);
        node.draw(new Pane()); // Ensure the circle is initialized

        // Act
        node.colorCircle(Color.BLUE);

        // Assert
        assertEquals(Color.BLUE, node.getColor(), "getColor() should return the correct color of the circle.");
    }

    @Test
    public void testCompareTo() {
        // Arrange
        Node node1 = new Node(1, 50.0, 50.0);
        Node node2 = new Node(2, 100.0, 200.0);
        Node node3 = new Node(1, 150.0, 250.0);

        // Act & Assert
        assertTrue(node1.compareTo(node2) < 0, "Node1 ID should be less than Node2 ID.");
        assertTrue(node2.compareTo(node1) > 0, "Node2 ID should be greater than Node1 ID.");
        assertEquals(0, node1.compareTo(node3), "Nodes with the same ID should be equal.");
    }
}
