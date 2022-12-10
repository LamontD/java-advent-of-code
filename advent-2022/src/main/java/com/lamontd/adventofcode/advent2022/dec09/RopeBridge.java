package com.lamontd.adventofcode.advent2022.dec09;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RopeBridge {
    private static final Logger logger = LoggerFactory.getLogger(RopeBridge.class);

    public static PlanckRope createTenKnottedRope() {
        PlanckRope headKnot = new PlanckRope();
        PlanckRope currentKnot = headKnot;
        for (int i=1; i < 10; i++) {
            PlanckRope newKnot = new PlanckRope(Integer.toString(i), currentKnot);
            currentKnot = newKnot;
        }
        return headKnot;
    }

    public static void showStatus(PlanckRope knottedRope) {
        int knotNumber = 0;
        for (PlanckRope currentRope = knottedRope; currentRope != null; currentRope = currentRope.getTrailingRope()) {
            logger.info(currentRope.toString());
        }
        // Find the tail rope
        PlanckRope tailRope = knottedRope;
        while (tailRope.getTrailingRope() != null) {
            tailRope = tailRope.getTrailingRope();
        }
        logger.info("Training rope has seen " + tailRope.countUniqueTailVisits() + " unique places");
    }

    public static void executeMoves(PlanckRope rope, LocalResourceInput input) {
        logger.info("Executing " + input.getInput().size() + " moves...");
        for (String line : input.getInput()) {
            String[] splitLine = line.split(" ");
            String direction = splitLine[0];
            int distance = Integer.parseInt(splitLine[1]);
            switch(direction) {
                case "U":
                    logger.debug("Moving Up " + distance);
                    rope.moveUp(distance);
                    break;
                case "D":
                    logger.debug("Moving Down " + distance);
                    rope.moveDown(distance);
                    break;
                case "R":
                    logger.debug("Moving Right " + distance);
                    rope.moveRight(distance);
                    break;
                case "L":
                    logger.debug("Moving Left " + distance);
                    rope.moveLeft(distance);
                    break;
                default:
                    throw new IllegalArgumentException("Unauthorized move: " + direction);
            }
//            showStatus(rope);
            logger.debug("Current rope: " + rope);
        }
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Rope Bridge");

        logger.info("Creating our PlanckRope");
        PlanckRope sampleRope = new PlanckRope();
        logger.info("Sample movement...");
        executeMoves(sampleRope, new LocalResourceInput("day09/sample.txt"));
        logger.info("Part 1: After sample, tail has seen " + sampleRope.countUniqueTailVisits() + " unique locations");

        logger.info("Creating our real rope");
        PlanckRope inputRope = new PlanckRope();
        logger.info("Real movement...");
        executeMoves(inputRope, new LocalResourceInput("day09/input.txt"));
        logger.info("Part 1: After real moves, tail has seen " + inputRope.countUniqueTailVisits() + " unique locations");

        logger.info("Creating our 10 knot rope");
        PlanckRope sample10Rope = createTenKnottedRope();
        logger.info("Pulling the 10 knot rope through the sample");
        executeMoves(sample10Rope, new LocalResourceInput("day09/sample.txt"));
        showStatus(sample10Rope);
        PlanckRope biggerSampleRope = createTenKnottedRope();
        executeMoves(biggerSampleRope, new LocalResourceInput("day09/biggersample.txt"));
        showStatus(biggerSampleRope);
        timer.finish();

        // TODO: There's a fence post error somewhere here, because the right answer appears on Rope #8 instead of #9.
        PlanckRope inputBigKnot = createTenKnottedRope();
        executeMoves(inputBigKnot, new LocalResourceInput("day09/input.txt"));
        showStatus(inputBigKnot);
    }
}
