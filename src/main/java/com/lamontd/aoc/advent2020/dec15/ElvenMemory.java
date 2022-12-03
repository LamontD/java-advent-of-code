package com.lamontd.aoc.advent2020.dec15;

import java.util.*;

public class ElvenMemory {
    private final Map<Integer, Integer> numericMemory = new HashMap<>(2048);
    private int lastTurnWritten = 0;
    private int lastNumberSpoken = 0;

    public ElvenMemory(List<Integer> memorySeed) {
        for (int index=0; index < memorySeed.size() -1; index++) {
            commitValueToMemory(memorySeed.get(index));
        }
        this.lastTurnWritten = numericMemory.size();
        this.lastNumberSpoken = memorySeed.get(lastTurnWritten);
    }

    private void commitValueToMemory(int value) {
        numericMemory.put(value, ++lastTurnWritten);
    }

    public int takeTurnSpeakingANumber() {
        int numberForThisTurn = -1;
        if (numericMemory.containsKey(lastNumberSpoken)) {
            numberForThisTurn = lastTurnWritten - numericMemory.get(lastNumberSpoken) + 1;
        } else {
            numberForThisTurn = 0;
        }
        commitValueToMemory(lastNumberSpoken);
        this.lastNumberSpoken = numberForThisTurn;
        return numberForThisTurn;
    }

    public int getNumberForTurn(int turnToStopAt) {
        if (numericMemory.containsKey(turnToStopAt))
            return numericMemory.get(turnToStopAt);
        while (this.lastTurnWritten < turnToStopAt - 1) {
            takeTurnSpeakingANumber();
        }
        return lastNumberSpoken;
    }

    public int getLastNumberSpoken() { return this.lastNumberSpoken; }
    public int getLastTurn() { return this.lastTurnWritten + 1; }

}
