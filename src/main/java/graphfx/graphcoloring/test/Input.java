package graphfx.graphcoloring.test;

import java.io.*;
import java.util.HashSet;
import java.util.Random;

public class Input {

    // Method to read the file and build the graph from the neighbor format
    // Method to validate a single line of input
    boolean validateInputLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            System.out.println("Invalid line: Line is empty.");
            return false;
        }

        String[] parts = line.split(" ");
        if (parts.length < 2) {
            System.out.println("Invalid line: Line must have at least a node and one neighbor.");
            return false;
        }

        try {
            int node = Integer.parseInt(parts[0].trim());
            for (int i = 1; i < parts.length; i++) {
                int neighbor = Integer.parseInt(parts[i].trim());

                if (node == neighbor) {
                    System.out.println("Invalid line: Self-loops are not allowed (" + node + " -> " + neighbor + ").");
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid line: Contains non-integer values (" + line + ").");
            return false;
        }

        return true; // Line is valid
    }

    // Updated method to read the file and build the graph
    public void readFileAndBuildGraph(File file, Graph graph) throws FileNotFoundException {
        if (file == null) {
            System.out.println("No file selected.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Validate the line before processing
                if (!validateInputLine(line)) {
                    continue; // Skip invalid lines
                }

                // Process valid lines
                String[] parts = line.split(" ");
                int node = Integer.parseInt(parts[0].trim());

                // Add the node to the graph
                graph.addNode(node);

                // Add the neighbors
                for (int i = 1; i < parts.length; i++) {
                    int neighbor = Integer.parseInt(parts[i].trim());

                    // Check for duplicate edges
                    if (!graph.hasEdge(node, neighbor)) {
                        graph.addEdge(node, neighbor);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error: Could not read the file at " + file + ".");
        }
    }


    public Graph generateRandomGraph(int nodes, int edges) {
        Graph graph = new Graph();
        Random random = new Random();

        // Step 1: Initialize adjacency list and add nodes
        for (int i = 1; i <= nodes; i++) {
            graph.getAdjacencyList().put(i, new HashSet<>());  // Add each node with an empty set of neighbors
        }

        // Step 2: Add random edges by directly modifying the adjacency list
        int addedEdges = 0;
        while (addedEdges < edges) {
            int node1 = random.nextInt(nodes) + 1;
            int node2 = random.nextInt(nodes) + 1;

            // Ensure no self-loops and no duplicate edges
            if (node1 != node2 && !graph.getAdjacencyList().get(node1).contains(node2)) {
                graph.getAdjacencyList().get(node1).add(node2);
                graph.getAdjacencyList().get(node2).add(node1); // Since it's an undirected graph
                addedEdges++;
            }

            // Check if it's possible to add more edges without duplicates
            if (addedEdges < edges && addedEdges >= nodes * (nodes - 1) / 2) {
                System.out.println("Maximum number of unique edges reached for the given nodes.");
                break;
            }
        }

        return graph;
    }
}