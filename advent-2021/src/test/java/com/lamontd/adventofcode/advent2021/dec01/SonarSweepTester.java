package com.lamontd.adventofcode.advent2021.dec01;

import com.lamontd.adventofcode.advent2021.dec01.SonarSweep;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SonarSweepTester {

    @Test
    public void testSweeper() throws IOException {
        SonarSweep sweep = new SonarSweep("simplesonar.txt");
        assertEquals(7, sweep.calculateBasicUps());
    }

    @Test
    public void testSlidingUps() throws IOException {
        SonarSweep sweep = new SonarSweep("simplesonar.txt");
        assertEquals(5, sweep.calculateSlidingUps());
    }
}
