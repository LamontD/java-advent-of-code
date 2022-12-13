package com.lamontd.adventofcode.advent2022.dec12;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignalHeightmapTester {

    private final List<String> sampleLayout = List.of(
            "Sabqponm",
            "abcryxxl",
            "accszExk",
            "acctuvwj",
            "abdefghi"
    );
    @Test
    public void testGenerateIntValueFromCharacter() {
        SignalHeightmap heightmap = new SignalHeightmap(sampleLayout);
        assertEquals(0, heightmap.generateIntValueFromLayoutCharacter(0, 0, 'S'));
        assertEquals(0, heightmap.generateIntValueFromLayoutCharacter(0, 1, 'a'));
        assertEquals(25, heightmap.generateIntValueFromLayoutCharacter(2, 5, 'E'));
        assertEquals(12, heightmap.generateIntValueFromLayoutCharacter(0, 7, 'm'));
    }
}
