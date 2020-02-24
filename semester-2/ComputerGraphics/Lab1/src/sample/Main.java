package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    final static int WIDTH = 800;
    final static int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Point z = new Point(300, 500);
        z.setStroke(Color.RED);
        z.setStrokeWidth(5);
        Polygon polygon = new Polygon(
                300, 500,
                600, 500,
                600, 200,
                300, 200
        );
        polygon.setFill(Color.color(.2, .95 , 1));
        polygon.setStroke(Color.BLACK);
        Group root = new Group(polygon, z);
        System.out.println(z.isInside(polygon));

        primaryStage.setTitle("The problem of point being inside a simple polygon");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
