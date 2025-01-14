package graphfx.graphcoloring.test;

import graphfx.graphcoloring.test.Edge;
import graphfx.graphcoloring.test.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EdgeTest {

    @Test
    public void testEdgeConstructor() {
        // Arrange
        Node node1 = new Node(1, 50.0, 50.0);
        Node node2 = new Node(2, 100.0, 100.0);

        // Act
        Edge edge = new Edge(node1, node2);

        // Assert
        assertNotNull(edge, "Edge instance should not be null.");
        assertEquals(node1, edge.getNode1(), "Node1 should be correctly set in the Edge.");
        assertEquals(node2, edge.getNode2(), "Node2 should be correctly set in the Edge.");
    }

    @Test
    public void testDraw() {
        // Arrange
        Node node1 = new Node(1, 50.0, 50.0);
        Node node2 = new Node(2, 100.0, 100.0);
        Edge edge = new Edge(node1, node2);

        javafx.scene.layout.Pane pane = new javafx.scene.layout.Pane();

        // Act
        edge.draw(pane);

        // Assert
        assertEquals(1, pane.getChildren().size(), "Pane should have one child after drawing the edge.");
        assertTrue(pane.getChildren().get(0) instanceof javafx.scene.shape.Line, "The child should be a Line.");
    }

    @Test
    public void testPrint() {
        // Arrange
        Node node1 = new Node(1, 50.0, 50.0);
        Node node2 = new Node(2, 100.0, 100.0);
        Edge edge = new Edge(node1, node2);

        // Act & Assert
        assertDoesNotThrow(() -> edge.print(), "Edge print method should not throw an exception.");
    }

    @Test
    public void testCompareTo() {
        // Arrange
        Node node1 = new Node(1, 50.0, 50.0);
        Node node2 = new Node(2, 100.0, 100.0);
        Edge edge1 = new Edge(node1, node2);
        Edge edge2 = new Edge(node1, node2);
        Edge edge3 = new Edge(node2, node1); // Reverse order

        // Act & Assert
        assertEquals(0, edge1.compareTo(edge2), "Edges with the same nodes should be equal.");
        assertNotEquals(0, edge1.compareTo(edge3), "Edges with reversed nodes should not be equal.");
    }
}

