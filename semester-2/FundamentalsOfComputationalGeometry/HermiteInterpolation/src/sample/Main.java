package sample;

import exceptions.XOutOfBoundsException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 960;


    @Override
    public void start(Stage primaryStage) {
        Point.setWindowHeight(HEIGHT);

        List<Point> points = points();
        Group root = new Group();
        List<Point> interpPoints = new ArrayList<>();
        Interpolator interpolator = new Interpolator(points);

        try {
            for (double x = 100; x < 1000; x += 0.5) {
                double y = interpolator.f(x);
                interpPoints.add(new Point(x, y, 1, Color.GREEN));
            }
        } catch (XOutOfBoundsException e) {
            e.printStackTrace();
        }

        root.getChildren().addAll(interpPoints);
        root.getChildren().addAll(points);


        primaryStage.setTitle("Hermite Interpolation");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }

    private List<Point> points() {
        return Arrays.asList(new Point(100, 100),
                new Point(200, 200),
                new Point(300, 400),
                new Point(500, 300),
                new Point(700, 200),
                new Point(600, 100),
                new Point(800, 200),
                new Point(900, 100),
                new Point(1000, 300)
        );
    }

    private List<Point> points2() {
        return Arrays.asList(new Point(100, 100),
                new Point(200, 200),
                new Point(400, 400),
                new Point(500, 300),
                new Point(700, 200),
                new Point(600, 100),
                new Point(800, 400),
                new Point(900, 400),
                new Point(1000, 300)
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
