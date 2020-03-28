package lab4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Graph {
    private int size;
    private List<List<Integer>> adjMatrix;
    private ReadWriteLock lock;

    public Graph(int size) {
        this.size = size;
        adjMatrix = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            adjMatrix.add(new ArrayList<>(Collections.nCopies(size, -1)));
        }
        lock = new ReentrantReadWriteLock();
    }

    public void changePrice(int i, int j, int price) {
        lock.writeLock().lock();
        if (i != j && !adjMatrix.get(i).get(j).equals(-1)) {
            adjMatrix.get(i).set(j, price);
            adjMatrix.get(j).set(i, price);
        }
        lock.writeLock().unlock();
    }

    public void addRoute(int i, int j, int price) {
        lock.writeLock().lock();
        if (i != j && adjMatrix.get(i).get(j).equals(-1)) {
            adjMatrix.get(i).set(j, price);
            adjMatrix.get(j).set(i, price);
        }
        lock.writeLock().unlock();
    }

    public void removeRoute(int i, int j) {
        lock.writeLock().lock();
        adjMatrix.get(i).set(j, -1);
        lock.writeLock().unlock();
    }

    public void addCity() {
        lock.writeLock().lock();
        for (List<Integer> row : adjMatrix) {
            row.add(-1);
        }
        size++;
        adjMatrix.add(new ArrayList<>(Collections.nCopies(size, -1)));
        lock.writeLock().unlock();
    }

    public void removeCity(int i) {
        lock.writeLock().lock();
        adjMatrix.remove(i);
        size--;
        for (List<Integer> row : adjMatrix) {
            row.remove(i);
        }
        lock.writeLock().unlock();
    }

    public Integer getPrice(int i, int j) {
        lock.readLock().lock();
        List<Boolean> visited = new ArrayList<>(Collections.nCopies(size, false));
        visited.set(i, true);
        Integer result =  dfs(i, j, visited);
        lock.readLock().unlock();
        return result;
    }

    private Integer dfs(int i, int j, List<Boolean> visited) {
        int price = 0;

        for (int k = 0; k < size; k++) {
            if (!adjMatrix.get(i).get(k).equals(-1)) {
                price = adjMatrix.get(i).get(k);

                if (k == j) {
                    return price;
                }

                if (visited.get(k).equals(false)) {
                    visited.set(k, true);
                    return price + dfs(k, j, visited);
                }
            }
        }

        return -1;
    }

    public int getSize() {
        return size;
    }

    public synchronized void print() {
        lock.readLock().lock();
        for (int i = 0; i < size; i++) {
            String row = new String();
            for (int j = 0; j < size; j++) {
                row += adjMatrix.get(i).get(j) + " ";
            }
            System.out.println(row);
        }
        System.out.println("\n");
        lock.readLock().unlock();
    }
}
