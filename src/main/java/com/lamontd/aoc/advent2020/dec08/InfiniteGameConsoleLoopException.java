package com.lamontd.aoc.advent2020.dec08;

import com.lamontd.aoc.utils.BasicCounter;

public class InfiniteGameConsoleLoopException extends Exception {
    private final int accumulatorValue;
    private final int linesExecuted;

    public InfiniteGameConsoleLoopException(BasicCounter counter, int linesExecuted) {
        this.accumulatorValue = counter.currentValue();
        this.linesExecuted = linesExecuted;
    }

    public int getAccumulatorValue() {
        return accumulatorValue;
    }

    public int getLinesExecuted() {
        return linesExecuted;
    }
}
