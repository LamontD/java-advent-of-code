package com.lamontd.adventofcode.utils;

import lombok.Builder;
import org.javatuples.Pair;

import java.util.Objects;

@Builder
public class Coordinate {
    private final Pair<Integer, Integer> coord;

    public Coordinate() {
        this.coord = new Pair<>(0, 0);
    }

    public Coordinate(int xCoord, int yCoord) {
        this.coord = Pair.with(xCoord, yCoord);
    }

    public Coordinate(Pair<Integer, Integer> intPair) { this.coord = Pair.with(intPair.getValue0(), intPair.getValue1()); }

    public int getX() { return this.coord.getValue0(); }
    public int getY() { return this.coord.getValue1(); }

    public void setX(int x) { this.coord.setAt0(x); }
    public void setY(int y) { this.coord.setAt1(y); }

    public Pair<Integer, Integer> toPair() { return Pair.with(coord.getValue0(), coord.getValue1()); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return coord.equals(that.coord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coord);
    }

    @Override
    public String toString() {
        return "(" + coord.getValue0() + "," + coord.getValue1() + ")";
    }
}
