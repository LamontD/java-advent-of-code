package com.lamontd.adventofcode.advent2020.dec11;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SeatingSystem {
    private static final Logger logger = LoggerFactory.getLogger(SeatingSystem.class);

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to the Ferry Seating system!");
        logger.info("Reading the input...");
        LocalResourceInput resourceInput = new LocalResourceInput("day11/advent-day11-input.txt");
        SeatingGrid seatingGrid = new SeatingGrid(resourceInput.getInput());
        logger.info("Loaded input and about to go for the logic flow...");
        seatingGrid.executeInitialLogicStepsUntilStable();
        logger.info("We have achieved stability with " + seatingGrid.getNumOccupiedSeats() + " occupied seats");

        logger.info("Creating an enhanced grid...");
        SeatingGrid enhancedGrid = new SeatingGrid(resourceInput.getInput());
        logger.info("Going for the logic flow...");
        enhancedGrid.executeEnhancedLogicStepsUntilStable();
        logger.info("We have achieved enhanced stability with " + enhancedGrid.getNumOccupiedSeats() + " occupied seats");
        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");

    }
}
