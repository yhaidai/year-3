package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class FormationPart implements Runnable {
    private List<Recruit> recruits;
    private CyclicBarrier cyclicBarrier;
    private FormationPart prev;
    private Formation formation;
    private boolean done;


    public FormationPart(CyclicBarrier cyclicBarrier, Formation formation, FormationPart prev) {
        recruits = new ArrayList<>();
        this.cyclicBarrier = cyclicBarrier;
        this.formation = formation;
        this.prev = prev;
    }

    public void add(Recruit recruit) {
        recruits.add(recruit);
    }

    public Recruit get(int index) {
        return recruits.get(index);
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public void run() {
        for (Recruit recruit : recruits) {
            recruit.turn();
        }

        do {
            done = true;

            for (int i = 0; i < recruits.size() - 1; i++) {
                Recruit first = recruits.get(i);
                Recruit second = recruits.get(i + 1);
                if (first.faces(second)) {
                    first.turnAround();
                    second.turnAround();
                    done = false;
                    i++;
                }
            }

            if (prev != null) {
                Recruit first = prev.get(Formation.MIN_PART_CAPACITY - 1);
                Recruit second = recruits.get(0);
                if (first.faces(second)) {
                    first.turnAround();
                    second.turnAround();
                    done = false;
                }
            }

            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        } while (!done);

        while (!formation.isDone()) {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Recruit recruit : recruits) {
            result.append(recruit).append(" ");
        }
        return result.toString();
    }
}
