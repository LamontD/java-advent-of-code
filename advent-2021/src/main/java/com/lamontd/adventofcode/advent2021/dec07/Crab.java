package com.lamontd.adventofcode.advent2021.dec07;

public class Crab {
    private int position;
    public Crab(int position) { this.position = position; }
    public int fuelForMove(int potentialPosition) { return Math.abs(this.position - potentialPosition); }
    public long crabbyFuelForMove(int potentialPosition) {
        long distance = fuelForMove(potentialPosition);
        return distance * (distance + 1) / 2;
    }
    public int getPosition() { return position; }
}
