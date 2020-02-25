package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

public class Controller {
    private final int MIN = 0;
    private final int MAX = 100;
    private final int TARGET_MIN = 10;
    private final int TARGET_MAX = 90;
    private final int TIMEOUT = 100;
    private final int INCREMENT = 1;

    private Model model;
    private View view;
    private Thread thread1;
    private Thread thread2;
    private Runnable increaseCounter;
    private Runnable decreaseCounter;
    private Button startButton1;
    private Button startButton2;
    private Button stopButton1;
    private Button stopButton2;
    private AtomicInteger semaphore;


    public Controller(Stage stage) {
        final int VALUE = (TARGET_MIN + TARGET_MAX) / 2;
        model = new Model(VALUE, TARGET_MIN, TARGET_MAX);
        view = new View(stage, MIN, MAX, VALUE, Thread.NORM_PRIORITY);
        semaphore = new AtomicInteger(0);
        initRunnables();
        initButtonClicks();
    }

    public void onStartButton1Click() {
        if (semaphore.compareAndSet(0, 1)) {
            thread1 = new Thread(increaseCounter);
            thread1.setPriority(Thread.MIN_PRIORITY);
            view.updatePriorityValueLabel1(String.valueOf(Thread.MIN_PRIORITY));
            thread1.start();
            stopButton2.setDisable(true);
        }
    }

    public void onStartButton2Click() {
        if (semaphore.compareAndSet(0, 1)) {
            thread2 = new Thread(decreaseCounter);
            thread2.setPriority(Thread.MAX_PRIORITY);
            view.updatePriorityValueLabel2(String.valueOf(Thread.MAX_PRIORITY));
            thread2.start();
            stopButton1.setDisable(true);
        }
    }

    public void onStopButton1Click() {
        semaphore.set(0);
        thread1.interrupt();
        stopButton2.setDisable(false);
    }

    public void onStopButton2Click() {
        semaphore.set(0);
        thread2.interrupt();
        stopButton1.setDisable(false);
    }

    private void updateSlider(int value) {
        Thread updater = new Thread(() -> {
            Platform.runLater(() -> {
                view.updateSliderValue(value);
            });
        });
        updater.setDaemon(true);
        updater.start();
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(TIMEOUT);
    }

    private void initRunnables() {
        increaseCounter = () -> {
            while (true) {
                synchronized (view) {
                    model.increase(INCREMENT);
                    System.out.println("increase -> " + model.getCounter());
                    updateSlider(model.getCounter());
                    try {
                        sleep();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        };

        decreaseCounter = () -> {
            while (true) {
                synchronized (view) {
                    model.decrease(INCREMENT);
                    System.out.println("decrease -> " + model.getCounter());
                    updateSlider(model.getCounter());
                    try {
                        sleep();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        };
    }

    private void initButtonClicks() {
        startButton1 = view.getIncreasePriorityButton1();
        startButton2 = view.getIncreasePriorityButton2();
        stopButton1 = view.getDecreasePriorityButton1();
        stopButton2 = view.getDecreasePriorityButton2();

        startButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                onStartButton1Click();
            }
        });

        startButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                onStartButton2Click();
            }
        });

        stopButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                onStopButton1Click();
            }
        });

        stopButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                onStopButton2Click();
            }
        });
    }
}
