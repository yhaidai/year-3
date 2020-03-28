package lab4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhoneNumberFinder implements Runnable {
    private Database database;
    private List<String> lastNames;

    public PhoneNumberFinder(Database database, String... lastNames) {
        this.database = database;
        this.lastNames = new ArrayList<String>(Arrays.asList(lastNames));
    }

    @Override
    public void run() {
        for (String lastName : lastNames) {
            String phoneNumber = database.findPhoneNumber(lastName);
            System.out.println("Found: " + phoneNumber + " by last name: " + lastName);
        }
    }

}
