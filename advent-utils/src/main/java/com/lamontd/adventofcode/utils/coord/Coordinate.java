package com.lamontd.adventofcode.utils.coord;

import lombok.Builder;
import org.javatuples.Pair;

import java.util.Objects;

@Builder
public class Coordinate implements Comparable<Coordinate> {
    private int x;
    private int y;

    public Coordinate() {
        this.x = 0;
        this.y = 0;
    }

    public Coordinate(int xCoord, int yCoord) {
        this.x = xCoord; this.y = yCoord;
    }

    public Coordinate(Pair<Integer, Integer> intPair) {
        this.x = intPair.getValue0();
        this.y = intPair.getValue1();
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }

    public void setX(int xValue) { this.x = xValue; }
    public void setY(int yValue) { this.y = yValue; }

    public void addX(int units) { x += units; }
    public void addY(int units) { y += units; }

    public void subtractX(int units) { x -= units; }
    public void subtractY(int units) { y -= units; }

    public Pair<Integer, Integer> toPair() { return Pair.with(x, y); }

    public static Coordinate of(int x, int y) { return Coordinate.builder().x(x).y(y).build(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Coordinate o) {
        if (this == o) return 0;
        if (this.x == o.x) {
            return Integer.compare(this.y, o.y);
        }
        return Integer.compare(this.x, o.x);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
