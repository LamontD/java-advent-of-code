package com.lamontd.aoc.advent2020.dec11;

public enum SeatState {
    FLOOR('.'),
    EMPTY_SEAT('L'),
    OCCUPIED('#');
    private char representation;
    SeatState(char representation) { this.representation = representation; }

    public char getRepresentation() {
        return representation;
    }

    public static SeatState fromRepresentation(char representation) {
        for (SeatState state : values()) {
            if (state.representation == representation) {
                return state;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.valueOf(representation);
    }
}
