package com.lamontd.adventofcode.advent2022.dec10;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CathodeRayTube {
    private static final Logger logger = LoggerFactory.getLogger(CathodeRayTube.class);

    private static List<CRTInstruction> getInstructionsFromResource(LocalResourceInput resourceInput) {
        return resourceInput.getInput().stream().map(CRTInstruction::new).collect(Collectors.toList());
    }

    private static void printVisiblePixels(List<Integer> visiblePixels) {
        StringBuilder output = new StringBuilder();
        for (int pixelRow = 0; pixelRow < 6; pixelRow++) {
            for (int position=1; position <= 40; position++) {
                if (visiblePixels.contains((pixelRow * 40) + position)) {
                    output.append("#");
                } else {
                    output.append(".");
                }
            }
            output.append("\n");
        }
        logger.info("Visible pixels follow: ");
        System.out.println(output.toString());
    }
    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Cathode-Ray Tube");

        logger.info("Getting meatier instructions");
        List<CRTInstruction> mediumInstructions = getInstructionsFromResource(new LocalResourceInput("day10/largerprogram.txt"));
        logger.info("Running this larger program");
        CRTRegister medimumRegister = new CRTRegister(mediumInstructions);
        List<Integer> visiblePixels = medimumRegister.runCycles();
        logger.info("Part 1: Signal strength is " + medimumRegister.getSignalStrength());
        printVisiblePixels(visiblePixels);

        List<CRTInstruction> inputInstructions = getInstructionsFromResource(new LocalResourceInput("day10/input.txt"));
        CRTRegister inputRegister = new CRTRegister(inputInstructions);
        List<Integer> inputPixels = inputRegister.runCycles();
        logger.info("Part 1: Signal strength is " + inputRegister.getSignalStrength());
        printVisiblePixels(inputPixels);
        timer.finish();


    }
}
