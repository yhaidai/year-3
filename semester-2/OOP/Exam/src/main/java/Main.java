import entities.Triangle;
import services.TriangleService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        TriangleService service = new TriangleService();

        System.out.println("Square: ");
        double square1 = scanner.nextDouble();
        Triangle triangle1 = service.findClosestSquare(square1);
        System.out.println("Triangle of square closest to " + square1 + ": " + triangle1);
        System.out.println();

        System.out.println("Square: : ");
        double square2 = scanner.nextDouble();
        List<Triangle> triangles2 = service.findClosestSquareTriangles(square2);
        System.out.println("Triangles of square sum closest to " + square2 + ":");
        for (Triangle triangle : triangles2){
            System.out.println(triangle);
        }
        System.out.println();

        System.out.println("Radius: ");
        double radius = scanner.nextDouble();
        List<Triangle> triangles3 = service.findTrianglesWithLessRadius(radius);
        System.out.println("Triangles that fit into a circle of radius " + radius + ":");
        for (Triangle triangle : triangles3){
            System.out.println(triangle);
        }
    }
}
