package com.lamontd.adventofcode.advent2020.dec20;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MIBImage {
    private static final Logger logger = LoggerFactory.getLogger(MIBImage.class);

    private final Map<Integer, MIBImageTile> tileHashMap;
    private final MIBImageTile[][] tileImageMapping;
    private final int imageEdgeLength;

    public MIBImage(List<MIBImageTile> imageTiles) {
        this.tileHashMap = new HashMap<>();
        for (MIBImageTile imageTile : imageTiles) {
            this.tileHashMap.put(imageTile.getTileId(), imageTile);
        }
        this.imageEdgeLength = (int) Math.sqrt(imageTiles.size());
        this.tileImageMapping = new MIBImageTile[imageEdgeLength][imageEdgeLength];
    }

    public int getNumberImageTiles() {
        return tileHashMap.size();
    }

    public MIBImageTile getTileById(int tileId) {
        return tileHashMap.get(tileId);
    }

    public void determineTileArrangement() {
        MIBImageTile startingTile = tileHashMap.get(tileHashMap.keySet().toArray()[0]);
        logger.info("Starting Image Tile: " + startingTile.getTileId());

        MIBImageEdgeFinder edgeFinder = new MIBImageEdgeFinder(tileHashMap.values());
        LinkedList<MIBImageTile> startingRow = edgeFinder.assembleRowForTile(startingTile, imageEdgeLength);
        LinkedList<MIBImageTile> firstColumn = edgeFinder.assembleColumnForTile(startingRow.getFirst(), imageEdgeLength);

        for (int index=0; index < firstColumn.size(); index++) {
            tileImageMapping[index][0] = firstColumn.get(index);
            if (tileImageMapping[index][0].equals(startingRow.getFirst())) {
                for(int col=1; col < startingRow.size(); col++) {
                    tileImageMapping[index][col] = startingRow.get(col);
                }
            }
        }

        useEdgeFinderToPopulateRemainingTiles(edgeFinder);
    }

    public void useEdgeFinderToPopulateRemainingTiles(MIBImageEdgeFinder edgeFinder) {
        for (int row=0; row < imageEdgeLength; row++) {
            for (int col=1; col < imageEdgeLength; col++) {
                if (tileImageMapping[row][col] == null) {
                    MIBImageTile potentialTile = edgeFinder.findTileToTheRight(tileImageMapping[row][col-1]);
                    if (potentialTile == null) {
                        logger.warn("Can't find an image to match the right of "
                                + tileImageMapping[row][col - 1].getTileId() + " for position ["
                                + row + "," + col + "]");
                        logger.warn("Current value of that is: " + tileImageMapping[row][col - 1].toString());
                        throw new IllegalArgumentException("Having problems finding a tile for ["
                                + row + "," + col + "]");
                    } else {
                        tileImageMapping[row][col] = potentialTile;
                    }
                }
            }
        }
    }

    public long getFourCornersMultiplicationResult() {
        long multiplyResult = 1L;
        multiplyResult *= tileImageMapping[0][0].getTileId();
        multiplyResult *= tileImageMapping[0][imageEdgeLength-1].getTileId();
        multiplyResult *= tileImageMapping[imageEdgeLength-1][0].getTileId();
        multiplyResult *= tileImageMapping[imageEdgeLength-1][imageEdgeLength-1].getTileId();
        return multiplyResult;
    }

    public MIBImageTile toCombinedImageTile() {
        logger.info("Creating combined image of " + imageEdgeLength + "x" + imageEdgeLength + " grid of images");
        int imageTileLength = tileImageMapping[0][0].getBorderlessImage().length;
        char [][]combinedImage = new char[imageTileLength * imageEdgeLength][imageTileLength * imageEdgeLength];
        for (int tileRow = 0; tileRow < imageEdgeLength; tileRow++) {
            for (int tileCol = 0; tileCol < imageEdgeLength; tileCol++) {
                char[][] borderlessImage = tileImageMapping[tileRow][tileCol].getBorderlessImage();
                int imageOffsetRow = tileRow * imageTileLength;
                int imageOffsetCol = tileCol * imageTileLength;
                for (int imageRow = 0; imageRow < imageTileLength; imageRow++) {
                    System.arraycopy(borderlessImage[imageRow], 0, combinedImage[imageOffsetRow + imageRow], imageOffsetCol + 0, imageTileLength);
                }
            }
        }
        return new MIBImageTile(combinedImage);

    }

    public MIBImageTile getImageTileAtLocation(int row, int col) {
        if (row < tileImageMapping.length && row >= 0 && col < tileImageMapping.length && col >= 0) {
            return tileImageMapping[row][col];
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (MIBImageTile[] tileRow : tileImageMapping) {
            for (MIBImageTile imageTile : tileRow) {
                if (imageTile == null) {
                    builder.append("null");
                } else {
                    builder.append(imageTile.getTileId());
                }
                builder.append("\t");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

}
