package com.lamontd.adventofcode.advent2020.dec20;

import junit.framework.Assert;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MIBImageTileTester {
    private static final Logger logger = LoggerFactory.getLogger(MIBImageTileTester.class);

    @BeforeAll
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testImageTilePrinter() {
        MIBImageTile imageTile = new MIBImageTile(2311, List.of(
                "..##.#..#.",
                "##..#.....",
                "#...##..#.",
                "####.#...#",
                "##.##.###.",
                "##...#.###",
                ".#.#.#..##",
                "..#....#..",
                "###...#.#.",
                "..###..###"
        ));
        Assert.assertEquals("..##.#..#.", imageTile.getTopEdge());
        Assert.assertEquals("..###..###", imageTile.getBottomEdge());
        Assert.assertEquals(".#####..#.", imageTile.getLeftEdge());
        Assert.assertEquals("...#.##..#", imageTile.getRightEdge());
        logger.info(imageTile.toString());

        imageTile.rotateRight();
        Assert.assertEquals(".#..#####.", imageTile.getTopEdge());
        Assert.assertEquals("#..##.#...", imageTile.getBottomEdge());
        Assert.assertEquals("..###..###", imageTile.getLeftEdge());
        Assert.assertEquals("..##.#..#.", imageTile.getRightEdge());
        logger.info(imageTile.toString());
        imageTile.rotateRight();
        logger.info(imageTile.toString());
        imageTile.rotateRight();
        logger.info(imageTile.toString());
        imageTile.rotateRight();
        logger.info(imageTile.toString());
    }

    @Test
    public void testGetBorderlessImage() {
        MIBImageTile imageTile = new MIBImageTile(2311, List.of(
                "..##.#..#.",
                "##..#.....",
                "#...##..#.",
                "####.#...#",
                "##.##.###.",
                "##...#.###",
                ".#.#.#..##",
                "..#....#..",
                "###...#.#.",
                "..###..###"
        ));

        char[][] borderlessImageTile = imageTile.getBorderlessImage();
        Assert.assertEquals("#..#....", String.copyValueOf(borderlessImageTile[0]));
        Assert.assertEquals("##...#.#", String.copyValueOf(borderlessImageTile[7]));
    }

}
