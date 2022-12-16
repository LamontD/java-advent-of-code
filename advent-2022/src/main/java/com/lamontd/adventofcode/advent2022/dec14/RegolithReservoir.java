package com.lamontd.adventofcode.advent2022.dec14;

import com.lamontd.adventofcode.utils.Coordinate;
import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RegolithReservoir {
    private static final Logger logger = LoggerFactory.getLogger(RegolithReservoir.class);

    private final UndergroundCave undergroundCave = new UndergroundCave();

    public void processInput(LocalResourceInput resourceInput) {
        resourceInput.getInput().forEach(undergroundCave::addRockStructure);
    }

    public int dropGrainsOfSand() {
        int grainsOfSandDropped = 0;
        while (!undergroundCave.entranceBlocked(Coordinate.of(500, 0))) {
            try {
                undergroundCave.dropSandFromPoint(Coordinate.of(500, 0));
                grainsOfSandDropped++;
            } catch (FreefallException e) {
                break;
            }
        }
        return grainsOfSandDropped;
    }

    public void addInfinityFloor() {
        undergroundCave.addInfinityFloor();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Regolith Reservoir");
        RegolithReservoir sampleReservoir = new RegolithReservoir();
        sampleReservoir.processInput(new LocalResourceInput("day14/sample.txt"));
        logger.info("Processing the sample reservoir");
        int part1Grains = sampleReservoir.dropGrainsOfSand();
        logger.info("Part 1: " + part1Grains + " grains before overflow");
        RegolithReservoir infinitySampel = new RegolithReservoir();
        infinitySampel.addInfinityFloor();
        infinitySampel.processInput(new LocalResourceInput("day14/sample.txt"));
        logger.info("Part 2: " + infinitySampel.dropGrainsOfSand() + " grains before blockage");

        RegolithReservoir mainReservoir = new RegolithReservoir();
        mainReservoir.processInput(new LocalResourceInput("day14/input.txt"));
        logger.info("Processing the main reservoir...");
        logger.info("Part 1: " + mainReservoir.dropGrainsOfSand() + " grains before overflow");
        RegolithReservoir infinityMain = new RegolithReservoir();
        infinityMain.addInfinityFloor();
        infinityMain.processInput(new LocalResourceInput("day14/input.txt"));
        logger.info("Part 2: " + infinityMain.dropGrainsOfSand() + " grains before blockage");
        timer.finish();
    }
}
