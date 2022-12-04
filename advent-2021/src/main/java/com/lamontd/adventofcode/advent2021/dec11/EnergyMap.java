package com.lamontd.adventofcode.advent2021.dec11;

import com.lamontd.adventofcode.utils.SingleValueIntGrid;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Stack;

public class EnergyMap extends SingleValueIntGrid {
    private static final Logger logger = LoggerFactory.getLogger(EnergyMap.class);

    private int currentStep = 0;
    public EnergyMap(List<String> layout) {
        super(layout);
    }

    public int fastForwardToStepAndCount(int stepToMoveTo) {
        int totalFlashes = 0;
        while (currentStep < stepToMoveTo) {
            totalFlashes += stepAndCountFlashes();
        }
        return totalFlashes;
    }

    public int stepAndCountFlashes() {
        List<Pair<Integer, Integer>> flashpoints = new Stack<>();
        for (int row=0; row < intGrid.length; row++) {
            for (int col=0; col < intGrid.length; col++) {
                intGrid[row][col]++;
                if (intGrid[row][col] > 9) {
                    flashpoints.add(Pair.with(row, col));
                }
            }
        }

        Stack<Pair<Integer, Integer>> workload = new Stack<>();
        workload.addAll(flashpoints);
        logger.debug("Initial workload =is " + flashpoints);
        while(!workload.isEmpty()) {
            Pair<Integer, Integer> flashpoint = workload.pop();
            logger.debug("Flashing " + flashpoint);
            for(Pair<Integer, Integer> touchpoint : getValidFullAdjacentPoints(flashpoint)) {
                intGrid[touchpoint.getValue0()][touchpoint.getValue1()]++;
                logger.debug("Incrementing " + touchpoint + " to " + getGridValue(touchpoint));
                if (getGridValue(touchpoint) > 9 && !flashpoints.contains(touchpoint)) {
                    logger.debug("Adding " +touchpoint+ " for future flashing");
                    flashpoints.add(touchpoint);
                    workload.push(touchpoint);
                }
            }
        }

        int flashCount = 0;
        for(int row=0; row < intGrid.length; row++) {
            for (int col=0; col < intGrid[0].length; col++) {
                if (getGridValue(row, col) > 9) {
                    flashCount++;
                    intGrid[row][col] = 0;
                }
            }
        }
        logger.debug("I flashed " + flashCount + " times");
        currentStep++;
        return flashCount;
    }

    public int waitForFlashDance() {
        int numFlashes = 0;
        while (numFlashes != 100) {
            numFlashes = stepAndCountFlashes();
        }
        return currentStep;
    }

    public int getCurrentStep() { return currentStep; }

}
