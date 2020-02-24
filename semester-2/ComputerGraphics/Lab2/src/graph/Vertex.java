package graph;

import javafx.scene.shape.Line;

import java.util.Comparator;
import java.util.Objects;

public class Vertex extends Line{
    int number;
    double x;
    double y;

    public Vertex(int number, double x, double y) {
        this.number = number;
        this.x = x;
        this.y = y;
        draw();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Returns orientation of ordered points triplet (this, a, b)
     * 1 - clockwise
     * 0 - collinear
     * -1 - counterclockwise
     */
    public int orientation(Vertex a, Vertex b) {
        return (int) Math.signum((a.y - this.y) * (b.x - a.x) - (a.x - this.x) * (b.y - a.y));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return number == vertex.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    private void draw() {
        this.setStartX(x);
        this.setEndX(x);
        this.setStartY(y);
        this.setEndY(y);
    }

    public static class NumberComparator implements Comparator<Vertex> {

        public int compare(Vertex v1, Vertex v2) {
            if (v1.getNumber() == v2.getNumber()) {
                return 0;
            } else if (v1.getNumber() < v2.getNumber()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public static class XAxisComparator implements Comparator<Vertex> {

        public int compare(Vertex v1, Vertex v2) {
            if (v1.getX() == v2.getX()) {
                return 0;
            } else if (v1.getX() < v2.getX()) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
