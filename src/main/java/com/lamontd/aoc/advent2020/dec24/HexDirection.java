package com.lamontd.aoc.advent2020.dec24;

public enum HexDirection {
    EAST("e"),
    SOUTHEAST("se"),
    SOUTHWEST("sw"),
    WEST("w"),
    NORTHWEST("nw"),
    NORTHEAST("ne");

    private String representation;
    HexDirection(String representation) { this.representation = representation; }

    public HexDirection getInverse() {
        HexDirection invertedDirection = null;
        switch(this) {
            case EAST:
                invertedDirection = WEST;
                break;
            case SOUTHEAST:
                invertedDirection = NORTHWEST;
                break;
            case SOUTHWEST:
                invertedDirection = NORTHEAST;
                break;
            case WEST:
                invertedDirection = EAST;
                break;
            case NORTHEAST:
                invertedDirection = SOUTHWEST;
                break;
            case NORTHWEST:
                invertedDirection = SOUTHEAST;
                break;
        }
        return invertedDirection;
    }

}
