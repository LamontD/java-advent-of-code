package com.lamontd.adventofcode.advent2020.dec18;

import java.util.Objects;

public class MathOperatorToken implements MathToken {
    private final MathOperator mathOperator;

    public MathOperatorToken(String tokenString) {
        this.mathOperator = MathOperator.fromRepresentation(tokenString);
    }
    public MathOperatorToken(MathOperator mathOperator) { this.mathOperator = mathOperator; }

    public MathOperator getMathOperator() { return this.mathOperator; }

    @Override
    public boolean isValid() {
        return mathOperator != null;
    }

    @Override
    public String toString() { return mathOperator == null ? "" : mathOperator.toString(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MathOperatorToken that = (MathOperatorToken) o;
        return mathOperator == that.mathOperator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mathOperator);
    }
}
