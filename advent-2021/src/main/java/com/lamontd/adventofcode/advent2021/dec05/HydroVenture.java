package com.lamontd.adventofcode.advent2021.dec05;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class HydroVenture {
    private static final Logger logger = LoggerFactory.getLogger(HydroVenture.class);

    private VentMap ventMap = new VentMap();
    private LocalResourceInput resourceInput;

    public HydroVenture(String inputFile) throws IOException {
        resourceInput = new LocalResourceInput(inputFile);
    }
    public void loadMapValues(boolean beBasic) {
        ventMap.clearMap();
        for (String str : resourceInput.getInput()) {
            String[] coordinates = str.split(" -> ");
            if (coordinates.length != 2) {
                throw new IllegalArgumentException("Couldn't correctly parse line >" + str + "<");
            }
            if (beBasic) {
                ventMap.addBasicLine(getCoordinateFromPiece(coordinates[0]), getCoordinateFromPiece(coordinates[1]));
            } else {
                ventMap.addFancyLine(getCoordinateFromPiece(coordinates[0]), getCoordinateFromPiece(coordinates[1]));
            }
        }
    }

    private static VentCoordinate getCoordinateFromPiece(String piece) {
        String[] xysplit = piece.split(",");
        if (xysplit.length != 2) {
            throw new IllegalArgumentException("Can't parse coordinate >" + piece + "<");
        }
        return new VentCoordinate(Integer.parseInt(xysplit[0].trim()), Integer.parseInt(xysplit[1].trim()));
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Hydrothermal Venture");
        logger.info("Part 1: Loading the sample map...");
        HydroVenture venture = new HydroVenture("day05/sample.txt");
        venture.loadMapValues(true);
        List<VentCoordinate> overlapPoints = venture.ventMap.findOverlapPoints(2);
        logger.info("Part 1: We have " + overlapPoints.size() + " overlap points.");
        logger.info("Part 1: They are " + overlapPoints);
        logger.info("Part 2: Getting fancy");
        venture.loadMapValues(false);
        List<VentCoordinate> fancyOverlapPoints = venture.ventMap.findOverlapPoints(2);
        logger.info("Part 2: We have " + fancyOverlapPoints.size() + " overlap points");
        logger.info("Part 2: They are " + fancyOverlapPoints);

        HydroVenture mainVenture = new HydroVenture("day05/input.txt");
        logger.info("Part 1: Loading the main map");
        mainVenture.loadMapValues(true);
        List<VentCoordinate> mainOverlapPoints = mainVenture.ventMap.findOverlapPoints(2);
        logger.info("Part 1: We have " + mainOverlapPoints.size() + " overlap points");
        logger.info("Part 2: Loading the fancy map");
        mainVenture.loadMapValues(false);
        List<VentCoordinate> mainFancyPoints = mainVenture.ventMap.findOverlapPoints(2);
        logger.info("Part 2: Main map has " + mainFancyPoints.size() + " overlap points");
        timer.finish();
    }
}
