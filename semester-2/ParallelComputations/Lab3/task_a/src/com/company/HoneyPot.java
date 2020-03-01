package com.company;

public class HoneyPot {
    private int capacity;
    private int size;
    private boolean isFull;

    public HoneyPot(int capacity) {
        this.capacity = capacity;
    }

    public void fill() {
        if (size < capacity) {
            size++;
            if (size == capacity) {
                isFull = true;
            }
        }
        System.out.println(size);
    }

    public void empty() {
        size = 0;
        isFull = false;
    }

    public boolean isFull() {
        return isFull;
    }
}
