package com.lamontd.aoc.advent2021.dec02;

import com.lamontd.aoc.utils.LocalResourceInput;
import com.lamontd.aoc.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dive {

    private static final Logger logger = LoggerFactory.getLogger(Dive.class);

    private final LocalResourceInput resourceInput;

    public Dive(String input) throws IOException {
        this.resourceInput = new LocalResourceInput(input);
    }

    public Pair<Integer, Integer> getPositionAndDepth() {
        List<SubMovement> movements = resourceInput.getInput().stream()
                .map(str -> new SubMovement(str))
                .collect(Collectors.toList());
        int position = 0;
        int depth = 0;
        for (SubMovement movement : movements) {
            switch (movement.getDirection()) {
                case UP:
                    depth -= movement.getUnits();
                    break;
                case DOWN:
                    depth += movement.getUnits();
                    break;
                case FORWARD:
                    position += movement.getUnits();
                    break;
            }
        }
        return new Pair<Integer, Integer>(position, depth);
    }

    public Pair<Integer, Integer> getAimedPositionAndDepth() {
        List<SubMovement> movements = resourceInput.getInput().stream()
                .map(str -> new SubMovement(str))
                .collect(Collectors.toList());
        int position = 0;
        int depth = 0;
        int aim = 0;
        for (SubMovement movement : movements) {
            switch(movement.getDirection()) {
                case DOWN:
                    aim += movement.getUnits();
                    break;
                case UP:
                    aim -= movement.getUnits();
                    break;
                case FORWARD:
                    position += movement.getUnits();
                    depth += movement.getUnits() * aim;
                    break;
            }
        }
        return new Pair<>(position, depth);
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer problemTimer = new ProblemTimer("Dive!");
        logger.info("Working on Part 1...");
        Dive sampleDive = new Dive("advent2021/day2/input.txt");
        Pair<Integer, Integer> firstDive = sampleDive.getPositionAndDepth();
        long firstAnswer = firstDive.getValue0() * firstDive.getValue1();
        logger.info("Part 1: New Position is " + firstDive.getValue0() + ", depth " + firstDive.getValue1());
        logger.info("First answer is " + firstAnswer);
        Pair<Integer, Integer> secondDive = sampleDive.getAimedPositionAndDepth();
        logger.info("Part 2: New Position is " + secondDive.getValue0() + ", depth " + secondDive.getValue1());
        long secondAnswer = secondDive.getValue0() * secondDive.getValue1();
        logger.info("Second answer is " + secondAnswer);
        problemTimer.finish();
    }
}
