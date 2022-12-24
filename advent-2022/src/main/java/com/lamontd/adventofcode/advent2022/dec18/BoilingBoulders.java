package com.lamontd.adventofcode.advent2022.dec18;

import com.lamontd.adventofcode.utils.coord.CubicCoordinate;
import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoilingBoulders {
    private static final Logger logger = LoggerFactory.getLogger(BoilingBoulders.class);

    private DropletGrid dropletGrid = new DropletGrid();

    public void processInput(LocalResourceInput resourceInput) {
        String inputRegex = "(\\d+),(\\d+),(\\d+)";
        Pattern inputPattern = Pattern.compile(inputRegex);
        for (String inputLine : resourceInput.getInput()) {
            Matcher inputMatcher = inputPattern.matcher(inputLine);
            if (inputMatcher.find()) {
                dropletGrid.addDroplet(CubicCoordinate.of(Integer.parseInt(inputMatcher.group(1)),
                        Integer.parseInt(inputMatcher.group(2)),
                        Integer.parseInt(inputMatcher.group(3))));
            }
        }
    }

    public int getTotalExposedSurfaceArea() {
        return dropletGrid.getTotalExposedSidesCountUsingGrid();
    }

    public int getExposedSurfaceAreaIgnoringAirPockets() {
        return dropletGrid.getTotalExposedSidesExcludingAirPockets();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Boiling Boulders");
        BoilingBoulders sampleBoulders = new BoilingBoulders();
        sampleBoulders.processInput(new LocalResourceInput("day18/sample.txt"));
        logger.info("Part 1: Total exposed surface area is " + sampleBoulders.getTotalExposedSurfaceArea());
        CubicCoordinate airPocket = CubicCoordinate.of(2, 2, 5);
        logger.info("Part 2: Total exposed area ignoring air pockets is " + sampleBoulders.getExposedSurfaceAreaIgnoringAirPockets());

        BoilingBoulders inputBoulders = new BoilingBoulders();
        inputBoulders.processInput(new LocalResourceInput("day18/input.txt"));
        logger.info("Part 1: Total exposed surface area is " + inputBoulders.getTotalExposedSurfaceArea());
        logger.info("Part 2: Total exposed area ignoring air pockets is " + inputBoulders.getExposedSurfaceAreaIgnoringAirPockets());

        timer.finish();
    }
}
