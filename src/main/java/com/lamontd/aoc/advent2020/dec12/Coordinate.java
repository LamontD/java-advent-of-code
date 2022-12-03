package com.lamontd.aoc.advent2020.dec12;

import java.util.Objects;

public class Coordinate {
    private int north;
    private int east;
    public Coordinate() {
        this.north = 0;
        this.east = 0;
    }
    public void moveNorth(int northValue) { this.north += northValue; }
    public void moveSouth(int southValue) { this.north -= southValue; }
    public void moveEast(int value) { this.east += value; }
    public void moveWest(int value) { this.east -= value; }
    public int getManhattanDistance() { return Math.abs(this.north) + Math.abs(this.east); }

    public int getNorth() {
        return north;
    }

    public int getEast() {
        return east;
    }

    @Override
    public String toString() {
        return "Coordinate (" + this.north + "N by " + this.east + "E)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return north == that.north &&
                east == that.east;
    }

    @Override
    public int hashCode() {
        return Objects.hash(north, east);
    }

    public static class Builder {
        int northValue;
        int eastValue;

        public Builder north(int value) { this.northValue = value; return this; }
        public Builder south(int value) { this.northValue = -value; return this; }
        public Builder east(int value) { this.eastValue = value; return this; }
        public Builder west(int value) { this.eastValue = -value; return this; }
        public Coordinate build() {
            Coordinate coord = new Coordinate();
            coord.north = northValue;
            coord.east = eastValue;
            return coord;
        }
    }
}
