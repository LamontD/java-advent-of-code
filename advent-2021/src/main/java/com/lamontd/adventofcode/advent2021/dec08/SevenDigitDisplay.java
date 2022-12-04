package com.lamontd.adventofcode.advent2021.dec08;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SevenDigitDisplay {
    private Set<Character> litSegments;

    public SevenDigitDisplay(String litSegments) {
        this.litSegments = new HashSet<>();
        for (char ch : litSegments.toCharArray()) {
            this.litSegments.add(ch);
        }
    }
    public Set<Character> getLitSegments() { return this.litSegments; }
    public int getLitSegmentCount() { return litSegments.size(); }
    public Set<Character> findDelta(SevenDigitDisplay altDisplay) {
        Set<Character> deltaSet = new HashSet<>(litSegments);
        deltaSet.removeAll(altDisplay.litSegments);
        return deltaSet;
    }
    public boolean matches(SevenDigitDisplay referenceDisplay) {
        return litSegments.equals(referenceDisplay.litSegments);
    }
    public boolean containsAllLitSegments(Set<Character> values) {
        for (char ch : values) {
            if (!litSegments.contains(ch)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SevenDigitDisplay display = (SevenDigitDisplay) o;
        return Objects.equals(litSegments, display.litSegments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(litSegments);
    }

    public static final SevenDigitDisplay ZERO = new SevenDigitDisplay("abcefg");
    public static final SevenDigitDisplay ONE = new SevenDigitDisplay("cf");
    public static final SevenDigitDisplay TWO = new SevenDigitDisplay("acdeg");
    public static final SevenDigitDisplay THREE = new SevenDigitDisplay("acdfg");
    public static final SevenDigitDisplay FOUR = new SevenDigitDisplay("bcdf");
    public static final SevenDigitDisplay FIVE = new SevenDigitDisplay("abdfg");
    public static final SevenDigitDisplay SIX = new SevenDigitDisplay("abdefg");
    public static final SevenDigitDisplay SEVEN = new SevenDigitDisplay("acf");
    public static final SevenDigitDisplay EIGHT = new SevenDigitDisplay("abcdefg");
    public static final SevenDigitDisplay NINE = new SevenDigitDisplay("abcdfg");
}
