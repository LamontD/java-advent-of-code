package com.lamontd.adventofcode.advent2022.dec10;

public class CRTInstruction {
    private final String instruction;

    private int cyclesRequired;
    private int valueChange;

    private int cycleStart;
    private int cycleFinish;

    public CRTInstruction(String crtString) {
        this.instruction = crtString;
        if (crtString.startsWith("noop")) {
            cyclesRequired = 1;
        } else if (crtString.startsWith("addx")) {
            cyclesRequired = 2;
        }
    }

    public String getInstruction() {
        return instruction;
    }

    public void setCycleStart(int cycleStart) {
        this.cycleStart = cycleStart;
        this.cycleFinish = cycleStart + cyclesRequired - 1;
    }

    public int getCycleFinish() {
        return cycleFinish;
    }
}
