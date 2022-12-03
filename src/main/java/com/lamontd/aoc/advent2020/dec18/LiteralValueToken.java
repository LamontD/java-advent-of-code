package com.lamontd.aoc.advent2020.dec18;

public class LiteralValueToken implements MathToken {
    private long value;
    public LiteralValueToken(String stringValue) {
        try {
            this.value = Long.parseLong(stringValue);
        } catch (NumberFormatException e) {
            this.value = Long.MAX_VALUE;
        }
    }

    public LiteralValueToken(long value) { this.value = value; }

    @Override
    public String toString() { return Long.toString(value); }

    @Override
    public boolean isValid() {
        return value != Long.MAX_VALUE;
    }

    public long getValue() { return this.value; }
}
