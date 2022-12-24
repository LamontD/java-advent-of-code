package com.lamontd.adventofcode.advent2022.dec18;

import com.lamontd.adventofcode.utils.coord.CubicCoordinate;
import com.lamontd.adventofcode.utils.SimpleMath;
import com.lamontd.adventofcode.utils.coord.RelativeDirections;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DropletGrid {
    private static final Logger logger = LoggerFactory.getLogger(DropletGrid.class);
    private final Set<CubicCoordinate> inputCoordinates = new HashSet<>();

    private Data[][][] dataGrid = null;

    private int greatestX;
    private int greatestY;
    private int greatestZ;

    public void addDroplet(CubicCoordinate dropletCoordinate) {
        inputCoordinates.add(dropletCoordinate);
        greatestX = Integer.max(greatestX, dropletCoordinate.getX());
        greatestY = Integer.max(greatestY, dropletCoordinate.getY());
        greatestZ = Integer.max(greatestZ, dropletCoordinate.getZ());
    }

    public void createDataGrid() {
        this.dataGrid = new Data[greatestX + 2][greatestY + 2][greatestZ + 2];
        for (int x = 0; x < greatestX + 2; x++) {
            for (int y = 0; y < greatestY + 2; y++) {
                for (int z = 0; z < greatestZ + 2; z++) {
                    dataGrid[x][y][z] = inputCoordinates.contains(CubicCoordinate.of(x, y, z)) ? Data.LAVA : Data.AIR;
                }
            }
        }
    }

    public int getExposedSidesUsingGrid(CubicCoordinate coordinate) {
        int exposedSides = 0;
        List<Triplet<Integer, Integer, Integer>> compareTriplets = List.of(
                Triplet.with(1, 0, 0),
                Triplet.with(-1, 0, 0),
                Triplet.with(0, -1, 0),
                Triplet.with(0, 1, 0),
                Triplet.with(0, 0, 1),
                Triplet.with(0, 0, -1)
        );
        for (Triplet<Integer, Integer, Integer> shifter : compareTriplets) {
            int xCoord = coordinate.getX() + shifter.getValue0();
            int yCoord = coordinate.getY() + shifter.getValue1();
            int zCoord = coordinate.getZ() + shifter.getValue2();
            if (xCoord < 0 || yCoord < 0 || zCoord < 0) {
                exposedSides++;
            } else if (xCoord > greatestX + 1 || yCoord > greatestY + 1 || zCoord > greatestZ + 1) {
                exposedSides++;
            } else if (dataGrid[xCoord][yCoord][zCoord] == Data.AIR) {
                exposedSides++;
            }
        }
        return exposedSides;
    }

    public int floodGrid() {
        int internalSurfaceArea = 0;
        Queue<CubicCoordinate> coordinatesToCheck = new ArrayDeque<>();
        Set<CubicCoordinate> beenThereDoneThat = new HashSet<>();
        coordinatesToCheck.add(CubicCoordinate.of(0, 0, 0));
        while (!coordinatesToCheck.isEmpty()) {
            CubicCoordinate coordinate = coordinatesToCheck.poll();
            if (dataGrid[coordinate.getX()][coordinate.getY()][coordinate.getZ()] == Data.AIR) {
                dataGrid[coordinate.getX()][coordinate.getY()][coordinate.getZ()] = Data.STEAM;
            }
            beenThereDoneThat.add(coordinate);
            for (CubicCoordinate neighbor : getValidNeighbors(coordinate)) {
                if (!beenThereDoneThat.contains(neighbor)) {
                    if (inputCoordinates.contains(neighbor)) {
                        internalSurfaceArea++;
                    } else if (dataGrid[neighbor.getX()][neighbor.getY()][neighbor.getZ()] == Data.AIR) {
                        dataGrid[neighbor.getX()][neighbor.getY()][neighbor.getZ()] = Data.STEAM;
                        coordinatesToCheck.add(neighbor);
                    }
                }
            }
        }
        logger.info("I think my external surface area is " + internalSurfaceArea);
        return internalSurfaceArea;
    }

    public int getTotalExposedSidesCountUsingGrid() {
        createDataGrid();
        int totalExposedSides = 0;
        for (CubicCoordinate coordinate : inputCoordinates) {
            totalExposedSides += getExposedSidesUsingGrid(coordinate);
        }
        return totalExposedSides;
    }

    public int getTotalExposedSidesExcludingAirPockets() {
        int totalExposedSides = floodGrid();

        // Flooding the grid won't cover the edge neighbors; for that, we'll have to add another check
        for (CubicCoordinate lavaCoordinate : inputCoordinates) {
            totalExposedSides += getInvalidGridNeighbors(lavaCoordinate).size();
        }
        return totalExposedSides;
    }

    private List<CubicCoordinate> getValidNeighbors(CubicCoordinate coordinate) {
        List<CubicCoordinate> neighbors = new ArrayList<>();
        for (RelativeDirections direction : RelativeDirections.values()) {
            int xCoord = coordinate.getX() + direction.getXAdjustment();
            int yCoord = coordinate.getY() + direction.getYAdjustment();
            int zCoord = coordinate.getZ() + direction.getZAdjustment();
            if (SimpleMath.between(xCoord, 0, greatestX + 1)
                    && SimpleMath.between(yCoord, 0, greatestY + 1)
                    && SimpleMath.between(zCoord, 0, greatestZ + 1)) {
                neighbors.add(CubicCoordinate.of(xCoord, yCoord, zCoord));
            }
        }
        return neighbors;
    }

    private List<CubicCoordinate> getInvalidGridNeighbors(CubicCoordinate coordinate) {
        List<CubicCoordinate> badNeighbors = new ArrayList<>();
        for (RelativeDirections direction : RelativeDirections.values()) {
            int xCoord = coordinate.getX() + direction.getXAdjustment();
            int yCoord = coordinate.getY() + direction.getYAdjustment();
            int zCoord = coordinate.getZ() + direction.getZAdjustment();
            if (!SimpleMath.between(xCoord, 0, greatestX + 1)
                    || !SimpleMath.between(yCoord, 0, greatestY + 1)
                    || !SimpleMath.between(zCoord, 0, greatestZ + 1)) {
                badNeighbors.add(CubicCoordinate.of(xCoord, yCoord, zCoord));
            }
        }
        return badNeighbors;
    }

    public enum Data {LAVA, AIR, STEAM}
}
