package com.lamontd.aoc.advent2021.dec13;

import lombok.Builder;

import java.util.Objects;

@Builder
public class GraphPoint implements Comparable<GraphPoint> {
    private int x;
    private int y;

    public GraphPoint(){}
    public GraphPoint(int xVal, int yVal) { this.x = xVal; this.y = yVal; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public void setX(int xVal) { this.x = xVal; }
    public void setY(int yVal) { this.y = yVal; }

    @Override
    public int compareTo(GraphPoint o) {
        return x == o.x ? Integer.compare(y, o.y) : Integer.compare(x, o.x);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphPoint that = (GraphPoint) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
