package lab3;

public class Visitor implements Runnable {
    private String name;
    private Barber barber;
    private boolean cutStarted;
    private boolean cutFinished;

    Visitor(String name, Barber barber) {
        this.name = name;
        this.barber = barber;
        cutStarted = false;
        cutFinished = false;
    }

    private void comeToBarbershop() {
        boolean wasSleeping = barber.sleeping.compareAndSet(true, false);

        if (!wasSleeping) {
            barber.putVisitorIntoQueue(this);
            sleepInQueue();
        } else {
            sit();
            wakeUpBarber();
        }

        sleepInChair();
    }

    @Override
    public void run() {
        comeToBarbershop();
    }

    private synchronized void sleepInChair() {
        System.out.println(this + " is sleeping in the chair");
        while (!cutFinished) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void sleepInQueue() {
        System.out.println(this + " is sleeping in the queue");
        while (!cutStarted) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sit() {
        synchronized (System.out) {
            System.out.println(this + "'s getting into the chair...");
        }

        barber.setCurrent(this);
        barber.sleeping.set(false);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void wakeUpBarber() {
        synchronized (System.out) {
            System.out.println(this + " woke the barber up");
        }

        synchronized (barber) {
            barber.notify();
        }
    }

    public void startCut() {
        cutStarted = true;
    }

    public void finishCut() {
        cutFinished = true;
    }

    @Override
    public String toString() {
        return name;
    }
}
