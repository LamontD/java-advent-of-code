package com.lamontd.aoc.advent2020.dec20;

import com.lamontd.aoc.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class MIBImageTester {
    private static final Logger logger = LoggerFactory.getLogger(MIBImageTester.class);

    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testBasicMIBImageConstruction() throws IOException {
        MIBImage image = MIBImageBuilder.extractMIBImage(new LocalResourceInput("advent2020/day20/advent-day20-example.txt"));
        Assert.assertEquals(9, image.getNumberImageTiles());
    }

    @Test
    public void testBasicMIBImageRetrieval() throws IOException {
        MIBImage image = MIBImageBuilder.extractMIBImage(new LocalResourceInput("advent2020/day20/advent-day20-example.txt"));
        MIBImageTile imageTile = image.getTileById(1951);
        logger.info(imageTile.toString());
        imageTile.rotateToOrientation(180);
        logger.info(imageTile.toString());
    }

    @Test
    public void testMIBImageAssembly() throws IOException {
        MIBImage image = MIBImageBuilder.extractMIBImage(new LocalResourceInput("advent2020/day20/advent-day20-example.txt"));
        image.determineTileArrangement();
        logger.info(image.toString());
        Assert.assertEquals(20899048083289L, image.getFourCornersMultiplicationResult());
    }

    @Test
    public void testGetCombinedImage() throws IOException {
        MIBImage image = MIBImageBuilder.extractMIBImage(new LocalResourceInput("advent2020/day20/advent-day20-example.txt"));
        image.determineTileArrangement();
        logger.info(image.toString());
        MIBImageTile giantImageTile = image.toCombinedImageTile();
        giantImageTile.flipImage();
        giantImageTile.rotateRight();
        giantImageTile.rotateRight();
        giantImageTile.rotateRight();
        giantImageTile.flipImage();
        Assert.assertEquals(".####...#####..#...###..", giantImageTile.getTopEdge());
        List<Pair<Integer, Integer>> seaMonsterCoords = giantImageTile.findSeaMonstersInImage();
        Assert.assertEquals(2, seaMonsterCoords.size());
    }

}
