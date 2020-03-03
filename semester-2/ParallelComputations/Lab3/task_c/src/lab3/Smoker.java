package lab3;

import java.util.concurrent.atomic.AtomicBoolean;

public class Smoker implements Runnable{
    private boolean hasMatches;
    private boolean hasPaper;
    private boolean hasTobacco;
    private Table table;
    private static AtomicBoolean isSmoking = new AtomicBoolean(false);

    private Smoker(boolean hasMatches, boolean hasPaper, boolean hasTobacco, Table table) {
        this.hasMatches = hasMatches;
        this.hasPaper = hasPaper;
        this.hasTobacco = hasTobacco;
        this.table = table;
    }

    public static Smoker createSmokerWithMatches(Table table) {
        return new Smoker(true, false, false, table);
    }

    public static Smoker createSmokerWithPaper(Table table) {
        return new Smoker(false, true, false, table);
    }

    public static Smoker createSmokerWithTobacco(Table table) {
        return new Smoker(false, false, true, table);
    }

    public static boolean isSmoking() {
        return isSmoking.get();
    }

    private void tryToSmoke() {
        try {
            Table.semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this + " acquired lock");

        if (hasMatches && table.hasPaperAndTobacco() ||
                hasPaper && table.hasMatchesAndTobacco() ||
                hasTobacco && table.hasMatchesAndPaper()) {
            table.removeItems();
            smoke();
        }

        System.out.println(this + " released lock");
        Table.semaphore.release();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void smoke() {
        isSmoking.set(true);

        System.out.println(this + ": smoking...");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isSmoking.set(false);
    }

    @Override
    public void run() {
        while (true) {
            if (!isSmoking()) {
                tryToSmoke();
            }
        }
    }

    @Override
    public String toString() {
        if (hasMatches && !hasPaper && !hasTobacco) {
            return "Smoker with matches";
        } else if (!hasMatches && hasPaper && !hasTobacco) {
            return "Smoker with paper";
        } else if (!hasMatches && !hasPaper && hasTobacco) {
            return "Smoker with tobacco";
        } else {
            return "";
        }
    }
}
