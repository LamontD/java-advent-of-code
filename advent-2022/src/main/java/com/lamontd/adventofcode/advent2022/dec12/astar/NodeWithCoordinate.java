package com.lamontd.adventofcode.advent2022.dec12.astar;

import com.lamontd.adventofcode.utils.Coordinate;

import java.util.Objects;

public class NodeWithCoordinate implements Comparable<NodeWithCoordinate> {
    private final String name;
    private final Coordinate coord;

    public NodeWithCoordinate(String name, Coordinate coord) {
        this.name = name;
        this.coord = Coordinate.builder().x(coord.getX()).y(coord.getY()).build();
    }

    public NodeWithCoordinate(Coordinate coord) {
        this.name = coord.toString();
        this.coord = Coordinate.builder().x(coord.getX()).y(coord.getY()).build();
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoord() {
        return coord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeWithCoordinate that = (NodeWithCoordinate) o;
        return name.equals(that.name) && coord.equals(that.coord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coord);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(NodeWithCoordinate o) {
        return name.compareTo(o.name);
    }
}
