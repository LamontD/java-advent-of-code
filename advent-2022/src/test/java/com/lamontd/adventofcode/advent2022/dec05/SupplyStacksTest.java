package com.lamontd.adventofcode.advent2022.dec05;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupplyStacksTest {
    private SupplyStacks stacks;

    @BeforeAll
    public static void init() {
        BasicConfigurator.configure();
    }

    @BeforeEach
    public void createInput() throws IOException {
        stacks = new SupplyStacks();
        stacks.populateStackMap(new LocalResourceInput("day05/sample.txt"));
    }

    @Test
    public void testStackSetup() {
        assertEquals(3, stacks.getStackMap().size());
        assertEquals(2, stacks.getStackMap().get(1).getStackSize());
        assertEquals('N', stacks.getStackMap().get(1).peekCrate());
        assertEquals(3, stacks.getStackMap().get(2).getStackSize());
        assertEquals('D', stacks.getStackMap().get(2).peekCrate());
        assertEquals(1, stacks.getStackMap().get(3).getStackSize());
        assertEquals('P', stacks.getStackMap().get(3).peekCrate());

        assertEquals(4, stacks.getCraneJobs().size());
        assertEquals(1, stacks.getCraneJobs().get(0).getQuantity());
        assertEquals(2, stacks.getCraneJobs().get(0).getSourceStack());
        assertEquals(1, stacks.getCraneJobs().get(0).getDestinationStack());
        assertEquals(3, stacks.getCraneJobs().get(1).getQuantity());
        assertEquals(1, stacks.getCraneJobs().get(1).getSourceStack());
        assertEquals(3, stacks.getCraneJobs().get(1).getDestinationStack());
        assertEquals(2, stacks.getCraneJobs().get(2).getQuantity());
        assertEquals(2, stacks.getCraneJobs().get(2).getSourceStack());
        assertEquals(1, stacks.getCraneJobs().get(2).getDestinationStack());
        assertEquals(1, stacks.getCraneJobs().get(3).getQuantity());
        assertEquals(1, stacks.getCraneJobs().get(3).getSourceStack());
        assertEquals(2, stacks.getCraneJobs().get(3).getDestinationStack());
    }

    @Test
    public void testCrateMover9000() {
        stacks.runCrateMover9000();
        assertEquals(1, stacks.getStackMap().get(1).getStackSize());
        assertEquals('C', stacks.getStackMap().get(1).peekCrate());
        assertEquals(1, stacks.getStackMap().get(2).getStackSize());
        assertEquals('M', stacks.getStackMap().get(2).peekCrate());
        assertEquals(4, stacks.getStackMap().get(3).getStackSize());
        assertEquals('Z', stacks.getStackMap().get(3).peekCrate());
    }

    @Test
    public void testCrateMover9001() {
        stacks.runCrateMover9001();
        assertEquals(1, stacks.getStackMap().get(1).getStackSize());
        assertEquals('M', stacks.getStackMap().get(1).peekCrate());
        assertEquals(1, stacks.getStackMap().get(2).getStackSize());
        assertEquals('C', stacks.getStackMap().get(2).peekCrate());
        assertEquals(4, stacks.getStackMap().get(3).getStackSize());
        assertEquals('D', stacks.getStackMap().get(3).peekCrate());
    }

    @Test
    public void testSetupWithRealInput() throws IOException {
        stacks.populateStackMap(new LocalResourceInput("day05/input.txt"));
        assertEquals(9, stacks.getStackMap().size());
        assertEquals(6, stacks.getStackMap().get(1).getStackSize());
        assertEquals('S', stacks.getStackMap().get(1).peekCrate());
        assertEquals(3, stacks.getStackMap().get(2).getStackSize());
        assertEquals('T', stacks.getStackMap().get(2).peekCrate());
        assertEquals(5, stacks.getStackMap().get(3).getStackSize());
        assertEquals('H', stacks.getStackMap().get(3).peekCrate());
        assertEquals(4, stacks.getStackMap().get(4).getStackSize());
        assertEquals('D', stacks.getStackMap().get(4).peekCrate());
        assertEquals(7, stacks.getStackMap().get(5).getStackSize());
        assertEquals('J', stacks.getStackMap().get(5).peekCrate());
        assertEquals(8, stacks.getStackMap().get(6).getStackSize());
        assertEquals('L', stacks.getStackMap().get(6).peekCrate());
        assertEquals(7, stacks.getStackMap().get(7).getStackSize());
        assertEquals('J', stacks.getStackMap().get(7).peekCrate());
        assertEquals(8, stacks.getStackMap().get(8).getStackSize());
        assertEquals('H', stacks.getStackMap().get(8).peekCrate());
        assertEquals(8, stacks.getStackMap().get(9).getStackSize());
        assertEquals('W', stacks.getStackMap().get(9).peekCrate());

    }

}
