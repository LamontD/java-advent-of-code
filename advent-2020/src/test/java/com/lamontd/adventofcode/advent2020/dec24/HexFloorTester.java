package com.lamontd.adventofcode.advent2020.dec24;

import junit.framework.Assert;
import org.apache.log4j.BasicConfigurator;
import org.javatuples.Triplet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HexFloorTester {
    private static final Logger logger = LoggerFactory.getLogger(HexFloorTester.class);

    @BeforeAll
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testBasicConstruction() {
        HexFloor hexFloor = new HexFloor();
        Assert.assertEquals(new HexTile(Triplet.with(0, 0, 0)), hexFloor.getTile(0, 0, 0));
    }

    @Test
    public void testDirectionStringConversion() {
        Assert.assertEquals(List.of(HexDirection.EAST, HexDirection.SOUTHEAST, HexDirection.WEST), HexFloor.convertStringToDirectionList("esew"));
        Assert.assertEquals(List.of(HexDirection.NORTHWEST, HexDirection.WEST,  HexDirection.SOUTHEAST, HexDirection.WEST, HexDirection.WEST), HexFloor.convertStringToDirectionList("nwwseww"));
    }

    @Test
    public void testBasicTraversal() {
        HexFloor hexFloor = new HexFloor();
        HexTile traversedTile = hexFloor.traverseFloor("esew");
        Assert.assertEquals(Triplet.with(0, -1, 1), traversedTile.getCoordinates());

        traversedTile = hexFloor.traverseFloor("nwwswee");
        Assert.assertEquals(Triplet.with(0, 0, 0), traversedTile.getCoordinates());
    }

    @Test
    public void testTraverseAndFlip() {
        HexFloor hexFloor = new HexFloor();
        List<String> traversals = List.of("sesenwnenenewseeswwswswwnenewsewsw",
                "neeenesenwnwwswnenewnwwsewnenwseswesw",
                "seswneswswsenwwnwse",
                "nwnwneseeswswnenewneswwnewseswneseene",
                "swweswneswnenwsewnwneneseenw",
                "eesenwseswswnenwswnwnwsewwnwsene",
                "sewnenenenesenwsewnenwwwse",
                "wenwwweseeeweswwwnwwe",
                "wsweesenenewnwwnwsenewsenwwsesesenwne",
                "neeswseenwwswnwswswnw",
                "nenwswwsewswnenenewsenwsenwnesesenew",
                "enewnwewneswsewnwswenweswnenwsenwsw",
                "sweneswneswneneenwnewenewwneswswnese",
                "swwesenesewenwneswnwwneseswwne",
                "enesenwswwswneneswsenwnewswseenwsese",
                "wnwnesenesenenwwnenwsewesewsesesew",
                "nenewswnwewswnenesenwnesewesw",
                "eneswnwswnwsenenwnwnwwseeswneewsenese",
                "neswnwewnwnwseenwseesewsenwsweewe",
                "wseweeenwnesenwwwswnew");
        for (String str : traversals) {
            HexTile tile = hexFloor.traverseFloor(str);
//            logger.debug("String " + str + " led us to tile " + tile);
            tile.flipTile();
            logger.debug("New Tile State: " + tile);
        }

        logger.debug("Tiles: " + hexFloor.getTiles());

        Assert.assertEquals(10L, hexFloor.getBlackTileCount());
    }
}
