package com.lamontd.adventofcode.advent2022.dec17;

import com.lamontd.adventofcode.utils.coord.Coordinate;
import org.javatuples.Quartet;

public class RowSpecification {
    private final Coordinate bottomCorner;
    private final Quartet<Integer, Integer, Integer, Integer> columnHeights;

    private int maxHeight;

    public RowSpecification(Coordinate startingCoordinate, int rowOne, int rowTwo, int rowThree, int rowFour) {
        this.bottomCorner = Coordinate.of(startingCoordinate.getX(), startingCoordinate.getY());
        this.columnHeights = Quartet.with(rowOne, rowTwo, rowThree, rowFour);
        this.maxHeight = Math.max(rowOne, Math.max(rowTwo, Math.max(rowThree, rowFour)));
    }

    public Coordinate getBottomCorner() {
        return bottomCorner;
    }

    public int getColumnOneHeight() {
        return columnHeights.getValue0();
    }

    public int getColumnTwoHeight() {
        return columnHeights.getValue1();
    }

    public int getColumnThreeHeight() {
        return columnHeights.getValue2();
    }

    public int getColumnFourHeight() {
        return columnHeights.getValue3();
    }

    public int getMaxHeight() {
        return maxHeight;
    }
}
