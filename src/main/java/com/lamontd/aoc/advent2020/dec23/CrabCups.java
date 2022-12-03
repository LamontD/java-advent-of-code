package com.lamontd.aoc.advent2020.dec23;

import com.lamontd.aoc.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrabCups {
    private static final Logger logger = LoggerFactory.getLogger(CrabCups.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Crab Cups");

        logger.info("Initializing...");
        LinkedCupCircle cupCircle = new LinkedCupCircle("614752839");
        logger.info("Part 1: Running the circle 100 times");
        for (int timesRan=0; timesRan < 100; timesRan++) {
            cupCircle.executeTurn();
        }
        logger.info("Part 1: After 100 times the circle string is " + cupCircle.getOrderStringStartingWith(1).substring(1));
        logger.info("Part 2: Creating the giant circle...");
        LinkedCupCircle million = new LinkedCupCircle("614752839", 1000000);
        logger.info("Part 2: Running 10 million turns...");
        for (int turns=0; turns < 10000000; turns++) {
            million.executeTurn();
        }
        logger.info("Part 2: The multiplication value after one is: " + million.getMultiplicandsAfterOne());
        timer.finish();
    }
}
