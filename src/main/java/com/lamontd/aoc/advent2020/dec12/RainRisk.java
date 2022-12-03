package com.lamontd.aoc.advent2020.dec12;

import com.lamontd.aoc.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RainRisk {
    private static final Logger logger = LoggerFactory.getLogger(RainRisk.class);

    private ShipNavigator shipNavigator;

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to Rain Risk!");
        logger.info("Reading the input...");
        LocalResourceInput resourceInput = new LocalResourceInput("advent2020/day12/advent-day12-input.txt");
        ShipNavigator navigator = new ShipNavigator(90);
        WaypointNavigator waypointNavigator = new WaypointNavigator(new Coordinate.Builder().east(10).north(1).build());
        logger.info("Navigating now...");
        for (String instruction: resourceInput.getInput()) {
            logger.debug(instruction);
            navigator.navigate(instruction);
            waypointNavigator.navigate(instruction);
        }
        logger.info("Processed " + resourceInput.getInput().size() + " instructions");
        logger.info("SHIP NAVIGATION RESULTS:");
        logger.info("------------------------");
        logger.info(navigator.toString());
        logger.info("Total Manhattan Distance traveled is " + navigator.getManhattanDistanceTraveled());
        logger.info("WAYPOINT NAVIGATION RESULTS:");
        logger.info("----------------------------");
        logger.info(waypointNavigator.toString());
        logger.info("Total Manhattan Distance traveled is " + waypointNavigator.getManhattanDistanceTraveled());
        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");
    }
}
