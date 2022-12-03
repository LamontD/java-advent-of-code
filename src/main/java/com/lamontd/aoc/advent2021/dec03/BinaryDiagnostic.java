package com.lamontd.aoc.advent2021.dec03;

import com.lamontd.aoc.utils.LocalResourceInput;
import com.lamontd.aoc.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class BinaryDiagnostic {
    private static final Logger logger = LoggerFactory.getLogger(BinaryDiagnostic.class);

    InputMatrix inputMatrix;

    public BinaryDiagnostic(String inputFile) throws IOException {
        LocalResourceInput resourceInput = new LocalResourceInput(inputFile);
        this.inputMatrix = new InputMatrix();
        for (String row : resourceInput.getInput()) {
            inputMatrix.addDataRow(row);
        }
    }

    private static char getMostCommonBit(List<Integer> integerList) {
        int numZeroes = Collections.frequency(integerList, 0);
        return (numZeroes > integerList.size() / 2) ? '0' : '1';
    }

    public String getBinaryStringOfColumns() {
        StringBuilder sb = new StringBuilder();
        int numCols = inputMatrix.getDataRow(0).size();
        for (int colNum=0; colNum < numCols; colNum++) {
            sb.append(getMostCommonBit(inputMatrix.getDataColumn(colNum)));
        }
        return sb.toString();
    }

    public long getPowerConsumption() {
        return inputMatrix.calculateGammaRate() * inputMatrix.calculateEpsilonRate();
    }

    public long getLifeSupportRating() {
        return inputMatrix.calculateOxygenGeneratorRating() * inputMatrix.calculateCO2ScrubberRating();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Binary Diagnostic");
        logger.info("Reading the input data...");
//        BinaryDiagnostic diagnostic = new BinaryDiagnostic("advent2021/day03/sample.txt");
        BinaryDiagnostic diagnostic = new BinaryDiagnostic("advent2021/day03/input.txt");
        logger.info("I see " + diagnostic.inputMatrix.getNumRows() + " x " + diagnostic.inputMatrix.getNumCols() + " of data");
        logger.info("Part 1: Gamma rate is " + diagnostic.inputMatrix.calculateGammaRate());
        logger.info("Part 1: Epsilon rate is " + diagnostic.inputMatrix.calculateEpsilonRate());
        logger.info("Part 1: Power consumption is " + diagnostic.getPowerConsumption());
        logger.info("Starting Part 2...");
        logger.info("Part 2: Oxygen Generator rating is " + diagnostic.inputMatrix.calculateOxygenGeneratorRating());
        logger.info("Part 2: CO2 Scrubber rating is " + diagnostic.inputMatrix.calculateCO2ScrubberRating());
        logger.info("Part 2: Life support rating is " + diagnostic.getLifeSupportRating());
        timer.finish();
    }
}
