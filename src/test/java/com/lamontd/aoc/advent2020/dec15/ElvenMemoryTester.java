package com.lamontd.aoc.advent2020.dec15;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ElvenMemoryTester {
    @Test
    public void testBasicNumberSpeaking() {
        ElvenMemory elvenMemory = new ElvenMemory(List.of(0, 3, 6));
        Assert.assertEquals(6, elvenMemory.getLastNumberSpoken());
        Assert.assertEquals(3, elvenMemory.getLastTurn());

        Assert.assertEquals(0, elvenMemory.takeTurnSpeakingANumber());
        Assert.assertEquals(3, elvenMemory.takeTurnSpeakingANumber());
        Assert.assertEquals(3, elvenMemory.takeTurnSpeakingANumber());
        Assert.assertEquals(1, elvenMemory.takeTurnSpeakingANumber());
        Assert.assertEquals(0, elvenMemory.takeTurnSpeakingANumber());
        Assert.assertEquals(4, elvenMemory.takeTurnSpeakingANumber());
        Assert.assertEquals(0, elvenMemory.takeTurnSpeakingANumber());
    }

    @Test
    public void test2020Turns() {

        ElvenMemory elvenMemory = new ElvenMemory(List.of(1, 3, 2));
        Assert.assertEquals(1, elvenMemory.getNumberForTurn(2020));

        elvenMemory = new ElvenMemory(List.of(2, 1, 3));
        Assert.assertEquals(10, elvenMemory.getNumberForTurn(2020));

        elvenMemory = new ElvenMemory(List.of(1, 2, 3));
        Assert.assertEquals(27, elvenMemory.getNumberForTurn(2020));

        elvenMemory = new ElvenMemory(List.of(2, 3, 1));
        Assert.assertEquals(78, elvenMemory.getNumberForTurn(2020));

        elvenMemory = new ElvenMemory(List.of(3, 2, 1));
        Assert.assertEquals(438, elvenMemory.getNumberForTurn(2020));

        elvenMemory = new ElvenMemory(List.of(3, 1, 2));
        Assert.assertEquals(1836, elvenMemory.getNumberForTurn(2020));
    }

    @Test
    public void testYugeNumbers() {
        ElvenMemory elvenMemory = new ElvenMemory(List.of(1, 3, 2));
        Assert.assertEquals(2578, elvenMemory.getNumberForTurn(30000000));

        elvenMemory = new ElvenMemory(List.of(2, 1, 3));
        Assert.assertEquals(3544142, elvenMemory.getNumberForTurn(30000000));

        elvenMemory = new ElvenMemory(List.of(1, 2, 3));
        Assert.assertEquals(261214, elvenMemory.getNumberForTurn(30000000));

        elvenMemory = new ElvenMemory(List.of(2, 3, 1));
        Assert.assertEquals(6895259, elvenMemory.getNumberForTurn(30000000));

        elvenMemory = new ElvenMemory(List.of(3, 2, 1));
        Assert.assertEquals(18, elvenMemory.getNumberForTurn(30000000));

        elvenMemory = new ElvenMemory(List.of(3, 1, 2));
        Assert.assertEquals(362, elvenMemory.getNumberForTurn(30000000));

    }
}
