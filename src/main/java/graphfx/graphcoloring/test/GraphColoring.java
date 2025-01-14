package graphfx.graphcoloring.test;

import java.util.*;
import java.util.concurrent.*;
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

    // Parallelized backtracking function to check if the graph can be colored with 'm' colors
    private static boolean parallelCanColorWithMColors(Map<Integer, Set<Integer>> adjacencyList, int[] colors, int m, int vertex, ExecutorService executor) throws ExecutionException, InterruptedException {
        if (vertex > adjacencyList.size()) {
            return true; // All vertices are successfully colored
        }

        List<Future<Boolean>> tasks = new ArrayList<>();
        for (int color = 1; color <= m; color++) {
            if (isSafe(adjacencyList, colors, vertex, color)) {
                final int[] newColors = colors.clone();
                newColors[vertex] = color; // Assign the color

                // Submit a task for the next vertex
                tasks.add(executor.submit(() -> parallelCanColorWithMColors(adjacencyList, newColors, m, vertex + 1, executor)));
            }
        }

        // Wait for results and return true if any task succeeds
        for (Future<Boolean> task : tasks) {
            if (task.get()) {
                return true;
            }
        }

        return false; // No valid coloring found
    }

    // Function to find the chromatic number of the graph using multithreading
    public static int findChromaticNumberParallel(Map<Integer, Set<Integer>> adjacencyList) throws ExecutionException, InterruptedException {
        int numVertices = adjacencyList.size();
        int[] colors = new int[numVertices + 1]; // +1 for 1-based indexing
        Arrays.fill(colors, 0); // Initialize all vertices as uncolored

        int m = 1; // Start with 1 color
        ExecutorService executor = Executors.newCachedThreadPool();

        try {
            while (!parallelCanColorWithMColors(adjacencyList, colors, m, 1, executor)) { // Start from vertex 1
                m++; // Increment the number of colors until a valid coloring is found
            }
        } finally {
            executor.shutdown(); // Ensure the executor is properly closed
        }

        return m; // Return the chromatic number
    }
}