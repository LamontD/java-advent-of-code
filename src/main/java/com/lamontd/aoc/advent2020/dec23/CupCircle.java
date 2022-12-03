package com.lamontd.aoc.advent2020.dec23;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CupCircle {
    private static final Logger logger = LoggerFactory.getLogger(CupCircle.class);

    private final LinkedList<Integer> circle;
    private int currentIndex;
    private int maxValueInCircle;
    private boolean millionsMode = false;

    public CupCircle(String initializationString) {
        this.circle = new LinkedList<>();
        int maxSeen = 0;
        for (int i = 0; i < initializationString.length(); i++) {
            int valueSeen = Integer.parseInt(initializationString.substring(i, i + 1));
            maxSeen = Math.max(valueSeen, maxSeen);
            circle.addLast(valueSeen);
        }
        this.currentIndex = 0;
        this.maxValueInCircle = maxSeen;
    }

    public void setMillionsMode() {
        millionsMode = true;
        for( int numbersToAdd = 1000000 - circle.size(); numbersToAdd > 0; numbersToAdd--) {
            if (numbersToAdd % 10000 == 0) {
                logger.debug("Adding number " + numbersToAdd);
            }
            circle.add(++maxValueInCircle);
        }
    }

    public void executeTurn() {
        logger.debug(toString());
        List<Integer> pickUpCups = new ArrayList<>();
        int currentCupValue = circle.get(currentIndex);

        int pickupIndex = this.currentIndex + 1;
        for (int pickupCount = 0; pickupCount < 3; pickupCount++) {
            if (pickupIndex >= circle.size()) {
                if (millionsMode) {
                    circle.addLast(++this.maxValueInCircle);
                } else {
                    pickupIndex = 0;
                }
            }
            pickUpCups.add(circle.remove(pickupIndex));
        }

        final StringBuilder sb = new StringBuilder();
        sb.append("pick up:");
        for (Integer pickupValue : pickUpCups) {
            sb.append(' ').append(pickupValue);
        }
        logger.debug(sb.toString());

        int destinationValue = currentCupValue - 1;
        while (!circle.contains(destinationValue)) {
            if (pickUpCups.contains(destinationValue)) {
                destinationValue--;
            } else {
                destinationValue = maxValueInCircle;
            }
        }

        int destinationIndex = circle.indexOf(destinationValue);
        logger.debug("destination: " + circle.get(destinationIndex));
        circle.addAll(destinationIndex + 1, pickUpCups);

        this.currentIndex = circle.indexOf(currentCupValue) + 1;
        if (this.currentIndex >= circle.size()) {
            this.currentIndex = 0;
        }
    }

    public String getOrderStringStartingWith(int valueToStartWith) {
        int readIndex = circle.indexOf(valueToStartWith);
        StringBuilder builder = new StringBuilder();
        for (int count=0; count < circle.size(); count++) {
            builder.append(circle.get(readIndex++));
            if (readIndex >= circle.size()) {
                readIndex = 0;
            }
        }
        return builder.toString();
    }

    public LinkedList<Integer> getCircle() {
        return circle;
    }

    public Pair<Long, Long> findPairToTheRightOfOne() {
        int oneIndex = circle.indexOf(1);
        int answerIndex1 = oneIndex + 1;
        if (answerIndex1 >= circle.size()) {
            answerIndex1 = 0;
        }
        int answer2Index = answerIndex1 + 1;
        if (answer2Index >= circle.size()) {
            answer2Index = 0;
        }
        return Pair.with((long)circle.get(answerIndex1), (long)circle.get(answer2Index));
    }

    public int getCurrentCupValue() { return circle.get(currentIndex); }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("cups:");
        if (!millionsMode) {
            for (int indx = 0; indx < circle.size(); indx++) {
                if (indx == currentIndex) {
                    builder.append(" (").append(circle.get(indx)).append(')');
                } else {
                    builder.append(' ').append(circle.get(indx));
                }
            }
        }
        else {
            builder.append("... (").append(circle.get(currentIndex)).append(")...");
        }
        return builder.toString();
    }
}
