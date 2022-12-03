package com.lamontd.aoc.advent2021.dec05;

import com.lamontd.aoc.utils.BasicCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VentMap {
    private static final Logger logger = LoggerFactory.getLogger(VentMap.class);
    private Map<VentCoordinate, BasicCounter> coordinateMap = new HashMap<>();

    public void clearMap() { coordinateMap.clear();}
    public void addBasicLine(VentCoordinate start, VentCoordinate end) {
        if (start.getXCoord() == end.getXCoord()) {
            logger.debug("Adding vertical line from " + start + " to " + end);
            addVerticalLine(start, end.getYCoord());
        } else if (start.getYCoord() == end.getYCoord()) {
            logger.debug("Adding horizontal line from " + start + " to " + end);
            addHorizontalLine(start, end.getXCoord());
        }
    }
    public void addFancyLine(VentCoordinate start, VentCoordinate end) {
        if (start.getXCoord() == end.getXCoord()) {
            logger.debug("Adding vertical line from " + start + " to " + end);
            addVerticalLine(start, end.getYCoord());
        } else if (start.getYCoord() == end.getYCoord()) {
            logger.debug("Adding horizontal line from " + start + " to " + end);
            addHorizontalLine(start, end.getXCoord());
        } else {
            logger.debug("Adding diagonal line from " + start + " to " + end);
            addDiagonalLine(start, end);
        }
    }
    private void addVerticalLine(VentCoordinate start, int finalYCoordinate) {
        boolean positive = finalYCoordinate > start.getYCoord();
        for (int ycoord=start.getYCoord(); ycoord != finalYCoordinate; ) {
            addPoint(new VentCoordinate(start.getXCoord(), ycoord));
            ycoord = positive ? ycoord + 1 : ycoord - 1;
        }
        addPoint(new VentCoordinate(start.getXCoord(), finalYCoordinate));
    }
    private void addDiagonalLine(VentCoordinate start, VentCoordinate end) {
        boolean right = end.getXCoord() > start.getXCoord();
        boolean up = end.getYCoord() > start.getYCoord();
        VentCoordinate writeCoordinate = new VentCoordinate(start.getXCoord(), start.getYCoord());
        while (!writeCoordinate.equals(end)) {
            addPoint(writeCoordinate);
            writeCoordinate = new VentCoordinate(right ? writeCoordinate.getXCoord() + 1 : writeCoordinate.getXCoord() - 1,
                    up ? writeCoordinate.getYCoord() + 1 : writeCoordinate.getYCoord() - 1);
        }
        addPoint(writeCoordinate);
    }

    private void addHorizontalLine(VentCoordinate start, int finalXCoordinate) {
        boolean positive = finalXCoordinate > start.getXCoord();
        for (int xCoord=start.getXCoord(); xCoord != finalXCoordinate; ) {
            addPoint(new VentCoordinate(xCoord, start.getYCoord()));
            xCoord = positive ? xCoord + 1 : xCoord - 1;
        }
        addPoint(new VentCoordinate(finalXCoordinate, start.getYCoord()));
    }

    private void addPoint(VentCoordinate point) {
        if (!coordinateMap.containsKey(point)) {
            coordinateMap.put(point, new BasicCounter());
        }
        coordinateMap.get(point).increment();
        logger.debug("Added " + point + " with scalar of " + coordinateMap.get(point).currentValue());
    }

    public List<VentCoordinate> findOverlapPoints(int overlapSize) {
        return coordinateMap.entrySet().stream().filter(entry -> entry.getValue().currentValue() >= overlapSize)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
