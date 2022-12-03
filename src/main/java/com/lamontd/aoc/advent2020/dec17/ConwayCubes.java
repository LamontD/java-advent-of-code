package com.lamontd.aoc.advent2020.dec17;

import com.lamontd.aoc.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ConwayCubes {
    private static final Logger logger = LoggerFactory.getLogger(ConwayCubes.class);

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to Conway Cubes!");
        logger.info("Getting input...");
        LocalResourceInput resourceInput = new LocalResourceInput("advent2020/day17/advent-day17-input.txt");
        logger.info("Initializing the Conway Dimension...");
        ConwayDimension conwayDimension = new ConwayDimension(resourceInput.getInput());
        logger.info("Performing state transitions:");
        conwayDimension.executeCycle();
        conwayDimension.executeCycle();
        conwayDimension.executeCycle();
        conwayDimension.executeCycle();
        conwayDimension.executeCycle();
        conwayDimension.executeCycle();
        logger.info("After six cycles, we have " + conwayDimension.getActiveCubeCount() + " active ConwayCubes!");
        logger.info("Oh shit -- it's a hyperdimension! Initializing...");
        ConwayHyperdimension conwayHyperdimension = new ConwayHyperdimension(resourceInput.getInput());
        logger.info("Performing state transitions...");
        conwayHyperdimension.executeCycle();
        conwayHyperdimension.executeCycle();
        conwayHyperdimension.executeCycle();
        conwayHyperdimension.executeCycle();
        conwayHyperdimension.executeCycle();
        conwayHyperdimension.executeCycle();
        logger.info("After six cycles, we now have " + conwayHyperdimension.getActiveCubeCount() + " active hypercubes!");
        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");

    }
}
