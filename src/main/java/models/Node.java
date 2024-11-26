package models;

import interfaces.Drawable;
import interfaces.Printable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

public class Node implements Drawable,Comparable<Node>, Printable {
    private final int id;
    private final double x;
    private final double y;
    private Circle circle;

    public Node(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public void draw(Pane pane) {
        // Draw the circle
        this.circle = new Circle(x, y, 20);
        this.circle.setFill(Color.WHITE);
        this.circle.setStroke(Color.BLACK);

        // Draw the ID text at the center of the circle
        Text text = new Text(String.valueOf(id));
        text.setX(x - text.getLayoutBounds().getWidth() / 2);  // Center text horizontally
        text.setY(y + text.getLayoutBounds().getHeight() / 4); // Center text vertically

        // Add both the circle and the text to the pane
        pane.getChildren().addAll(this.circle, text);
    }

    private void toggleColor(Color color) {
        circle.setFill(color);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getId() {
        return id;
    }
    public void colorCircle(Color color) {
        this.circle.setFill(color);
    }

    public Circle getCircle() {
        return this.circle;
    }

    public Color getColor() {
        return (Color) this.circle.getFill();
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.id, other.id);
    }
    public void print(){
        System.out.println("Node: " + this.id);
    }

}
