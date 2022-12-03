package com.lamontd.aoc.advent2021.dec08;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DisplayMapping {
    private Map<Character, Character> characterMap = new HashMap<>();
    private Map<Integer, Set<Character>> displayMap = new HashMap<>();
    private BrokenDisplay brokenDisplay;

    public DisplayMapping(BrokenDisplay brokenDisplay) { this.brokenDisplay = brokenDisplay; }

    public void solveMap() {
        solveUniqueEntries();
        cleverlyGenerateOtherEntries();
    }

    public int getFourCharacterOutput() {
        StringBuilder sb = new StringBuilder();
        for (SevenDigitDisplay output : brokenDisplay.getOutputDigits()) {
            for (Map.Entry<Integer, Set<Character>> entry : displayMap.entrySet()) {
                if (entry.getValue().equals(output.getLitSegments())) {
                    sb.append(entry.getKey());
                }
            }
        }
        return Integer.parseInt(sb.toString());
    }

    private void solveUniqueEntries() {
        displayMap.put(1, this.brokenDisplay.findMatchingDigitsOfLength(2).get(0).getLitSegments());
        displayMap.put(4, this.brokenDisplay.findMatchingDigitsOfLength(4).get(0).getLitSegments());
        displayMap.put(7, this.brokenDisplay.findMatchingDigitsOfLength(3).get(0).getLitSegments());
        displayMap.put(8, this.brokenDisplay.findMatchingDigitsOfLength(7).get(0).getLitSegments());

        // From one and seven, I can figure out the 'a' mapping
        Set<Character> difference = new HashSet<>(displayMap.get(7));
        difference.removeAll(displayMap.get(1));
        characterMap.put('a', difference.toArray(new Character[1])[0]);
    }

    private void cleverlyGenerateOtherEntries() {
        // Find the THREE -- it contains the same digits as the ONE
        for (SevenDigitDisplay brokenFive : brokenDisplay.findMatchingDigitsOfLength(5)) {
            if (brokenFive.containsAllLitSegments(displayMap.get(1))) {
                displayMap.put(3, brokenFive.getLitSegments());
                break;
            }
        }

        // With THREE and FOUR, I can figure out the 'b' mapping
        for (char fourCh : displayMap.get(4)) {
            if (!displayMap.get(3).contains(fourCh)) {
                characterMap.put('b', fourCh);
                break;
            }
        }

        // With 'b', I can figure out the difference between TWO and FIVE
        for (SevenDigitDisplay brokenFive : brokenDisplay.findMatchingDigitsOfLength(5)) {
            if (!brokenFive.containsAllLitSegments(displayMap.get(3))) {
                if (brokenFive.getLitSegments().contains(characterMap.get('b'))) {
                    displayMap.put(5, brokenFive.getLitSegments());
                } else {
                    displayMap.put(2, brokenFive.getLitSegments());
                }
            }
        }

        // With FOUR and 'b', I can figure out 'd'
        for (char fourCh : displayMap.get(4)) {
            if ((fourCh != characterMap.get('b')) && (!displayMap.get(1).contains(fourCh))) {
                characterMap.put('d', fourCh);
                break;
            }
        }

        // With 'd', I can figure out ZERO
        for (SevenDigitDisplay brokenSix : brokenDisplay.findMatchingDigitsOfLength(6)) {
            if (!brokenSix.getLitSegments().contains(characterMap.get('d'))) {
                displayMap.put(0, brokenSix.getLitSegments());
                break;
            }
        }

        // With 'd' and ONE, I can tell the difference beteween NINE and SIX
        for (SevenDigitDisplay brokenSix : brokenDisplay.findMatchingDigitsOfLength(6)) {
            if (!brokenSix.containsAllLitSegments(displayMap.get(0))) {
                if (brokenSix.containsAllLitSegments(displayMap.get(1))) {
                    displayMap.put(9, brokenSix.getLitSegments());
                } else {
                    displayMap.put(6, brokenSix.getLitSegments());
                }
            }
        }
    }

}
