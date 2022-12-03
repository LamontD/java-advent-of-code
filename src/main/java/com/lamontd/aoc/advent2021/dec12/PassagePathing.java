package com.lamontd.aoc.advent2021.dec12;

import com.lamontd.aoc.utils.LocalResourceInput;
import com.lamontd.aoc.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PassagePathing {
    private static final Logger logger = LoggerFactory.getLogger(PassagePathing.class);

    private CaveMap caveMap = new CaveMap();

    public void loadCaveMap(String inputFile) throws IOException {
        caveMap = new CaveMap();
        LocalResourceInput resourceInput = new LocalResourceInput(inputFile);
        resourceInput.getInput().stream().forEach(connection -> caveMap.addCavePath(connection));
        logger.info("I loaded " + resourceInput.getInput().size() + " connections with a total of " + caveMap.getNumberOfCaves() + " caves");
    }

    public List<List<Cave>> findPartOneCavePaths() {
        List<List<Cave>> allCavePaths = new ArrayList<>();
        findCavePathFrom(allCavePaths, new ArrayList<>(), caveMap.getStartCave());
        return allCavePaths;
    }

    private void findCavePathFrom(List<List<Cave>> caveList, List<Cave> pathSoFar, Cave currentCave) {
        pathSoFar.add(currentCave);
        if (currentCave == caveMap.getEndCave()) {
            caveList.add(pathSoFar);
            return;
        }
        for (Cave connectedCave : currentCave.getConnectedCaves()) {
            boolean canTakeThisBranch = false;
            switch(connectedCave.getCaveType()) {
                case SMALL:
                    if (!pathSoFar.contains(connectedCave)) {
                        canTakeThisBranch = true;
                    }
                    break;
                case LARGE:
                case END:
                    canTakeThisBranch = true;
                    break;
            }
            if (canTakeThisBranch) {
                findCavePathFrom(caveList, new ArrayList<>(pathSoFar), connectedCave);
            }
        }
    }

    public List<List<Cave>> findPartTwoCavePaths() {
        List<List<Cave>> allCavePaths = new ArrayList<>();
        findCavePathFromWhileVisitingOneSmallCaveTwice(allCavePaths, new ArrayList<>(), caveMap.getStartCave(), true);
        return allCavePaths;
    }

    private void findCavePathFromWhileVisitingOneSmallCaveTwice(List<List<Cave>> caveList, List<Cave> pathSoFar, Cave currentCave, boolean canStillVisitSmallCaveTwice) {
        pathSoFar.add(currentCave);
        if (currentCave == caveMap.getEndCave()) {
            caveList.add(pathSoFar);
            logger.debug("Completing path " + printCavePath(pathSoFar));
            return;
        }
        for (Cave connectedCave : currentCave.getConnectedCaves()) {
            boolean canTakeThisBranch = false;
            boolean canDoubleDownOnThisCave = canStillVisitSmallCaveTwice;
            switch(connectedCave.getCaveType()) {
                case SMALL:
                    if (!pathSoFar.contains(connectedCave)) {
                        canTakeThisBranch = true;
                    } else if (canStillVisitSmallCaveTwice) {
                        canTakeThisBranch = true;
                        canDoubleDownOnThisCave = false;
                        logger.debug(printCavePath(pathSoFar) + ": Burning my small cave visit on " + connectedCave.getName() + " for path");
                    } else {
                        logger.debug(printCavePath(pathSoFar) + ": Cannot visit small cave " + connectedCave.getName());
                    }
                    break;
                case LARGE:
                case END:
                    canTakeThisBranch = true;
                    break;
            }
            if (canTakeThisBranch) {
                findCavePathFromWhileVisitingOneSmallCaveTwice(caveList, new ArrayList<>(pathSoFar), connectedCave, canDoubleDownOnThisCave);
            }
        }
    }


    private static String printCavePath(List<Cave> cavePath) {
        StringBuilder sb = new StringBuilder();
        for(Cave cave : cavePath) {
            sb.append(cave.getName()).append("-");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public void documentPathsFound(List<List<Cave>> pathsFound) {
        for (List<Cave> cavePath : pathsFound) {
            logger.debug(printCavePath(cavePath));
        }
        logger.info("I found " + pathsFound.size() + " paths");
    }

    public void processAndDocumentPhase1Input(String inputFile) throws IOException {
        logger.info("Loading a map from " + inputFile);
        loadCaveMap(inputFile);
        List<List<Cave>> pathsFound = findPartOneCavePaths();
        for (List<Cave> cavePath : pathsFound) {
            logger.debug(printCavePath(cavePath));
        }
        logger.info("I found " + pathsFound.size() + " paths");
    }

    public void processAndDocumentPhase2Input(String inputFile) throws IOException {
        logger.info("Loading a map from " + inputFile);
        loadCaveMap(inputFile);
        List<List<Cave>> pathsFound = findPartTwoCavePaths();
        for (List<Cave> cavePath : pathsFound) {
            logger.debug(printCavePath(cavePath));
        }
        logger.info("I found " + pathsFound.size() + " paths");
    }
    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Passage Pathing");
        PassagePathing pathing = new PassagePathing();
        pathing.processAndDocumentPhase1Input("advent2021/day12/tiny.txt");
        pathing.processAndDocumentPhase1Input("advent2021/day12/small.txt");
        pathing.processAndDocumentPhase1Input("advent2021/day12/medium.txt");
        logger.info("Part 1: Finding main problem paths...");
        pathing.processAndDocumentPhase1Input("advent2021/day12/input.txt");
        logger.info("Exploring Part 2...");
        pathing.processAndDocumentPhase2Input("advent2021/day12/tiny.txt");
        pathing.processAndDocumentPhase2Input("advent2021/day12/small.txt");
        pathing.processAndDocumentPhase2Input("advent2021/day12/medium.txt");
        logger.info("Part 2: Finding main problem paths...");
        pathing.processAndDocumentPhase2Input("advent2021/day12/input.txt");

        timer.finish();
    }
}
