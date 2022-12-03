package com.lamontd.aoc.advent2021.dec03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InputMatrix {
    private List<List<Integer>> rawData = new ArrayList<>();

    public void addDataRow(String rowString) {
        List<Integer> intList = new ArrayList<>();
        for (char ch : rowString.toCharArray()) {
            switch(ch) {
                case '0':
                    intList.add(0);
                    break;
                case '1':
                    intList.add(1);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected character " + ch);
            }
        }
        rawData.add(intList);
    }

    public void addDataRow(List<Integer> rowList) { rawData.add(rowList); }

    public List<Integer> getDataRow(int rowNum) {
        return rawData.get(rowNum);
    }

    public List<Integer> getDataColumn(int colNum) {
        return rawData.stream().map(row -> row.get(colNum)).collect(Collectors.toList());
    }

    public int calculateGammaRate() {
        StringBuilder gammaRate = new StringBuilder();
        for (int col=0; col < rawData.get(0).size(); col++) {
            gammaRate.append(getMostCommonBit(getDataColumn(col)));
        }
        return Integer.parseInt(gammaRate.toString(), 2);
    }

    public int calculateEpsilonRate() {
        StringBuilder epsilonRate = new StringBuilder();
        for (int col=0; col < rawData.get(0).size(); col++) {
            epsilonRate.append(getMostCommonBit(getDataColumn(col)) == '0' ? 1 : 0);
        }
        return Integer.parseInt(epsilonRate.toString(), 2);
    }

    public int calculateOxygenGeneratorRating() {
        List<Integer> oxygenGeneratorBinary = filterMatrixByBit(this, 0, true);
        return Integer.parseInt(convertBinaryListToString(oxygenGeneratorBinary), 2);
    }

    public int calculateCO2ScrubberRating() {
        List<Integer> co2ScrubberRating = filterMatrixByBit(this, 0, false);
        return Integer.parseInt(convertBinaryListToString(co2ScrubberRating), 2);
    }

    private static List<Integer> filterMatrixByBit(InputMatrix currentMatrix, int bitToCheck, boolean mostCommon) {
        if (currentMatrix.rawData.isEmpty()) {
            throw new IllegalArgumentException("We went too far for some reason!");
        }
        else if (currentMatrix.rawData.size() == 1) {
            return currentMatrix.rawData.get(0);
        }
        List<Integer> dataColumn = currentMatrix.getDataColumn(bitToCheck);
        char matchingChar = mostCommon ? getMostCommonBit(dataColumn) : getLeastCommonBit(dataColumn);
        int matchingInt = matchingChar == '0' ? 0 : 1;
        InputMatrix nextMatrix = new InputMatrix();
        for (int col=0; col < dataColumn.size(); col++) {
            if (dataColumn.get(col) == matchingInt) {
                nextMatrix.addDataRow(currentMatrix.rawData.get(col));
            }
        }
        return filterMatrixByBit(nextMatrix, ++bitToCheck, mostCommon);
    }

    private static char getMostCommonBit(List<Integer> inputData) {
        int numZeroes = Collections.frequency(inputData, 0);
        return (numZeroes > inputData.size() / 2) ? '0' : '1';
    }

    private static char getLeastCommonBit(List<Integer> inputData) {
        int numZeroes = Collections.frequency(inputData, 0);
        return (numZeroes <= inputData.size() / 2 ? '0' : '1');
    }

    private static String convertBinaryListToString(List<Integer> binaryList) {
        StringBuilder output = new StringBuilder();
        for (Integer val : binaryList) {
            output.append(val);
        }
        return output.toString();
    }

    public int getNumRows() { return rawData.size(); }
    public int getNumCols() { return rawData.get(0).size(); }

}
