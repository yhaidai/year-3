package com.company;

public class Winnie implements Runnable {
    HoneyPot pot;
    volatile boolean sleep;

    public Winnie(HoneyPot pot) {
        this.pot = pot;
        sleep = true;
    }

    public synchronized void wakeUp() {
        System.out.println("Good morning, bees!");
        sleep = false;
        notify();
    }

    public void eatHoney() {
        pot.empty();
        System.out.println("Yummy yummy!");
        sleep = true;
    }

    public synchronized void run() {
        while (true) {
            while (sleep) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            eatHoney();
        }
    }
}
