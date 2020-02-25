package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.geometry.Pos.CENTER;

public class View {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int PREF_PRIORITY_BUTTON_WIDTH = 100;
    private final int PREF_PRIORITY_BUTTON_HEIGHT = 30;
    private final String title = "Lab1(b)";

    private Stage stage;
    private BorderPane root;
    private Slider slider;
    private Label priorityLabel1;
    private Label priorityLabel2;
    private Label priorityValueLabel1;
    private Label priorityValueLabel2;
    private Button startButton1;
    private Button stopButton1;
    private Button startButton2;
    private Button stopButton2;
    private VBox priorityVBox1;
    private VBox priorityVBox2;
    private HBox priorityHBox;
    private HBox priorityValueHBox1;
    private HBox priorityValueHBox2;

    public View(Stage stage, int min, int max, int value, int defaultPriority) {
        this.stage = stage;
        slider = new Slider(min, max, value);

        initPriorityLabels();
        initPriorityValueLabels(defaultPriority);
        initPriorityValueHBoxes();
        initPriorityButtons();
        initPriorityVBoxes();
        initPriorityHBox();
        initRoot();
        initStage();
    }

    public Button getIncreasePriorityButton1() {
        return startButton1;
    }

    public Button getDecreasePriorityButton1() {
        return stopButton1;
    }

    public Button getIncreasePriorityButton2() {
        return startButton2;
    }

    public Button getDecreasePriorityButton2() {
        return stopButton2;
    }

    public void updateSliderValue(int value) {
        slider.setValue(value);
    }

    public void updatePriorityValueLabel1(String text) {
        priorityValueLabel1.setText(text);
    }

    public void updatePriorityValueLabel2(String text) {
        priorityValueLabel2.setText(text);
    }

    private void initPriorityLabels() {
        priorityLabel1 = new Label("thread1: ");
        priorityLabel2 = new Label("thread2: ");
    }

    private void initPriorityValueLabels(int defaultPriority) {
        priorityValueLabel1 = new Label(String.valueOf(defaultPriority));
        priorityValueLabel2 = new Label(String.valueOf(defaultPriority));
    }

    private void initPriorityValueHBoxes() {
        priorityValueHBox1 = new HBox(priorityLabel1, priorityValueLabel1);
        priorityValueHBox2 = new HBox(priorityLabel2, priorityValueLabel2);
    }

    private void initPriorityButtons() {
        startButton1 = new Button("start");
        startButton2 = new Button("start");
        stopButton1 = new Button("stop");
        stopButton2 = new Button("stop");
        startButton1.setPrefSize(PREF_PRIORITY_BUTTON_WIDTH, PREF_PRIORITY_BUTTON_HEIGHT);
        startButton2.setPrefSize(PREF_PRIORITY_BUTTON_WIDTH, PREF_PRIORITY_BUTTON_HEIGHT);
        stopButton1.setPrefSize(PREF_PRIORITY_BUTTON_WIDTH, PREF_PRIORITY_BUTTON_HEIGHT);
        stopButton2.setPrefSize(PREF_PRIORITY_BUTTON_WIDTH, PREF_PRIORITY_BUTTON_HEIGHT);
    }

    private void initPriorityVBoxes() {
        priorityVBox1 = new VBox(priorityValueHBox1, startButton1, stopButton1);
        priorityVBox2 = new VBox(priorityValueHBox2, startButton2, stopButton2);
    }

    private void initPriorityHBox() {
        priorityHBox = new HBox(priorityVBox1, priorityVBox2);
        priorityHBox.setAlignment(CENTER);
        priorityHBox.setSpacing(100);
    }

    private void initRoot() {
        root = new BorderPane();
        root.setTop(slider);
        root.setCenter(priorityHBox);
    }

    private void initStage() {
        stage.setTitle(title);
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }
}
