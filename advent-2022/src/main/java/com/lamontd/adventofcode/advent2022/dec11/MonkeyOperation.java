package com.lamontd.adventofcode.advent2022.dec11;

public class MonkeyOperation {
    private final Type operationType;
    private long argument;

    private long upperLimit = 0;

    public MonkeyOperation(Type opType) {
        this.operationType = opType;
    }

    public MonkeyOperation(Type opType, long arg) {
        this.operationType = opType;
        this.argument = arg;
    }

    public long execute(long input) {
        long result = 0;
        switch (operationType) {
            case ADD:
                result = input + argument;
                break;
            case SUBTRACT:
                result = input - argument;
                break;
            case MULTIPLY:
                result = input * argument;
                break;
            case DIVIDE:
                result = input / argument;
                break;
            case SQUARE:
                result = input * input;
                break;
        }
        return upperLimit == 0 ? result : result % upperLimit;

    }

    public void setUpperLimit(long upperLimit) {
        this.upperLimit = upperLimit;
    }

    public enum Type { ADD, SUBTRACT, MULTIPLY, DIVIDE, SQUARE; }
}
