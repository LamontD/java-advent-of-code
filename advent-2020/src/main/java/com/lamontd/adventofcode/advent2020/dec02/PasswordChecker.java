package com.lamontd.adventofcode.advent2020.dec02;

import com.lamontd.adventofcode.utils.BasicCounter;
import com.lamontd.adventofcode.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PasswordChecker {
    private static final Logger logger = LoggerFactory.getLogger(PasswordChecker.class);

    private static Pair<PasswordRule, String> convertInputToPasswordRule(String inputString) throws IllegalArgumentException {
        String[] splitInput = inputString.split(" ");
        if (splitInput.length != 3)
            throw new IllegalArgumentException("Input String '"+inputString+"' does not follow the proper format");

        String[] lengthEntry = splitInput[0].split("-");
        if (lengthEntry.length != 2)
            throw new IllegalArgumentException("Length entry '"+splitInput[0]+"' not in the proper format");
        int min = Integer.parseInt(lengthEntry[0]);
        int max = Integer.parseInt(lengthEntry[1]);

        Character requiredChar = splitInput[1].charAt(0);
        String passwordToCheck = splitInput[2];

        return new Pair<>(new PasswordRule(min, max, requiredChar), passwordToCheck);
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Advent Day #2: Password Tester");
        try {
            LocalResourceInput inputFile = new LocalResourceInput("day2/advent-day2-input.txt");
            logger.info("Processing the file");

            final BasicCounter originalRuleCounter = new BasicCounter();
            final BasicCounter modifiedRuleCounter = new BasicCounter();

            inputFile.getInput().stream().forEach(line -> {
                final Pair<PasswordRule, String> processedLine = convertInputToPasswordRule(line);
                if (processedLine.getValue0().isPasswordValidByOriginalRule(processedLine.getValue1())) {
                    originalRuleCounter.increment();
                }
                if (processedLine.getValue0().isPasswordValidByModifiedRule(processedLine.getValue1())) {
                    modifiedRuleCounter.increment();
                }
            });
            logger.info("Original Rule: I found " + originalRuleCounter.currentValue() + " valid passwords in the input data");
            logger.info("Modified Rule: I found " + modifiedRuleCounter.currentValue() + " valid passwords in the input data");
            long milliFinish = System.currentTimeMillis();
            logger.info("Execution took " + (milliFinish - milliStart) + "ms");
        } catch (IOException ioe) {
            logger.error("Trouble in paradise", ioe);
        }
    }
}
