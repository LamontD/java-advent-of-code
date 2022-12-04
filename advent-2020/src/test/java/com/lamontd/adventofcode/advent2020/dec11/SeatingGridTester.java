package com.lamontd.adventofcode.advent2020.dec11;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import junit.framework.Assert;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SeatingGridTester {
    @Test
    public void testSeatingGridInit() throws IOException {
        LocalResourceInput resourceInput = new LocalResourceInput("seating-sample.txt");
        SeatingGrid grid = new SeatingGrid(resourceInput.getInput());
        Assert.assertEquals(SeatState.FLOOR, grid.getSeatState(2, 1));
        Assert.assertEquals(SeatState.EMPTY_SEAT, grid.getSeatState(0, 0));
        System.out.println("At creation:");
        System.out.println(grid.toString());
        Assert.assertTrue(grid.executeInitialLogicStep());
        System.out.println("After logic step");
        System.out.println(grid.toString());
        Assert.assertEquals(2, grid.getBasicAdjacentOccupiedSeats(0, 0));
        Assert.assertEquals(5, grid.getBasicAdjacentOccupiedSeats(1, 2));
        System.out.println("Going for stability...");
        grid.executeInitialLogicStepsUntilStable();
        Assert.assertEquals(37, grid.getNumOccupiedSeats());
    }

    @Test
    public void testEnhancedSightLines() throws IOException {
        List<String> sampleInput = List.of(
                ".......#.",
                "...#.....",
                ".#.......",
                ".........",
                "..#L....#",
                "....#....",
                ".........",
                "#........",
                "...#.....");
        SeatingGrid grid = new SeatingGrid(sampleInput);
        Map<SeatingGrid.Direction, Pair<Integer, Integer>> sightLines = grid.findLinesOfSight(4, 3);
        Assert.assertEquals(Pair.with(4, 2), sightLines.get(SeatingGrid.Direction.WEST));
        Assert.assertEquals(Pair.with(2, 1), sightLines.get(SeatingGrid.Direction.NORTHWEST));
        Assert.assertEquals(Pair.with(1, 3), sightLines.get(SeatingGrid.Direction.NORTH));
        Assert.assertEquals(Pair.with(0, 7), sightLines.get(SeatingGrid.Direction.NORTHEAST));
        Assert.assertEquals(Pair.with(4, 8), sightLines.get(SeatingGrid.Direction.EAST));
        Assert.assertEquals(Pair.with(5, 4), sightLines.get(SeatingGrid.Direction.SOUTHEAST));
        Assert.assertEquals(Pair.with(8, 3), sightLines.get(SeatingGrid.Direction.SOUTH));
        Assert.assertEquals(Pair.with(7, 0), sightLines.get(SeatingGrid.Direction.SOUTHWEST));

        sampleInput = List.of(
                ".............",
                ".L.L.#.#.#.#.",
                ".............");
        grid = new SeatingGrid(sampleInput);
        sightLines = grid.findLinesOfSight(1, 1);
        Assert.assertEquals(Pair.with(1, 3), sightLines.get(SeatingGrid.Direction.EAST));
    }

    @Test
    public void testEnhancedSeatingLogic() throws IOException {
        LocalResourceInput resourceInput = new LocalResourceInput("seating-sample.txt");
        SeatingGrid grid = new SeatingGrid(resourceInput.getInput());
        System.out.println("At creation:");
        System.out.println(grid.toString());
        System.out.println("After first enhanced logic step: ");
        grid.executeEnhancedLogicStep();
        System.out.println(grid.toString());
        grid.executeEnhancedLogicStepsUntilStable();
        System.out.println("After all the logic is done");
        System.out.println(grid.toString());
        Assert.assertEquals(26, grid.getNumOccupiedSeats());
    }
}
