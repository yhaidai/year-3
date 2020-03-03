package lab3;

public class Main {

    public static void main(String[] args) {
        Table table = new Table();
        Smoker smoker1 = Smoker.createSmokerWithMatches(table);
        Smoker smoker2 = Smoker.createSmokerWithPaper(table);
        Smoker smoker3 = Smoker.createSmokerWithTobacco(table);
        Mediator mediator = new Mediator(table);

        Thread smokerThread1 = new Thread(smoker1);
        Thread smokerThread2 = new Thread(smoker2);
        Thread smokerThread3 = new Thread(smoker3);
        Thread mediatorThread = new Thread(mediator);

        smokerThread1.start();
        smokerThread2.start();
        smokerThread3.start();
        mediatorThread.start();
    }
}
