package com.lamontd.aoc.advent2020.dec17;

import org.javatuples.Triplet;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ConwayCoordinate implements Comparable<ConwayCoordinate> {
    private final Triplet<Integer, Integer, Integer> coordinate;
    private final Set<ConwayCoordinate> neighbors = new HashSet<>();

    public ConwayCoordinate(int xPos, int yPos, int zPos) {
        this.coordinate = Triplet.with(xPos, yPos, zPos);
    }

    public static ConwayCoordinate at(int xPos, int yPos, int zPos) {
        return new ConwayCoordinate(xPos, yPos, zPos);
    }

    public int getX() { return coordinate.getValue0(); }
    public int getY() { return coordinate.getValue1(); }
    public int getZ() { return coordinate.getValue2(); }

    public Set<ConwayCoordinate> getNeighbors() {
        if (neighbors.isEmpty()) {
            for (int z = getZ() - 1; z <= getZ() + 1; z++) {
                for (int y = getY() - 1; y <= getY() + 1; y++) {
                    for (int x = getX() - 1; x <= getX() + 1; x++) {
                        neighbors.add(at(x, y, z));
                    }
                }
            }
            neighbors.remove(this);
        }
        return neighbors;
    }

    @Override
    public int compareTo(ConwayCoordinate rhs) {
        return coordinate.compareTo(rhs.coordinate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConwayCoordinate that = (ConwayCoordinate) o;
        return coordinate.equals(that.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate);
    }

    @Override
    public String toString() { return coordinate.toString(); }
}
