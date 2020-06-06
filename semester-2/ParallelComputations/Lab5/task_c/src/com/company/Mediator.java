package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Mediator {
    private List<SummingArray> arrays;
    private int arrayCount;
    private Integer maxSum;

    public Mediator(SummingArray... elements) {
        this.arrayCount = elements.length;
        arrays = new ArrayList<>();

        for (int i = 0; i < arrayCount; i++) {
            SummingArray array = elements[i];
            arrays.add(array);
        }
    }

    public void start() {
        List<Thread> threads = new ArrayList<>();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(arrayCount, () -> {
            System.out.println(this);
        });

        for (SummingArray array : arrays) {
            Runnable arrayModifier = new ArrayModifier(array, this,  cyclicBarrier);
            Thread thread = new Thread(arrayModifier);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean sumsAreEqual() {
        boolean result = true;
        Integer sum = arrays.get(0).getSum();
        maxSum = sum;

        for (int i = 1; i < arrayCount; i++) {
            Integer currentSum = arrays.get(i).getSum();

            if (currentSum > maxSum) {
                maxSum = currentSum;
            }

            if (!sum.equals(currentSum)) {
                result = false;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("{\n");
        for (SummingArray array : arrays) {
            result.append(array).append("\n");
        }
        result.append("}");

        return String.valueOf(result);
    }

    public Integer getMaxSum() {
        return maxSum;
    }
}
