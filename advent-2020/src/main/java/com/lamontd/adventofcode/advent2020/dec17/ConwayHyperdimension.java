package com.lamontd.adventofcode.advent2020.dec17;

import java.util.*;

public class ConwayHyperdimension {
    private final Map<ConwayHypercoordinate, CubeState> dimensionMap = new HashMap<>(2048);
    private boolean isFlat = true;
    private ConwayHypercoordinate topLeft = ConwayHypercoordinate.at(0, 0, 0, 0);
    private ConwayHypercoordinate bottomRight;

    public ConwayHyperdimension(List<String> planeState) {
        int zPos = 0, wPos = 0;
        for (int y=0; y < planeState.size(); y++) {
            String yString = planeState.get(y);
            for (int x=0; x < yString.length(); x++) {
                CubeState state = CubeState.fromRepresentation(yString.charAt(x));
                if (state == CubeState.ACTIVE) {
                    dimensionMap.put(ConwayHypercoordinate.at(x, y, zPos, wPos), CubeState.ACTIVE);
                }
            }
        }
        this.bottomRight = ConwayHypercoordinate.at(planeState.size(), planeState.size(), 0, 0);
    }

    public void executeCycle() {
        Map<ConwayHypercoordinate, CubeState> newStateMap = new HashMap<>(2048);
        Set<ConwayHypercoordinate> coordinatesToCheck = dimensionMap.keySet();
        for (ConwayHypercoordinate existingCoordinate : coordinatesToCheck) {
            if (getNewStateBasedOnNeighborCount(existingCoordinate) == CubeState.ACTIVE) {
                newStateMap.put(existingCoordinate, CubeState.ACTIVE);
            }
            for (ConwayHypercoordinate neighbor : existingCoordinate.getNeighbors()) {
                if (getNewStateBasedOnNeighborCount(neighbor) == CubeState.ACTIVE) {
                    newStateMap.put(neighbor, CubeState.ACTIVE);
                }
            }
        }
        if (isFlat) {
            isFlat = false;
        }
        this.topLeft = ConwayHypercoordinate.at(this.topLeft.getX() - 1, this.topLeft.getY() - 1, this.topLeft.getZ() - 1, this.topLeft.getW() - 1);
        this.bottomRight = ConwayHypercoordinate.at(this.bottomRight.getX() + 1, this.bottomRight.getY() + 1, this.bottomRight.getZ() + 1, this.bottomRight.getW() + 1);
        dimensionMap.clear();
        dimensionMap.putAll(newStateMap);
    }

    private CubeState getNewStateBasedOnNeighborCount(ConwayHypercoordinate conwayCoordinate) {
        CubeState currentState = dimensionMap.containsKey(conwayCoordinate) ? dimensionMap.get(conwayCoordinate) : CubeState.INACTIVE;
        int activeNeighbors = getActiveNeighborCount(conwayCoordinate);
        if (currentState == CubeState.ACTIVE && (activeNeighbors == 2 || activeNeighbors == 3))
            return CubeState.ACTIVE;
        if (currentState == CubeState.INACTIVE && activeNeighbors == 3)
            return CubeState.ACTIVE;

        return CubeState.INACTIVE;
    }

    public int getActiveNeighborCount(ConwayHypercoordinate conwayCoordinate) {
        int activeNeighborCount = 0;
        for (ConwayHypercoordinate neighbor : conwayCoordinate.getNeighbors()) {
            if (dimensionMap.get(neighbor) == CubeState.ACTIVE)
                activeNeighborCount++;
        }
        return activeNeighborCount;
    }

    public Set<ConwayHypercoordinate> getActiveNeighbors(ConwayHypercoordinate conwayCoordinate) {
        Set<ConwayHypercoordinate> activeNeighbors = new HashSet<>();
        for (ConwayHypercoordinate neighbor : conwayCoordinate.getNeighbors()) {
            if (dimensionMap.get(neighbor) == CubeState.ACTIVE) {
                activeNeighbors.add(neighbor);
            }
        }
        return activeNeighbors;
    }

    public int getActiveNeighborCount(int xPos, int yPos, int zPos, int wPos) {
        return getActiveNeighborCount(ConwayHypercoordinate.at(xPos, yPos, zPos, wPos));
    }

    public CubeState getState(ConwayHypercoordinate conwayCoordinate) {
        return dimensionMap.containsKey(conwayCoordinate) ? dimensionMap.get(conwayCoordinate) : CubeState.INACTIVE;
    }
    public CubeState getState(int x, int y, int z, int w) { return getState(ConwayHypercoordinate.at(x, y, z, w)); }
    public int getActiveCubeCount() {
        return (int)dimensionMap.values().stream().filter(state -> state == CubeState.ACTIVE).count();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (int w=topLeft.getW(); w <= bottomRight.getW(); w++) {
            for (int z = topLeft.getZ(); z <= bottomRight.getZ(); z++) {
                sb.append("z == ").append(z).append("\n");
                for (int y = topLeft.getY(); y <= bottomRight.getY(); y++) {
                    for (int x = topLeft.getX(); x <= bottomRight.getX(); x++) {
                        sb.append(getState(x, y, z, w).getRepresentation());
                    }
                    sb.append("\n");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
