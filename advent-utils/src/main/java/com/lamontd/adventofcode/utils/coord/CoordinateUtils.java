package com.lamontd.adventofcode.utils.coord;

import java.util.ArrayList;
import java.util.List;

public class CoordinateUtils {
    public static List<Coordinate> lineBetween(Coordinate start, Coordinate end) {
        final List<Coordinate> line = new ArrayList<>();
        if (start.getX() == end.getX()) {
            int yBegin = Math.min(start.getY(), end.getY());
            int yEnd = Math.max(start.getY(), end.getY());
            for (int yCoord = yBegin; yCoord <= yEnd; yCoord++) {
                line.add(Coordinate.of(start.getX(), yCoord));
            }
        } else if (start.getY() == end.getY()) {
            int xBegin = Math.min(start.getX(), end.getX());
            int xEnd = Math.max(start.getX(), end.getX());
            for (int xCoord = xBegin; xCoord <= xEnd; xCoord++) {
                line.add(Coordinate.of(xCoord, start.getY()));
            }
        }
        return line;
    }

    public static Coordinate moveInDirection(Coordinate startingCoord, CardinalDirections direction) {
        return Coordinate.of(startingCoord.getX() + direction.getColAdjustment(), startingCoord.getY() + direction.getRowAdjustment());
    }

    public static int manhattanDistance(Coordinate c1, Coordinate c2) {
        return Math.abs(c1.getX() - c2.getX()) + Math.abs(c1.getY() - c2.getY());
    }
}
