package com.lamontd.adventofcode.advent2022.dec05;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class CargoStack {
    private Deque<Character> internalStack = new ArrayDeque<>();
    private final int stackNumber;

    public CargoStack(int stackNumber) {
        this.stackNumber = stackNumber;
    }

    public void addCrate (Character crate) {
        internalStack.addFirst(crate);
    }

    public void bottomFeedCrate(Character crate) { internalStack.addLast(crate);}

    public Character pullCrate() {
        return internalStack.removeFirst();
    }

    public Character peekCrate() { return internalStack.getFirst(); }

    public int getStackSize() { return internalStack.size(); }

    public int getStackNumber() {
        return stackNumber;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Cargo Stack #").append(stackNumber).append(": ");
        Iterator<Character> printingIterator = internalStack.descendingIterator();
        while (printingIterator.hasNext()) {
            sb.append("[").append(printingIterator.next()).append("] ");
        }
        return sb.toString();
    }
}
