package com.lamontd.aoc.advent2021.dec10;

import com.lamontd.aoc.utils.LocalResourceInput;
import com.lamontd.aoc.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class SyntaxScoring {
    private static final Logger logger = LoggerFactory.getLogger(SyntaxScoring.class);

    LocalResourceInput resourceInput;
    public SyntaxScoring(String inputFile) throws IOException {
        this.resourceInput = new LocalResourceInput(inputFile);
    }

    private static Stack<SyntaxChunk> processLine(String dataLine) throws CorruptedSyntaxException {
        Stack<SyntaxChunk> chunkStack = new Stack<>();
        SyntaxChunk thisChunk = null;
        for (char inputChar : dataLine.toCharArray()) {
            if (thisChunk == null) {
                thisChunk = new SyntaxChunk(inputChar);
            } else {
                SyntaxDelimiter delimiter = SyntaxDelimiter.findDelimiter(inputChar);
                if (delimiter.isOpener()) {
                    chunkStack.push(thisChunk);
                    thisChunk = new SyntaxChunk(inputChar);
                } else {
                    if (delimiter == thisChunk.getExpectedCloser()) {
                        thisChunk = null;
                        if (!chunkStack.empty()) {
                            thisChunk = chunkStack.pop();
                        }
                    } else {
                        throw new CorruptedSyntaxException(thisChunk.getExpectedCloser(), delimiter);
                    }
                }
            }
        }
        if (thisChunk != null) {
            chunkStack.push(thisChunk);
        }
        return chunkStack;
    }

    public int generateLineScore() {
        int lineScore = 0;
        for (String input : resourceInput.getInput()) {
            try {
                processLine(input);
            } catch (CorruptedSyntaxException cse) {
                logger.debug(" - " + input + " - Expected " + cse.getExpectedDelimiter().getValue() + ", but got " + cse.getActualDelimiter().getValue() + ", instead");
                switch(cse.getActualDelimiter()) {
                    case CLOSE_PAREN:
                        lineScore += 3;
                        break;
                    case CLOSE_BRACKET:
                        lineScore += 57;
                        break;
                    case CLOSE_CURLYBRACE:
                        lineScore += 1197;
                        break;
                    case GREATER_THAN:
                        lineScore += 25137;
                        break;
                }
            }
        }
        return lineScore;
    }

    public long scoreIncompleteLines() {
        List<Long> repairScores = new ArrayList<>();
        for (String input : resourceInput.getInput()) {
            try {
                StringBuilder completionBuilder = new StringBuilder();
                Stack<SyntaxChunk> incompleteChunks = processLine(input);
                for (SyntaxChunk chunk : incompleteChunks) {
                    completionBuilder.append(chunk.getExpectedCloser().getValue());
                }
                String completionString = completionBuilder.reverse().toString();
                long localRepairScore = 0;
                for (char ch : completionString.toCharArray()) {
                    localRepairScore *= 5;
                    SyntaxDelimiter delim = SyntaxDelimiter.findDelimiter(ch);
                    switch (delim) {
                        case CLOSE_PAREN:
                            localRepairScore += 1;
                            break;
                        case CLOSE_BRACKET:
                            localRepairScore += 2;
                            break;
                        case CLOSE_CURLYBRACE:
                            localRepairScore += 3;
                            break;
                        case GREATER_THAN:
                            localRepairScore += 4;
                            break;
                    }
                }
                logger.debug(" - " + input + " - Complete by adding " + completionString + " - " + localRepairScore + " points");
                repairScores.add(localRepairScore);
            } catch (CorruptedSyntaxException cse) {
                // Skipping corrupted line
            }
        }
        Collections.sort(repairScores);
        return repairScores.get(repairScores.size()/2);
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Syntax Scoring");
        logger.info("Loading the sample data...");
        SyntaxScoring sampleScoring = new SyntaxScoring("advent2021/day10/sample.txt");
        int sampleScore = sampleScoring.generateLineScore();
        logger.info("Sample score is " + sampleScore);
        long sampleRepairScore = sampleScoring.scoreIncompleteLines();
        logger.info("Sample repair score is " + sampleRepairScore);
        SyntaxScoring mainScoring = new SyntaxScoring("advent2021/day10/input.txt");
        int mainScore = mainScoring.generateLineScore();
        logger.info("Part 1: Main score is " + mainScore);
        long mainRepairScore = mainScoring.scoreIncompleteLines();
        logger.info("Part 2: Repair score is " + mainRepairScore);
        timer.finish();
    }
}
