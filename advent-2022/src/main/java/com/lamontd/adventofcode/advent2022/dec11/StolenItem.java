package com.lamontd.adventofcode.advent2022.dec11;

public class StolenItem {
    private long startingWorryLevel;
    private long currentWorryLevel;

    public StolenItem(long worryLevel) {
        this.startingWorryLevel = worryLevel;
        this.currentWorryLevel = worryLevel;
    }

    public void changeWorryLevel(long newWorryLevel) {
        this.currentWorryLevel = newWorryLevel;
    }

    public long getStartingWorryLevel() {
        return startingWorryLevel;
    }

    public long getCurrentWorryLevel() {
        return currentWorryLevel;
    }

    public void setCurrentWorryLevel(long currentWorryLevel) {
        this.currentWorryLevel = currentWorryLevel;
    }

    @Override
    public String toString() {
        return "StolenItem: worryLevel = " + currentWorryLevel;
    }
}
