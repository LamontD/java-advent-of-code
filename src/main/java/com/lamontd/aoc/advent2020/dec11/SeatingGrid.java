package com.lamontd.aoc.advent2020.dec11;

import org.javatuples.Pair;

import java.util.*;

public class SeatingGrid {
    private final SeatState[][] seatingGrid;
    private final int[][] basicAdjacencyMatrix;
    private final int[][] sightAdjacencyMatrix;

    public SeatingGrid(List<String> stringLayout) {
        seatingGrid = new SeatState[stringLayout.size()][];
        basicAdjacencyMatrix = new int[stringLayout.size()][];
        sightAdjacencyMatrix = new int[stringLayout.size()][];
        for (int row=0; row < stringLayout.size(); row++) {
            char[] rowChars = stringLayout.get(row).toCharArray();
            seatingGrid[row] = new SeatState[rowChars.length];
            basicAdjacencyMatrix[row] = new int[rowChars.length];
            sightAdjacencyMatrix[row] = new int[rowChars.length];
            for (int col=0; col < rowChars.length; col++) {
                SeatState seatState = SeatState.fromRepresentation(rowChars[col]);
                seatingGrid[row][col] = seatState;
                basicAdjacencyMatrix[row][col] = seatState == SeatState.FLOOR ? -1 : 0;
                sightAdjacencyMatrix[row][col] = seatState == SeatState.FLOOR ? -1 : 0;
            }
        }
    }

    public SeatState getSeatState(int row, int col) {
        return seatingGrid[row][col];
    }

    public int getNumOccupiedSeats() {
        int occupiedSeats = 0;
        for (SeatState[] row : seatingGrid) {
            for (SeatState col : row) {
                if (col == SeatState.OCCUPIED)
                    occupiedSeats++;
            }
        }
        return occupiedSeats;
    }

    public void executeInitialLogicStepsUntilStable() {
        boolean madeChanges = executeInitialLogicStep();
        while (madeChanges) {
            madeChanges = executeInitialLogicStep();
        }
    }

    public void executeEnhancedLogicStepsUntilStable() {
        boolean madeChanges = executeEnhancedLogicStep();
        while (madeChanges) {
            madeChanges = executeEnhancedLogicStep();
        }
    }

    public boolean executeInitialLogicStep() {
        Map<SeatState, List<Pair<Integer, Integer>>> stateTransitions = findBasicStateTransitions();
        if (stateTransitions.get(SeatState.EMPTY_SEAT).isEmpty() && stateTransitions.get(SeatState.OCCUPIED).isEmpty())
            return false;
        for (Pair<Integer, Integer> seatToVacate : stateTransitions.get(SeatState.EMPTY_SEAT)) {
            vacateOccupiedSeat(seatToVacate.getValue0(), seatToVacate.getValue1());
        }
        for (Pair<Integer, Integer> seatToOccupy : stateTransitions.get(SeatState.OCCUPIED)) {
            occupyEmptySeat(seatToOccupy.getValue0(), seatToOccupy.getValue1());
        }
        return true;
    }

    public boolean executeEnhancedLogicStep() {
        Map<SeatState, List<Pair<Integer, Integer>>> stateTransitions = findEnhancedStateTransitions();
        if (stateTransitions.get(SeatState.EMPTY_SEAT).isEmpty() && stateTransitions.get(SeatState.OCCUPIED).isEmpty())
            return false;
        for (Pair<Integer, Integer> seatToVacate : stateTransitions.get(SeatState.EMPTY_SEAT)) {
            vacateOccupiedSeat(seatToVacate.getValue0(), seatToVacate.getValue1());
        }
        for (Pair<Integer, Integer> seatToOccupy : stateTransitions.get(SeatState.OCCUPIED)) {
            occupyEmptySeat(seatToOccupy.getValue0(), seatToOccupy.getValue1());
        }
        return true;
    }

    private Map<SeatState, List<Pair<Integer, Integer>>> findBasicStateTransitions() {
        Map<SeatState, List<Pair<Integer, Integer>>> stateTransitionMap = new HashMap<>();
        stateTransitionMap.put(SeatState.EMPTY_SEAT, new ArrayList<>());
        stateTransitionMap.put(SeatState.OCCUPIED, new ArrayList<>());
        for (int row = 0; row < seatingGrid.length; row++) {
            for (int col = 0; col < seatingGrid[row].length; col++) {
                switch (seatingGrid[row][col]) {
                    case EMPTY_SEAT:
                        // If the empty seat has no adjacent occupied seats, fill it
                        if (basicAdjacencyMatrix[row][col] == 0) {
                            stateTransitionMap.get(SeatState.OCCUPIED).add(Pair.with(row, col));
                        }
                        break;
                    case OCCUPIED:
                        // If the occupied seat has 4 adjacent seats, vacate it
                        if (basicAdjacencyMatrix[row][col] >= 4) {
                            stateTransitionMap.get(SeatState.EMPTY_SEAT).add(Pair.with(row, col));
                        }
                        break;
                }
            }
        }
        return stateTransitionMap;
    }

    private Map<SeatState, List<Pair<Integer, Integer>>> findEnhancedStateTransitions() {
        Map<SeatState, List<Pair<Integer, Integer>>> stateTransitionMap = new HashMap<>();
        stateTransitionMap.put(SeatState.EMPTY_SEAT, new ArrayList<>());
        stateTransitionMap.put(SeatState.OCCUPIED, new ArrayList<>());
        for (int row = 0; row < seatingGrid.length; row++) {
            for (int col = 0; col < seatingGrid[row].length; col++) {
                switch (seatingGrid[row][col]) {
                    case EMPTY_SEAT:
                        // If the empty seat has no adjacent occupied seats, fill it
                        if (sightAdjacencyMatrix[row][col] == 0) {
                            stateTransitionMap.get(SeatState.OCCUPIED).add(Pair.with(row, col));
                        }
                        break;
                    case OCCUPIED:
                        // If the occupied seat has 4 adjacent seats, vacate it
                        if (sightAdjacencyMatrix[row][col] >= 5) {
                            stateTransitionMap.get(SeatState.EMPTY_SEAT).add(Pair.with(row, col));
                        }
                        break;
                }
            }
        }
        return stateTransitionMap;

    }


    public void occupyEmptySeat(int row, int col) {
        if (seatingGrid[row][col] == SeatState.EMPTY_SEAT) {
            seatingGrid[row][col] = SeatState.OCCUPIED;
            updateBasicAdjacency(row, col, true);
            updateSightAdjacency(row, col, true);
        }
    }

    private void updateBasicAdjacency(int baseRow, int baseCol, boolean increment) {
        for (int row = baseRow - 1; row <= baseRow + 1; row++) {
            for (int col = baseCol - 1; col <= baseCol + 1; col++) {
                if (row == baseRow && col == baseCol)
                    continue;
                if (row >= 0 && row < basicAdjacencyMatrix.length) {
                    if (col >= 0 && col < basicAdjacencyMatrix[row].length) {
                        if (seatingGrid[row][col] == SeatState.FLOOR)
                            continue;
                        if (increment)
                            basicAdjacencyMatrix[row][col]++;
                        else
                            basicAdjacencyMatrix[row][col]--;
                    }
                }
            }
        }
    }

    private Pair<Integer, Integer> findNorthSightLine(int baseRow, int baseCol) {
        for (int northRow = baseRow - 1; northRow >= 0; northRow--) {
            if (seatingGrid[northRow][baseCol] == SeatState.FLOOR)
                continue;
            return Pair.with(northRow, baseCol);
        }
        return null;
    }

    private Pair<Integer, Integer> findNortheastSightLine(int baseRow, int baseCol) {
        int northeastRow = baseRow - 1;
        int northeastCol = baseCol + 1;
        while (northeastRow >= 0 && northeastCol < seatingGrid[northeastRow].length) {
            if (seatingGrid[northeastRow][northeastCol] == SeatState.FLOOR) {
                northeastRow--;
                northeastCol++;
            } else {
                return Pair.with(northeastRow, northeastCol);
            }
        }
        return null;
    }

    private Pair<Integer, Integer> findNorthwestSightLine(int baseRow, int baseCol) {
        int northwestRow = baseRow - 1;
        int northwestCol = baseCol - 1;
        while (northwestRow >= 0 && northwestCol >= 0) {
            if (seatingGrid[northwestRow][northwestCol] == SeatState.FLOOR) {
                northwestRow--;
                northwestCol--;
            } else {
                return Pair.with(northwestRow, northwestCol);
            }
        }
        return null;
    }

    private Pair<Integer, Integer> findSouthSightLine(int baseRow, int baseCol) {
        for (int southRow = baseRow + 1; southRow < seatingGrid.length; southRow++) {
            if (seatingGrid[southRow][baseCol] == SeatState.FLOOR)
                continue;
            return Pair.with(southRow, baseCol);
        }
        return null;
    }

    private Pair<Integer, Integer> findSouthwestSightLine(int baseRow, int baseCol) {
        int southwestRow = baseRow + 1;
        int southwestCol = baseCol - 1;
        while (southwestRow < seatingGrid.length && southwestCol >= 0) {
            if (seatingGrid[southwestRow][southwestCol] == SeatState.FLOOR) {
                southwestRow++;
                southwestCol--;
            } else {
                return Pair.with(southwestRow, southwestCol);
            }
        }
        return null;
    }

    private Pair<Integer, Integer> findSoutheastSightLine(int baseRow, int baseCol) {
        int southeastRow = baseRow + 1;
        int southeastCol = baseCol + 1;
        while (southeastRow < seatingGrid.length && southeastCol < seatingGrid[southeastRow].length) {
            if (seatingGrid[southeastRow][southeastCol] == SeatState.FLOOR) {
                southeastRow++;
                southeastCol++;
            } else {
                return Pair.with(southeastRow, southeastCol);
            }
        }
        return null;
    }

    private Pair<Integer, Integer> findEastSightLine(int baseRow, int baseCol) {
        for(int eastCol = baseCol + 1; eastCol < seatingGrid[baseRow].length; eastCol++) {
            if (seatingGrid[baseRow][eastCol] == SeatState.FLOOR)
                continue;
            return Pair.with(baseRow, eastCol);
        }
        return null;
    }

    private Pair<Integer, Integer> findWestSightLine(int baseRow, int baseCol) {
        for (int westCol = baseCol - 1; westCol >= 0; westCol--) {
            if (seatingGrid[baseRow][westCol] == SeatState.FLOOR)
                continue;
            return Pair.with(baseRow, westCol);
        }
        return null;
    }

    public Map<Direction, Pair<Integer, Integer>> findLinesOfSight(int baseRow, int baseCol) {
        Map<Direction, Pair<Integer, Integer>> sightLines = new HashMap<>();
        sightLines.put(Direction.NORTH, findNorthSightLine(baseRow, baseCol));
        sightLines.put(Direction.NORTHEAST, findNortheastSightLine(baseRow, baseCol));
        sightLines.put(Direction.EAST, findEastSightLine(baseRow, baseCol));
        sightLines.put(Direction.SOUTHEAST, findSoutheastSightLine(baseRow, baseCol));
        sightLines.put(Direction.SOUTH, findSouthSightLine(baseRow, baseCol));
        sightLines.put(Direction.SOUTHWEST, findSouthwestSightLine(baseRow, baseCol));
        sightLines.put(Direction.WEST, findWestSightLine(baseRow, baseCol));
        sightLines.put(Direction.NORTHWEST, findNorthwestSightLine(baseRow, baseCol));
        return sightLines;
    }

    private void updateSightAdjacency(int baseRow, int baseCol, boolean increment) {
        for (Pair<Integer, Integer> valueToUpdate : findLinesOfSight(baseRow, baseCol).values()) {
            if (valueToUpdate != null) {
                if (increment)
                    sightAdjacencyMatrix[valueToUpdate.getValue0()][valueToUpdate.getValue1()]++;
                else
                    sightAdjacencyMatrix[valueToUpdate.getValue0()][valueToUpdate.getValue1()]--;
            }
        }
    }

    public void vacateOccupiedSeat(int row, int col) {
        if (seatingGrid[row][col] == SeatState.OCCUPIED) {
            seatingGrid[row][col] = SeatState.EMPTY_SEAT;
            updateBasicAdjacency(row, col, false);
            updateSightAdjacency(row, col, false);
        }
    }

    public int getBasicAdjacentOccupiedSeats(int row, int col) {
        return basicAdjacencyMatrix[row][col];
    }

    public int getSightAdjacentOccupiedSeats(int row, int col) {
        return sightAdjacencyMatrix[row][col];
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row=0; row < seatingGrid.length; row++) {
            for (int col=0; col < seatingGrid[row].length; col++)
                sb.append(seatingGrid[row][col]);
            sb.append("\t").append(Arrays.toString(basicAdjacencyMatrix[row])).append("\n");
        }
        return sb.toString();
    }

    public enum Direction { NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST; }
}
