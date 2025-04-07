package com.lamontd.adventofcode.advent2023.day04;

import com.lamontd.adventofcode.utils.BasicCounter;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScratchcardPile {
    private static final Logger logger = LoggerFactory.getLogger(ScratchcardPile.class);

    @Getter final private Map<Integer, BasicCounter> countingMap = new HashMap<>();
    @Getter final private Map<Integer, Scratchcards> cardPile = new HashMap<>();

    public ScratchcardPile(List<Scratchcards> cards) {
        for (Scratchcards scratchcards : cards) {
            cardPile.put(scratchcards.getCardNum(), scratchcards);
            addCardForCounting(scratchcards.getCardNum());
        }
        recursePile();
    }

    public void recursePile() {
        for (int currentCard = 1; currentCard <= cardPile.size(); currentCard++) {
            Scratchcards thisCard = cardPile.get(currentCard);
            for (int numTimes = 0; numTimes < countingMap.get(currentCard).currentValue(); numTimes++) {
                for (int bonusCard = 1; bonusCard <= cardPile.get(currentCard).getMatches().size(); bonusCard++) {
                    addCardForCounting(currentCard + bonusCard);
                }
            }
        }
    }

    public int getTotalCardCount() {
        return countingMap.values().stream().mapToInt(BasicCounter::currentValue).sum();
    }

    private void addCardForCounting(int cardNumber) {
        if (!countingMap.containsKey(cardNumber)) {
            countingMap.put(cardNumber, new BasicCounter(0));
        }
        countingMap.get(cardNumber).increment();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ScratchCard pile looks like this:\n");
        for(Map.Entry<Integer, BasicCounter> entry : countingMap.entrySet()) {
            sb.append("\tCard ").append(entry.getKey()).append(" has ").append(entry.getValue().currentValue()).append(" copies\n");
        }
        return sb.toString();
    }

}
