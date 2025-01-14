package graphfx.graphcoloring.test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Output {

    // Method to print the neighbors of each vertex in the graph
    public void printNeighbors(Graph graph) {
        Map<Integer, Set<Integer>> adjacencyList = graph.getAdjacencyList();  // Access the graph's adjacency list
        Set<String> printedEdges = new HashSet<>();  // Track printed edges to avoid duplicates

        for (int vertex : adjacencyList.keySet()) {  // Iterate over all vertices
            System.out.print("Neighbors of vertex " + vertex + ": ");
            Set<Integer> neighbors = adjacencyList.get(vertex);  // Get the set of neighbors
            if (neighbors.isEmpty()) {
                System.out.println("No neighbors");
            } else {
                for (int neighbor : neighbors) {  // Iterate over the neighbors
                    // Track edge pairs to avoid printing twice
                    String edgeKey = vertex < neighbor ? vertex + "-" + neighbor : neighbor + "-" + vertex;
                    if (!printedEdges.contains(edgeKey)) {
                        System.out.print(neighbor + " ");
                        printedEdges.add(edgeKey);
                    }
                }
                System.out.println();  // Print a new line after listing all neighbors
            }
        }
    }
    public String countNodes(Graph graph) {
        // Access the graph's adjacency list and count the number of keys (which represent nodes)
        int numberOfNodes = graph.getAdjacencyList().size();

        // Return a message indicating how many nodes the graph has
        return "The graph contains " + numberOfNodes + " nodes.";
    }
}
