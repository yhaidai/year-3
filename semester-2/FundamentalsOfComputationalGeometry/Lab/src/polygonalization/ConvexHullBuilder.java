package polygonalization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvexHullBuilder {

    public static Polygon convexHull(Point... points) {
        return convexHull(new ArrayList<>(Arrays.asList(points)));
    }

    public static Polygon convexHull(List<Point> points) {
        List<Point> convexHull = new ArrayList<>();
        Point lowest = lowestPoint(points);

        List<Point> pointsCopy = new ArrayList<>(points);
        pointsCopy.remove(lowest);

        Point current = minPolarAnglePoint(lowest, pointsCopy);
        Point prev = lowest;

        convexHull.add(current);
        points.remove(current);

        do {
            current = minRelativeAnglePoint(prev, current, points);
            prev = convexHull.get(convexHull.size() - 1);
            convexHull.add(current);
            points.remove(current);
        } while (current != lowest);

        return new Polygon(convexHull);
    }

    private static Point minPolarAnglePoint(Point pivot, List<Point> points) {
        double maxCos = cosX(pivot, points.get(0));
        Point result = points.get(0);

        for (Point p : points) {
            double cos = cosX(pivot, p);
            if (cos > maxCos) {
                maxCos = cos;
                result = p;
            }
        }

        return result;
    }

    private static Point minRelativeAnglePoint(Point prev, Point current, List<Point> points) {
        double maxCos = cos(prev, current, points.get(0));
        Point result = points.get(0);

        for (Point p : points) {
            double cos = cos(prev, current, p);
            if (cos < maxCos) {
                maxCos = cos;
                result = p;
            }
        }

        return result;
    }

    private static Point lowestPoint(List<Point> points) {
        double minY = points.get(0).y;
        Point lowestPoint = points.get(0);

        for (Point p : points) {
            if (p.y < minY) {
                minY = p.y;
                lowestPoint = p;
            }

            if (p.y == minY) {
                if (p.x < lowestPoint.x) {
                    lowestPoint = p;
                }
            }
        }

        return lowestPoint;
    }

    private static double cos(Point a, Point b, Point c) {
        double x1 = b.x - a.x;
        double y1 = b.y - a.y;
        double x2 = b.x - c.x;
        double y2 = b.y - c.y;
        double numerator = x1 * x2 + y1 * y2;
        double denominator = Math.sqrt(x1 * x1 + y1 * y1) * Math.sqrt(x2 * x2 + y2 * y2);

        return numerator / denominator;
    }

    private static double cosX(Point a, Point b) {
        return cos(b, a, new Point(a.x - 1, a.y));
    }
}
