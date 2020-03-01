package com.company;

public class Winnie implements Runnable{
    HoneyPot pot;
    volatile boolean sleep;

    public Winnie(HoneyPot pot) {
        this.pot = pot;
        sleep = true;
    }

    public void wakeUp() {
        System.out.println("Good morning, bees!");
        sleep = false;
    }

    public void eatHoney() {
        pot.empty();
        System.out.println("Yummy yummy!");
        sleep = true;
    }

    public void run() {
        while (true) {
            if (!sleep) {
                eatHoney();
            }
        }
    }
}
