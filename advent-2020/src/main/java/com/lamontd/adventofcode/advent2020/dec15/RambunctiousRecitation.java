package com.lamontd.adventofcode.advent2020.dec15;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RambunctiousRecitation {
    private static final Logger logger = LoggerFactory.getLogger(RambunctiousRecitation.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to Rambunctious Recitation!");
        logger.info("Seeding the reciter...");
        ElvenMemory elvenMemory = new ElvenMemory(List.of(2, 15, 0, 9, 1, 20));
        logger.info("The 2020th number spoken is: " + elvenMemory.getNumberForTurn(2020));
        logger.info("Going big or going home (part 2)...");
        logger.info("The 30000000th number spoken is: " + elvenMemory.getNumberForTurn(30000000));
        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");

    }

}
