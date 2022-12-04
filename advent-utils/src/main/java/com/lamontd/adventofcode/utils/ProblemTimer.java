package com.lamontd.adventofcode.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProblemTimer {
    private static final Logger logger = LoggerFactory.getLogger(ProblemTimer.class);

    private long startTime;
    public ProblemTimer(String problemName) {
        this.startTime = System.currentTimeMillis();
        logger.info("Welcome to " + problemName + "!");
    }

    public void finish() {
        long millisEnd = System.currentTimeMillis();
        logger.info("That took all of " + (millisEnd - startTime) + " ms");
    }
}
