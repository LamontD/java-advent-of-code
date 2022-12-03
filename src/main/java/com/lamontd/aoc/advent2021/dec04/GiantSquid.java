package com.lamontd.aoc.advent2021.dec04;

import com.lamontd.aoc.utils.LocalResourceInput;
import com.lamontd.aoc.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class GiantSquid {
    private static final Logger logger = LoggerFactory.getLogger(GiantSquid.class);

    private LocalResourceInput resourceInput;
    private List<Integer> numbersToCall;
    private List<BingoCard> bingoCards;

    public GiantSquid(String inputFile) throws IOException {
        resourceInput = new LocalResourceInput(inputFile);
        resetCard();
    }

    public void resetCard() {
        List<String> input = resourceInput.getInput();
        numbersToCall = new ArrayList<>();
        String[] callingNums = input.get(0).split(",");
        for (String str : callingNums) {
            numbersToCall.add(Integer.parseInt(str));
        }
//        for (int indx=callingNums.length - 1; indx >= 0; indx--) {
//            numbersToCall.add(Integer.parseInt(callingNums[indx]));
//        }

        bingoCards = new ArrayList<>();
        int readingLevel=2;
        while(readingLevel <= input.size()) {
            bingoCards.add(new BingoCard(getCardValues(readingLevel)));
            readingLevel += 6;
        }

    }

    public int playBasicBingoForScore() {
        for (int currentNumber : numbersToCall) {
            logger.info("Number: " + currentNumber);
            for (BingoCard card : bingoCards) {
                card.checkNumber(currentNumber);
                if (card.hasBingo()) {
                    logger.info("We have bingo!!");
                    logger.info("Winning card is: " + card);
                    return card.scoreBoard() * currentNumber;
                }
            }
        }
        throw new IllegalArgumentException("No winner. Booooo!");
    }

    public int playHighlanderBingoForScore() {
        int bingoCardsLeft = bingoCards.size();
        for (int currentNumber : numbersToCall) {
            logger.info("Highlander Number: " + currentNumber);
            for (BingoCard card : bingoCards) {
                if (!card.hasBingo()) {
                    card.checkNumber(currentNumber);
                    if (card.hasBingo()) {
                        bingoCardsLeft--;
                        if (bingoCardsLeft == 0) {
                            logger.info("There can be only one and it is " + card);
                            return card.scoreBoard() * currentNumber;
                        }
                        logger.info("One down -- " + bingoCardsLeft + " to go!");
                    }
                }
            }
        }
        throw new IllegalArgumentException("Wait a sec...I though there needed to be one!!!");
    }

    private Integer[][] getCardValues(int startingIndex) {
        Integer[][] values =  new Integer[5][5];
        for (int i=0; i < 5; i++) {
            String [] strVals = resourceInput.getInput().get(startingIndex+i).trim().split("\\s+");
            for (int col=0; col < 5; col++) {
                values[i][col] = Integer.parseInt(strVals[col]);
            }
        }
        return values;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(bingoCards.size()).append(" bingo cards as follows: ").append("\n");
        bingoCards.stream().forEach(card -> sb.append("=============\n").append(card));
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Giant Squid");
        GiantSquid giantSquid = new GiantSquid("advent2021/day04/input.txt");
        logger.info("Giant Squid: " + giantSquid.toString());
        logger.info("Let's play bingo!");
        int partOneScore = giantSquid.playBasicBingoForScore();
        logger.info("Part 1: Score is " + partOneScore);
        logger.info("Getting ready for the Highlander round...");
        giantSquid.resetCard();
        int partTwoScore = giantSquid.playHighlanderBingoForScore();
        logger.info("Part 2: Score is " + partTwoScore);

        timer.finish();
    }
}
