package com.lamontd.aoc.advent2020.dec17;

public enum CubeState {
    ACTIVE('#'),
    INACTIVE('.');

    private final char representation;
    CubeState(char representation) { this.representation = representation; }

    public char getRepresentation() { return representation; }

    public static CubeState fromRepresentation(char representation) {
        for (CubeState state:  values()) {
            if (state.representation == representation) {
                return state;
            }
        }
        return null;
    }
}
