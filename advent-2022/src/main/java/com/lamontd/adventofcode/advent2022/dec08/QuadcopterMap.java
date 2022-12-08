package com.lamontd.adventofcode.advent2022.dec08;

import org.javatuples.Pair;
import org.javatuples.Tuple;

import java.util.*;

public class QuadcopterMap {
    private int[][] rawDataMap;

    private Map<Pair<Integer, Integer>, Set<Directions>> visibilityMap = new HashMap<>();
    private Map<Pair<Integer, Integer>, Integer> scenicMap = new HashMap<>();


    public QuadcopterMap(List<String> rawInput) {
        this.rawDataMap = new int[rawInput.size()][];
        for (int rowNum = 0; rowNum < rawInput.size(); rowNum++) {
            String rawRow = rawInput.get(rowNum);
            this.rawDataMap[rowNum] = new int[rawRow.length()];
            for (int colNum = 0; colNum < rawRow.length(); colNum++) {
                this.rawDataMap[rowNum][colNum] = Character.getNumericValue(rawRow.charAt(colNum));
            }
        }
        calculateVisibilty();
        processScenery();
    }

    private void calculateVisibilty() {
        for (int row = 0; row < rawDataMap.length; row++) {
            addVisibility(row, 0, Directions.LEFT);
            int highestSoFar = rawDataMap[row][0];
            for (int leftCol = 1; leftCol < rawDataMap.length; leftCol++) {
                if (rawDataMap[row][leftCol] > highestSoFar) {
                    addVisibility(row, leftCol, Directions.LEFT);
                    highestSoFar = rawDataMap[row][leftCol];
                }
            }
            addVisibility(row, rawDataMap[row].length - 1, Directions.RIGHT);
            highestSoFar = rawDataMap[row][rawDataMap[row].length - 1];
            for (int rightCol = rawDataMap[row].length - 2; rightCol >= 0; rightCol--) {
                if (rawDataMap[row][rightCol] > highestSoFar) {
                    addVisibility(row, rightCol, Directions.RIGHT);
                    highestSoFar = rawDataMap[row][rightCol];
                }
            }
        }

        for (int col = 0; col < rawDataMap[0].length; col++) {
            addVisibility(0, col, Directions.TOP);
            int highestSoFar = rawDataMap[0][col];
            for (int topRow = 1; topRow < rawDataMap.length; topRow++) {
                if (rawDataMap[topRow][col] > highestSoFar) {
                    addVisibility(topRow, col, Directions.TOP);
                    highestSoFar = rawDataMap[topRow][col];
                }
            }

            addVisibility(rawDataMap.length - 1, col, Directions.BOTTOM);
            highestSoFar = rawDataMap[rawDataMap.length - 1][col];
            for (int bottomRow = rawDataMap.length - 2; bottomRow >= 0; bottomRow--) {
                if (rawDataMap[bottomRow][col] > highestSoFar) {
                    addVisibility(bottomRow, col, Directions.BOTTOM);
                    highestSoFar = rawDataMap[bottomRow][col];
                }
            }
        }
    }

    private void processScenery() {
        for (int row = 1; row < rawDataMap.length - 1; row++) {
            for (int col = 1; col < rawDataMap.length - 1; col++) {
                final int thisHeight = rawDataMap[row][col];
                int topVisibility = 0;
                for (int topCounter = row - 1; topCounter >= 0; topCounter--) {
                    topVisibility++;
                    if (rawDataMap[topCounter][col] >= thisHeight) {
                        break;
                    }
                }
                int rightVisibility = 0;
                for (int rightCounter = col + 1; rightCounter < rawDataMap.length; rightCounter++) {
                    rightVisibility++;
                    if (rawDataMap[row][rightCounter] >= thisHeight) {
                        break;
                    }
                }
                int bottomVisibility = 0;
                for (int bottomCounter = row + 1; bottomCounter < rawDataMap.length; bottomCounter++) {
                    bottomVisibility++;
                    if (rawDataMap[bottomCounter][col] >= thisHeight) {
                        break;
                    }
                }
                int leftVisibility = 0;
                for (int leftCounter = col - 1; leftCounter >= 0; leftCounter--) {
                    leftVisibility++;
                    if (rawDataMap[row][leftCounter] >= thisHeight) {
                        break;
                    }
                }
                scenicMap.put(Pair.with(row, col), topVisibility * rightVisibility * bottomVisibility * leftVisibility);
            }
        }
    }

    public int getVisibleTreeCount() {
        return visibilityMap.size();
    }

    private void addVisibility(int row, int col, Directions direction) {
        if (!this.visibilityMap.containsKey(Pair.with(row, col))) {
            this.visibilityMap.put(Pair.with(row, col), new HashSet<>());
        }
        this.visibilityMap.get(Pair.with(row, col)).add(direction);
    }

    public Map<Pair<Integer, Integer>, Integer> getScenicMap() {
        return scenicMap;
    }

    public int getHighestScenicScore() {
        return scenicMap.values().stream().max(Integer::compareTo).get();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < rawDataMap.length; row++) {
            for (int col = 0; col < rawDataMap[row].length; col++) {
                sb.append(this.rawDataMap[row][col]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public enum Directions { TOP, RIGHT, BOTTOM, LEFT; }
}
