package approximation;

import javafx.scene.paint.Color;
import polygonalization.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BSpline {
    private static final double PRECISION = 0.00001;

    private int knotCount;
    private int n;
    private int degree;
    private double perimeter;
    private List<Point> points;
    private List<Point> spline = new ArrayList<>();
    List<Double> knots = new ArrayList<>();
    List<Double> sides = new ArrayList<>();

    public BSpline(List<Point> points, int degree) {
        closeContour(points);
        this.points = points;
        this.n = points.size();
        this.degree = degree;
        this.knotCount = degree + n;
        calculateSides();
        calculateT();
        buildSpline();
    }

    private void closeContour(List<Point> points) {
        points.add(new Point(points.get(0).getX(), points.get(0).getY() + 0.01));
    }

    public List<Point> getSpline() {
        return new ArrayList<>(new HashSet<>(spline));
    }

    private void buildSpline() {
        for (double t = 0; t <= 1; t += PRECISION) {
            double x = 0;
            double y = 0;

            for (int i = 0; i < n; i++) {
                x += points.get(i).getX() * basisSpline(i, degree - 1, t);
                y += points.get(i).getY() * basisSpline(i, degree - 1, t);
            }

            if (!isControlPoint(x, y)) {
                spline.add(new Point(x, y, 1.5, Color.RED));
            }
        }
    }

    private boolean isControlPoint(double x, double y) {
        for (Point p : points) {
            if (p.getX() == x && p.getY() == y) {
                return true;
            }
        }

        return false;
    }

    private double basisSpline(int i, int j, double t) {
        double result;

        if (j == 0) {
            result = knots.get(i) <= t && t < knots.get(i + 1) ? 1.0 : 0.0;
        } else {
            double d1 = knots.get(i + j) - knots.get(i);
            double d2 = knots.get(i + 1) - knots.get(i + j + 1);
            double c1 = (d1 == 0) ? 0 : (t - knots.get(i)) / d1;
            double c2 = (d2 == 0) ? 0 : (t - knots.get(i + j + 1)) / d2;
            double s1 = basisSpline(i, j - 1, t);
            double s2 = basisSpline(i + 1, j - 1, t);
            result = c1 * s1 + c2 * s2;
        }

        return result;
    }

    private void calculateT() {
        for (int i = 0; i < degree; i++) {
            knots.add(0.0);
        }

        int innerKnotsCount = n - degree;
        double length = getSidesSummaryLength(degree - 1);
        for (int i = 1; i < innerKnotsCount + 1; i++) {
            length += sides.get(degree + i - 1);
            knots.add(length / perimeter);
        }

        for (int i = 0; i < knotCount - n; i++) {
            knots.add(1.0);
        }
    }

    private void calculateSides() {
        perimeter = 0;

        for (int i = 0; i < n; i++) {
            double distance = points.get(i % n).distance(points.get((i + 1) % n));
            perimeter += distance;
            sides.add(distance);
        }
    }

    private double getSidesSummaryLength(int last) {
        double result = 0;

        for (int i = 0; i < last; i++) {
            result += sides.get(i);
        }

        return result;
    }
}
