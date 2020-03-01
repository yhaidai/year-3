package com.company;

public class Bee implements Runnable{
    private HoneyPot pot;
    private Winnie winnie;

    public Bee(HoneyPot pot, Winnie winnie) {
        this.pot = pot;
        this.winnie = winnie;
    }

    public void fillHoneyPot() {
        synchronized (pot) {
            if (!pot.isFull()) {
                pot.fill();
                if (pot.isFull()) {
                    System.out.println("Winnie, wake up!");
                    winnie.wakeUp();
                }
            }
        }

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            fillHoneyPot();
        }
    }
}
