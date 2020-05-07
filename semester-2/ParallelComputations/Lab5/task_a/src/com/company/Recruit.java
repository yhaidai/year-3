package com.company;

import java.util.Random;

public class Recruit {
    private Orientation orientation;

    public Recruit() {
        orientation = Orientation.FORWARD;
    }

    public void turn() {
        Random random = new Random();
        boolean side = random.nextBoolean();
        if (side) {
            this.turnLeft();
        } else {
            this.turnRight();
        }
    }

    public void turnAround() {
        if (orientation == Orientation.LEFT) {
            orientation = Orientation.RIGHT;
        } else if (orientation == Orientation.RIGHT) {
            orientation = Orientation.LEFT;
        }
    }

    public boolean faces(Recruit other) {
        return this.orientation == Orientation.RIGHT && other.orientation == Orientation.LEFT;
    }

    @Override
    public String toString() {
        String result;

        switch (orientation) {
            case LEFT:
                result = "L";
                break;
            case RIGHT:
                result = "R";
                break;
            default:
                result = "F";
        }
        return result;
    }

    private void turnLeft() {
        orientation = Orientation.LEFT;
    }

    private void turnRight() {
        orientation = Orientation.RIGHT;
    }
}
