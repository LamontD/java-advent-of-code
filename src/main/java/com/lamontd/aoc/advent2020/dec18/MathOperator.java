package com.lamontd.aoc.advent2020.dec18;

import org.apache.commons.lang3.StringUtils;

public enum MathOperator {
    ADD("+"), SUBTRACT("-"), MULTIPLY("*"), DIVIDE("/");
    private String representation;
    MathOperator(String rep) { this.representation = rep; }

    public static MathOperator fromRepresentation(String representation) {
        for (MathOperator op : values()) {
            if (StringUtils.equals(op.representation, representation)) {
                return op;
            }
        }
        return null;
    }

    public long evaluate(long lhs, long rhs) {
        long value = 0L;
        switch(this) {
            case ADD:
                value = lhs + rhs;
                break;
            case SUBTRACT:
                value = lhs - rhs;
                break;
            case MULTIPLY:
                value = lhs * rhs;
                break;
            case DIVIDE:
                value = lhs / rhs;
                break;
        }
        return value;
    }


    @Override
    public String toString() { return representation; }
}
