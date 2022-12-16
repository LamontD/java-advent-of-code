package com.lamontd.adventofcode.utils;

import java.util.Objects;

public class CoordinateLine {
    private final Coordinate start;
    private final Coordinate end;

    private final Type lineType;

    public CoordinateLine(Coordinate start, Coordinate end) {
        boolean reversed = false;
        if (start.getX() == end.getX()) {
            lineType = Type.VERTICAL;
            reversed = end.getY() < start.getY();
        } else if (start.getY() == end.getY()) {
            lineType = Type.HORIZONTAL;
            reversed = end.getX() < start.getX();
        } else {
            lineType = Type.OTHER;
        }
        this.start = reversed ? end : start;
        this.end = reversed ? start : end;
    }

    public boolean contains(Coordinate coordinate) {
        switch(lineType) {
            case HORIZONTAL:
                return coordinate.getY() == start.getY() && coordinate.getX() >= start.getX() && coordinate.getX() <= end.getX();
            case VERTICAL:
                return coordinate.getX() == start.getX() && coordinate.getY() >= start.getY() && coordinate.getY() <= end.getY();
            default:
                throw new UnsupportedOperationException("Can't do contains on lines with slope");
        }
    }

    public int length() {
        return CoordinateUtils.manhattanDistance(start, end) + 1;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinateLine that = (CoordinateLine) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "CoordinateLine: " + start + " --> " + end;
    }

    enum Type { HORIZONTAL, VERTICAL, OTHER }
}
