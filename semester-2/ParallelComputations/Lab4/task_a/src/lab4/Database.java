package lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {
    private List<Record> records;
    private ReadWriteLock lock;

    public Database() {
        records = new ArrayList<>();
        lock = new ReentrantReadWriteLock();
        initRecords();
    }

    public String findPhoneNumber(String lastName) {
        String result = new String();
        lock.readLock().lock();

        for (Record record : records) {
            if (record.getName().contains(lastName)) {
                result =  record.getPhoneNumber();
            }
        }

        lock.readLock().unlock();
        return result;
    }

    public String findName(String phoneNumber) {
        String result = new String();
        lock.readLock().lock();

        for (Record record : records) {
            if (record.getPhoneNumber().equals(phoneNumber)) {
                result = record.getName();
            }
        }

        lock.readLock().unlock();
        return result;
    }

    public void addRecord(Record record) {
        lock.writeLock().lock();
        records.add(record);
        lock.writeLock().unlock();
    }

    public void removeRecord(Record record) {
        lock.writeLock().lock();
        records.remove(record);
        lock.writeLock().unlock();
    }

    private void initRecords() {
        records.add(new Record("John Hawking", "+13285782934"));
        records.add(new Record("Justin Blake", "+79845733495"));
    }
}
