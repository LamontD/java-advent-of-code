package com.lamontd.aoc.advent2020.dec05;

public class BoardingPass {
    private String rawBoardingData;
    private int row;
    private int column;

    public BoardingPass(String rawBoardingData) {
        this.rawBoardingData = rawBoardingData;
        this.row = calculatePlaneRow(rawBoardingData);
        this.column = calculatePlaneColumn(rawBoardingData);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getSeatId() {
        return row * 8 + column;
    }

    private static int calculatePlaneColumn(String inputString) {
        String colString = inputString.length() == 3 ? inputString : inputString.substring(inputString.length() - 3);
        final Range columnRange = new Range(0, 7);
        colString.chars().forEach(ch -> {
            switch(ch) {
                case 'L':
                    columnRange.takeLowerHalf();
                    break;
                case 'R':
                    columnRange.takeUpperHalf();
                    break;
            }
        });
        return columnRange.getLower();
    }

    private static int calculatePlaneRow(String inputString) {
        String rowString = inputString.substring(0, 7);
        final Range rowRange = new Range(0, 127);
        rowString.chars().forEach(ch -> {
            switch(ch) {
                case 'F':
                    rowRange.takeLowerHalf();
                    break;
                case 'B':
                    rowRange.takeUpperHalf();
                    break;
            }
        });
        return rowRange.getLower();
    }

}
