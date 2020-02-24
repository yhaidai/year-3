package sample;

import javafx.scene.shape.Line;

public class Point extends Line {
    double x;
    double y;

    public Point(double x, double y) {
        super(x, y, x, y);
        this.x = x;
        this.y = y;
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
                    System.out.println("on the edge!");
                    return this.onSegment(side);
                }
                count++;
            }
        }

        return (count % 2 == 1);
    }
}
