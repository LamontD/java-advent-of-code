package com.lamontd.adventofcode.advent2020.dec05;


import junit.framework.Assert;
import org.junit.jupiter.api.Test;

public class BoardingPassTester {
    @Test
    public void testBasicBoardingPass() {
        BoardingPass boardingPass = new BoardingPass("FBFBBFFRLR");
        Assert.assertEquals(44, boardingPass.getRow());
        Assert.assertEquals(5, boardingPass.getColumn());
        Assert.assertEquals(357, boardingPass.getSeatId());

        boardingPass = new BoardingPass("BFFFBBFRRR");
        Assert.assertEquals(70, boardingPass.getRow());
        Assert.assertEquals(7, boardingPass.getColumn());
        Assert.assertEquals(567, boardingPass.getSeatId());

        boardingPass = new BoardingPass("FFFBBBFRRR");
        Assert.assertEquals(14, boardingPass.getRow());
        Assert.assertEquals(7, boardingPass.getColumn());
        Assert.assertEquals(119, boardingPass.getSeatId());

        boardingPass = new BoardingPass("BBFFBBFRLL");
        Assert.assertEquals(102, boardingPass.getRow());
        Assert.assertEquals(4, boardingPass.getColumn());
        Assert.assertEquals(820, boardingPass.getSeatId());

        boardingPass = new BoardingPass("BBBBBBBLLL");
        Assert.assertEquals(127, boardingPass.getRow());

    }
}
