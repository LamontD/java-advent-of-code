package com.lamontd.adventofcode.advent2021.dec09;

import com.lamontd.adventofcode.utils.coord.CardinalDirections;
import com.lamontd.adventofcode.utils.SingleValueIntGrid;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HeightMap extends SingleValueIntGrid {
    private static final Logger logger = LoggerFactory.getLogger(HeightMap.class);

    public HeightMap(List<String> layout) {
        super(layout);
    }

    public List<Pair<Integer, Integer>> calculateLowPoints() {
        List<Pair<Integer, Integer>> lowPoints = new ArrayList<>();
        for (int row = 0; row < intGrid.length; row++) {
            for (int col = 0; col < intGrid[row].length; col++) {
                boolean isLowPoint = true;
                for (CardinalDirections quad : calculateValidBasicCardinalDirections(row, col)) {
                    if (intGrid[row + quad.getRowAdjustment()][col + quad.getColAdjustment()] <= intGrid[row][col]) {
                        isLowPoint = false;
                        break;
                    }
                }
                if (isLowPoint) {
                    lowPoints.add(Pair.with(row, col));
                }
            }
        }
        logger.info("Found " + lowPoints.size() + " low points");
        return lowPoints;
    }

    public int getHeight(Pair<Integer, Integer> dataPoint) {
        return getGridValue(dataPoint);
    }


    public List<Pair<Integer, Integer>> calculateBasinFromPoint(Pair<Integer, Integer> point) {
        List<Pair<Integer, Integer>> basinPoints = new ArrayList<>();
        if (getHeight(point) == 9) {
            return basinPoints;
        }
        basinPoints.add(point);
        for (Pair<Integer, Integer> newPoint : getValidBasicAdjacentPoints(point)) {
//        for (CardinalDirections validCardinalDirections : calculateValidBasicCardinalDirections(point)) {
//            Pair<Integer, Integer> newPoint = Pair.with(point.getValue0() + validCardinalDirections.getRowAdjustment(), point.getValue1()+ validCardinalDirections.getColAdjustment());
            basinPoints.addAll(calculateBasinFromPoint(newPoint));
        }
        return basinPoints;
    }

    public List<Pair<Integer, Integer>> recursivelyCalculateBasin(Pair<Integer, Integer> point) {
        Set<Pair<Integer, Integer>> basinSet = new HashSet<>();
        recursivelyCalculateBasin(basinSet, point);
        return new ArrayList<>(basinSet);
    }

    private void recursivelyCalculateBasin(Set<Pair<Integer, Integer>> basinPoints, Pair<Integer, Integer> startingPoint) {
        if (getHeight(startingPoint) == 9) {
            return;
        }
        basinPoints.add(startingPoint);
//        for(CardinalDirections validCardinalDirections : calculateValidBasicCardinalDirections(startingPoint)) {
//            Pair<Integer, Integer> nextPoint = Pair.with(startingPoint.getValue0() + validCardinalDirections.getRowAdjustment(),
//                    startingPoint.getValue1()+ validCardinalDirections.getColAdjustment());
        for (Pair<Integer, Integer> nextPoint : getValidBasicAdjacentPoints(startingPoint)) {
            if (!basinPoints.contains(nextPoint)) {
                recursivelyCalculateBasin(basinPoints, nextPoint);
            }
        }
    }

}
