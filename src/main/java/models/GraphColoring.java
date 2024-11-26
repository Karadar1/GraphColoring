package models;

import java.util.*;

public class GraphColoring {

    // Function to check if a color can be assigned to a vertex
    private static boolean isSafe(Map<Integer, Set<Integer>> adjacencyList, int[] colors, int vertex, int color) {
        Set<Integer> neighbors = adjacencyList.getOrDefault(vertex, new HashSet<>());
        for (int neighbor : neighbors) {
            if (colors[neighbor] == color) {
                return false; // Color is not safe as it conflicts with a neighbor
            }
        }
        return true;
    }

    // Backtracking function to check if the graph can be colored with 'm' colors
    private static boolean canColorWithMColors(Map<Integer, Set<Integer>> adjacencyList, int[] colors, int m, int vertex) {
        if (vertex > adjacencyList.size()) {
            return true; // All vertices are successfully colored
        }

        for (int color = 1; color <= m; color++) {
            if (isSafe(adjacencyList, colors, vertex, color)) {
                colors[vertex] = color; // Assign the color
                if (canColorWithMColors(adjacencyList, colors, m, vertex + 1)) {
                    return true; // Valid coloring found
                }
                colors[vertex] = 0; // Backtrack
            }
        }
        return false; // No valid coloring found
    }

    // Function to find the chromatic number of the graph
    public static int findChromaticNumber(Map<Integer, Set<Integer>> adjacencyList) {
        int numVertices = adjacencyList.size();
        int[] colors = new int[numVertices + 1]; // +1 for 1-based indexing
        Arrays.fill(colors, 0); // Initialize all vertices as uncolored

        int m = 1; // Start with 1 color
        while (!canColorWithMColors(adjacencyList, colors, m, 1)) { // Start from vertex 1
            m++; // Increment the number of colors until a valid coloring is found
        }

        return m; // Return the chromatic number
    }

}
