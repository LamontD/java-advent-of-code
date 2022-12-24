package com.lamontd.adventofcode.utils.coord;

import java.util.List;

public enum CardinalDirections {
    NW(-1, -1),
    N(-1, 0),
    NE(-1, 1),
    E(0, 1),
    SE(1, 1),
    S(1, 0),
    SW(1, -1),
    W(0, -1);
    private final int rowAdjustment;
    private final int colAdjustment;
    CardinalDirections(int rowAdjustment, int colAdjustment) {
        this.rowAdjustment = rowAdjustment;
        this.colAdjustment = colAdjustment;
    }

    public static List<CardinalDirections> getBasicDirections() { return List.of(N, S, E, W);}
    public static List<CardinalDirections> getFullDirections() { return List.of(NW, N, NE, E, SE, S, SW, W); }

    public int getColAdjustment() {
        return colAdjustment;
    }

    public int getRowAdjustment() {
        return rowAdjustment;
    }
}
