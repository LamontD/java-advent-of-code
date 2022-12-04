package com.lamontd.adventofcode.advent2021.dec06;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lanternfish {
    private static final Logger logger = LoggerFactory.getLogger(Lanternfish.class);

    private static LanternfishWatcher makeWatcher(String inputFile) throws IOException {
        LocalResourceInput resourceInput = new LocalResourceInput(inputFile);
        List<Integer> initialList = new ArrayList<>();
        for (String str : resourceInput.getInput().get(0).split(",")) {
            initialList.add(Integer.parseInt(str.trim()));
        }
        return new LanternfishWatcher(initialList);
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Lanternfish");
        logger.info("Setting up the initial conditions...");
        LanternfishWatcher sampleWatcher = makeWatcher("day06/sample.txt");
        logger.info("Aging 18 days...");
        while (sampleWatcher.getCurrentDay() < 18) {
            sampleWatcher.ageOneDay();
        }
        logger.info("After " + sampleWatcher.getCurrentDay() + " days I have " + sampleWatcher.getLongFishCount() + " fish");
        logger.info("Getting to day 80...");
        while (sampleWatcher.getCurrentDay() < 80) {
            sampleWatcher.ageOneDay();
        }
        logger.info("After 80 days I have " + sampleWatcher.getLongFishCount() + " fish");
        logger.info("Setting up the actual problem...");
        LanternfishWatcher mainWatcher = makeWatcher("day06/input.txt");
        logger.info("Aging 80 days...");
        while (mainWatcher.getCurrentDay() < 80) {
            mainWatcher.ageOneDay();
        }
        logger.info("After 80 days I have " + mainWatcher.getLongFishCount() + " fish");
        logger.info("Part 2: Try the sample on for size!");
        while (sampleWatcher.getCurrentDay() < 256) {
            sampleWatcher.ageOneDay();
        }
        logger.info(sampleWatcher.toString());
        logger.info("Part 2: The main event is here!");
        while (mainWatcher.getCurrentDay() < 256) {
            mainWatcher.ageOneDay();
        }
        logger.info(mainWatcher.toString());
        timer.finish();
    }
}
