package com.lamontd.adventofcode.utils;

import com.lamontd.adventofcode.utils.coord.CardinalDirections;
import com.lamontd.adventofcode.utils.coord.Coordinate;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SingleValueCharGrid {
    protected final char[][] charGrid;

    public SingleValueCharGrid(List<String> layout) {
        charGrid = new char[layout.size()][];
        for (int row = 0; row < layout.size(); row++) {
            char[] rowChars = layout.get(row).toCharArray();
            charGrid[row] = new char[rowChars.length];
            System.arraycopy(rowChars, 0, charGrid[row], 0, rowChars.length);
        }
    }

    protected int generateIntValueFromLayoutCharacter(int row, int col, Character cellCharacter) {
        return Integer.parseInt(Character.toString(cellCharacter));
    }

    protected List<CardinalDirections> calculateValidBasicCardinalDirections(Pair<Integer, Integer> point) {
        return calculateValidBasicCardinalDirections(point.getValue0(), point.getValue1());
    }

    protected List<CardinalDirections> calculateValidFullCardinalDirections(Pair<Integer, Integer> point) {
        return calculateValidFullCardinalDirections(point.getValue0(), point.getValue1());
    }

    protected List<CardinalDirections> calculateValidFullCardinalDirections(int row, int col) {
        List<CardinalDirections> validCardinalDirections = new ArrayList<>();
        for (CardinalDirections cardinalDirections : CardinalDirections.getFullDirections()) {
            int potentialRow = row + cardinalDirections.getRowAdjustment();
            int potentialCol = col + cardinalDirections.getColAdjustment();
            if (potentialRow < 0 || potentialCol < 0 || potentialRow >= charGrid.length || potentialCol >= charGrid[0].length) {
                continue;
            }
            validCardinalDirections.add(cardinalDirections);
        }
        return validCardinalDirections;
    }

    protected List<CardinalDirections> calculateValidBasicCardinalDirections(int row, int col) {
        List<CardinalDirections> validCardinalDirections = new ArrayList<>();
        for (CardinalDirections cardinalDirections : CardinalDirections.getBasicDirections()) {
            int potentialRow = row + cardinalDirections.getRowAdjustment();
            int potentialCol = col + cardinalDirections.getColAdjustment();
            if (potentialRow < 0 || potentialCol < 0 || potentialRow >= charGrid.length || potentialCol >= charGrid[0].length) {
                continue;
            }
            validCardinalDirections.add(cardinalDirections);
        }
        return validCardinalDirections;
    }

    public List<Pair<Integer, Integer>> getValidBasicAdjacentPoints(Pair<Integer, Integer> point) {
        return getValidBasicAdjacentPoints(point.getValue0(), point.getValue1());
    }

    public List<Coordinate> getValidBasicAdjacentPoints(Coordinate coordinate) {
        return getValidBasicAdjacentPoints(coordinate.getX(), coordinate.getY()).stream()
                .map(Coordinate::new).collect(Collectors.toList());
    }

    public List<Pair<Integer, Integer>> getValidBasicAdjacentPoints(int row, int col) {
        List<Pair<Integer, Integer>> adjacentPoints = new ArrayList<>();
        for (CardinalDirections validBasic : calculateValidBasicCardinalDirections(row, col)) {
            adjacentPoints.add(Pair.with(row+validBasic.getRowAdjustment(), col+validBasic.getColAdjustment()));
        }
        return adjacentPoints;
    }

    public List<Coordinate> getValidFullAdjacentPoints(Coordinate coord) {
        return getValidFullAdjacentPoints(coord.getX(), coord.getY()).stream()
                .map(Coordinate::new).collect(Collectors.toList());
    }

    public List<Pair<Integer, Integer>> getValidFullAdjacentPoints(Pair<Integer, Integer> point) {
        return getValidFullAdjacentPoints(point.getValue0(), point.getValue1());
    }

    public List<Pair<Integer, Integer>> getValidFullAdjacentPoints(int row, int col) {
        List<Pair<Integer, Integer>> adjacentPoints = new ArrayList<>();
        for (CardinalDirections validBasic : calculateValidFullCardinalDirections(row, col)) {
            adjacentPoints.add(Pair.with(row+validBasic.getRowAdjustment(), col+validBasic.getColAdjustment()));
        }
        return adjacentPoints;
    }

    public char getGridValue(Pair<Integer, Integer> point) { return getGridValue(point.getValue0(), point.getValue1()); }
    public char getGridValue(int row, int col) { return charGrid[row][col]; }
    public char getGridValue(Coordinate coordinate) { return getGridValue(coordinate.getX(), coordinate.getY()); }

    public int getNumRows() { return charGrid.length; }
    public int getNumColumns() { return charGrid[0].length; }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CharGrid with dimensions ").append(charGrid.length).append("x").append(charGrid[0].length).append("\n");
        for (int row = 0; row < charGrid.length; row++) {
            for (int col = 0; col < charGrid[0].length; col++) {
                sb.append(charGrid[row][col]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
