package com.lamontd.adventofcode.advent2022.dec17;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class PyroclasticFlow {
    private static final Logger logger = LoggerFactory.getLogger(PyroclasticFlow.class);

    private FlowChamber flowChamber;

    private final List<FallingRock> initialRockOrder = List.of(FallingRock.MINUS(), FallingRock.PLUS(), FallingRock.ELBOW(), FallingRock.PIPE(), FallingRock.BOX());

    public void processChamberInput(LocalResourceInput resourceInput) {
        flowChamber = new FlowChamber(7, resourceInput.getInput().get(0), initialRockOrder);
        logger.info(flowChamber.toString());
    }

    public void stopDropShutEmDownOpenUpShop(int numTurns) {
        // I...may have obsessed over this a little too much.
        flowChamber.dropRocks(numTurns);
    }

    public long estimateDropHeight(long largeNumberOfDrops) {
        flowChamber.resetState();
        Pair<Pair<Long, Long>, Pair<Long, Long>> flipPattern = flowChamber.findFlipPattern();
        if (flipPattern == null) {
            logger.warn("Could not find flip pattern. Something's wrong with your algorithm, sir!");
            return -1L;
        }

        Pair<Long, Long> cycleStart = flipPattern.getValue0();
        Pair<Long, Long> cycleDelta = flipPattern.getValue1();
        logger.debug("The cycle starts with rock " + cycleStart.getValue0() + " which drops with height " + cycleStart.getValue1());
        logger.debug("The cycle adds " + cycleDelta.getValue1() + " height every " + cycleDelta.getValue0() + " rows");
        long timesToExecuteTheCycle = (largeNumberOfDrops - cycleStart.getValue0()) / cycleDelta.getValue0();
        long representedDrops = timesToExecuteTheCycle * cycleDelta.getValue0() + cycleStart.getValue0();
        long dropsRemaining = largeNumberOfDrops - representedDrops;

        long estimatedHeight = cycleStart.getValue1() + (timesToExecuteTheCycle * cycleDelta.getValue1());
        logger.debug("Estimated height after the big run is " + estimatedHeight);
        flowChamber.resetState();
        flowChamber.dropRocks((int)(dropsRemaining + cycleStart.getValue0()));
        estimatedHeight += flowChamber.getHighestRock() - cycleStart.getValue1();
        logger.info("Estimated height for " + largeNumberOfDrops + " is " + estimatedHeight);
        return estimatedHeight;
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Pyroclastic Flow");
        PyroclasticFlow sampleFlow = new PyroclasticFlow();
        sampleFlow.processChamberInput(new LocalResourceInput("day17/sample.txt"));
        sampleFlow.stopDropShutEmDownOpenUpShop(2022);
//        logger.info(sampleFlow.flowChamber.toString());
        logger.info("Part 1: Tallest rock is at " + sampleFlow.flowChamber.getHighestRock());
        logger.info("Part 2: Estimating value at 1T drops to be: " + sampleFlow.estimateDropHeight(1000000000000L));

        PyroclasticFlow inputFlow = new PyroclasticFlow();
        inputFlow.processChamberInput(new LocalResourceInput("day17/input.txt"));
        inputFlow.stopDropShutEmDownOpenUpShop(2022);
        logger.info("Part 1: Tallest input rock is at " + inputFlow.flowChamber.getHighestRock());
        logger.info("Part 2: Estimating value at 1T drops to be: " + inputFlow.estimateDropHeight(1000000000000L));
        timer.finish();
    }
}
