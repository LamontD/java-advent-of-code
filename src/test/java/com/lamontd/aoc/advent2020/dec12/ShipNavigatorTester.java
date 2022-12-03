package com.lamontd.aoc.advent2020.dec12;

import org.junit.Assert;
import org.junit.Test;

public class ShipNavigatorTester {
    @Test
    public void basicNavigatorTester() {
        ShipNavigator navigator = new ShipNavigator(90);
        Assert.assertEquals(new Coordinate.Builder().build(), navigator.getLocation());
        Assert.assertEquals(90, navigator.getHeading());
        Assert.assertEquals(0, navigator.getManhattanDistanceTraveled());
        navigator.navigate("F10");
        Assert.assertEquals(new Coordinate.Builder().east(10).build(), navigator.getLocation());
        Assert.assertEquals(10, navigator.getManhattanDistanceTraveled());
        Assert.assertEquals(90, navigator.getHeading());
        navigator.navigate("N3");
        Assert.assertEquals(new Coordinate.Builder().east(10).north(3).build(), navigator.getLocation());
        Assert.assertEquals(90, navigator.getHeading());
        navigator.navigate("F7");
        Assert.assertEquals(new Coordinate.Builder().east(17).north(3).build(), navigator.getLocation());
        Assert.assertEquals(90, navigator.getHeading());
        navigator.navigate("R90");
        Assert.assertEquals(new Coordinate.Builder().east(17).north(3).build(), navigator.getLocation());
        Assert.assertEquals(180, navigator.getHeading());
        navigator.navigate("F11");
        Assert.assertEquals(new Coordinate.Builder().east(17).south(8).build(), navigator.getLocation());
        Assert.assertEquals(180, navigator.getHeading());
    }
}
