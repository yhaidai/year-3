package sample;

public class LineSegment {
    Point start;
    Point end;

    LineSegment(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    /** Returns true if two line segments intersect, false otherwise */
    public boolean intersects(LineSegment other) {
        int o1 = start.orientation(end, other.start);
        int o2 = start.orientation(end, other.end);
        int o3 = other.start.orientation(other.end, start);
        int o4 = other.start.orientation(other.end, end);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special cases
        if (o1 == 0 && other.start.onSegment(this))
            return true;
        if (o2 == 0 && other.end.onSegment(this))
            return true;
        if (o3 == 0 && start.onSegment(other))
            return true;
        if (o4 == 0 && end.onSegment(other))
            return true;

        return false;
    }
}
