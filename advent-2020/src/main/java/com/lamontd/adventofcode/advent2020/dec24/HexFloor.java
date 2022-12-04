package com.lamontd.adventofcode.advent2020.dec24;

import org.javatuples.Triplet;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;

public class HexFloor {
    protected static final Map<Triplet<Integer, Integer, Integer>, HexTile> floorTiles = new HashMap<>();
    private final HexTile centerTile;

    public HexFloor() {
        this.centerTile = new HexTile(Triplet.with(0, 0, 0));
        addTile(centerTile);
    }

    public HexTile traverseFloor(String traversalString) {
        HexTile currentTile = centerTile;
        for (HexDirection direction : convertStringToDirectionList(traversalString)) {
            currentTile = currentTile.getNeighbor(direction);
        }
        return currentTile;
    }

    public static HexTile getTile(Triplet<Integer, Integer, Integer> coordinates) {
        return floorTiles.get(coordinates);
    }

    public static HexTile getTile(int xCoord, int yCoord, int zCoord) {
        return getTile(Triplet.with(xCoord, yCoord, zCoord));
    }

    protected static void addTile(HexTile hexTile) {
        floorTiles.put(hexTile.getCoordinates(), hexTile);
    }

    public long getBlackTileCount() {
        return floorTiles.values().stream().filter(tile -> tile.getTileOrientation() == HexTile.Color.BLACK).count();
    }

    public Collection<HexTile> getTiles() { return floorTiles.values(); }

    public int getFloorTileCount() { return floorTiles.size(); }

    public void performDailyOperations() {
        expandAllBlackTileNeighbors();
        List<Triplet<Integer, Integer, Integer>> tilesToBeFlipped = new ArrayList<>();
        List<HexTile> currentHexTiles = new ArrayList<>(floorTiles.values());
        for (HexTile hexTile : currentHexTiles) {
            int adjacentBlackTiles = hexTile.getAdjacentBlackTileCount();
            if (hexTile.getTileOrientation() == HexTile.Color.BLACK) {
                if (adjacentBlackTiles == 0 || adjacentBlackTiles > 2) {
                    tilesToBeFlipped.add(hexTile.getCoordinates());
                }
            } else if (hexTile.getTileOrientation() == HexTile.Color.WHITE) {
                if (adjacentBlackTiles == 2) {
                    tilesToBeFlipped.add(hexTile.getCoordinates());
                }
            }
        }

        for (Triplet<Integer, Integer, Integer> coord : tilesToBeFlipped) {
            floorTiles.get(coord).flipTile();
        }
    }

    public void expandAllBlackTileNeighbors() {
        List<HexTile> currentHexTiles = new ArrayList<>(floorTiles.values());
        for (HexTile hexTile : currentHexTiles) {
            if (hexTile.getTileOrientation() == HexTile.Color.BLACK) {
                hexTile.touchYourNeighbors();
            }
        }
    }

    public static List<HexDirection> convertStringToDirectionList(String traversalString) {
        List<HexDirection> directionList = new ArrayList<>();
        StringCharacterIterator stringIterator = new StringCharacterIterator(traversalString);
        for(char ch = stringIterator.current(); ch != CharacterIterator.DONE; ch = stringIterator.next()) {
            switch(ch) {
                case 'e':
                    directionList.add(HexDirection.EAST);
                    break;
                case 'w':
                    directionList.add(HexDirection.WEST);
                    break;
                case 'n':
                    char northChar = stringIterator.next();
                    if (northChar == 'e') {
                        directionList.add(HexDirection.NORTHEAST);
                    } else if (northChar == 'w') {
                        directionList.add(HexDirection.NORTHWEST);
                    } else {
                        throw new IllegalArgumentException("Can't parse character of '" + northChar + "' after 'n' in traversal list");
                    }
                    break;
                case 's':
                    char southChar = stringIterator.next();
                    if (southChar == 'e') {
                        directionList.add(HexDirection.SOUTHEAST);
                    } else if (southChar == 'w') {
                        directionList.add(HexDirection.SOUTHWEST);
                    } else {
                        throw new IllegalArgumentException("Can't parse character of '" + southChar + "' after 's' in traversal list");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Can't parse character of '" + ch + "'");
            }
        }

        return directionList;
    }
}
