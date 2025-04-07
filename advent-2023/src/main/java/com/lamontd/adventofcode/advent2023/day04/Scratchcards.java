package com.lamontd.adventofcode.advent2023.day04;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import lombok.Getter;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scratchcards {
    private static final Logger logger = LoggerFactory.getLogger(Scratchcards.class);

    @Getter private final int cardNum;
    @Getter private final List<Integer> winningNumbers = new ArrayList<>();
    @Getter private final List<Integer> scratchedNumbers = new ArrayList<>();

    public Scratchcards(String inputString) {
        String [] initialSplit = inputString.split(":");
        this.cardNum = Integer.parseInt(initialSplit[0].split("\\s+")[1]);
        String[] numberSplit = initialSplit[1].trim().split("\\|");
        String winners = numberSplit[0].trim();
        String scratched = numberSplit[1].trim();
        for (String str : winners.split("\\s+")) {
            winningNumbers.add(Integer.parseInt(str));
        }
        for (String str : scratched.split("\\s+")) {
            scratchedNumbers.add(Integer.parseInt(str));
        }
    }

    public List<Integer> getMatches() {
        return scratchedNumbers.stream()
                .distinct()
                .filter(winningNumbers::contains)
                .toList();
    }

    public int getInitialScore() {
        List<Integer> matches = getMatches();
        return matches.isEmpty() ? 0 : (int)Math.pow(2, matches.size() - 1);
    }

    public static List<Scratchcards> loadScratchcards(LocalResourceInput resourceInput) {
        return resourceInput.getInput().stream().map(Scratchcards::new).toList();
    }

    public static void scoreCards(List<Scratchcards> pile) {
        logger.info("Pile of {} cards is worth {} points", pile.size(),
                pile.stream().mapToInt(Scratchcards::getInitialScore).sum());

    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Scratchcards");
        logger.info("Loading the sample pile of cards");
        List<Scratchcards> sampleList = loadScratchcards(new LocalResourceInput("day04/sample.txt"));
        scoreCards(sampleList);
        ScratchcardPile samplePile = new ScratchcardPile(sampleList);
        logger.info(samplePile.toString());
        logger.info("Sample pile count is {}", samplePile.getTotalCardCount());

        List<Scratchcards> inputList = loadScratchcards(new LocalResourceInput("day04/input.txt"));
        scoreCards(inputList);
        ScratchcardPile inputPile = new ScratchcardPile(inputList);
        logger.info("Input pile count is {}", inputPile.getTotalCardCount());
        timer.finish();
    }
}
