package com.lamontd.aoc.advent2020.dec13;

import com.lamontd.aoc.utils.LocalResourceInput;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class ShuttleSearch {
    private static final Logger logger = LoggerFactory.getLogger(ShuttleSearch.class);

    private final Map<Integer, Shuttle> shuttleMap = new HashMap<>();
    private final int timestamp;

    public ShuttleSearch(int timestamp) {
        this.timestamp = timestamp;
    }

    public void addShuttle(int index, Shuttle newShuttle) {
        shuttleMap.put(index, newShuttle);
    }

    public int getShuttleCount() {
        return shuttleMap.size();
    }

    public int getTimestamp() {
        return timestamp;
    }

    public long findIdealTimestamp() {
        long productOfFactors = 1;
        int[] baseValues = new int[shuttleMap.size()];
        int[] baseModulusValues = new int[shuttleMap.size()];
        int indx = 0;
        for (Map.Entry<Integer, Shuttle> shuttleEntry : shuttleMap.entrySet()) {
            productOfFactors *= shuttleEntry.getValue().getShuttleId();
            baseModulusValues[indx] = shuttleEntry.getKey();
            baseValues[indx] = shuttleEntry.getValue().getShuttleId();
            indx++;
        }

        long[] mValues = new long[baseValues.length];
        for (indx = 0; indx < mValues.length; indx++) {
            mValues[indx] = productOfFactors / baseValues[indx];
        }

        int[] yValues = new int[baseValues.length];
        for (indx = 0; indx < mValues.length; indx++) {
            long reducedValue = mValues[indx];
            reducedValue -= (reducedValue / baseValues[indx]) * baseValues[indx];
            int yi = 1;
            while (((reducedValue * yi) % baseValues[indx]) != 1L) {
                yi++;
            }
            yValues[indx] = yi;
        }

        long sumOfProducts = 0;
        for (indx = 0; indx < baseModulusValues.length; indx++) {
            sumOfProducts += baseModulusValues[indx] * mValues[indx] * yValues[indx];
        }
        long idealTimestamp = sumOfProducts % productOfFactors;

        return idealTimestamp;
    }

    public Shuttle findClosestShuttle() {
        Shuttle closestShuttle = null;
        int earliestTimestamp = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Shuttle> shuttleEntry : shuttleMap.entrySet()) {
            int closeTimestamp = shuttleEntry.getValue().firstDepartureAfterTimestamp(timestamp);
            logger.info("Shuttle " + shuttleEntry.getValue().getShuttleId() + " leaves at timestamp " + closeTimestamp);
            if (closeTimestamp < earliestTimestamp) {
                earliestTimestamp = closeTimestamp;
                closestShuttle = shuttleEntry.getValue();
            }
        }
        return closestShuttle;
    }

    public long findStairstepTimestamp() {
        Shuttle shuttleWithHighestId = null;
        int highestIndex = -1;
        int highestIdIndex = -1;
        for (Map.Entry<Integer, Shuttle> mapEntry : shuttleMap.entrySet()) {
            if (mapEntry.getKey() > highestIndex) {
                highestIndex = mapEntry.getKey();
            }
            if ((shuttleWithHighestId == null)
                    || (mapEntry.getValue().getShuttleId() > shuttleWithHighestId.getShuttleId())) {
                shuttleWithHighestId = mapEntry.getValue();
                highestIdIndex = mapEntry.getKey();
            }
        }

        List<Pair<Integer, Integer>> indexIdArray = new ArrayList<>();
        for (Map.Entry<Integer, Shuttle> mapEntry : shuttleMap.entrySet()) {
            indexIdArray.add(new Pair<Integer, Integer>(mapEntry.getKey(), mapEntry.getValue().getShuttleId()));
        }

        long stairstepTimestamp = -highestIdIndex;
        long stairstepHeight = shuttleWithHighestId.getShuttleId();
        boolean foundStairstep = false;
        while (!foundStairstep) {
            foundStairstep = true;
            stairstepTimestamp += stairstepHeight;
            for (Pair<Integer, Integer> intPair : indexIdArray) {
                if ((stairstepTimestamp + intPair.getValue0()) % intPair.getValue1() != 0L) {
                    foundStairstep = false;
                    break;
                }
            }
            if (stairstepTimestamp % 100000000000L == 0) {
                logger.debug("Passing timestamp " + stairstepTimestamp);
            }
        }
        return stairstepTimestamp;
    }

    public long findBestTimestampSinglePass() {
        long timestamp = 0L;
        long stairstep = 1L;
        for (Map.Entry<Integer, Shuttle> shuttleEntry : shuttleMap.entrySet()) {
            int offset = shuttleEntry.getKey();
            int shuttleId = shuttleEntry.getValue().getShuttleId();
            while ((timestamp + offset) % shuttleId != 0) {
                timestamp += stairstep;
            }
            stairstep *= shuttleId;
        }
        return timestamp;
    }

    public Collection<Shuttle> getShuttles() {
        return shuttleMap.values();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to Shuttle Search!");

        LocalResourceInput resourceInput = new LocalResourceInput("advent2020/day13/advent-day13-input.txt");
//        LocalResourceInput resourceInput = new LocalResourceInput("advent-day13-input-tiny.txt");

        ShuttleSearch shuttleSearch = new ShuttleSearch(Integer.parseInt(resourceInput.getInput().get(0)));
        String shuttleIdLine = resourceInput.getInput().get(1);
        String[] lineSplit = shuttleIdLine.split(",");
        for (int indx = 0; indx < lineSplit.length; indx++) {
            if (!StringUtils.equalsIgnoreCase(lineSplit[indx], "x"))
                shuttleSearch.addShuttle(indx, new Shuttle(Integer.parseInt(lineSplit[indx])));
        }
        logger.info("I found " + shuttleSearch.getShuttleCount() + " shuttles");
        Shuttle closestShuttle = shuttleSearch.findClosestShuttle();
        int waitTime = closestShuttle.howLongWillIHaveToWait(shuttleSearch.timestamp);
        logger.info("The closest shuttle to the timeframe " + shuttleSearch.getTimestamp() + " is "
                + closestShuttle + " which leaves at " + closestShuttle.firstDepartureAfterTimestamp(shuttleSearch.getTimestamp()));
        logger.info("I will have to wait " + waitTime + " minutes");
        logger.info("Bus Id x wait is " + closestShuttle.getShuttleId() * waitTime);
        logger.info("Searching for stairstep timestamp...");
        long stairstepTimestamp = shuttleSearch.findBestTimestampSinglePass();
        logger.info("Stairstep timestamp is " + stairstepTimestamp);

        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");
    }
}
