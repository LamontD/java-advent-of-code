package com.lamontd.adventofcode.advent2022.dec05;

public class CraneJob {
    private final int quantity;
    private final int sourceStack;
    private final int destinationStack;

    public CraneJob(int quantity, int sourceStack, int destinationStack) {
        this.quantity = quantity;
        this.sourceStack = sourceStack;
        this.destinationStack = destinationStack;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSourceStack() {
        return sourceStack;
    }

    public int getDestinationStack() {
        return destinationStack;
    }
}
