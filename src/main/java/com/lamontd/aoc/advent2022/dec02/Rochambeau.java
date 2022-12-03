package com.lamontd.aoc.advent2022.dec02;

import com.lamontd.aoc.utils.LocalResourceInput;
import com.lamontd.aoc.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Rochambeau {
    private static final Logger logger = LoggerFactory.getLogger(Rochambeau.class);

    private List<RPSRound> rounds = new ArrayList<>();

    public void loadBasicPlays(LocalResourceInput resourceInput) {
        rounds.clear();
        for (String line : resourceInput.getInput()) {
            String[] split = line.split(" ");
            Move opponentShape = null;
            switch (split[0]) {
                case "A":
                    opponentShape = Move.ROCK;
                    break;
                case "B":
                    opponentShape = Move.PAPER;
                    break;
                case "C":
                    opponentShape = Move.SCISSORS;
                    break;
            }
            Move yourShape = null;
            switch (split[1]) {
                case "X":
                    yourShape = Move.ROCK;
                    break;
                case "Y":
                    yourShape = Move.PAPER;
                    break;
                case "Z":
                    yourShape = Move.SCISSORS;
                    break;
            }

            rounds.add(new RPSRound(yourShape, opponentShape));
        }
    }

    public void loadEnhancedPlays(LocalResourceInput resourceInput) {
        rounds.clear();
        for (String line : resourceInput.getInput()) {
            Move opponentShape = null;
            String[] split = line.split(" ");
            switch (split[0]) {
                case "A":
                    opponentShape = Move.ROCK;
                    break;
                case "B":
                    opponentShape = Move.PAPER;
                    break;
                case "C":
                    opponentShape = Move.SCISSORS;
                    break;
            }
            Move yourShape = null;
            switch(split[1]) {
                case "X": // Loser
                    switch (opponentShape) {
                        case ROCK:
                            yourShape = Move.SCISSORS;
                            break;
                        case PAPER:
                            yourShape = Move.ROCK;
                            break;
                        case SCISSORS:
                            yourShape = Move.PAPER;
                            break;
                    }
                    break;
                case "Y":
                    yourShape = opponentShape;
                    break;
                case "Z": // winner
                    switch(opponentShape) {
                        case ROCK:
                            yourShape = Move.PAPER;
                            break;
                        case PAPER:
                            yourShape = Move.SCISSORS;
                            break;
                        case SCISSORS:
                            yourShape = Move.ROCK;
                            break;
                    }
                    break;
            }
            rounds.add(new RPSRound(yourShape, opponentShape));
        }
    }

    public long getGameScore() {
        logger.info("Cslculating the Part 1 score for " + rounds.size() + " rounds");
        return rounds.stream().mapToLong(RPSRound::basicRoundScore).sum();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Rock Paper Scissors");
        logger.info("Checking out the sample...");
        Rochambeau sampler = new Rochambeau();
        sampler.loadBasicPlays(new LocalResourceInput("advent2022/day02/sample.txt"));
        logger.info("The part 1 sample score is: " + sampler.getGameScore());
        sampler.loadEnhancedPlays(new LocalResourceInput("advent2022/day02/sample.txt"));
        logger.info("The part 2 sample score is: " + sampler.getGameScore());
        logger.info("Loading the real thing");
        Rochambeau realMccoy = new Rochambeau();
        realMccoy.loadBasicPlays(new LocalResourceInput("advent2022/day02/input.txt"));
        logger.info("The Part 1 real score is: " + realMccoy.getGameScore());
        realMccoy.loadEnhancedPlays(new LocalResourceInput("advent2022/day02/input.txt"));
        logger.info("The Part 2 real score is: " + realMccoy.getGameScore());
        timer.finish();
    }
}
