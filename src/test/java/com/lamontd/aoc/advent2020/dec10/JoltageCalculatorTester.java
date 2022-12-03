package com.lamontd.aoc.advent2020.dec10;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JoltageCalculatorTester {

    @Test
    public void testGetValidCombinations() {
        JoltageCalculator calculator = new JoltageCalculator(List.of(16L, 10L, 15L, 5L, 1L, 11L, 7L, 19L, 6L, 12L, 4L));
        Assert.assertEquals(8, calculator.getValidCombinations());
        Assert.assertEquals(List.of(new JoltageAdapter(5), new JoltageAdapter(6), new JoltageAdapter(11)), calculator.getFullConnectorBag().getSuperfluousAdapters());
        Set<Set<JoltageAdapter>> removalCandidates = JoltageCalculator.generateCombinations(new HashSet<>(calculator.getFullConnectorBag().getSuperfluousAdapters()));
        for (Set<JoltageAdapter> candidateSet : removalCandidates) {
            System.out.println("Check: " + candidateSet);
        }
        Assert.assertEquals(8, calculator.countValidCombinationsThroughSetTheory());

        calculator = new JoltageCalculator(List.of(28L, 33L, 18L, 42L, 31L, 14L, 46L, 20L, 48L, 47L, 24L, 23L, 49L, 45L, 19L, 38L, 39L, 11L, 1L, 32L, 25L, 35L, 8L, 17L, 7L, 9L, 4L, 2L, 34L, 10L, 3L));
        System.out.println(calculator.getFullConnectorBag().getSuperfluousAdapters());
        Assert.assertEquals(19208, calculator.getValidCombinations());
        Assert.assertEquals(19208, calculator.countValidCombinationsThroughSetTheory());
    }
}
