package com.lamontd.adventofcode.advent2020.dec08;

import com.lamontd.adventofcode.utils.BasicCounter;
import com.lamontd.adventofcode.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HandheldGameConsole {
    private static final Logger logger = LoggerFactory.getLogger(HandheldGameConsole.class);

    private final ArrayList<CodeLine> sourceCode = new ArrayList<>();

    public void loadLineOfCode(String lineOfCode) {
        String []codePoints = lineOfCode.split(" ");
        CodeLine.Instruction instruction = null;
        Integer argument = null;
        try {
            instruction = CodeLine.Instruction.valueOf(codePoints[0].toUpperCase());
            argument = Integer.valueOf(codePoints[1]);
        } catch (Exception e) {
            logger.error("Error trying to parse code line '" + lineOfCode + "'", e);
        }
        sourceCode.add(new CodeLine(instruction, argument));
    }

    public ArrayList<CodeLine> getSourceCode() {
        return sourceCode;
    }

    public int fixInfiniteLoopBySwappingNoOpAndJmp() {
        for (CodeLine codeLine : sourceCode) {
            switch(codeLine.getInstruction()) {
                case NOP:
                    logger.info("Attempting NOP/JMP swap...");
                    codeLine.setInstruction(CodeLine.Instruction.JMP);
                    try {
                        return executeSource(sourceCode);
                    } catch (InfiniteGameConsoleLoopException e) {
                        codeLine.setInstruction(CodeLine.Instruction.NOP);
                    }
                    break;

                case JMP:
                    logger.info("Attempting JMP/NOP swap...");
                    codeLine.setInstruction(CodeLine.Instruction.NOP);
                    try {
                        return executeSource(sourceCode);
                    } catch (InfiniteGameConsoleLoopException e) {
                        codeLine.setInstruction(CodeLine.Instruction.JMP);
                    }
                    break;
            }
        }
        throw new IllegalArgumentException("Could not fix this program through swaps!");
    }

    public int executeSource(List<CodeLine> codeToExecute) throws InfiniteGameConsoleLoopException {
        BasicCounter accumulator = new BasicCounter();
        int nextInstruction = 0;
        final List<Integer> executedLines = new ArrayList<>();
        while(nextInstruction < codeToExecute.size()) {
            CodeLine currentLineOfCode = codeToExecute.get(nextInstruction);
            executedLines.add(nextInstruction);
            switch(currentLineOfCode.getInstruction()) {
                case ACC:
                    accumulator.increment(currentLineOfCode.getArgument());
                    nextInstruction++;
                    break;
                case JMP:
                    nextInstruction += currentLineOfCode.getArgument();
                    break;
                case NOP:
                    nextInstruction++;
                    break;
            }
            if(executedLines.contains(nextInstruction)) {
                throw new InfiniteGameConsoleLoopException(accumulator, executedLines.size());
            }
        }

        logger.info("Reached the end of the road after executing " + executedLines.size() + " lines");
        return accumulator.currentValue();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to the handheld game console program!");
        logger.info("Loading the input data into the program...");
        HandheldGameConsole gameConsole = new HandheldGameConsole();
        LocalResourceInput resourceInput = new LocalResourceInput("day8/advent-day8-input.txt");
        for (String line : resourceInput.getInput()) {
            gameConsole.loadLineOfCode(line);
        }
        logger.info("Loaded " + gameConsole.sourceCode.size() + " lines of code");
        logger.info("Executing the program with input of 0...");
        try {
            int finalValue = gameConsole.executeSource(gameConsole.sourceCode);
            logger.info("Final output after running to completion: " + finalValue);
        } catch (InfiniteGameConsoleLoopException e) {
            logger.info("Ran into infinite loop after " + e.getLinesExecuted() + " lines; final value=" + e.getAccumulatorValue());
        }
        logger.info("Trying to fix the infinite loop...");
        int finalValue = gameConsole.fixInfiniteLoopBySwappingNoOpAndJmp();
        logger.info("With fixing the loop, the final value is " + finalValue);
        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");

    }
}
