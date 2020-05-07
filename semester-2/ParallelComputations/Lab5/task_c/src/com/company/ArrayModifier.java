package com.company;


import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ArrayModifier implements Runnable {
    private SummingArray numbers;
    private final Mediator mediator;
    private CyclicBarrier cyclicBarrier;

    public ArrayModifier(SummingArray numbers, Mediator mediator, CyclicBarrier cyclicBarrier) {
        this.numbers = numbers;
        this.mediator = mediator;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        while (!mediator.sumsAreEqual()) {
            synchronized (mediator) {
                modify();
            }

            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        if (cyclicBarrier.getNumberWaiting() != 0) {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    private void modify() {
        if (mediator.sumsAreEqual()) {
            return;
        }

        if (numbers.getSum().equals(mediator.getMaxSum())) {
            numbers.decrease();
        } else {
            Random random = new Random();
            boolean b = random.nextBoolean();
            if (b) {
                numbers.increase();
            } else {
                numbers.decrease();
            }
        }
    }
}
