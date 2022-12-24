package com.lamontd.adventofcode.utils.coord;

import lombok.Builder;
import org.javatuples.Pair;

import java.util.Objects;

@Builder
public class BigCoordinate implements Comparable<BigCoordinate> {
    private long x;
    private long y;

    public BigCoordinate() {
        this.x = 0;
        this.y = 0;
    }

    public BigCoordinate(long xCoord, long yCoord) {
        this.x = xCoord; this.y = yCoord;
    }

    public BigCoordinate(Pair<Integer, Integer> intPair) {
        this.x = intPair.getValue0();
        this.y = intPair.getValue1();
    }

    public long getX() { return this.x; }
    public long getY() { return this.y; }

    public void setX(long xValue) { this.x = xValue; }
    public void setY(long yValue) { this.y = yValue; }

    public void addX(long units) { x += units; }
    public void addY(long units) { y += units; }

    public void subtractX(long units) { x -= units; }
    public void subtractY(long units) { y -= units; }

    public Pair<Long, Long> toPair() { return Pair.with(x, y); }

    public static BigCoordinate of(long x, long y) { return BigCoordinate.builder().x(x).y(y).build(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigCoordinate that = (BigCoordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(BigCoordinate o) {
        if (this == o) return 0;
        if (this.x == o.x) {
            return Long.compare(this.y, o.y);
        }
        return Long.compare(this.x, o.x);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
