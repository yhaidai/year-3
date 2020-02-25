package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {
    private final int MIN = 0;
    private final int MAX = 100;
    private final int TARGET_MIN = 10;
    private final int TARGET_MAX = 90;
    private final int TIMEOUT = 0;
    private final int INCREMENT = 1;

    private Model model;
    private View view;
    private Thread thread1;
    private Thread thread2;
    private Runnable increaseCounter;
    private Runnable decreaseCounter;

    public Controller(Stage stage) {
        final int VALUE = (TARGET_MIN + TARGET_MAX) / 2;
        model = new Model(VALUE, TARGET_MIN, TARGET_MAX);
        view = new View(stage, MIN, MAX, VALUE, Thread.NORM_PRIORITY);
        initThreads();
        initButtonClicks();
    }

    public void start() {
        thread1.start();
        thread2.start();
    }

    public void onIncreasePriorityButton1Click() {
        int newPriority = thread1.getPriority() + 1;
        try {
            thread1.setPriority(newPriority);
            view.updatePriorityValueLabel1(String.valueOf(newPriority));
        } catch (IllegalArgumentException e) {
        }
    }

    public void onIncreasePriorityButton2Click() {
        int newPriority = thread2.getPriority() + 1;
        try {
            thread2.setPriority(newPriority);
            view.updatePriorityValueLabel2(String.valueOf(newPriority));
        } catch (IllegalArgumentException e) {
        }
    }

    public void onDecreasePriorityButton1Click() {
        int newPriority = thread1.getPriority() - 1;
        try {
            thread1.setPriority(newPriority);
            view.updatePriorityValueLabel1(String.valueOf(newPriority));
        } catch (IllegalArgumentException e) {
        }
    }

    public void onDecreasePriorityButton2Click() {
        int newPriority = thread2.getPriority() - 1;
        try {
            thread2.setPriority(newPriority);
            view.updatePriorityValueLabel2(String.valueOf(newPriority));
        } catch (IllegalArgumentException e) {
        }
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

    private void sleep() {
        try {
            Thread.sleep(TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initThreads() {
        increaseCounter = () -> {
            while (true) {
                synchronized (view) {
                    model.increase(INCREMENT);
                    System.out.println("increase -> " + model.getCounter());
                    updateSlider(model.getCounter());
                    sleep();
                }
            }
        };

        decreaseCounter = () -> {
            while (true) {
                synchronized (view) {
                    model.decrease(INCREMENT);
                    System.out.println("decrease -> " + model.getCounter());
                    updateSlider(model.getCounter());
                    sleep();
                }
            }
        };

        thread1 = new Thread(increaseCounter);
        thread2 = new Thread(decreaseCounter);
    }

    private void initButtonClicks() {
        Button increasePriorityButton1 = view.getIncreasePriorityButton1();
        Button increasePriorityButton2 = view.getIncreasePriorityButton2();
        Button decreasePriorityButton1 = view.getDecreasePriorityButton1();
        Button decreasePriorityButton2 = view.getDecreasePriorityButton2();

        increasePriorityButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                onIncreasePriorityButton1Click();
            }
        });

        increasePriorityButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                onIncreasePriorityButton2Click();
            }
        });

        decreasePriorityButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                onDecreasePriorityButton1Click();
            }
        });

        decreasePriorityButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                onDecreasePriorityButton2Click();
            }
        });
    }
}
