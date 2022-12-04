package com.lamontd.adventofcode.advent2021.dec08;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BrokenDisplay {
    private List<SevenDigitDisplay> allDigits = new ArrayList<>();
    private List<SevenDigitDisplay> outputDigits = new ArrayList<>();

    public BrokenDisplay(String inputLine) {
        String[] frontAndBack = inputLine.split("\\|");
        if (frontAndBack.length != 2) {
            throw new IllegalArgumentException("Can't parse input line of '" + inputLine + "'");
        }
        for (String input : frontAndBack[0].trim().split("\\s+")) {
            allDigits.add(new SevenDigitDisplay(input.trim()));
        }
        for (String input : frontAndBack[1].trim().split("\\s+")) {
            outputDigits.add(new SevenDigitDisplay(input.trim()));
        }
    }

    public boolean inMatchingOutput(SevenDigitDisplay referenceDisplay) {
        for (SevenDigitDisplay display : outputDigits) {
            if (display.matches(referenceDisplay)) {
                return true;
            }
        }
        return false;
    }

    public List<SevenDigitDisplay> findMatchingDigits(SevenDigitDisplay display) {
        return allDigits.stream().filter(digit -> digit.matches(display)).collect(Collectors.toList());
    }
    public List<SevenDigitDisplay> findMatchingDigitsOfLength(int length) {
        return allDigits.stream().filter(digit -> digit.getLitSegmentCount() == length).collect(Collectors.toList());
    }

    public List<SevenDigitDisplay> getOutputDigits() { return outputDigits; }

    public int getNumEasyMatchesInOutput() {
        int easyMatchCount = 0;
        for (SevenDigitDisplay outputDisplay : outputDigits) {
            if(outputDisplay.matches(SevenDigitDisplay.ONE) || outputDisplay.matches(SevenDigitDisplay.FOUR)
                    || outputDisplay.matches(SevenDigitDisplay.SEVEN) || outputDisplay.matches(SevenDigitDisplay.EIGHT)) {
                easyMatchCount++;
            }
        }
        return easyMatchCount;
    }
}
