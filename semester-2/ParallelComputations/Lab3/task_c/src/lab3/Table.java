package lab3;

import java.util.concurrent.Semaphore;

public class Table {
    private boolean hasMatches;
    private boolean hasPaper;
    private boolean hasTobacco;
    static Semaphore semaphore = new Semaphore(1);

    public Table() {
        removeItems();
    }

    public void placeMatchesAndPaper() {
        hasMatches = true;
        hasPaper = true;
    }

    public void placeMatchesAndTobacco() {
        hasMatches = true;
        hasTobacco = true;
    }

    public void placePaperAndTobacco() {
        hasPaper = true;
        hasTobacco = true;
    }

    public boolean hasMatchesAndPaper() {
        return (hasMatches && hasPaper);
    }

    public boolean hasMatchesAndTobacco() {
        return (hasMatches && hasTobacco);
    }

    public boolean hasPaperAndTobacco() {
        return (hasPaper && hasTobacco);
    }

    public boolean isEmpty() {
        return (!hasMatches && !hasPaper && !hasTobacco);
    }

    public void removeItems() {
        hasMatches = false;
        hasPaper = false;
        hasTobacco = false;
    }
}
