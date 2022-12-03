package com.lamontd.aoc.advent2020.dec23;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LinkedCupCircle {
    private Cup currentCup;
    private Cup endOfCircle;
    private final Map<Integer, Cup> cupSearchMap = new HashMap<>();
    private int maxValueSeen;

    public LinkedCupCircle(String initializationString) {
        for (int i = 0; i < initializationString.length(); i++) {
            int valueSeen = Integer.parseInt(initializationString.substring(i, i + 1));
            addCup(valueSeen);
            maxValueSeen = Math.max(valueSeen, maxValueSeen);
        }
    }

    public LinkedCupCircle(String initialiationString, int expansionSize) {
        this(initialiationString);
        int expansionSlotsToAdd = expansionSize - cupSearchMap.size();
        for (int i=0; i < expansionSlotsToAdd; i++) {
            addCup(++maxValueSeen);
        }
    }

    public void executeTurn() {
        Cup removedCupsHandle = currentCup.getNextCup();
        Set<Integer> cupValueSet = Set.of(removedCupsHandle.getCupValue(),
                removedCupsHandle.getNextCup().getCupValue(),
                removedCupsHandle.getNextCup().getNextCup().getCupValue());

        closeCupOpening(currentCup, removedCupsHandle.getNextCup().getNextCup().getNextCup());
        removedCupsHandle.getNextCup().getNextCup().setNextCup(null);

        int destinationCupValue = currentCup.getCupValue() - 1;
        if (!cupSearchMap.containsKey(destinationCupValue)) {
            destinationCupValue = this.maxValueSeen;
        }
        while (cupValueSet.contains(destinationCupValue)) {
            destinationCupValue--;
            if (!cupSearchMap.containsKey(destinationCupValue)) {
                destinationCupValue = this.maxValueSeen;
            }
        }

        Cup destinationCup = cupSearchMap.get(destinationCupValue);
        connectCups(removedCupsHandle.getNextCup().getNextCup(), destinationCup.getNextCup());
        connectCups(destinationCup, removedCupsHandle);

        this.currentCup = this.currentCup.getNextCup();
    }

    public String getOrderStringStartingWith(int valueToStartWith) {
        Cup startingCup = cupSearchMap.get(valueToStartWith);
        StringBuilder builder = new StringBuilder();
        while (startingCup.getNextCup().getCupValue() != valueToStartWith) {
            builder.append(startingCup.getCupValue());
            startingCup = startingCup.getNextCup();
        }
        builder.append(startingCup.getCupValue());
        return builder.toString();
    }


    protected void closeCupOpening(Cup previousCup, Cup nextCup) {
        if (previousCup.getNextCup() != null) {
            previousCup.getNextCup().setPreviousCup(nextCup);
        }
        connectCups(previousCup, nextCup);
    }

    protected void connectCups(Cup previousCup, Cup nextCup) {
        previousCup.setNextCup(nextCup);
        nextCup.setPreviousCup(previousCup);
    }

    public Cup getCupOfValue(int cupValue) { return cupSearchMap.get(cupValue); }

    public int getCircleSize() { return cupSearchMap.size(); }

    public long getMultiplicandsAfterOne() {
        Cup oneCup = cupSearchMap.get(1);
        if (oneCup == null) {
            throw new IllegalArgumentException("Can't find up with value of 1. What's up with that?");
        }
        long toTheRight = oneCup.getNextCup().getCupValue();
        long rightRight = oneCup.getNextCup().getNextCup().getCupValue();
        return toTheRight * rightRight;
    }

    protected void addCup(int cupValue) {
        Cup newCup = new Cup(cupValue);
        cupSearchMap.put(cupValue, newCup);
        if (currentCup == null) {
            currentCup = newCup;
            endOfCircle = newCup;
        } else {
            connectCups(newCup, currentCup);
            connectCups(endOfCircle, newCup);
            endOfCircle = newCup;
        }
    }

    public int getCurrentCupValue() { return currentCup.getCupValue(); }

    @Override public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("cups:");
        if (cupSearchMap.size() > 20) {
            builder.append(" ... (").append(currentCup.getCupValue()).append(") ...");
        } else if (cupSearchMap.size() > 0) {
            for (Cup thisCup = endOfCircle.getNextCup(); thisCup != endOfCircle; thisCup = thisCup.getNextCup()) {
                if (thisCup == currentCup) {
                    builder.append(" (").append(thisCup.getCupValue()).append(')');
                } else {
                    builder.append(' ').append(thisCup.getCupValue());
                }
            }
            builder.append(' ').append(endOfCircle.getCupValue());
        }
        return builder.toString();
    }
}
