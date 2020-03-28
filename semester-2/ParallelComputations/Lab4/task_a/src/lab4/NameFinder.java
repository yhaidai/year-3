package lab4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NameFinder implements Runnable {
    private Database database;
    private List<String> phoneNumbers;

    public NameFinder(Database database, String... phoneNumbers) {
        this.database = database;
        this.phoneNumbers = new ArrayList<>(Arrays.asList(phoneNumbers));
    }

    @Override
    public void run() {
        for (String phoneNumber : phoneNumbers) {
            String name = database.findName(phoneNumber);
            System.out.println("Found: " + name + " by phone number: " + phoneNumber);
        }
    }
}
