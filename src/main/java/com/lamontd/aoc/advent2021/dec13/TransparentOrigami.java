package com.lamontd.aoc.advent2021.dec13;

import com.lamontd.aoc.utils.LocalResourceInput;
import com.lamontd.aoc.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransparentOrigami {
    private static final Logger logger = LoggerFactory.getLogger(TransparentOrigami.class);

    TransparentPaper paper;
    List<String> instructions;

    public void loadPaper(String inputFile) throws IOException {
        LocalResourceInput resourceInput = new LocalResourceInput(inputFile);
        paper = new TransparentPaper();
        instructions = new ArrayList<>();

        for (String input : resourceInput.getInput()) {
            if (input.contains(",")) {
                String[] coord = input.trim().split(",");
                paper.addVisibleDot(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));
            } else if (input.startsWith("fold")) {
                instructions.add(input.substring(11));
            }
        }
    }

    public void performFirstFold() {
        String firstInstruction = instructions.get(0);
        String[] div = firstInstruction.split("=");
        paper.foldAlongLine(div[0], Integer.parseInt(div[1]));
        logger.info("Folded along " + firstInstruction);
    }

    public void performAllFolds() {
        for(String instruction : instructions) {
            String[] div = instruction.split("=");
            paper.foldAlongLine(div[0], Integer.parseInt(div[1]));
            logger.info("Folded along " + instruction);
        }
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Transparent Origami");
        logger.info("Loading paper...");
        TransparentOrigami origami = new TransparentOrigami();
        origami.loadPaper("advent2021/day13/sample.txt");
        logger.info(origami.paper.toString());
        logger.info("Working the first fold");
        origami.performFirstFold();
        logger.info(origami.paper.toString());

        origami.loadPaper("advent2021/day13/input.txt");
        logger.info("Working the first fold");
        origami.performFirstFold();

        logger.info("Setting up for Part 2");
        origami.loadPaper("advent2021/day13/input.txt");
        origami.performAllFolds();
        logger.info(origami.paper.toString());
        logger.info("\n" + origami.paper.getPaperContents());
        timer.finish();
    }
}
