package com.company;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        SummingArray summingArray1 = new SummingArray(Arrays.asList(1, 2, 4));
        SummingArray summingArray2 = new SummingArray(Arrays.asList(1, 2, 2));
        SummingArray summingArray3 = new SummingArray(Arrays.asList(3, 2, 1));
        Mediator mediator = new Mediator(summingArray1, summingArray2, summingArray3);

        System.out.println(mediator);
        mediator.start();
        System.out.println("end");
    }
}
