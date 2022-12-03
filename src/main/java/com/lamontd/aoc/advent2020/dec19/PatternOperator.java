package com.lamontd.aoc.advent2020.dec19;

import org.apache.commons.lang3.StringUtils;

public enum PatternOperator {
    AND("&"),
    OR("|");

    final private String representation;

    PatternOperator(String representation) { this.representation = representation; }
    public static PatternOperator of(String representation) {
        for (PatternOperator op : values()) {
            if (StringUtils.equals(representation, op.representation)) {
                return op;
            }
        }
        return null;
    }


    @Override
    public String toString() { return this.representation; }
}
