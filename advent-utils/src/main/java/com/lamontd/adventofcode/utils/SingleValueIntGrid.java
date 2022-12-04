package com.lamontd.adventofcode.utils;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SingleValueIntGrid {
    protected final int[][] intGrid;

    public SingleValueIntGrid(List<String> layout) {
        intGrid = new int[layout.size()][];
        for (int row = 0; row < layout.size(); row++) {
            char[] rowChars = layout.get(row).toCharArray();
            intGrid[row] = new int[rowChars.length];
            for (int col = 0; col < rowChars.length; col++) {
                intGrid[row][col] = Integer.parseInt(Character.toString(rowChars[col]));
            }
        }
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
            if (potentialRow < 0 || potentialCol < 0 || potentialRow >= intGrid.length || potentialCol >= intGrid[0].length) {
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
            if (potentialRow < 0 || potentialCol < 0 || potentialRow >= intGrid.length || potentialCol >= intGrid[0].length) {
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

    public int getGridValue(Pair<Integer, Integer> point) { return getGridValue(point.getValue0(), point.getValue1()); }
    public int getGridValue(int row, int col) { return intGrid[row][col]; }
    public int getGridValue(Coordinate coordinate) { return getGridValue(coordinate.getX(), coordinate.getY()); }

    public int getNumRows() { return intGrid.length; }
    public int getNumColumns() { return intGrid[0].length; }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HeightGrid with dimensions ").append(intGrid.length).append("x").append(intGrid[0].length).append("\n");
        for (int row = 0; row < intGrid.length; row++) {
            for (int col = 0; col < intGrid[0].length; col++) {
                sb.append(intGrid[row][col]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
