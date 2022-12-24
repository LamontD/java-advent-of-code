package com.lamontd.adventofcode.utils.coord;

import lombok.Builder;
import org.javatuples.Triplet;

import java.util.Objects;

@Builder
public class CubicCoordinate implements Comparable<CubicCoordinate> {
    private int x;
    private int y;
    private int z;

    public CubicCoordinate() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public CubicCoordinate(int xCoord, int yCoord, int zCoord) {
        this.x = xCoord;
        this.y = yCoord;
        this.z = zCoord;
    }

    public CubicCoordinate(Triplet<Integer, Integer, Integer> intTriplet) {
        this.x = intTriplet.getValue0();
        this.y = intTriplet.getValue1();
        this.z = intTriplet.getValue2();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public static CubicCoordinate of(int x, int y, int z) {
        return CubicCoordinate.builder().x(x).y(y).z(z).build();
    }

    public CubicCoordinate getLeft() {
        return CubicCoordinate.of(this.x - 1, this.y, this.z);
    }

    public CubicCoordinate getRight() {
        return CubicCoordinate.of(this.x + 1, this.y, this.z);
    }

    public CubicCoordinate getTop() {
        return CubicCoordinate.of(this.x, this.y + 1, this.z);
    }

    public CubicCoordinate getBottom() {
        return CubicCoordinate.of(this.x, this.y - 1, this.z);
    }

    public CubicCoordinate getFront() {
        return CubicCoordinate.of(this.x, this.y, this.z + 1);
    }

    public CubicCoordinate getBack() {
        return CubicCoordinate.of(this.x, this.y, this.z - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CubicCoordinate that = (CubicCoordinate) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public int compareTo(CubicCoordinate that) {
        if (this == that) return 0;
        if (this.x == that.x) {
            if (this.y == that.y) {
                return Integer.compare(this.z, that.z);
            }
            return Integer.compare(this.y, that.y);
        }
        return Integer.compare(this.x, that.x);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }
}
