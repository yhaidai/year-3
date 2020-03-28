package lab4;

import java.util.List;

public class Writer implements Runnable{
    private Database database;
    private List<Record> recordsToAdd;
    private List<Record> recordsToRemove;

    public Writer(Database database, List<Record> recordsToAdd, List<Record> recordsToRemove) {
        this.database = database;
        this.recordsToAdd = recordsToAdd;
        this.recordsToRemove = recordsToRemove;
    }

    @Override
    public void run() {
        for (Record record : recordsToAdd) {
            database.addRecord(record);
            System.out.println("Added record: " + record);
        }

        for (Record record : recordsToAdd) {
            database.removeRecord(record);
            System.out.println("Removed record: " + record);
        }
    }
}
