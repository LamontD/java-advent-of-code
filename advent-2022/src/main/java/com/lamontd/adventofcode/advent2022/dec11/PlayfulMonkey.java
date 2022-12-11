package com.lamontd.adventofcode.advent2022.dec11;

import com.lamontd.adventofcode.utils.BasicCounter;
import com.lamontd.adventofcode.utils.LongCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class PlayfulMonkey {
    private static final Logger logger = LoggerFactory.getLogger(PlayfulMonkey.class);
    private int monkeyNumber;
    private final Deque<StolenItem> currentItems = new ArrayDeque<>();

    private final LongCounter inspectionCounter = new LongCounter();

    private int testDivisor;
    private PlayfulMonkey trueMonkey;
    private PlayfulMonkey falseMonkey;

    private boolean reduceWorry = true;

    private final MonkeyOperation operation;

    public PlayfulMonkey(int monkeyNumber, MonkeyOperation operation) {
        this.monkeyNumber = monkeyNumber;
        this.operation = operation;
    }

    public void receiveConditions(int testDivisor, PlayfulMonkey truePath, PlayfulMonkey falsePath) {
        this.testDivisor = testDivisor;
        this.trueMonkey = truePath;
        this.falseMonkey = falsePath;
    }

    public int getMonkeyNumber() {
        return monkeyNumber;
    }

    public Deque<StolenItem> getCurrentItems() {
        return currentItems;
    }

    public void catchItem(StolenItem item) {
        this.currentItems.addLast(item);
    }

    public PlayfulMonkey getTrueMonkey() {
        return trueMonkey;
    }

    public void setTrueMonkey(PlayfulMonkey trueMonkey) {
        this.trueMonkey = trueMonkey;
    }

    public PlayfulMonkey getFalseMonkey() {
        return falseMonkey;
    }

    public void setFalseMonkey(PlayfulMonkey falseMonkey) {
        this.falseMonkey = falseMonkey;
    }

    public void setReduceWorry(boolean reduceWorry) {
        this.reduceWorry = reduceWorry;
    }

    public long getInspectionCount() {
        return inspectionCounter.currentValue();
    }

    public int getTestDivisor() {
        return testDivisor;
    }

    public MonkeyOperation getOperation() {
        return operation;
    }

    public void checkOutItems() {
        logger.debug("Monkey " + monkeyNumber + ": Currently has " + currentItems.size() + " items to inspect!");
        while (!currentItems.isEmpty()) {
            StolenItem swipedItem = currentItems.removeFirst();
            logger.debug("Monkey " + monkeyNumber + ": inspecting item with worry level of " + swipedItem.getCurrentWorryLevel());
            inspectionCounter.increment();
            swipedItem.setCurrentWorryLevel(operation.execute(swipedItem.getCurrentWorryLevel()));
            logger.debug("Monkey " + monkeyNumber + ": Worry level changed to " + swipedItem.getCurrentWorryLevel());
            if (reduceWorry) {
                swipedItem.setCurrentWorryLevel(swipedItem.getCurrentWorryLevel() / 3);
                logger.debug("Monkey " + monkeyNumber + " got bored with item; worry level changed to " + swipedItem.getCurrentWorryLevel());
            }
            boolean takeTruePath = swipedItem.getCurrentWorryLevel() % testDivisor == 0;
            logger.debug("Monkey " + monkeyNumber + ": Current item " + (takeTruePath ? "is" : "is not") + " divisible by " + testDivisor);
            PlayfulMonkey destinationMonkey = takeTruePath ? trueMonkey : falseMonkey;
            destinationMonkey.catchItem(swipedItem);
            logger.debug("Monkey " + monkeyNumber + ": Item with worry level " + swipedItem + " thrown to monkey " + destinationMonkey.monkeyNumber);
        }
    }

    public String getStateString() {
        String sb = "Monkey " + monkeyNumber + ": " +
                currentItems.stream()
                        .map(item -> Long.toString(item.getCurrentWorryLevel()))
                        .collect(Collectors.joining(", "));
        return sb;
    }
}
