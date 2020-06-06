package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import static java.lang.Math.max;

public class Formation {
    List<FormationPart> parts;
    private int partCount;
    static int MIN_PART_CAPACITY = 50;
    private boolean done;

    public Formation(Recruit... recruits) {
        Runnable onBarrierPassed = () -> {
            System.out.println(this);
            done = true;
            for (FormationPart part : parts) {
                done &= part.isDone();
            }
        };
        partCount = max(1, recruits.length / MIN_PART_CAPACITY);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(partCount, onBarrierPassed);
        parts = new ArrayList<>();

        for (int i = 0; i < partCount; i++) {
            int start = i * MIN_PART_CAPACITY;
            FormationPart prev = (i == 0) ? null : parts.get(i - 1);
            FormationPart part = new FormationPart(cyclicBarrier, this, prev);
            int size = (recruits.length - start < 2 * MIN_PART_CAPACITY) ?
                    recruits.length - start : MIN_PART_CAPACITY;

            System.out.println(size);

            for (int j = start; j < start + size; j++) {
                part.add(recruits[j]);
            }

            parts.add(part);
        }
    }

    public boolean isDone() {
        return done;
    }

    public void obeyCommand() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (FormationPart part : parts) {
            Thread thread = new Thread(part);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (FormationPart part : parts) {
            result.append("[").append(part).append("]  ");
        }

        return result.toString();
    }
}
