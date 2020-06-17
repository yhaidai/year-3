package gui;

import approximation.BSpline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import polygonalization.*;

import java.util.*;

public class Main extends Application {
    private static final double WIDTH = 1280;
    private static final double HEIGHT = 960;
    private static final String title = "Simple polygonalization approximation";
    private static final Random random = new Random();

    private Group root = new Group();
    private Scene scene = new Scene(root, WIDTH, HEIGHT, Color.SILVER);
    private HBox globalHBox = new HBox();
    private VBox picturePane = new VBox();
    private VBox leftPane = new VBox();
    private VBox randomGenerationVBox = new VBox();
    private TextField randomPointsCountTextField = new TextField();
    private Label legendLabel = new Label("Legend:\n" +
            "Black - convex hull\n" +
            "Green - minimal area simple polygon\n" +
            "Red - B-spline approximation of minimal area simple polygon");
    private Button randomGenerationButton = new Button("Random generation");
    private Button showcaseButton1 = new Button("Showcase #1");
    private Button showcaseButton2 = new Button("Showcase #2");
    private Button showcaseButton3 = new Button("Showcase #3");

    List<Point> points;

    @Override
    public void start(Stage primaryStage) {
        initLayout();

//        System.out.println("\n" + concaveHull);
//        System.out.println();
//        System.out.println(convexHull);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void displayPolygonalizationApproximation() {
        List<Point> pointsCopy = new ArrayList<>(points);

        Polygon concaveHull = concaveHull(points);
        points = new ArrayList<>(pointsCopy);
        Polygon convexHull = convexHull(pointsCopy);


        BSpline bSpline = new BSpline(concaveHull.getVertices(), 4);
        List<Point> splinePoints = bSpline.getSpline();

        root.getChildren().addAll(convexHull);
        root.getChildren().addAll(concaveHull);
        root.getChildren().addAll(splinePoints);
        root.getChildren().addAll(points);
    }

    private void reset() {
        root.getChildren().clear();
        root.getChildren().add(globalHBox);
    }

    private void initButtons() {
        randomGenerationButton.setOnAction(value -> {
            int count = Integer.parseInt(randomPointsCountTextField.getText());
            points = points(count);
            reset();
            displayPolygonalizationApproximation();
        });

        showcaseButton1.setOnAction(value -> {
            points = pointsSample1();
            reset();
            displayPolygonalizationApproximation();
        });

        showcaseButton2.setOnAction(value -> {
            points = pointsSample2();
            reset();
            displayPolygonalizationApproximation();
        });

        showcaseButton3.setOnAction(value -> {
            points = pointsSample3();
            reset();
            displayPolygonalizationApproximation();
        });
    }



    private void initLayout() {
        initLeftPane();
        initPicturePane();

        globalHBox.getChildren().addAll(leftPane, picturePane);

        root.getChildren().add(globalHBox);
    }

    private void initPicturePane() {
        initLegendLabel();
        initButtons();

        picturePane.setMinWidth(4 * WIDTH / 5);
        picturePane.setMinHeight(HEIGHT);
        picturePane.getChildren().add(legendLabel);
        picturePane.setAlignment(Pos.BOTTOM_LEFT);
    }

    private void initLeftPane() {
        initRandomGenerationVBox();

        leftPane.getChildren().addAll(
                randomGenerationVBox,
                showcaseButton1,
                showcaseButton2,
                showcaseButton3
        );
        leftPane.setStyle("-fx-background-color: #A0A0FF;");
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setMaxWidth(WIDTH / 10);
        leftPane.setMinHeight(HEIGHT);
        leftPane.setSpacing(60);
    }

    private void initLegendLabel() {
        legendLabel.setBorder(new Border(
                new BorderStroke(
                        Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(2))
        ));
        legendLabel.setStyle("-fx-background-color: #A0FFA0;");
        legendLabel.setFont(new Font(14));
    }

    private void initRandomGenerationVBox() {
        randomPointsCountTextField.setAlignment(Pos.CENTER);

        randomGenerationVBox.getChildren().addAll(randomGenerationButton, randomPointsCountTextField);
        randomGenerationVBox.setAlignment(Pos.CENTER);
    }


    private Polygon convexHull(List<Point> points) {
        Polygon convexHull = ConvexHullBuilder.convexHull(points);
        convexHull.setFill(null);
        convexHull.setStroke(Color.BLACK);
        convexHull.getStrokeDashArray().addAll(25d, 10d);

        return convexHull;
    }

    private Polygon concaveHull(List<Point> points) {
        Polygon concaveHull = ConcaveHullBuilder.concaveHull(points);
        concaveHull.setFill(null);
        concaveHull.setStroke(Color.rgb(20, 255, 20, 0.8));
        concaveHull.setStrokeWidth(2);

        return concaveHull;
    }

    private List<Point> points(int count) {
        Set<Point> uniquePoints = new HashSet<>();

        for (int i = 0; i < count; i++) {
            uniquePoints.add(new Point(WIDTH / 2 + random.nextInt() % (WIDTH * 2 / 5),
                    HEIGHT / 2 + random.nextInt() % (HEIGHT * 2 / 5)));
        }

        return new ArrayList<>(uniquePoints);
    }

    private List<Point> pointsSample1() {
        return new ArrayList<>(Arrays.asList(
                new Point(754.0, 115.0),
                new Point(526.0, 812.0),
                new Point(714.0, 633.0),
                new Point(371.0, 121.0),
                new Point(463.0, 838.0),
                new Point(324.0, 641.0),
                new Point(732.0, 798.0),
                new Point(487.0, 482.0),
                new Point(1034.0, 494.0),
                new Point(505.0, 468.0),
                new Point(1074.0, 767.0),
                new Point(879.0, 637.0),
                new Point(175.0, 617.0),
                new Point(766.0, 643.0),
                new Point(360.0, 453.0)
        ));
    }

    private List<Point> pointsSample2() {
        return new ArrayList<>(Arrays.asList(
                new Point(100.0, 700.0),
                new Point(300.0, 700.0),
                new Point(1200.0, 900.0),
                new Point(800.0, 800.0),
                new Point(1200.0, 750.0),
                new Point(900.0, 820.0),
                new Point(1200.0, 200.0),
                new Point(1100.0, 482.0),
                new Point(900.0, 800.0)
        ));
    }

    private List<Point> pointsSample3() {
        return new ArrayList<>(Arrays.asList(
                new Point(200.0, 200.0),
                new Point(200.0, 800.0),
                new Point(800.0, 800.0),
                new Point(800.0, 200.0),
                new Point(750.0, 500.0),
                new Point(750.0, 550.0),
                new Point(750.0, 450.0)
        ));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
