package com.lamontd.aoc.advent2020.dec05;

class Range {
    int upper;
    int lower;

    Range(int lower, int upper) {
        this.upper = upper;
        this.lower = lower;
    }

    public int getScale() {
        return upper - lower + 1;
    }

    public void takeUpperHalf() {
        this.lower += getScale() / 2;
    }
    public void takeLowerHalf() {
        this.upper -= getScale() / 2;
    }

    public int getLower() { return lower; }
    public int getUpper() { return upper; }
}
