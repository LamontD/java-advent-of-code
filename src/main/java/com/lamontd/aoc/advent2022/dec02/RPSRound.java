package com.lamontd.aoc.advent2022.dec02;

public class RPSRound {
    private Move yourShape;
    private Move opponentShape;

    public RPSRound(Move you, Move opponent) {
        this.yourShape = you;
        this.opponentShape = opponent;
    }

    public boolean winningMove() {
        switch(yourShape) {
            case ROCK:
                return opponentShape == Move.SCISSORS;
            case PAPER:
                return opponentShape == Move.ROCK;
            case SCISSORS:
                return opponentShape == Move.PAPER;
        }
        return false;
    }

    public Move getYourShape() {
        return yourShape;
    }

    public Move getOpponentShape() {
        return opponentShape;
    }

    public long basicRoundScore() {
        long roundScore = yourShape.getShapeScore();
        if (winningMove()) {
            roundScore += 6;
        } else if (yourShape == opponentShape) {
            roundScore += 3;
        }
        return roundScore;
    }
}
