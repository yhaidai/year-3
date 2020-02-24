package sample;

public class Polygon extends javafx.scene.shape.Polygon {
    Point vertices[];

    public Polygon(double... coords) {
        super(coords);
        vertices = new Point[coords.length / 2];

        for (int i = 0; i < coords.length; i += 2) {
            Point p = new Point(coords[i], coords[i + 1]);
            vertices[i / 2] = p;
        }
    }

    public int size() {
        return vertices.length;
    }

    public LineSegment getSide(int i) {
        return new LineSegment(vertices[i % this.size()], vertices[(i + 1) % this.size()]);
    }
}
