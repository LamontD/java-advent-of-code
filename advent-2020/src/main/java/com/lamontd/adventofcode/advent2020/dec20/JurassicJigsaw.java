package com.lamontd.adventofcode.advent2020.dec20;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JurassicJigsaw {
    private static final Logger logger = LoggerFactory.getLogger(JurassicJigsaw.class);

    public static void findAndMarkSeaMonsters(MIBImageTile giantImageTile) {
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
        if (seaMonsterCoords.isEmpty()) {
            throw new IllegalArgumentException("Could not find sea monsters in image");
        }
        giantImageTile.markSeaMonsters(seaMonsterCoords);
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to Jurassic Jigsaw!");

        logger.info("Building my image now...");
        MIBImage sampleImage = MIBImageBuilder.extractMIBImage(new LocalResourceInput("day20/advent-day20-input.txt"));
        try {
            sampleImage.determineTileArrangement();
            logger.info("Image: " + sampleImage.toString());
            logger.info("Part 1: Four corners result is " + sampleImage.getFourCornersMultiplicationResult());
            logger.info("Part 2: Creating combined image tile...");
            MIBImageTile combinedImageTile = sampleImage.toCombinedImageTile();
            logger.info("Part 2: Finding and marking sea monsters...");
            findAndMarkSeaMonsters(combinedImageTile);
            logger.info("Part 2: The water roughness is " + combinedImageTile.calculateWaterRoughness());

        } catch (Exception e) {
            logger.error("Failure: ", e);
            logger.error("Current state: " + sampleImage.toString());
        }

        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");
    }
}
