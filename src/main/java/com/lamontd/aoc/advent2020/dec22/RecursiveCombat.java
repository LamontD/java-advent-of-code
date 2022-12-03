package com.lamontd.aoc.advent2020.dec22;

import com.lamontd.aoc.utils.LocalResourceInput;
import com.lamontd.aoc.utils.ProblemTimer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class RecursiveCombat {
    private static final Logger logger = LoggerFactory.getLogger(RecursiveCombat.class);

    private final Deck player1Deck;
    private final Deck player2Deck;
    private boolean infiniteGameInterrupt = false;
    private final int gameNumber;

    private final Set<DeckState> deckStates = new HashSet<>();

    public RecursiveCombat(List<String> inputData) {
        this.gameNumber = 1;
        ListIterator<String> inputIter = inputData.listIterator();
        inputIter.next();
        List<Integer> player1Values = new ArrayList<>();
        while (inputIter.hasNext()) {
            String thisString = inputIter.next();
            if (StringUtils.isEmpty(thisString)) {
                break;
            }
            player1Values.add(Integer.parseInt(thisString.trim()));
        }
        this.player1Deck = new Deck(player1Values);

        inputIter.next();
        List<Integer> player2Values = new ArrayList<>();
        while (inputIter.hasNext()) {
            String thisString = inputIter.next();
            if (StringUtils.isEmpty(thisString)) {
                break;
            }
            player2Values.add(Integer.parseInt(thisString.trim()));
        }
        this.player2Deck = new Deck(player2Values);
    }

    public RecursiveCombat(int gameNumber, List<Integer> player1CardValues, List<Integer> player2CardValues) {
        this.gameNumber = gameNumber;
        player1Deck = new Deck(player1CardValues);
        player2Deck = new Deck(player2CardValues);
    }

    public void playFullGame() {
        int round = 0;
        logger.debug("=== Game " + gameNumber + " ===");
        while (!infiniteGameInterrupt && !player1Deck.isEmpty() && !player2Deck.isEmpty()) {
            playRound(++round);
        }

    }

    protected void printWinnerOfGame() {
        if (infiniteGameInterrupt) {
            logger.info("Player 1 wins game " + gameNumber + " in an attempt to avoid infinity!");
        } else if (player1Deck.isEmpty()) {
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

    public boolean isInfiniteGameInterrupt() {
        return infiniteGameInterrupt;
    }

    public void printCurrentHandStatus() {
        logger.debug("Player 1's " + player1Deck.toString());
        logger.debug("Player 2's " + player2Deck.toString());
    }

    public void playRound(int roundNumber) {
        logger.debug("-- Round " + roundNumber + " (Game " + gameNumber + ") --");
        printCurrentHandStatus();
        DeckState currentDeckState = new DeckState(player1Deck, player2Deck);
        if (deckStates.contains(currentDeckState)) {
            infiniteGameInterrupt = true;
            return;
        }
        deckStates.add(currentDeckState);

        Card player1Card = player1Deck.playCard();
        logger.debug("Player 1 plays: " + player1Card);
        Card player2Card = player2Deck.playCard();
        logger.debug("Player 2 plays: " + player2Card);
        boolean player1WinsRound = false;
        if (player1Deck.getSize() >= player1Card.getFaceValue() && player2Deck.getSize() >= player2Card.getFaceValue()) {
            logger.debug("Playing a sub-game to determine the winner...");
            RecursiveCombat subGame = new RecursiveCombat(gameNumber + 1, player1Deck.getNextValues(player1Card.getFaceValue()),
                    player2Deck.getNextValues(player2Card.getFaceValue()));
            subGame.playFullGame();
            player1WinsRound = subGame.isInfiniteGameInterrupt() || subGame.getPlayer2Deck().isEmpty();
            if (player1WinsRound) {
                logger.debug("The winner of game " + (gameNumber + 1) + " is Player 1");
            } else {
                logger.debug("The winner of game " + (gameNumber + 1) + " is Player 2");
            }
            logger.debug("");
            logger.debug("...anyway, back to game " + gameNumber);
        } else {
            player1WinsRound = player1Card.isHigher(player2Card);
        }

        if (player1WinsRound) {
            logger.debug("Player 1 wins round " + roundNumber + " of game " + gameNumber);
            player1Deck.addWinningCards(player1Card, player2Card);
        } else {
            logger.debug("Player 2 wins round " + roundNumber + " of game " + gameNumber);
            player2Deck.addWinningCards(player2Card, player1Card);
        }
        logger.debug("");
    }

    public static class DeckState {
        final LinkedList<Integer> player1State;
        final LinkedList<Integer> player2State;

        public DeckState(Deck deck1, Deck deck2) {
            player1State = new LinkedList<>(deck1.getNextValues(deck1.getSize()));
            player2State = new LinkedList<>(deck2.getNextValues(deck2.getSize()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DeckState deckState = (DeckState) o;
            return player1State.equals(deckState.player1State) &&
                    player2State.equals(deckState.player2State);
        }

        @Override
        public int hashCode() {
            return Objects.hash(player1State, player2State);
        }
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Recursive Combat");

        LocalResourceInput resourceInput = new LocalResourceInput("advent2020/day22/input.txt");
        RecursiveCombat recursiveCombat = new RecursiveCombat(resourceInput.getInput());
        logger.info("Playing the game (part 2): ");
        recursiveCombat.playFullGame();
        logger.info("== Post-game results ==");
        recursiveCombat.printCurrentHandStatus();
        recursiveCombat.printWinnerOfGame();
        timer.finish();
    }

}
