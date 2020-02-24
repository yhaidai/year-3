package sample;

import graph.Node;
import graph.StripGraph;
import graph.Vertex;
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
        StripGraph g = createGraph();
        Vertex z = new Vertex(0, 100, 350);
        g.locate(z);
        z.setStroke(Color.GREEN);
        z.setStrokeWidth(4);
        Group root = new Group(g, z);
        primaryStage.setTitle("Point location with the strips method");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private StripGraph createGraph() {
        Vertex v1 = new Vertex(1, 150, 550);
        Vertex v2 = new Vertex(2, 700, 500);
        Vertex v3 = new Vertex(3, 70, 440);
        Vertex v4 = new Vertex(4, 280, 410);
        Vertex v5 = new Vertex(5, 680, 330);
        Vertex v6 = new Vertex(6, 340, 290);
        Vertex v7 = new Vertex(7, 640, 200);
        Vertex v8 = new Vertex(8, 50, 200);
        Vertex v9 = new Vertex(9, 330, 80);
        StripGraph result = new StripGraph(new ArrayList<Node>(List.of(
                new Node(1, v1, v3),
                new Node(2, v1, v4),
                new Node(3, v1, v5),
                new Node(4, v1, v2),
                new Node(5, v2, v5),
                new Node(6, v3, v4),
                new Node(7, v3, v8),
                new Node(8, v3, v6),
                new Node(9, v4, v6),
                new Node(10, v4, v5),
                new Node(11, v5, v6),
                new Node(12, v5, v7),
                new Node(13, v6, v7),
                new Node(14, v6, v9),
                new Node(15, v8, v9),
                new Node(16, v7, v9)
        )));

        return result;
    }
}
