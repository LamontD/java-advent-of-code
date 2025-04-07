package com.lamontd.adventofcode.advent2023.day02;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import org.javatuples.Triplet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CubeGameTest {
    private static List<String> inputLines;

    @BeforeAll
    public static void init() throws IOException {
        LocalResourceInput resourceInput = new LocalResourceInput("day02/sample.txt");
        inputLines = resourceInput.getInput();
    }

    @Test
    public void testIntegrityOfDefinitions() {
        CubeGame game = new CubeGame(inputLines.get(0));
        assertEquals(1, game.getGameId());
        assertEquals(3, game.getPulls().size());
        compareCubeSizes(Triplet.with(4, 0, 3), game.getPulls().get(0));
        compareCubeSizes(Triplet.with(1, 2, 6), game.getPulls().get(1));
        compareCubeSizes(Triplet.with(0, 2, 0), game.getPulls().get(2));

        CubeGame game2 = new CubeGame(inputLines.get(1));
        assertEquals(2, game2.getGameId());
        assertEquals(3, game2.getPulls().size());
        compareCubeSizes(Triplet.with(0, 2, 1), game2.getPulls().get(0));
        compareCubeSizes(Triplet.with(1, 3, 4), game2.getPulls().get(1));
        compareCubeSizes(Triplet.with(0, 1, 1), game2.getPulls().get(2));

        CubeGame game3 = new CubeGame(inputLines.get(2));
        assertEquals(3, game3.getPulls().size());
        compareCubeSizes(Triplet.with(20, 8, 6), game3.getPulls().get(0));
        compareCubeSizes(Triplet.with(4, 13, 5), game3.getPulls().get(1));
        compareCubeSizes(Triplet.with(1, 5, 0), game3.getPulls().get(2));

        CubeGame game4 = new CubeGame(inputLines.get(3));

        CubeGame game5 = new CubeGame(inputLines.get(4));
    }

    @Test
    public void testMinimumCubesAndPower() {
        CubeGame game1 = new CubeGame(inputLines.get(0));
        assertEquals(Triplet.with(4, 2, 6), game1.getMinimumCubesNeeded());
        assertEquals(48L, game1.getMinimumPower());

        CubeGame game2 = new CubeGame(inputLines.get(1));
        assertEquals(Triplet.with(1, 3, 4), game2.getMinimumCubesNeeded());
        assertEquals(12L, game2.getMinimumPower());

        CubeGame game3 = new CubeGame(inputLines.get(2));
        assertEquals(Triplet.with(20, 13, 6), game3.getMinimumCubesNeeded());
        assertEquals(1560L, game3.getMinimumPower());

        CubeGame game4 = new CubeGame(inputLines.get(3));
        assertEquals(Triplet.with(14, 3, 15), game4.getMinimumCubesNeeded());
        assertEquals(630, game4.getMinimumPower());

        CubeGame game5 = new CubeGame(inputLines.get(4));
        assertEquals(Triplet.with(6, 3, 2), game5.getMinimumCubesNeeded());
        assertEquals(36, game5.getMinimumPower());
    }

    private static void compareCubeSizes(Triplet<Integer, Integer, Integer> sizes, BagPull pull) {
        assertEquals(sizes.getValue0(), pull.getRedCubes());
        assertEquals(sizes.getValue1(), pull.getGreenCubes());
        assertEquals(sizes.getValue2(), pull.getBlueCubes());
    }
}
