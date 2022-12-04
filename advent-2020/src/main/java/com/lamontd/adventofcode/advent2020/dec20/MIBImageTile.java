package com.lamontd.adventofcode.advent2020.dec20;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MIBImageTile {
    private final int tileId;
    private final char[][] rawImageData;
    private int orientation = 0;
    private boolean flipped = false;

    private static final List<String> seaMonsterPattern = List.of(
            "                  # ",
            "#    ##    ##    ###",
            " #  #  #  #  #  #   "
    );

    public MIBImageTile(int tileId, List<String> imageStrings) {
        this.tileId = tileId;
        this.rawImageData = new char[imageStrings.size()][imageStrings.size()];
        for (int row = 0; row < imageStrings.size(); row++) {
            for (int col = 0; col < imageStrings.size(); col++) {
                rawImageData[row][col] = imageStrings.get(row).charAt(col);
            }
        }
    }

    public MIBImageTile(char[][]imageData) {
        this.tileId = -1;
        this.rawImageData = new char[imageData.length][imageData.length];
        for (int row=0; row < imageData.length; row++) {
            System.arraycopy(imageData[row], 0, rawImageData[row], 0, imageData.length);
        }
    }

    public int getTileId() { return tileId; }
    public int getOrientation() { return orientation; }
    public boolean isFlipped() { return flipped; }

    public String getEdge(TileOrientation orientation) {
        switch(orientation) {
            case TOP:
                return getTopEdge();
            case BOTTOM:
                return getBottomEdge();
            case LEFT:
                return getLeftEdge();
            case RIGHT:
                return getRightEdge();
        }
        throw new IllegalArgumentException("Cannot process edge with orientation " + orientation);
    }

    public String getTopEdge() { return new String(rawImageData[0]); }

    public String getBottomEdge() { return new String(rawImageData[rawImageData.length - 1]); }

    public String getLeftEdge() {
        char[] edgeArray = new char[rawImageData.length];
        for (int i=0; i < rawImageData.length; i++) {
            edgeArray[i] = rawImageData[i][0];
        }
        return new String(edgeArray);
    }

    public String getRightEdge() {
        char[] edgeArray = new char[rawImageData.length];
        for (int i=0; i < rawImageData.length; i++) {
            edgeArray[i] = rawImageData[i][rawImageData.length - 1];
        }
        return new String(edgeArray);
    }

    public List<Pair<Integer, Integer>> findSeaMonstersInImage() {
        List<Pair<Integer, Integer>> seaMonsterCoordinates = new ArrayList<>();
        List<Pair<Integer, Integer>> seaMonsterOnPoints = convertToOnPoints(seaMonsterPattern);
        for (int dataRow=0; dataRow < rawImageData.length; dataRow++) {
            for (int dataCol=0; dataCol < rawImageData[dataRow].length; dataCol++) {
                boolean seaMonsterFound = true;
                for(Pair<Integer, Integer> testPoint : seaMonsterOnPoints) {
                    int relRow = dataRow + testPoint.getValue0();
                    int relCol = dataCol + testPoint.getValue1();
                    if ((relRow >= rawImageData.length)
                            || (relCol >= rawImageData[dataRow].length)
                            || (rawImageData[relRow][relCol] != '#')) {
                        seaMonsterFound = false;
                        break;
                    }
                }
                if (seaMonsterFound) {
                    seaMonsterCoordinates.add(Pair.with(dataRow, dataCol));
                }
            }
        }
        return seaMonsterCoordinates;
    }

    public void markSeaMonsters(List<Pair<Integer, Integer>> seaMonsterCoords) {
        List<Pair<Integer, Integer>> seaMonsterOnPoints = convertToOnPoints(seaMonsterPattern);
        for (Pair<Integer, Integer> seaMonsterStart : seaMonsterCoords) {
            for (Pair<Integer, Integer> onPoint : seaMonsterOnPoints) {
                int monsterRow = seaMonsterStart.getValue0() + onPoint.getValue0();
                int monsterCol = seaMonsterStart.getValue1() + onPoint.getValue1();
                if (rawImageData[monsterRow][monsterCol] != '#' && rawImageData[monsterRow][monsterCol] != 'O') {
                    throw new IllegalArgumentException("Invalid attempt to mark sea monster where none exists");
                }
                rawImageData[monsterRow][monsterCol] = 'O';
            }
        }
    }

    public int calculateWaterRoughness() {
        int waterRoughness = 0;
        for (char[] rawImageDatum : rawImageData) {
            for (char c : rawImageDatum) {
                if (c == '#') {
                    waterRoughness++;
                }
            }
        }
        return waterRoughness;
    }

    public static List<Pair<Integer, Integer>> convertToOnPoints(List<String> characters) {
        List<Pair<Integer, Integer>> onPoints = new ArrayList<>();
        for (int row=0; row < characters.size(); row++) {
            String rowString = characters.get(row);
            for (int col=0; col < rowString.length(); col++) {
                if (rowString.charAt(col) == '#') {
                    onPoints.add(Pair.with(row, col));
                }
            }
        }
        return onPoints;
    }

    public void rotateRight() {
        orientation += 90;
        orientation %= 360;

        for (int layer = 0; layer < rawImageData.length / 2; layer++) {
            int last = rawImageData.length - 1 - layer;
            for (int i = layer; i < last; i++) {
                int offset = i - layer;
                char top = rawImageData[layer][i];
                rawImageData[layer][i] = rawImageData[last-offset][layer];
                rawImageData[last-offset][layer] = rawImageData[last][last - offset];
                rawImageData[last][last - offset] = rawImageData[i][last];
                rawImageData[i][last] = top;
            }
        }
    }

    public void flipImage() {
        flipped = !flipped;

        char[][] flippedImage = new char[rawImageData.length][rawImageData.length];
        for (int writeRow = 0; writeRow < rawImageData.length; writeRow++) {
            System.arraycopy(rawImageData[rawImageData.length - writeRow - 1], 0, flippedImage[writeRow], 0, rawImageData.length);
        }

        for (int row=0; row < rawImageData.length; row++) {
            System.arraycopy(flippedImage[row], 0, rawImageData[row], 0, rawImageData.length);
        }
    }

    public void rotateToOrientation(int orientation) {
        if (orientation % 90 != 0) {
            throw new IllegalArgumentException("Cannot rotate orientation to " + orientation);
        }
        while (this.orientation != orientation) {
            rotateRight();
        }
    }

    public char[][] getBorderlessImage() {
        char[][] borderlessImage = new char[rawImageData.length-2][rawImageData.length-2];
        for (int row=0; row < rawImageData.length - 2; row++) {
            System.arraycopy(rawImageData[row + 1], 1, borderlessImage[row], 0, rawImageData.length - 2);
        }
        return borderlessImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MIBImageTile imageTile = (MIBImageTile) o;
        return tileId == imageTile.tileId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tileId);
    }

    @Override public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Tile #").append(tileId).append(" (orientation ").append(orientation);
        if (flipped) {
            builder.append(" flipped");
        }
        builder.append(')').append("\n");
        for (char[] rawImageDatum : rawImageData) {
            for (int col = 0; col < rawImageData.length; col++) {
                builder.append(rawImageDatum[col]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

}
