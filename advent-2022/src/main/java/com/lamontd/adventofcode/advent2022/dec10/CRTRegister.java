package com.lamontd.adventofcode.advent2022.dec10;

import com.lamontd.adventofcode.utils.BasicCounter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class CRTRegister {
    private static final Logger logger = LoggerFactory.getLogger(CRTRegister.class);
    private final BasicCounter register = new BasicCounter(1);
    private final BasicCounter cycleCounter = new BasicCounter();

    private long signalStrength = 0;

    private final List<CRTInstruction> instructions;

    public CRTRegister(List<CRTInstruction> instructions) {
        this.instructions = instructions;
    }

    public long getRegisterValue() {
        return register.currentValue();
    }

    public int getCurrentCycle() {
        return cycleCounter.currentValue();
    }

    public void addSignalStrength(long newStrength) {
        signalStrength += newStrength;
    }

    public long getSignalStrength() {
        return signalStrength;
    }

    public List<Integer> runCycles() {
        List<Integer> visiblePixels = new ArrayList<>();
        ListIterator<CRTInstruction> executionSet = instructions.listIterator();
        CRTInstruction currentInstruction = executionSet.next();
        currentInstruction.setCycleStart(1);
        while (currentInstruction != null) {
            cycleCounter.increment();
            if (cycleCounter.currentValue() >= 20 && ((cycleCounter.currentValue() - 20) % 40 == 0)) {
                logger.info("Signal strength at cycle " + cycleCounter.currentValue() + " is " + cycleCounter.currentValue() * register.currentValue());
                addSignalStrength(cycleCounter.currentValue() * register.currentValue());
            }

            logger.debug("Start cycle " + cycleCounter.currentValue() + ": begin executing " + currentInstruction.getInstruction());

            logger.debug("During cycle " + cycleCounter.currentValue() + ": CRT draws pixel in position " + register.currentValue());

            if (spriteIsCurrentlyVisible()) {
                visiblePixels.add(cycleCounter.currentValue());
            }

            if (currentInstruction.getCycleFinish() == cycleCounter.currentValue()) {
                if (StringUtils.equals(currentInstruction.getInstruction(), "noop")) {
                    logger.debug("End of cycle " + cycleCounter.currentValue() + ": Finish executing noop");
                    // Do nothing fabulously.
                } else if (StringUtils.startsWith(currentInstruction.getInstruction(), "addx")) {
                    String[] split = currentInstruction.getInstruction().split(" ");
                    register.increment(Integer.parseInt(split[1]));
                    logger.debug("End of cycle " + cycleCounter.currentValue() + ": Finish executing command " + currentInstruction.getInstruction() + " (Register X is now " + register.currentValue() + ")");
                }
                if (executionSet.hasNext()) {
                    currentInstruction = executionSet.next();
                    currentInstruction.setCycleStart(cycleCounter.currentValue() + 1);
                } else {
                    currentInstruction = null;
                }
            }
        }

        return visiblePixels;

    }

    private boolean spriteIsCurrentlyVisible() {
        int bottomOfSprite = register.currentValue() - 1;
        int topOfSprite = register.currentValue() + 1;
        int currentPixel = (cycleCounter.currentValue() - 1) % 40;
        return currentPixel >= bottomOfSprite && currentPixel <= topOfSprite;
    }

}
