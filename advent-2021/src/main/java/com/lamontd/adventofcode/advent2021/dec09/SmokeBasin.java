package com.lamontd.adventofcode.advent2021.dec09;

import com.lamontd.adventofcode.utils.BasicCounter;
import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class SmokeBasin {
    private static final Logger logger = LoggerFactory.getLogger(SmokeBasin.class);

    private HeightMap heightMap;

    public void loadHeightMap(String inputFile) throws IOException {
        LocalResourceInput resourceInput = new LocalResourceInput(inputFile);
        heightMap = new HeightMap(resourceInput.getInput());
    }

    public int getHeightRisk() {
        final BasicCounter counter = new BasicCounter();
        heightMap.calculateLowPoints().stream()
                .forEach(point -> {
                    counter.increment(1 + heightMap.getHeight(point));
                });
        return counter.currentValue();
    }

    public long calculateBasins() {
        final Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> basinMap = new HashMap<>();
        heightMap.calculateLowPoints().stream()
                .forEach(point -> basinMap.put(point, heightMap.recursivelyCalculateBasin(point)));
        logger.info("I found " + basinMap.size() + " basins");
        final List<Integer> basinSizes = new ArrayList<>();
        basinMap.values().stream().forEach(basin -> basinSizes.add(basin.size()));
        long basinScore = 1;
        Collections.sort(basinSizes);
        for (int i=1; i <= 3; i++) {
            basinScore *= basinSizes.get(basinSizes.size() - i);
        }
        return basinScore;
    }


    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Smoke Basin");
        logger.info("Loading the sample data...");
        SmokeBasin basin = new SmokeBasin();
        basin.loadHeightMap("day09/sample.txt");
        logger.info(basin.heightMap.toString());
        logger.info("Finding the low points and risk sum");
        logger.info("Height risk is " + basin.getHeightRisk());
        logger.info("Calculate basins...");
        logger.info("Basin score is " + basin.calculateBasins());
        logger.info("Part 1: Doing this for real");
        basin.loadHeightMap("day09/input.txt");
        logger.info("Part 1: Height risk is " + basin.getHeightRisk());
        logger.info("Part 2: Calculating basins...");
        long basinScore = basin.calculateBasins();
        logger.info("Part 2: The basin score is " + basinScore);
        timer.finish();
    }
}
