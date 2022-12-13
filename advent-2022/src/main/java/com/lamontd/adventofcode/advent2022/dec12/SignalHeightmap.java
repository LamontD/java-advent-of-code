package com.lamontd.adventofcode.advent2022.dec12;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import com.lamontd.adventofcode.advent2022.dec12.astar.NodeWithCoordinate;
import com.lamontd.adventofcode.utils.Coordinate;
import com.lamontd.adventofcode.utils.SingleValueIntGrid;

import java.util.ArrayList;
import java.util.List;

public class SignalHeightmap extends SingleValueIntGrid {
    private Coordinate startingPosition;
    private Coordinate bestSignal;
    public SignalHeightmap(List<String> layout) {
        super(layout);
    }

    public Coordinate getStartingPosition() {
        return startingPosition;
    }

    public Coordinate getBestSignal() {
        return bestSignal;
    }

    @Override
    protected int generateIntValueFromLayoutCharacter(int row, int col, Character cellCharacter) {
        switch(cellCharacter) {
            case 'S':
                startingPosition = Coordinate.builder().x(row).y(col).build();
                return 0;
            case 'E':
                bestSignal = Coordinate.builder().x(row).y(col).build();
                return 25;
            default:
                return cellCharacter - 'a';
        }
    }

    public List<Coordinate> findAllValleys() {
        List<Coordinate> valleyCoords = new ArrayList<>();
        for (int row = 0; row < intGrid.length; row++) {
            for (int col = 0; col < intGrid[0].length; col++) {
                if (intGrid[row][col] == 0) {
                    valleyCoords.add(Coordinate.of(row, col));
                }
            }
        }
        return valleyCoords;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SignalHeightmap with dimensions ").append(intGrid.length).append("x").append(intGrid[0].length).append("\n");
        for (int row = 0; row < intGrid.length; row++) {
            for (int col = 0; col < intGrid[0].length; col++) {
                if (Coordinate.of(row, col).equals(startingPosition)) {
                    sb.append('S');
                } else if (Coordinate.of(row, col).equals(bestSignal)) {
                    sb.append('E');
                } else {
                    sb.append(Character.toString('a' + intGrid[row][col]));
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public ValueGraph<NodeWithCoordinate, Double> toDirectedEdgeGraph() {
        MutableValueGraph<NodeWithCoordinate, Double> edgeGraph = ValueGraphBuilder.directed().build();
        for (int row = 0; row < intGrid.length; row++) {
            for (int col = 0; col < intGrid[0].length; col++) {
                int sourceValue = intGrid[row][col];
                Coordinate sourceCoordinate = Coordinate.of(row, col);
                for (Coordinate adjacentCoord : getValidBasicAdjacentPoints(Coordinate.of(row, col))) {
                    int destinationValue = intGrid[adjacentCoord.getX()][adjacentCoord.getY()];
                    if (destinationValue <= sourceValue + 1) {
                        edgeGraph.putEdgeValue(new NodeWithCoordinate(sourceCoordinate), new NodeWithCoordinate(adjacentCoord), 1.0);
                    }
                }
            }
        }
        return edgeGraph;
    }
}
