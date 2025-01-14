package graphfx.graphcoloring.test;

import graphfx.graphcoloring.test.Graph;
import graphfx.graphcoloring.test.Input;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class InputTest {

    @Test
    public void testValidateInputLine() {
        Input input = new Input();
        assertTrue(input.validateInputLine("1 2 3"));
        assertFalse(input.validateInputLine("1 1")); // Self-loop
        assertFalse(input.validateInputLine("1 a")); // Invalid format
    }

    @Test
    public void testGenerateRandomGraph() {
        Input input = new Input();
        Graph graph = input.generateRandomGraph(5, 4);

        assertEquals(5, graph.getAdjacencyList().size());
        assertTrue(graph.getAdjacencyList().size() >= 4);
    }

    @Test
    public void testReadFileAndBuildGraph() {
        Input input = new Input();
        Graph graph = new Graph();

        File testFile = new File("src/test/resources/test_graph.txt"); // Adjust the path
        assertDoesNotThrow(() -> input.readFileAndBuildGraph(testFile, graph));
        assertFalse(graph.getAdjacencyList().isEmpty());
    }
}
