package com.company;

public class Main {
    private static final int beesCount = 9;
    private static final int honeyPotCapacity = 30;

    public static void main(String[] args) {
        HoneyPot pot = new HoneyPot(honeyPotCapacity);

        Winnie winnie = new Winnie(pot);
        Thread winnieThread = new Thread(winnie);
        winnieThread.start();

	    for (int i = 0; i < beesCount; i++) {
	        Bee bee = new Bee(pot, winnie);
            Thread beeThread = new Thread(bee);
            beeThread.start();
        }
    }
}
