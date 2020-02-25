package sample;

public class Model {
    int counter;
    int targetMin;
    int targetMax;

    public Model() {
        targetMin = 10;
        targetMax = 90;
        counter = (targetMin + targetMax) / 2;
    }

    public Model(int counter, int targetMin, int targetMax) {
        this.counter = counter;
        this.targetMin = targetMin;
        this.targetMax = targetMax;
    }

    public int getCounter() {
        return counter;
    }

    public synchronized void increase(int increment) {
        if (counter < targetMax) {
            counter += increment;
        } else {

        }
    }

    public synchronized void decrease(int decrement) {
        if (counter > targetMin) {
            counter -= decrement;
        }
    }
}
