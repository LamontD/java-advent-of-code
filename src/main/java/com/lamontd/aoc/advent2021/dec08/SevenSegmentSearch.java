package com.lamontd.aoc.advent2021.dec08;

import com.lamontd.aoc.utils.BasicCounter;
import com.lamontd.aoc.utils.LocalResourceInput;
import com.lamontd.aoc.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SevenSegmentSearch {
    private static final Logger logger = LoggerFactory.getLogger(SevenSegmentSearch.class);

    private List<BrokenDisplay> brokenDisplays = new ArrayList<>();

    public void loadInputData(String inputFile) throws IOException {
        brokenDisplays.clear();
        LocalResourceInput resourceInput = new LocalResourceInput(inputFile);
        for (String line : resourceInput.getInput()) {
            brokenDisplays.add(new BrokenDisplay(line));
        }
    }

    public int countOutputWithEasyNumbers() {
        final BasicCounter outputCounter = new BasicCounter();
        brokenDisplays.stream().forEach(display -> outputCounter.increment(display.getNumEasyMatchesInOutput()));
        return outputCounter.currentValue();
    }

    public int getDisplayMappingSum() {
        int displayMapSum = 0;
        for (BrokenDisplay display : brokenDisplays) {
            DisplayMapping mapping = new DisplayMapping(display);
            mapping.solveMap();
            displayMapSum += mapping.getFourCharacterOutput();
        }
        return displayMapSum;
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Seven Segment Search");
        logger.info("Reading the sample input");
        SevenSegmentSearch sss = new SevenSegmentSearch();
        sss.loadInputData("advent2021/day08/sample.txt");
        logger.info("I have " + sss.brokenDisplays.size() + " broken displays");
        logger.info("I also have " + sss.countOutputWithEasyNumbers() + " output lines with easy numbers");
        logger.info("Part 1: Loading the real data...");

        SevenSegmentSearch realSegmentSearch = new SevenSegmentSearch();
        realSegmentSearch.loadInputData("advent2021/day08/input.txt");
        logger.info("Part 1: I have " + realSegmentSearch.countOutputWithEasyNumbers() + " easy number references");

        logger.info("Part 2: Let's start with the little sample");
        logger.info("The display mapping for the sample is " + sss.getDisplayMappingSum());
        logger.info("Part 2: Going for the big fish!");
        logger.info("Part 2: The display mapping sum for the real data is " + realSegmentSearch.getDisplayMappingSum());
        timer.finish();
    }
}
