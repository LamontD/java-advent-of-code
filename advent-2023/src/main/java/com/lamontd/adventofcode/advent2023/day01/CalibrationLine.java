package com.lamontd.adventofcode.advent2023.day01;

import com.lamontd.adventofcode.utils.coord.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CalibrationLine {
    private static final Logger logger = LoggerFactory.getLogger(CalibrationLine.class);
    private final String inputString;
    public CalibrationLine(String input) { this.inputString = input; }
    public int getFirstDigit() {
        for (char ch : inputString.toCharArray()) {
            if (Character.isDigit(ch)) {
                return Character.digit(ch, 10);
            }
        }
        throw new IllegalArgumentException("Input string <" + inputString + "> doesn't contain any digits");
    }

    public int getFirstSpelledDigit() {
        int digitIndex = 0;
        if (Character.isDigit(inputString.charAt(digitIndex))) {
            return Character.digit(inputString.charAt(digitIndex), 10);
        }
        for (digitIndex = 1; digitIndex < inputString.length(); digitIndex++) {
            if (Character.isDigit(inputString.charAt(digitIndex))) {
                break;
            }
        }
        List<Coordinate> digitsFound = new ArrayList<>(findIntegersInSubstring(inputString.substring(0, digitIndex), true));
        if (digitIndex < inputString.length()) {
            digitsFound.add(new Coordinate(digitIndex, Character.digit(inputString.charAt(digitIndex), 10)));
        }
        if (digitsFound.isEmpty()) {
            throw new IllegalArgumentException("Could not find any digits in input string <" + inputString + ">");
        }
        return digitsFound.stream()
                .min(Comparator.comparingInt(Coordinate::getX))
                .get().getY();
    }

    public int getLastDigit() {
        for (int i=inputString.length() - 1; i >= 0; i--) {
            if (Character.isDigit(inputString.charAt(i))) {
                return Character.digit(inputString.charAt(i), 10);
            }
        }
        throw new IllegalArgumentException("Input string <" + inputString + "> doesn't contain any digits");
    }

    public int getLastSpelledDigit() {
        int digitIndex = inputString.length() - 1;
        if (Character.isDigit(inputString.charAt(digitIndex))) {
            return Character.digit(inputString.charAt(digitIndex),10);
        }
        for (; digitIndex >= 0; digitIndex--) {
            if (Character.isDigit(inputString.charAt(digitIndex))) {
                break;
            }
        }
        List<Coordinate> digitsFound = new ArrayList<>(findIntegersInSubstring(inputString.substring(Math.max(digitIndex, 0)), false));
        int finalDigitIndex = digitIndex;
        digitsFound.forEach(d -> d.setX(d.getX() + finalDigitIndex));
        if (digitIndex >= 0) {
            digitsFound.add(new Coordinate(digitIndex, Character.digit(inputString.charAt(digitIndex), 10)));
        }
        if (digitsFound.isEmpty()) {
            throw new IllegalArgumentException("Could not find any digits in input <" + inputString + ">");
        }

        return digitsFound.stream()
                .max(Comparator.comparingInt(Coordinate::getX))
                .get().getY();
    }

    private static List<Coordinate> findIntegersInSubstring(String input, boolean first) {
        List<Coordinate> intPairs = new ArrayList<>();
        intPairs.add(new Coordinate(first ? input.indexOf("one") : input.lastIndexOf("one"), 1));
        intPairs.add(new Coordinate(first ? input.indexOf("two") : input.lastIndexOf("two"), 2));
        intPairs.add(new Coordinate(first ? input.indexOf("three") : input.lastIndexOf("three"), 3));
        intPairs.add(new Coordinate(first ? input.indexOf("four") : input.lastIndexOf("four"), 4));
        intPairs.add(new Coordinate(first ? input.indexOf("five") : input.lastIndexOf("five"), 5));
        intPairs.add(new Coordinate(first ? input.indexOf("six") : input.lastIndexOf("six"), 6));
        intPairs.add(new Coordinate(first ? input.indexOf("seven") : input.lastIndexOf("seven"), 7));
        intPairs.add(new Coordinate(first ? input.indexOf("eight") : input.lastIndexOf("eight"), 8));
        intPairs.add(new Coordinate(first ? input.indexOf("nine") : input.lastIndexOf("nine"), 9));
        return intPairs.stream().filter(c -> c.getX() >= 0).toList();
    }

    public int getCalibratedDigitalValue() { return getFirstDigit()*10 + getLastDigit(); }
    public int getCalibratedSpelledValue() { return getFirstSpelledDigit()*10 + getLastSpelledDigit();
    }
}
