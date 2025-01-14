package graphfx.graphcoloring.test;

import interfaces.Drawable;
import interfaces.Printable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;

public class Edge implements Drawable,Comparable<Edge>, Printable {
    private final Node node1;
    private final Node node2;

    public Edge(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    public void draw(Pane pane) {
        Line line = new Line(node1.getX(), node1.getY(), node2.getX(), node2.getY());
        line.setStroke(Color.GRAY);
        pane.getChildren().add(line);
    }

    public int compareTo(Edge other) {
        int compareStart = Integer.compare(this.node1.getId(), other.node1.getId());
        if (compareStart != 0) {
            return compareStart;
        }
        return Integer.compare(this.node2.getId(), other.node2.getId());
    }
    public void print() {
        System.out.println("Edge: (" + node1.getId() + ", " + node2.getId() + ")");
    }

    public Node getNode1() {
        return node1;
    }
    public Node getNode2() {
        return node2;
    }
}