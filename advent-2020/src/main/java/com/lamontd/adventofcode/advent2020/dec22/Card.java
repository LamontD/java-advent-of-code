package com.lamontd.adventofcode.advent2020.dec22;

import java.util.Objects;

public class Card implements Comparable<Card> {
    private final int faceValue;

    public Card(int faceValue) { this.faceValue = faceValue; }

    public int getFaceValue() {
        return faceValue;
    }

    @Override
    public int compareTo(Card o) {
        return Integer.compare(faceValue, o.faceValue);
    }

    public boolean isHigher(Card that) { return this.faceValue > that.faceValue; }

    @Override
    public String toString() {
        return Integer.toString(faceValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return faceValue == card.faceValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(faceValue);
    }
}
