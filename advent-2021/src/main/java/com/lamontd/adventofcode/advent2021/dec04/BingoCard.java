package com.lamontd.adventofcode.advent2021.dec04;

public class BingoCard {

    Tile[][] boardSpace = new Tile[5][5];

    public BingoCard(Integer[][] initialCard) {
        for (int row=0; row < 5; row++) {
            for(int col=0; col < 5; col++) {
                boardSpace[row][col] = new Tile(initialCard[row][col]);
            }
        }
    }

    public void checkNumber(int calledNumber) {
        for (int row=0; row < 5; row++) {
            for (int col=0; col < 5; col++) {
                if (boardSpace[row][col].value == calledNumber) {
                    boardSpace[row][col].markTile();
                    return;
                }
            }
        }
    }

    public boolean hasBingo() {
        // Check the rows first
        for (int row=0; row < 5; row++) {
            boolean bingo = true;
            for (int col=0; col < 5; col++) {
                bingo &= boardSpace[row][col].isSelected();
            }
            if (bingo) return true;
        }

        // Now check the columns
        for (int col=0; col < 5; col++) {
            boolean bingo = true;
            for (int row=0; row < 5; row++) {
                bingo &= boardSpace[row][col].isSelected();
            }
            if (bingo) return true;
        }

        // Invariant: No soup for me
        return false;
    }

    public int scoreBoard() {
        int boardScore = 0;
        for (int row=0; row < 5; row++) {
            for (int col=0; col < 5; col++) {
                if (!boardSpace[row][col].isSelected()) {
                    boardScore += boardSpace[row][col].value;
                }
            }
        }
        return boardScore;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row=0; row < 5; row++) {
            for(int col=0; col < 5; col++) {
                sb.append(boardSpace[row][col]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static class Tile {
        int value;
        boolean selected = false;
        public Tile(int value) { this.value = value; }
        public void markTile() { this.selected = true; }
        public boolean isSelected() { return selected; }
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            if (isSelected()) {
                sb.append("*");
            }
            return sb.toString();
        }
    }
}
