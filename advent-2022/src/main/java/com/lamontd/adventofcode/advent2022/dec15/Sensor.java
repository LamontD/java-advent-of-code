package com.lamontd.adventofcode.advent2022.dec15;

import com.lamontd.adventofcode.utils.Coordinate;
import com.lamontd.adventofcode.utils.CoordinateLine;
import com.lamontd.adventofcode.utils.CoordinateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sensor {
    private final Coordinate location;
    private final Coordinate closestBeacon;

    private final int coverageDistance;

    public Sensor(Coordinate location, Coordinate closestBeacon) {
        this.location = location;
        this.closestBeacon = closestBeacon;
        this.coverageDistance = CoordinateUtils.manhattanDistance(location, closestBeacon);
    }

    public Coordinate getLocation() {
        return location;
    }

    public Coordinate getClosestBeacon() {
        return closestBeacon;
    }

    public List<Coordinate> getExclusionZoneForRow(int rowNum) {
        List<Coordinate> excludedPoints = new ArrayList<>();
        int rowSeparation = Math.abs(location.getY() - rowNum);
        if (rowSeparation <= coverageDistance) {
            int columnDelta = Math.abs(rowSeparation - coverageDistance);
            excludedPoints = CoordinateUtils.lineBetween(Coordinate.of(location.getX() - columnDelta, rowNum),
                    Coordinate.of(location.getX() + columnDelta, rowNum));
            excludedPoints.remove(closestBeacon);
        }
        return excludedPoints;
    }
    public boolean covers(Coordinate point) {
        return CoordinateUtils.manhattanDistance(location, closestBeacon) >= CoordinateUtils.manhattanDistance(location, point);
    }

    public List<Coordinate> getManhattanDistanceRing() {
        List<Coordinate> ringCoordinates = new ArrayList<>();
        int manhattanDistance = coverageDistance + 1;
        for (int row = location.getY() - manhattanDistance; row <= location.getY() + manhattanDistance; row++) {
            int columnDelta = manhattanDistance - Math.abs(location.getY() - row);
            if (columnDelta == 0) {
                ringCoordinates.add(Coordinate.of(location.getX(), row));
            } else {
                ringCoordinates.add(Coordinate.of(location.getX() - columnDelta, row));
                ringCoordinates.add(Coordinate.of(location.getX() + columnDelta, row));
            }
        }
        return ringCoordinates;
    }

    public CoordinateLine getExclusionLineForRow(int rowNum) {
        CoordinateLine exclusionLine = null;
        int manhattanDistanceToBeacon = CoordinateUtils.manhattanDistance(location, closestBeacon);
        int rowSeparation = Math.abs(location.getY() - rowNum);
        if (rowSeparation <= manhattanDistanceToBeacon) {
            int columnDelta = Math.abs(rowSeparation - manhattanDistanceToBeacon);
            Coordinate firstPoint = Coordinate.of(location.getX() - columnDelta, rowNum);
            if (firstPoint.equals(closestBeacon)) {
                firstPoint = Coordinate.of(firstPoint.getX() + 1, firstPoint.getY());
            }
            Coordinate secondPoint = Coordinate.of(location.getX() + columnDelta, rowNum);
            if (secondPoint.equals(closestBeacon)) {
                secondPoint = Coordinate.of(secondPoint.getX() - 1, secondPoint.getY());
            }
            exclusionLine = new CoordinateLine(firstPoint, secondPoint);
        }
        return exclusionLine;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return Objects.equals(location, sensor.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        return "Sensor at " + location + ": closest beacon is at " + closestBeacon;
    }
}
