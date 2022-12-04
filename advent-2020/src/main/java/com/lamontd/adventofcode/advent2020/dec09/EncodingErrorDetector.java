package com.lamontd.adventofcode.advent2020.dec09;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EncodingErrorDetector {
    private static final Logger logger = LoggerFactory.getLogger(EncodingErrorDetector.class);

    private final LocalResourceInput localResourceInput;
    private XmasEncoder encoder;
    private List<Long> additionalInputs;

    public EncodingErrorDetector(LocalResourceInput resourceInput) {
        this.localResourceInput = resourceInput;
    }

    public void initializeEncoder(int preambleSize) {
        List<Long> longList = localResourceInput.getInput()
                .stream().map(Long::parseLong).collect(Collectors.toList());
        encoder = new XmasEncoder(longList.subList(0, preambleSize));
        additionalInputs = new ArrayList<>(longList.subList(preambleSize, longList.size()));
        logger.info("Preamble initialized with size " + encoder.getPreambleLength());
        logger.info("Initialized to process " + additionalInputs.size() + " additional data inputs");
    }

    public long findFirstInvalidValue() {
        for (long value : additionalInputs) {
            try {
                encoder.processNumber(value);
            } catch (BadXmasCypherInputException e) {
                return e.getBadInput();
            }
        }
        throw new IllegalArgumentException("No bad input found. This is unexpected...");
    }

    public Pair<Long, Long> findContiguousRangeEndpoints(long numberToAddTo) {
        List<Long> allInput = localResourceInput.getInput()
                .stream().map(Long::parseLong).collect(Collectors.toList());
        for (int rangeStart=0; rangeStart < allInput.size() - 1; rangeStart++) {
            long sumSoFar = allInput.get(rangeStart);
            for (int rangeEnd=rangeStart+1; rangeEnd < allInput.size(); rangeEnd++) {
                sumSoFar += allInput.get(rangeEnd);
                if (sumSoFar == numberToAddTo) {
                    return new Pair<>(allInput.get(rangeStart), allInput.get(rangeEnd));
                } else if (sumSoFar > numberToAddTo) {
                    break;
                }
            }
        }
        throw new IllegalArgumentException("Could not find a range to add up to " + numberToAddTo);
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to the Encoding Error Detector!");
        logger.info("Reading in the file input...");
        logger.info("Initializing the detector...");
        LocalResourceInput resourceInput = new LocalResourceInput("day9/advent-day9-input.txt");
        EncodingErrorDetector errorDetector = new EncodingErrorDetector(resourceInput);
        errorDetector.initializeEncoder(25);
        long firstInvalidValue = errorDetector.findFirstInvalidValue();
        logger.info("Part 1: First invalid value is " + firstInvalidValue);
        logger.info("Part 2: Finding the contiguous range that gets us to " + firstInvalidValue);
        Pair<Long, Long> range = errorDetector.findContiguousRangeEndpoints(firstInvalidValue);
        logger.info("Part 2 range is " + range + " with sum of " + (range.getValue0() + range.getValue1()));
        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");

    }
}
