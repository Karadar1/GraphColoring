package graphfx.graphcoloring.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {


    @Test
    public void testGraphConstructor() {
        // Create an instance of the Graph class
        Graph graph = new Graph();

        // Assert that the graph's adjacency list is initialized and empty
        assertNotNull(graph.getAdjacencyList(), "Adjacency list should not be null.");
        assertTrue(graph.getAdjacencyList().isEmpty(), "Adjacency list should be empty upon initialization.");
    }

    @Test
    public void testAddNode() {
        Graph graph = new Graph();
        graph.addNode(1);
        assertTrue(graph.getAdjacencyList().containsKey(1));
        assertTrue(graph.getAdjacencyList().get(1).isEmpty());
    }

    @Test
    public void testAddEdge() {
        Graph graph = new Graph();
        graph.addNode(1);
        graph.addNode(2);
        graph.addEdge(1, 2);

        assertTrue(graph.getAdjacencyList().get(1).contains(2));
        assertTrue(graph.getAdjacencyList().get(2).contains(1));
    }

    @Test
    public void testAddSelfLoop() {
        Graph graph = new Graph();
        graph.addNode(1);
        graph.addEdge(1, 1); // Self-loop

        assertFalse(graph.getAdjacencyList().get(1).contains(1));
    }
}