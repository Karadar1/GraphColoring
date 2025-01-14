//CLASS B


package graphfx.graphcoloring.test;

import interfaces.Printable;
import javafx.scene.layout.Pane;

import java.util.*;

public class Graph implements Printable {
    private final HashMap<Integer, Set<Integer>> adjacencyList;
    private final HashMap<Integer, Node> nodes;
    private final List<Edge> edges;

    public Graph() {
        this.adjacencyList = new HashMap<>();
        this.nodes = new HashMap<>();
        this.edges = new ArrayList<>();
    }

    // Getter to access the adjacency list
    public Map<Integer, Set<Integer>> getAdjacencyList() {
        return adjacencyList;
    }

    public Map<Integer, Node> getNodes() {
        return this.nodes;
    }

    public boolean hasEdge(int node1, int node2) {
        return adjacencyList.containsKey(node1) && adjacencyList.get(node1).contains(node2);
    }

    // Add a vertex to the graph
    public void addNode(int node) {
        adjacencyList.putIfAbsent(node,new HashSet<Integer>());
    }

    // Add an edge between two vertices (undirected graph)
    public void addEdge(int vertex1, int vertex2) {
        // Check for self-loops
        if (vertex1 == vertex2) {
            System.err.println("Invalid operation: Self-loops are not allowed in this graph.");
            return; // Exit the method if a self-loop is detected
        }

        // Add the vertices if they are not already in the adjacency list
        adjacencyList.putIfAbsent(vertex1, new HashSet<>());
        adjacencyList.putIfAbsent(vertex2, new HashSet<>());

        // Add the edge (undirected graph)
        adjacencyList.get(vertex1).add(vertex2);
        adjacencyList.get(vertex2).add(vertex1);
    }



    public void print() {
        for (Map.Entry<Integer, Set<Integer>> entry : adjacencyList.entrySet()) {
            int node = entry.getKey();
            Set<Integer> neighbors = entry.getValue();
            System.out.print("Node " + node + " has neighbors: ");
            for (Integer neighbor : neighbors) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }

    //CHANGES


    public void initializeNodes(Pane borderPane) {
        int numNodes = adjacencyList.size();
        double paneWidth = borderPane.getWidth(); // Get current pane width
        double paneHeight = borderPane.getHeight(); // Get current pane height
        double margin = 50; // Margin to keep nodes away from edges
        double minDistance = 50; // Minimum distance between nodes

        Random random = new Random();

        for (int i = 1; i <= numNodes; i++) {
            double x, y;
            boolean validPosition;

            do {
                // Generate random coordinates within the margin boundaries
                x = margin + (paneWidth - 2 * margin) * random.nextDouble();
                y = margin + (paneHeight - 2 * margin) * random.nextDouble();
                validPosition = true;

                // Check if this position is at least minDistance away from all other nodes
                for (Node otherNode : nodes.values()) {
                    double distance = Math.sqrt(Math.pow(x - otherNode.getX(), 2) + Math.pow(y - otherNode.getY(), 2));
                    if (distance < minDistance) {
                        validPosition = false;
                        break; // Exit the loop and generate new coordinates
                    }
                }

            } while (!validPosition);

            nodes.put(i, new Node(i, x, y));
        }
    }

    public void initializeEdges() {
        for (int node : adjacencyList.keySet()) {
            for (int neighbor : adjacencyList.get(node)) {
                if (node < neighbor) { // Avoid duplicate edges
                    edges.add(new Edge(nodes.get(node), nodes.get(neighbor)));
                }
            }
        }
    }

    public void drawGraph(Pane pane) {
        // Draw edges first, then nodes
        for (Edge edge : edges) {
            edge.draw(pane);
        }
        for (Node node : nodes.values()) {
            node.draw(pane);
        }
    }



}

