package lab3;

import java.util.Random;

public class Mediator implements Runnable {
    private Table table;

    public Mediator(Table table) {
        this.table = table;
    }

    private void putTwoRandomItemsOnTheTable() {
        Random random = new Random();
        int randomInt = random.nextInt(3);

        try {
            Table.semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Mediator acquired lock");

        switch (randomInt) {
        case 0:
            table.placeMatchesAndPaper();
            System.out.println("Mediator has put matches and paper on the table");
            break;
        case 1:
            table.placeMatchesAndTobacco();
            System.out.println("Mediator has put matches and tobacco on the table");
            break;
        case 2:
            table.placePaperAndTobacco();
            System.out.println("Mediator has put paper and tobacco on the table");
            break;
        }

        Table.semaphore.release();
        System.out.println("Mediator released lock");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (!Smoker.isSmoking() && table.isEmpty()) {
                putTwoRandomItemsOnTheTable();
            }
        }
    }
}
