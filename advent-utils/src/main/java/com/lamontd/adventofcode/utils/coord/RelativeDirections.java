package com.lamontd.adventofcode.utils.coord;

public enum RelativeDirections {
    LEFT(-1, 0, 0),
    RIGHT(1, 0, 0),
    TOP(0, 1, 0),
    BOTTOM(0, -1, 0),
    FRONT(0, 0, 1),
    BACK(0, 0, -1);

    private final int xAdjustment;
    private final int yAdjustment;
    private final int zAdjustment;

    RelativeDirections(int xAdjustment, int yAdjustment, int zAdjustment) {
        this.xAdjustment = xAdjustment;
        this.yAdjustment = yAdjustment;
        this.zAdjustment = zAdjustment;
    }

    public int getXAdjustment() {
        return xAdjustment;
    }

    public int getYAdjustment() {
        return yAdjustment;
    }

    public int getZAdjustment() {
        return zAdjustment;
    }
}
