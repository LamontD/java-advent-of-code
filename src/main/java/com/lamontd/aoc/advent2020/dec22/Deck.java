package com.lamontd.aoc.advent2020.dec22;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Deck {
    private final LinkedList<Card> hand = new LinkedList<>();

    public Deck(List<Integer> startingCards) {
        for (int value : startingCards) {
            hand.offer(new Card(value));
        }
    }

    public boolean isEmpty() { return this.hand.isEmpty(); }

    public Card playCard() { return hand.poll(); }

    public int getSize() { return hand.size(); }

    public void addWinningCards(Card card1, Card card2) {
        hand.offer(card1);
        hand.offer(card2);
    }

    public List<Integer> getNextValues(int valueLength) {
        List<Integer> nextValues = new ArrayList<>();
        for (int i=0; i < valueLength && i < hand.size(); i++) {
            nextValues.add(hand.get(i).getFaceValue());
        }
        return nextValues;
    }

    public long calculateScore() {
        long baseScore = 0L;
        int handSize = hand.size();
        for (int i=0; i < handSize; i++) {
            baseScore += (handSize - i) * hand.get(i).getFaceValue();
        }
        return baseScore;
    }

    @Override public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Deck:");
        hand.stream().forEach( card -> builder.append(" ").append(card.getFaceValue()).append(","));
        return builder.substring(0, builder.length() - 1);
    }
}
