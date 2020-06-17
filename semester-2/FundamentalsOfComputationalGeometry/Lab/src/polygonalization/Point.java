package polygonalization;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class Point extends Circle {
    private static final double RADIUS = 3.0;
    double x;
    double y;

    public Point(double x, double y) {
        super(x, y, RADIUS);
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y, double radius, Color color) {
        super(x, y, radius, color);
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distance(Point other) {
        return Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));
    }

    /**
     * Returns orientation of ordered points triplet (this, a, b)
     * 1 - clockwise
     * 0 - collinear
     * -1 - counterclockwise
     */
    public int orientation(Point a, Point b) {
        return (int) Math.signum((a.y - this.y) * (b.x - a.x) - (a.x - this.x) * (b.y - a.y));
    }

    public boolean onSegment(Point start, Point end) {
        return (this.x <= Math.max(start.x, end.x) && this.x >= Math.min(start.x, end.x) &&
                this.y <= Math.max(start.y, end.y) && this.y >= Math.min(start.y, end.y));
    }

    public boolean onSegment(LineSegment segment) {
        return onSegment(segment.getStart(), segment.getEnd());
    }

    public boolean isInside(Polygon polygon) {
        final double INFINITY = 100000;
        int size = polygon.size();

        // Create horizontal ray from this point to +inf
        LineSegment ray = new LineSegment(this, new Point(INFINITY, this.y));

        // Count intersections of the ray with sides of polygon
        int count = 0;
        for (int i = 0; i < size; i++) {
            LineSegment side = polygon.getSide(i);

            if (ray.intersects(side)) {
                if (this.orientation(side.getStart(), side.getEnd()) == 0) {
                    return this.onSegment(side);
                }
                count++;
            }
        }

        return (count % 2 == 1);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
