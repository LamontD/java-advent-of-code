package com.lamontd.aoc.advent2021.dec15;

import com.lamontd.aoc.utils.Coordinate;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Builder
@Data
public class MapEdge {
    private Coordinate source;
    private Coordinate destination;
    private int weight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapEdge mapEdge = (MapEdge) o;
        return weight == mapEdge.weight && Objects.equals(source, mapEdge.source) && Objects.equals(destination, mapEdge.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination, weight);
    }
}
