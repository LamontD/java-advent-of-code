package com.lamontd.adventofcode.advent2020.dec17;

import org.javatuples.Quartet;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ConwayHypercoordinate implements Comparable<ConwayHypercoordinate> {
    private final Quartet<Integer, Integer, Integer, Integer> coordinate;
    private final Set<ConwayHypercoordinate> neighbors = new HashSet<>();

    public ConwayHypercoordinate(int xPos, int yPos, int zPos, int wPos) {
        this.coordinate = Quartet.with(xPos, yPos, zPos, wPos);
    }

    public static ConwayHypercoordinate at(int xPos, int yPos, int zPos, int wPos) {
        return new ConwayHypercoordinate(xPos, yPos, zPos, wPos);
    }

    public int getX() { return coordinate.getValue0(); }
    public int getY() { return coordinate.getValue1(); }
    public int getZ() { return coordinate.getValue2(); }
    public int getW() { return coordinate.getValue3(); }

    public Set<ConwayHypercoordinate> getNeighbors() {
        if (neighbors.isEmpty()) {
            for (int w = getW() - 1; w <= getW() + 1; w++) {
                for (int z = getZ() - 1; z <= getZ() + 1; z++) {
                    for (int y = getY() - 1; y <= getY() + 1; y++) {
                        for (int x = getX() - 1; x <= getX() + 1; x++) {
                            neighbors.add(at(x, y, z, w));
                        }
                    }
                }
            }
            neighbors.remove(this);
        }
        return neighbors;
    }

    @Override
    public int compareTo(ConwayHypercoordinate rhs) {
        return coordinate.compareTo(rhs.coordinate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConwayHypercoordinate that = (ConwayHypercoordinate) o;
        return coordinate.equals(that.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate);
    }

    @Override
    public String toString() { return coordinate.toString(); }
}
