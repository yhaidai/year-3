package com.company;

import java.util.List;
import java.util.Random;

public class SummingArray {
    private final List<Integer> numbers;
    private Integer sum;

    public SummingArray(List<Integer> numbers) {
        this.numbers = numbers;

        sum = 0;
        for (Integer number : numbers) {
            sum += number;
        }
    }

    public Integer getSum() {
        return sum;
    }

    public void increase() {
        Random random = new Random();
        int index = random.nextInt(numbers.size());
        Integer value = numbers.get(index);
        numbers.set(index, value + 1);
        sum++;
    }

    public void decrease() {
        Random random = new Random();
        int index = random.nextInt(numbers.size());
        Integer value = numbers.get(index);
        numbers.set(index, value - 1);
        sum--;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("[ ");
        for (Integer number : numbers) {
            result.append(number).append(" ");
        }
        result.append("] -> ").append(getSum());

        return String.valueOf(result);
    }
}
