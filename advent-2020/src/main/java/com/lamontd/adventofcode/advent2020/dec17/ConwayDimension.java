package com.lamontd.adventofcode.advent2020.dec17;

import java.util.*;

public class ConwayDimension {
    private final Map<ConwayCoordinate, CubeState> dimensionMap = new HashMap<>(2048);
    private boolean isFlat = true;
    private ConwayCoordinate topLeft = ConwayCoordinate.at(0, 0, 0);
    private ConwayCoordinate bottomRight;

    public ConwayDimension(List<String> planeState) {
        int zPos = 0;
        for (int y=0; y < planeState.size(); y++) {
            String yString = planeState.get(y);
            for (int x=0; x < yString.length(); x++) {
                CubeState state = CubeState.fromRepresentation(yString.charAt(x));
                if (state == CubeState.ACTIVE) {
                    dimensionMap.put(ConwayCoordinate.at(x, y, zPos), CubeState.ACTIVE);
                }
            }
        }
        this.bottomRight = ConwayCoordinate.at(planeState.size(), planeState.size(), 0);
    }

    public void executeCycle() {
        Map<ConwayCoordinate, CubeState> newStateMap = new HashMap<>(2048);
        Set<ConwayCoordinate> coordinatesToCheck = dimensionMap.keySet();
        for (ConwayCoordinate existingCoordinate : coordinatesToCheck) {
            if (getNewStateBasedOnNeighborCount(existingCoordinate) == CubeState.ACTIVE) {
                newStateMap.put(existingCoordinate, CubeState.ACTIVE);
            }
            for (ConwayCoordinate neighbor : existingCoordinate.getNeighbors()) {
                if (getNewStateBasedOnNeighborCount(neighbor) == CubeState.ACTIVE) {
                    newStateMap.put(neighbor, CubeState.ACTIVE);
                }
            }
        }
        if (isFlat) {
            isFlat = false;
        }
        this.topLeft = ConwayCoordinate.at(this.topLeft.getX() - 1, this.topLeft.getY() - 1, this.topLeft.getZ() - 1);
        this.bottomRight = ConwayCoordinate.at(this.bottomRight.getX() + 1, this.bottomRight.getY() + 1, this.bottomRight.getZ() + 1);
        dimensionMap.clear();
        dimensionMap.putAll(newStateMap);
    }

    private CubeState getNewStateBasedOnNeighborCount(ConwayCoordinate conwayCoordinate) {
        CubeState currentState = dimensionMap.containsKey(conwayCoordinate) ? dimensionMap.get(conwayCoordinate) : CubeState.INACTIVE;
        int activeNeighbors = getActiveNeighborCount(conwayCoordinate);
        if (currentState == CubeState.ACTIVE && (activeNeighbors == 2 || activeNeighbors == 3))
            return CubeState.ACTIVE;
        if (currentState == CubeState.INACTIVE && activeNeighbors == 3)
            return CubeState.ACTIVE;

        return CubeState.INACTIVE;
    }

    public int getActiveNeighborCount(ConwayCoordinate conwayCoordinate) {
        int activeNeighborCount = 0;
        for (ConwayCoordinate neighbor : conwayCoordinate.getNeighbors()) {
            if (dimensionMap.get(neighbor) == CubeState.ACTIVE)
                activeNeighborCount++;
        }
        return activeNeighborCount;
    }

    public Set<ConwayCoordinate> getActiveNeighbors(ConwayCoordinate conwayCoordinate) {
        Set<ConwayCoordinate> activeNeighbors = new HashSet<>();
        for (ConwayCoordinate neighbor : conwayCoordinate.getNeighbors()) {
            if (dimensionMap.get(neighbor) == CubeState.ACTIVE) {
                activeNeighbors.add(neighbor);
            }
        }
        return activeNeighbors;
    }

    public int getActiveNeighborCount(int xPos, int yPos, int zPos) {
        return getActiveNeighborCount(ConwayCoordinate.at(xPos, yPos, zPos));
    }

    public CubeState getState(ConwayCoordinate conwayCoordinate) {
        return dimensionMap.containsKey(conwayCoordinate) ? dimensionMap.get(conwayCoordinate) : CubeState.INACTIVE;
    }
    public CubeState getState(int x, int y, int z) { return getState(ConwayCoordinate.at(x, y, z)); }
    public int getActiveCubeCount() {
        return (int)dimensionMap.values().stream().filter(state -> state == CubeState.ACTIVE).count();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (int z= topLeft.getZ(); z <= bottomRight.getZ(); z++) {
            sb.append("z == ").append(z).append("\n");
            for (int y=topLeft.getY(); y <= bottomRight.getY(); y++) {
                for (int x= topLeft.getX(); x <= bottomRight.getX(); x++) {
                    CubeState state = dimensionMap.get(ConwayCoordinate.at(x, y, z));
                    sb.append(state == null ? CubeState.INACTIVE.getRepresentation() : state.getRepresentation());
                }
                sb.append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
