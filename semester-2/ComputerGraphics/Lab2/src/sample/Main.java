package sample;

import graph.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    final int WIDTH = 800;
    final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ChainGraph g = createGraph();
//        Vertex z = new Vertex(0, 200, 250);
//        Vertex z = new Vertex(0, 30, 300);
        Vertex z = new Vertex(0, 400, 250);
        g.locate(z);
        z.setStroke(Color.GREEN);
        z.setStrokeWidth(4);
        Group root = new Group(g, z);
        primaryStage.setTitle("Point location with the chains method");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }

    private ChainGraph createGraph() {
        Vertex v1 = new Vertex(1, 150, 550);
        Vertex v2 = new Vertex(2, 700, 500);
        Vertex v3 = new Vertex(3, 70, 440);
        Vertex v4 = new Vertex(4, 280, 410);
        Vertex v5 = new Vertex(5, 680, 330);
        Vertex v6 = new Vertex(6, 340, 290);
        Vertex v7 = new Vertex(7, 640, 200);
        Vertex v8 = new Vertex(8, 50, 200);
        Vertex v9 = new Vertex(9, 330, 80);
        ChainGraph result = new ChainGraph(new ArrayList<WeightedNode>(List.of(
                new WeightedNode(1, v1, v3, 1),
                new WeightedNode(2, v1, v4, 1),
                new WeightedNode(3, v1, v5, 1),
                new WeightedNode(4, v1, v2, 1),
                new WeightedNode(5, v2, v5, 1),
                new WeightedNode(6, v3, v4, 1),
                new WeightedNode(7, v3, v8, 1),
                new WeightedNode(8, v3, v6, 1),
                new WeightedNode(9, v4, v6, 1),
                new WeightedNode(10, v4, v5, 1),
                new WeightedNode(11, v5, v6, 1),
                new WeightedNode(12, v5, v7, 1),
                new WeightedNode(13, v6, v7, 1),
                new WeightedNode(14, v6, v9, 1),
                new WeightedNode(15, v8, v9, 1),
                new WeightedNode(16, v7, v9, 1)
        )));

        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
