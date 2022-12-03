package com.lamontd.aoc.advent2021.dec15;

import com.lamontd.aoc.utils.Coordinate;
import com.lamontd.aoc.utils.SingleValueIntGrid;

import java.util.ArrayList;
import java.util.List;

public class CoordinateGraph {
    private final List<Coordinate> vertexes;
    private final List<MapEdge> edges;

    public CoordinateGraph() {
        this.vertexes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public CoordinateGraph(List<Coordinate> vertexes, List<MapEdge> edges) {
        this.vertexes = new ArrayList<>(vertexes);
        this.edges = new ArrayList<>(edges);
    }

    public boolean supportsDiagonals = false;

    public List<MapEdge> getEdges() { return this.edges; }
    public List<Coordinate> getVertexes() { return this.vertexes; }
    public boolean isSupportsDiagonals() { return supportsDiagonals; }
    public void setSupportsDiagonals(boolean supportsDiagonals) { this.supportsDiagonals = supportsDiagonals; }

    public void buildFromGrid(SingleValueIntGrid intGrid) {
        for (int row = 0; row < intGrid.getNumRows(); row++) {
            for (int col = 0; col < intGrid.getNumColumns(); col++) {
                Coordinate source = new Coordinate(row, col);
                vertexes.add(source);
                for (Coordinate destination : supportsDiagonals
                        ? intGrid.getValidFullAdjacentPoints(source) : intGrid.getValidBasicAdjacentPoints(source)) {
                    if (!vertexes.contains(destination)) {
                        edges.add(MapEdge.builder().source(source).destination(destination).weight(intGrid.getGridValue(destination)).build());
                    }
                }
            }
        }
    }

}
