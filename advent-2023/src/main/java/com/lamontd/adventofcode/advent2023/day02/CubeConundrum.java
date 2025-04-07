package com.lamontd.adventofcode.advent2023.day02;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CubeConundrum {
    private static final Logger logger = LoggerFactory.getLogger(CubeConundrum.class);

    private List<CubeGame> games = new ArrayList<>();

    private void initialize(LocalResourceInput resourceInput) {
        games.clear();
        for (String inputLine : resourceInput.getInput()) {
            games.add(new CubeGame(inputLine));
        }
        logger.info("I inputted {} games based on input", games.size());
    }

    private int getSumOfPossibleGames(int redCubes, int greenCubes, int blueCubes) {
        return games.stream()
                .filter(game -> game.isPossible(redCubes, greenCubes, blueCubes))
                .mapToInt(CubeGame::getGameId).sum();
    }

    private long getSumOfPower() {
        return games.stream().mapToLong(CubeGame::getMinimumPower).sum();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Cube Conundrum");
        CubeConundrum conundrum = new CubeConundrum();
        logger.info("Working on sample input");
        conundrum.initialize(new LocalResourceInput("day02/sample.txt"));
        logger.info("Sample input has possible game sum of {}", conundrum.getSumOfPossibleGames(12, 13, 14));
        logger.info("Sample input has power of {}", conundrum.getSumOfPower());
        logger.info("Working on real input");
        conundrum.initialize(new LocalResourceInput("day02/input.txt"));
        logger.info("Real input has possible game sum of {}", conundrum.getSumOfPossibleGames(12, 13, 14));
        logger.info("Real input has power of {}", conundrum.getSumOfPower());
        timer.finish();
    }
}
