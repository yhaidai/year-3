package com.company;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int recruitCount = 171;
        Recruit[] recruits = new Recruit[recruitCount];
        for (int i = 0; i < recruitCount; i++) {
            Recruit recruit = new Recruit();
            recruits[i] = recruit;
        }
        Formation formation = new Formation(recruits);
        formation.obeyCommand();
    }
}
