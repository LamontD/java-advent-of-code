package com.lamontd.aoc.advent2020.dec23;

public class Cup {
    private Cup nextCup;
    private Cup previousCup;
    private final int cupValue;

    public Cup(int value) { this.cupValue = value; }

    public Cup getNextCup() {
        return nextCup;
    }

    public void setNextCup(Cup nextCup) {
        this.nextCup = nextCup;
    }

    public Cup getPreviousCup() {
        return previousCup;
    }

    public void setPreviousCup(Cup previousCup) {
        this.previousCup = previousCup;
    }

    public int getCupValue() {
        return cupValue;
    }

    @Override
    public String toString() {
        return "Cup{" +
                "cupValue=" + cupValue +
                '}';
    }
}
