package lab4;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Database database = new Database();
        List<Record> recordsToAdd = new ArrayList<Record>();
        List<Record> recordsToRemove = new ArrayList<Record>();

        recordsToAdd.add(new Record("Jessica James", "+182578435093"));
        recordsToAdd.add(new Record("John Hawking", "+13285782934"));


        Runnable phoneNumberFinder = new PhoneNumberFinder(database, "Hawking", "Blake");
        Runnable nameFinder = new NameFinder(database, "+13285782934", "+79845733495");
        Runnable writer = new Writer(database, recordsToAdd, recordsToRemove);

	    Thread thread1 = new Thread(phoneNumberFinder);
        Thread thread2 = new Thread(nameFinder);
        Thread thread3 = new Thread(writer);
	    thread1.start();
	    thread2.start();
	    thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}