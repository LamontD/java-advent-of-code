package com.lamontd.adventofcode.advent2022.dec15;

import com.lamontd.adventofcode.utils.*;
import com.lamontd.adventofcode.utils.coord.Coordinate;
import com.lamontd.adventofcode.utils.coord.CoordinateLine;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BeaconExclusionZone {
    private static final Logger logger = LoggerFactory.getLogger(BeaconExclusionZone.class);

    private final List<Sensor> sensors = new ArrayList<>();

    public void processInput(LocalResourceInput resourceInput) {
        String inputRegex = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)";
        Pattern inputPattern = Pattern.compile(inputRegex);
        for (String input : resourceInput.getInput()) {
            Matcher inputMatcher = inputPattern.matcher(input);
            if (inputMatcher.find()) {
                Coordinate sensor = Coordinate.of(Integer.parseInt(inputMatcher.group(1)), Integer.parseInt(inputMatcher.group(2)));
                Coordinate beacon = Coordinate.of(Integer.parseInt(inputMatcher.group(3)), Integer.parseInt(inputMatcher.group(4)));
                sensors.add(new Sensor(sensor, beacon));
            }
        }
    }

    public void showSensors() {
        sensors.forEach(sensor -> logger.info(sensor.toString()));
    }

    public long getTuningFrequency(Coordinate beacon) {
        return beacon.getX() * 4_000_000L + beacon.getY();
    }

    public int getExcludedPositionCountUsingLines(int rowNum) {
        final Set<Integer> uniquePositions = new HashSet<>();
        sensors.forEach(sensor -> {
            CoordinateLine line = sensor.getExclusionLineForRow(rowNum);
            if (line != null) {
                for (int column = line.getStart().getX(); column <= line.getEnd().getX(); column++) {
                    uniquePositions.add(column);
                }
            }
        });
        sensors.forEach(sensor -> {
            if (sensor.getClosestBeacon().getY() == rowNum) {
                uniquePositions.remove(sensor.getClosestBeacon().getX());
            }
        });
        return uniquePositions.size();
    }

    public Coordinate getLostBeaconCoordinateUsingZones(int maxCoordinate) {
        logger.info("Checking zones...");
        for (Sensor sensor : sensors) {
            for (Coordinate ringCoordinate : sensor.getManhattanDistanceRing()) {
                if (ringCoordinate.getX() >= 0 && ringCoordinate.getX() <= maxCoordinate
                && ringCoordinate.getY() >= 0 && ringCoordinate.getY() <= maxCoordinate ) {
                    boolean isCovered = false;
                    for (Sensor innerSensor : sensors) {
                        if (innerSensor.covers(ringCoordinate)) {
                            isCovered = true;
                            break;
                        }
                    }
                    if (!isCovered) {
                        logger.info("Found it!");
                        return ringCoordinate;
                    }
                }
            }
        }
        logger.error("Bad things happened -- everything is covered!");
        return null;
    }

    public Coordinate bruteForceLostBeaconCoordinate(int maxValue) {
        final Set<Coordinate> knownBeacons = new HashSet<>();
        sensors.forEach(sensor -> knownBeacons.add(sensor.getClosestBeacon()));
        for (int rowNum = 0; rowNum <= maxValue; rowNum++) {
            if (rowNum % 5000 == 0) {
                logger.info("Checking row " + rowNum + " / " + maxValue);
            }
            Set<Integer> uniqueColumns = new HashSet<>(maxValue);
            for (Sensor sensor : sensors) {
                CoordinateLine line = sensor.getExclusionLineForRow(rowNum);
                if (line != null) {
                    if (line.getEnd().getX() < 0) {
                        continue;
                    }
                    int startingColumn = Math.max(line.getStart().getX(), 0);
                    int endingColumn = Math.min(line.getEnd().getX(), maxValue);
                    for (int column = startingColumn; column <= endingColumn; column++) {
                        uniqueColumns.add(column);
                    }
                }
            }
            for (Coordinate beacon : knownBeacons) {
                if (beacon.getY() == rowNum) {
                    uniqueColumns.remove(beacon.getX());
                }
            }
            if (uniqueColumns.size() < maxValue + 1) {
//                logger.info("Row " + rowNum + " only has " + uniqueColumns.size() + "places where a beacon can hide");
                for (int col = 0; col < maxValue; col++) {
                    if (!uniqueColumns.contains(col) && !knownBeacons.contains(Coordinate.of(col, rowNum))) {
                        return Coordinate.of(col, rowNum);
                    }
                }
            }
        }
        logger.warn("Did not find what we were looking for");
        return null;
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Beacon Exclusion Zone");
        BeaconExclusionZone sample = new BeaconExclusionZone();
        sample.processInput(new LocalResourceInput("day15/sample.txt"));
        sample.showSensors();
        logger.info("Part 1: There are " + sample.getExcludedPositionCountUsingLines(10) + " positions in row 10 where a beacon cannot be present");
        logger.info("Part 2: Lost beacon is at " + sample.bruteForceLostBeaconCoordinate(20));
        Coordinate sampleLostBeacon = sample.getLostBeaconCoordinateUsingZones(20);
        logger.info("Part 2: Zone style gives me " + sampleLostBeacon);
        logger.info("Part 2: Tuning frequency is " + sample.getTuningFrequency(sampleLostBeacon));

        BeaconExclusionZone input = new BeaconExclusionZone();
        input.processInput(new LocalResourceInput("day15/input.txt"));
        logger.info("Part 1: There are " + input.getExcludedPositionCountUsingLines(2000000) + " positions in row 2000000 where a beacon cannot be present");
        Coordinate inputLostBeacon = input.getLostBeaconCoordinateUsingZones(4000000);
        logger.info("Part 2: Zone style gives me " + inputLostBeacon);
        logger.info("Part 2: Tuning frequency is " + input.getTuningFrequency(inputLostBeacon));
        timer.finish();
    }
}
