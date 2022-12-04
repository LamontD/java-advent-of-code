package com.lamontd.adventofcode.advent2020.dec20;

import java.util.*;

public class MIBImageEdgeFinder {
    private final Set<MIBImageTile> unmappedTiles;
    private final HashMap<Integer, MIBImageTile> workingTileMap = new HashMap<>();

    public MIBImageEdgeFinder(Collection<MIBImageTile> unmappedTiles) {
        this.unmappedTiles = new HashSet<>(unmappedTiles);
        this.unmappedTiles.forEach(tile -> workingTileMap.put(tile.getTileId(), tile));
    }

    public LinkedList<MIBImageTile> assembleRowForTile(MIBImageTile startingTile, int expectedRowSize) {
        unmappedTiles.remove(startingTile);
        final LinkedList<MIBImageTile> assembledRow = new LinkedList<>();

        while(assembledRow.size() != expectedRowSize) {
            unmappedTiles.forEach(tile -> workingTileMap.put(tile.getTileId(), tile));
            assembledRow.clear();
            startingTile.rotateRight();

            assembledRow.add(startingTile);
            MIBImageTile rightEdge = findTileToMatchEdgeFromWorkingMap(startingTile.getRightEdge(), TileOrientation.LEFT);
            while (rightEdge != null) {
                assembledRow.addLast(rightEdge);
                workingTileMap.remove(rightEdge.getTileId());
                rightEdge = findTileToMatchEdgeFromWorkingMap(rightEdge.getRightEdge(), TileOrientation.LEFT);
            }

            MIBImageTile leftEdge = findTileToMatchEdgeFromWorkingMap(startingTile.getLeftEdge(), TileOrientation.RIGHT);
            while (leftEdge != null) {
                assembledRow.addFirst(leftEdge);
                workingTileMap.remove(leftEdge.getTileId());
                leftEdge = findTileToMatchEdgeFromWorkingMap(startingTile.getLeftEdge(), TileOrientation.RIGHT);
            }
        }

        assembledRow.forEach(unmappedTiles::remove);

        return assembledRow;
    }

    public LinkedList<MIBImageTile> assembleColumnForTile(MIBImageTile startingTile, int expectedColumnSize) {
        unmappedTiles.remove(startingTile);
        workingTileMap.clear();
        unmappedTiles.forEach(tile -> workingTileMap.put(tile.getTileId(), tile));

        LinkedList<MIBImageTile> assembledColumn = new LinkedList<>();
        assembledColumn.add(startingTile);

        MIBImageTile aboveTile = findTileToMatchEdgeFromWorkingMap(startingTile.getTopEdge(), TileOrientation.BOTTOM);
        while (aboveTile != null) {
            assembledColumn.addFirst(aboveTile);
            workingTileMap.remove(aboveTile.getTileId());
            aboveTile = findTileToMatchEdgeFromWorkingMap(aboveTile.getTopEdge(), TileOrientation.BOTTOM);
        }

        MIBImageTile belowTile = findTileToMatchEdgeFromWorkingMap(startingTile.getBottomEdge(), TileOrientation.TOP);
        while (belowTile != null) {
            assembledColumn.addLast(belowTile);
            workingTileMap.remove(belowTile.getTileId());
            belowTile = findTileToMatchEdgeFromWorkingMap(belowTile.getBottomEdge(), TileOrientation.TOP);
        }

        assembledColumn.forEach(unmappedTiles::remove);

        return assembledColumn;
    }

    public MIBImageTile findTileToTheRight(MIBImageTile referenceTile) {
        unmappedTiles.remove(referenceTile);
        workingTileMap.remove(referenceTile.getTileId());
        MIBImageTile rightTile = findTileToMatchEdgeFromWorkingMap(referenceTile.getRightEdge(), TileOrientation.LEFT);
        if (rightTile != null) {
            unmappedTiles.remove(rightTile);
            workingTileMap.remove(rightTile.getTileId());
        }
        return rightTile;
    }

    private MIBImageTile findTileToMatchEdgeFromWorkingMap(String edgeString, TileOrientation edgeToMatch) {
        for (MIBImageTile imageTileToCheck : workingTileMap.values()) {
            for (int i = 0; i < 4; i++) {
                String edgeToCheck = imageTileToCheck.getEdge(edgeToMatch);
                if (edgeString.equals(edgeToCheck)) {
                    return imageTileToCheck;
                }
                imageTileToCheck.flipImage();
                edgeToCheck = imageTileToCheck.getEdge(edgeToMatch);
                if (edgeString.equals(edgeToCheck)) {
                    return imageTileToCheck;
                }
                imageTileToCheck.flipImage();
                imageTileToCheck.rotateRight();
            }
        }
        return null;
    }

    public Set<MIBImageTile> getUnmappedTiles() {
        return unmappedTiles;
    }
}
