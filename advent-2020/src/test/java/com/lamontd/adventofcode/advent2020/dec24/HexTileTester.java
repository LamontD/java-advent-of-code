package com.lamontd.adventofcode.advent2020.dec24;

import junit.framework.Assert;
import org.javatuples.Triplet;
import org.junit.jupiter.api.Test;

public class HexTileTester {
    @Test
    public void testConstruction() {
        HexTile hexTile = new HexTile(Triplet.with(0, 0, 0));
        Assert.assertEquals(Triplet.with(0, 0, 0), hexTile.getCoordinates());
        Assert.assertEquals(HexTile.Color.WHITE, hexTile.getTileOrientation());
    }

    @Test
    public void testGetNeighbors() {
        HexTile hexTile = new HexTile(Triplet.with(0, 0, 0));

        HexTile northeastNeighbor = hexTile.getNeighbor(HexDirection.NORTHEAST);
        Assert.assertEquals(Triplet.with(1, 0, -1), northeastNeighbor.getCoordinates());

        HexTile southeastNeighbor = hexTile.getNeighbor(HexDirection.SOUTHEAST);
        Assert.assertEquals(Triplet.with(0, -1, 1), southeastNeighbor.getCoordinates());

        HexTile eastNeighbor = hexTile.getNeighbor(HexDirection.EAST);
        Assert.assertEquals(Triplet.with(1, -1, 0), eastNeighbor.getCoordinates());

        HexTile southwestNeighbor = hexTile.getNeighbor(HexDirection.SOUTHWEST);
        Assert.assertEquals(Triplet.with(-1, 0, 1), southwestNeighbor.getCoordinates());

        HexTile westNeighbor = hexTile.getNeighbor(HexDirection.WEST);
        Assert.assertEquals(Triplet.with(-1, 1, 0), westNeighbor.getCoordinates());

        HexTile northwestNeighbor = hexTile.getNeighbor(HexDirection.NORTHWEST);
        Assert.assertEquals(Triplet.with(0, 1, -1), northwestNeighbor.getCoordinates());

        Assert.assertEquals(eastNeighbor.getNeighbor(HexDirection.NORTHWEST), northeastNeighbor);
    }

    @Test
    public void testFlipTile() {
        HexTile hexTile = new HexTile(Triplet.with(0, 0, 0));
        Assert.assertEquals(HexTile.Color.WHITE, hexTile.getTileOrientation());
        hexTile.flipTile();
        Assert.assertEquals(HexTile.Color.BLACK, hexTile.getTileOrientation());
    }
}
