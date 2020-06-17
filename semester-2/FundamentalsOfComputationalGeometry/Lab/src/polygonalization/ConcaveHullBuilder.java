package polygonalization;

import java.util.*;

public class ConcaveHullBuilder {

    public static Polygon concaveHull(List<Point> points) {
        Polygon concaveHull = ConvexHullBuilder.convexHull(points); // O(n^2)
        Queue<Point> innerPoints = new ArrayDeque<>(points);
        int queueCounter = 0;

        // points that lie on the convex hull have been removed from the list during convex hull building
        while (!innerPoints.isEmpty()) {
            Point p = innerPoints.poll();
            queueCounter++;
//            System.out.println(p);

            double maxArea = 0;
            LineSegment maxAreaSide = null;

            // finding max area triangle that does not intersect with concave hull and contains no inner points
            for (int i = 0; i < concaveHull.size(); i++) {
                LineSegment side = concaveHull.getSide(i);
                Point a = side.getStart();
                Point b = side.getEnd();
                Triangle triangle = new Triangle(a, b, p);

                if (triangle.containsNoneOf(points)) {
                    if (!triangle.intersects(concaveHull)) {

                        double area = Triangle.area(a, b, p);
                        if (area > maxArea) {
                            maxArea = area;
                            maxAreaSide = side;
                        }
                    }
                }
            }

//            System.out.println(maxAreaSide);
            if (maxAreaSide != null) {
                int index = concaveHull.vertices.indexOf(maxAreaSide.getEnd());
                concaveHull.add(index, p);
                queueCounter = 0;
            } else {
                // modification unsuccessful, try again later
                if (queueCounter <= innerPoints.size()) {
//                    System.out.println("adding: " + p);
                    innerPoints.add(p);
                } else {
//                    System.out.println(p + "size: " + concaveHull.size());
                    queueCounter = 0;
                }
            }
        }

        return concaveHull;
    }


}
