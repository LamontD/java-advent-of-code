package com.lamontd.adventofcode.advent2022.dec02;

public enum Move {
    ROCK(1),
    PAPER (2),
    SCISSORS (3);

    private final int shapeScore;
    private Move(int shapeScore) { this.shapeScore = shapeScore; }

    public int getShapeScore() {
        return shapeScore;
    }
}
