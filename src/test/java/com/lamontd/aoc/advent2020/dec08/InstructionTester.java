package com.lamontd.aoc.advent2020.dec08;

import org.junit.Assert;
import org.junit.Test;

public class InstructionTester {

    @Test
    public void testInstructionTypeLookup() {
        Assert.assertEquals(CodeLine.Instruction.ACC, CodeLine.Instruction.valueOf("acc".toUpperCase()));
        Assert.assertEquals(CodeLine.Instruction.JMP, CodeLine.Instruction.valueOf("jmp".toUpperCase()));
        Assert.assertEquals(CodeLine.Instruction.NOP, CodeLine.Instruction.valueOf("nop".toUpperCase()));
    }

    @Test
    public void testHandheldGameConsole() {
        HandheldGameConsole gameConsole = new HandheldGameConsole();
        gameConsole.loadLineOfCode("nop +0");
        gameConsole.loadLineOfCode("acc +1");
        gameConsole.loadLineOfCode("jmp +4");
        gameConsole.loadLineOfCode("acc +3");
        gameConsole.loadLineOfCode("jmp -3");
        gameConsole.loadLineOfCode("acc -99");
        gameConsole.loadLineOfCode("acc +1");
        gameConsole.loadLineOfCode("jmp -4");
        gameConsole.loadLineOfCode("acc +6");

        try {
            gameConsole.executeSource(gameConsole.getSourceCode());
            Assert.fail("Should've hit the infinite loop point");
        } catch (InfiniteGameConsoleLoopException e) {
            Assert.assertEquals(5, e.getAccumulatorValue());
        }
    }

    @Test
    public void testFixingProblemsThroughSwaps() {
        HandheldGameConsole gameConsole = new HandheldGameConsole();
        gameConsole.loadLineOfCode("nop +0");
        gameConsole.loadLineOfCode("acc +1");
        gameConsole.loadLineOfCode("jmp +4");
        gameConsole.loadLineOfCode("acc +3");
        gameConsole.loadLineOfCode("jmp -3");
        gameConsole.loadLineOfCode("acc -99");
        gameConsole.loadLineOfCode("acc +1");
        gameConsole.loadLineOfCode("jmp -4");
        gameConsole.loadLineOfCode("acc +6");

        Assert.assertEquals(8, gameConsole.fixInfiniteLoopBySwappingNoOpAndJmp());
    }
}
