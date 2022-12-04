package com.lamontd.adventofcode.advent2021.dec11;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DumboOctopus {
    private static final Logger logger = LoggerFactory.getLogger(DumboOctopus.class);

    EnergyMap energyMap;

    public DumboOctopus(String inputFile) throws IOException {
        LocalResourceInput resourceInput = new LocalResourceInput(inputFile);
        energyMap = new EnergyMap(resourceInput.getInput());
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Dumbo Octopus");
        logger.info("Setting up the sample data...");
        DumboOctopus sampler = new DumboOctopus("day11/sample.txt");
        logger.info(sampler.energyMap.toString());
        logger.info("into the future...");
        int numFlashes = sampler.energyMap.fastForwardToStepAndCount(100);
        logger.info(sampler.energyMap.toString());
        logger.info("I saw " + numFlashes + " flashes after 100 steps");
        logger.info(sampler.energyMap.toString());
        logger.info("Going for flashdance...");
        int flashdanceSample = sampler.energyMap.waitForFlashDance();
        logger.info("Sample flashdance achieved at step " + flashdanceSample);

        DumboOctopus mainCourse = new DumboOctopus("day11/input.txt");
        logger.info("Part 1: Going 100 steps");
        int mainFlashes = mainCourse.energyMap.fastForwardToStepAndCount(100);
        logger.info("I saw " + mainFlashes + " flashes in 100 steps");
        logger.info("Part 2: Going for flashdance...");
        int mainFlashdance = mainCourse.energyMap.waitForFlashDance();
        logger.info("Part 2: The main course danced like it's never danced before in step " + mainFlashdance);
        timer.finish();

    }
}
