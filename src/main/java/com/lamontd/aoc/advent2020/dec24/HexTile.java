package com.lamontd.aoc.advent2020.dec24;

import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HexTile {
    private final Triplet<Integer, Integer, Integer> coordinates;
    private Color tileOrientation = Color.WHITE;

    private final Map<HexDirection, HexTile> neighbors = new HashMap<>();

    public HexTile(Triplet<Integer, Integer, Integer> coordinates) {
        this.coordinates = coordinates;
    }

    public void addNeighbor(HexTile hexTile, HexDirection direction) {
        neighbors.put(direction, hexTile);
        hexTile.neighbors.put(direction.getInverse(), this);
    }

    public HexTile getNeighbor(HexDirection hexDirection) {
        HexTile neighborTile = neighbors.get(hexDirection);

        if (neighborTile == null) {
            Triplet<Integer, Integer, Integer> neighborCoordinates = getNeighboringCoordinatesInDirection(this.coordinates, hexDirection);
            neighborTile = HexFloor.getTile(neighborCoordinates);
            if (neighborTile == null) {
                neighborTile = new HexTile(neighborCoordinates);
                HexFloor.addTile(neighborTile);
            }
            this.addNeighbor(neighborTile, hexDirection);
        }
        return neighborTile;
    }

    public void touchYourNeighbors() {
        for (HexDirection direction : HexDirection.values()) {
            getNeighbor(direction);
        }
    }

    public int getAdjacentBlackTileCount() {
        int adjacentBlackTiles = 0;
        for (HexDirection direction : HexDirection.values()) {
            if (getNeighbor(direction).getTileOrientation() == Color.BLACK) {
                adjacentBlackTiles++;
            }
        }
        return adjacentBlackTiles;
    }

    public Triplet<Integer, Integer, Integer> getCoordinates() {
        return coordinates;
    }

    protected static Triplet<Integer, Integer, Integer> getNeighboringCoordinatesInDirection(Triplet<Integer, Integer, Integer> currentCoords,
                                                                                             HexDirection direction) {
        int neighborX = currentCoords.getValue0();
        int neighborY = currentCoords.getValue1();
        int neighborZ = currentCoords.getValue2();

        switch(direction) {
            case EAST:
                neighborX++;
                neighborY--;
                break;
            case SOUTHEAST:
                neighborY--;
                neighborZ++;
                break;
            case SOUTHWEST:
                neighborX--;
                neighborZ++;
                break;
            case WEST:
                neighborX--;
                neighborY++;
                break;
            case NORTHWEST:
                neighborY++;
                neighborZ--;
                break;
            case NORTHEAST:
                neighborX++;
                neighborZ--;
                break;
        }
        return Triplet.with(neighborX, neighborY, neighborZ);

    }

    public void flipTile() { tileOrientation = tileOrientation == Color.BLACK ? Color.WHITE : Color.BLACK; }

    public Color getTileOrientation() {
        return tileOrientation;
    }

    @Override
    public String toString() {
        return "HexTile{" +
                "coordinates=" + coordinates +
                ", tileOrientation=" + tileOrientation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HexTile hexTile = (HexTile) o;
        return coordinates.equals(hexTile.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }

    public static enum Color {
        BLACK, WHITE;
    }
}
