package polygonalization;

import java.util.List;

public class Triangle extends Polygon {
    LineSegment ab;
    LineSegment bc;
    LineSegment ac;

    public Triangle(Point a, Point b, Point c) {
        super(a, b, c);
        ab = new LineSegment(a, b);
        bc = new LineSegment(b, c);
        ac = new LineSegment(a, c);
    }

    public static double area(Point a, Point b, Point c) {
        // Heron formula
        double ab = a.distance(b);
        double bc = b.distance(c);
        double ac = a.distance(c);
        double p = (ab + bc + ac) / 2;

        return Math.sqrt(p * (p - ab) * (p - bc) * (p - ac));
    }

    public boolean intersects(Polygon polygon) {
        for (int i = 0; i < polygon.size(); i++) {
            LineSegment side = polygon.getSide(i);

//            if (side.equals(ab) || side.equals(bc) || side.equals(ac)) {
//                continue;
//            }

            if (segmentsIntersectEndsNonInclusive(side, ab) ||
                    segmentsIntersectEndsNonInclusive(side, bc) ||
                    segmentsIntersectEndsNonInclusive(side, ac)) {
                return true;
            }
        }

        return false;
    }

    public boolean containsNoneOf(List<Point> points) {
        for (Point p : points) {
            if (p.equals(vertices.get(2)) || p.equals(vertices.get(1)) || p.equals(vertices.get(0))) {
                continue;
            }
            if (p.isInside(this)) {
                return false;
            }
        }

        return true;
    }

    private boolean segmentsIntersectEndsNonInclusive(LineSegment a, LineSegment b) {
        return a.intersects(b) &&
                !a.getEnd().equals(b.getEnd()) &&
                !a.getEnd().equals(b.getStart()) &&
                !a.getStart().equals(b.getEnd()) &&
                !a.getStart().equals(b.getStart());
    }
}
