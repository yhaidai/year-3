package polygonalization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polygon extends javafx.scene.shape.Polygon {
    List<Point> vertices = new ArrayList<>();

    public Polygon(double... coords) {
        super(coords);

        for (int i = 0; i < coords.length; i += 2) {
            Point p = new Point(coords[i], coords[i + 1]);
            vertices.add(p);
        }
    }

    public Polygon(List<Point> vertices) {
        this.vertices = vertices;

        for (Point vertex : vertices) {
            this.getPoints().add(vertex.x);
            this.getPoints().add(vertex.y);
        }
    }

    public Polygon(Point... vertices) {
        this.vertices = Arrays.asList(vertices);

        for (Point vertex : vertices) {
            this.getPoints().add(vertex.x);
            this.getPoints().add(vertex.y);
        }
    }

    public Polygon(Polygon other) {
        this.vertices = other.vertices;

        for (Point vertex : vertices) {
            this.getPoints().add(vertex.x);
            this.getPoints().add(vertex.y);
        }
    }

    public List<Point> getVertices() {
        return vertices;
    }

    public void add(int i, Point p) {
        vertices.add(i, p);
        this.getPoints().add(i * 2, p.y);
        this.getPoints().add(i * 2, p.x);
    }

    public int size() {
        return vertices.size();
    }

    public LineSegment getSide(int i) {
        return new LineSegment(vertices.get(i % this.size()), vertices.get((i + 1) % this.size()));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Point v : vertices) {
            stringBuilder.append(v).append("\n");
        }

        return stringBuilder.toString();
    }
}
