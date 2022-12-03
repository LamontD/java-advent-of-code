package com.lamontd.aoc.advent2021.dec01;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class SonarSweepTester {

    @Test
    public void testSweeper() throws IOException {
        SonarSweep sweep = new SonarSweep("simplesonar.txt");
        Assert.assertEquals(7, sweep.calculateBasicUps());
    }

    @Test
    public void testSlidingUps() throws IOException {
        SonarSweep sweep = new SonarSweep("simplesonar.txt");
        Assert.assertEquals(5, sweep.calculateSlidingUps());
    }
}
