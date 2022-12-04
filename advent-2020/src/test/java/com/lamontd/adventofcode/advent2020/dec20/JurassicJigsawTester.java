package com.lamontd.adventofcode.advent2020.dec20;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import junit.framework.Assert;
import org.apache.log4j.BasicConfigurator;
import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JurassicJigsawTester {
    private static final Logger logger = LoggerFactory.getLogger(JurassicJigsawTester.class);

    @BeforeAll
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testFindSeaMonsters() throws IOException {
        MIBImage image = MIBImageBuilder.extractMIBImage(new LocalResourceInput("advent2020/day20/advent-day20-example.txt"));
        image.determineTileArrangement();
        logger.info(image.toString());
        MIBImageTile giantImageTile = image.toCombinedImageTile();
        List<Pair<Integer, Integer>> seaMonsterCoords = new ArrayList<>();
        for (int i=0; i < 4; i++) {
            seaMonsterCoords = giantImageTile.findSeaMonstersInImage();
            if (!seaMonsterCoords.isEmpty()) {
                break;
            }
            giantImageTile.flipImage();
            seaMonsterCoords = giantImageTile.findSeaMonstersInImage();
            if (!seaMonsterCoords.isEmpty()) {
                break;
            }
            giantImageTile.flipImage();
            giantImageTile.rotateRight();
        }
        Assert.assertEquals(2, seaMonsterCoords.size());
    }

    @Test
    public void testMarkSeaMonsters() throws IOException {
        MIBImage image = MIBImageBuilder.extractMIBImage(new LocalResourceInput("advent2020/day20/advent-day20-example.txt"));
        image.determineTileArrangement();
        logger.info(image.toString());
        MIBImageTile giantImageTile = image.toCombinedImageTile();
        JurassicJigsaw.findAndMarkSeaMonsters(giantImageTile);
        logger.info(giantImageTile.toString());
        Assert.assertEquals(273, giantImageTile.calculateWaterRoughness());
    }
}
