package lab3;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Barber implements Runnable {
    private BlockingQueue<Visitor> visitors;
    private Visitor current;
    AtomicBoolean sleeping;

    public Barber() {
        visitors = new LinkedBlockingQueue<Visitor>();
        current = null;
        sleeping = new AtomicBoolean(true);
        sleep();
    }

    public void setCurrent(Visitor visitor) {
        current = visitor;
    }

    public void putVisitorIntoQueue(Visitor visitor) {
        try {
            visitors.put(visitor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (System.out) {
            System.out.println(visitor + " has entered the queue");
        }
    }

    public boolean isSleeping() {
        return sleeping.get();
    }

    public void sleep() {
        current = null;
        sleeping.set(true);
    }

    public void wakeUpVisitor(Visitor visitor) {
        synchronized (visitor) {
            visitor.notify();
        }
    }

    private void cut() {
        synchronized (System.out) {
            System.out.println("Barber: Cutting " + current + "'s hair...");
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (current) {
            current.finishCut();
            current.notify();
        }
    }

    private void seeOff() {
        synchronized (System.out) {
            System.out.println("Barber walked " + current + " out");
        }

        if (!visitors.isEmpty()) {
            try {
                Visitor next = visitors.take();
                next.startCut();
                next.sit();
                wakeUpVisitor(next);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            sleep();
        }
    }

    @Override
    public void run() {
        while (true) {
            while (isSleeping()) {
                try {
                    synchronized (System.out) {
                        System.out.println("Barber fell asleep");
                    }

                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            cut();
            seeOff();
        }
    }
}
