package com.lamontd.adventofcode.advent2020.dec22;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class CrabCombat {
    private static final Logger logger = LoggerFactory.getLogger(CrabCombat.class);

    private final Deck player1Deck;
    private final Deck player2Deck;
    private boolean gameOver = false;

    public CrabCombat(List<String> inputData) {
        ListIterator<String> inputIter = inputData.listIterator();
        inputIter.next();
        List<Integer> player1Values = new ArrayList<>();
        while(inputIter.hasNext()) {
            String thisString = inputIter.next();
            if (StringUtils.isEmpty(thisString)) {
                break;
            }
            player1Values.add(Integer.parseInt(thisString.trim()));
        }
        this.player1Deck = new Deck(player1Values);

        inputIter.next();
        List<Integer> player2Values = new ArrayList<>();
        while(inputIter.hasNext()) {
            String thisString = inputIter.next();
            if (StringUtils.isEmpty(thisString)) {
                break;
            }
            player2Values.add(Integer.parseInt(thisString.trim()));
        }
        this.player2Deck = new Deck(player2Values);
    }

    public CrabCombat(List<Integer> player1CardValues, List<Integer> player2CardValues) {
        player1Deck = new Deck(player1CardValues);
        player2Deck = new Deck(player2CardValues);
    }

    public void playFullGame() {
        int round = 0;
        while(!gameOver) {
            playRound(++round);
        }

        logger.info("== Post-game results ==");
        printCurrentHandStatus();
        printWinnerOfGame();
    }

    protected void printWinnerOfGame() {
        if (player1Deck.isEmpty()) {
            logger.info("Player 2 wins with a score of: " + player2Deck.calculateScore());
        } else {
            logger.info("Player 1 wins with a score of: " + player1Deck.calculateScore());
        }
    }

    public Deck getPlayer1Deck() {
        return player1Deck;
    }

    public Deck getPlayer2Deck() {
        return player2Deck;
    }

    protected void markGameAsOver() { this.gameOver = true; }

    public void printCurrentHandStatus() {
        logger.info("Player 1's " + player1Deck.toString());
        logger.info("Player 2's " + player2Deck.toString());
    }

    public void playRound(int roundNumber) {
        logger.info("-- Round " + roundNumber + " --");
        printCurrentHandStatus();
        Card player1Card = player1Deck.playCard();
        logger.info("Player 1 plays: " + player1Card);
        Card player2Card = player2Deck.playCard();
        logger.info("Player 2 plays: " + player2Card);
        if (player1Card.isHigher(player2Card)) {
            logger.info("Player 1 wins the round!");
            player1Deck.addWinningCards(player1Card, player2Card);
        } else {
            logger.info("Player 2 wins the round!");
            player2Deck.addWinningCards(player2Card, player1Card);
        }
        if (player1Deck.isEmpty() || player2Deck.isEmpty()) {
            markGameAsOver();
        }
        logger.info("");
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Crab Combat");

        LocalResourceInput resourceInput = new LocalResourceInput("day22/input.txt");
        CrabCombat crabCombat = new CrabCombat(resourceInput.getInput());
        logger.info("Playing the game (part 1): ");
        crabCombat.playFullGame();
        timer.finish();
    }

}
