package com.lamontd.adventofcode.advent2022.dec14;

import com.lamontd.adventofcode.utils.CardinalDirections;
import com.lamontd.adventofcode.utils.Coordinate;
import com.lamontd.adventofcode.utils.CoordinateUtils;
import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.List;

public class UndergroundCave {

    private final HashMap<Coordinate, Contents> caveMap = new HashMap<>();

    private int greatestDepth = -1;
    private int minWidth = 1000;
    private int maxWidth = -1;

    private boolean infinityFloor = false;

    public boolean entranceBlocked(Coordinate entrance) {
        return caveMap.containsKey(entrance);
    }

    public void dropSandFromPoint(Coordinate startingPoint) throws FreefallException {
        Coordinate sandCoordinate = Coordinate.builder().x(startingPoint.getX()).y(startingPoint.getY()).build();
        boolean cameToRest = false;
        while (!cameToRest) {
            cameToRest = true;
            if (! infinityFloor && sandCoordinate.getY() > greatestDepth) {
                throw new FreefallException();
            }
            for (CardinalDirections direction : List.of(CardinalDirections.S, CardinalDirections.SW, CardinalDirections.SE)) {
                Coordinate nextMove = CoordinateUtils.moveInDirection(sandCoordinate, direction);
                if (infinityFloor && nextMove.getY() == greatestDepth + 2) {
                    caveMap.put(nextMove, Contents.ROCK);
                }
                if (!caveMap.containsKey(nextMove)) {
                    sandCoordinate = nextMove;
                    cameToRest = false;
                    break;
                }
            }
        }
        caveMap.put(sandCoordinate, Contents.SAND);
    }

    public void addInfinityFloor() {
        infinityFloor = true;
    }

    public void addRockStructure(String rockStructure) {
        Coordinate lastCoordinate = null;
        for (String rockString : rockStructure.split(" -> ")) {
            Coordinate rockCoordinate = getCoordFromString(rockString);
            if (lastCoordinate == null) {
                addRockStructure(rockCoordinate);
                caveMap.put(rockCoordinate, Contents.ROCK);
            } else {
                CoordinateUtils.lineBetween(lastCoordinate, rockCoordinate).forEach(this::addRockStructure);
            }
            lastCoordinate = rockCoordinate;
        }
    }

    public void addRockStructure(Coordinate rockCoordinate) {
        caveMap.put(rockCoordinate, Contents.ROCK);
        greatestDepth = Math.max(greatestDepth, rockCoordinate.getY());
        minWidth = Math.min(minWidth, rockCoordinate.getX());
        maxWidth = Math.max(maxWidth, rockCoordinate.getX());
    }

    private static final Coordinate getCoordFromString(String coordString) {
        String[] coordSplit = coordString.split(",");
        return Coordinate.of(Integer.parseInt(coordSplit[0]), Integer.parseInt(coordSplit[1]));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UndergroundCave - starting from " + minWidth + " and going to " + maxWidth);
        for (int row = 0; row <= this.greatestDepth; row++) {
            sb.append("\n").append(String.format("%3d", row));
            for (int col = minWidth; col <= maxWidth; col++) {
                Contents currentContents = caveMap.get(Coordinate.of(col, row));
                if (currentContents == null) {
                    currentContents = Contents.AIR;
                }
                switch (currentContents) {
                    case AIR:
                        sb.append(".");
                        break;
                    case ROCK:
                        sb.append("#");
                        break;
                    case SAND:
                        sb.append("o");
                        break;
                }
            }
        }
        return sb.toString();
    }

    public enum Contents {AIR, ROCK, SAND;}
}
