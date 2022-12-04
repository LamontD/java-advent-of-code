package com.lamontd.adventofcode.advent2020.dec08;

public class CodeLine {
    private Instruction instruction;
    private final Integer argument;

    public CodeLine(Instruction instruction, Integer argument) {
        this.instruction = instruction;
        this.argument = argument;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public Integer getArgument() {
        return argument;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    @Override
    public String toString() {
        return "CodeLine{" +
                "instruction=" + instruction +
                ", argument=" + argument +
                '}';
    }

    public static enum Instruction { ACC, JMP, NOP; }
}
