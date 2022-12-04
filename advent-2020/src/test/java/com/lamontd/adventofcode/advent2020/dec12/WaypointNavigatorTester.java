package com.lamontd.adventofcode.advent2020.dec12;

import junit.framework.Assert;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WaypointNavigatorTester {
    @BeforeAll
    public static void setupLogger() {
        BasicConfigurator.configure();
    }
    @Test
    public void basicNavigatorTester() {
        WaypointNavigator navigator = new WaypointNavigator(new Coordinate.Builder().north(1).east(10).build());
        Assert.assertEquals(new Coordinate.Builder().build(), navigator.getLocation());
        Assert.assertEquals(new Coordinate.Builder().north(1).east(10).build(), navigator.getRelativeWaypoint());
        Assert.assertEquals(0, navigator.getManhattanDistanceTraveled());

        navigator.navigate("F10");
        Assert.assertEquals(new Coordinate.Builder().east(100).north(10).build(), navigator.getLocation());
        Assert.assertEquals(new Coordinate.Builder().north(1).east(10).build(), navigator.getRelativeWaypoint());

        navigator.navigate("N3");
        Assert.assertEquals(new Coordinate.Builder().east(100).north(10).build(), navigator.getLocation());
        Assert.assertEquals(new Coordinate.Builder().north(4).east(10).build(), navigator.getRelativeWaypoint());

        navigator.navigate("S3");
        navigator.navigate("N3");
        navigator.navigate("W17");
        navigator.navigate("E17");
        Assert.assertEquals(new Coordinate.Builder().east(100).north(10).build(), navigator.getLocation());
        Assert.assertEquals(new Coordinate.Builder().north(4).east(10).build(), navigator.getRelativeWaypoint());

        navigator.navigate("F7");
        Assert.assertEquals(new Coordinate.Builder().east(170).north(38).build(), navigator.getLocation());
        Assert.assertEquals(new Coordinate.Builder().north(4).east(10).build(), navigator.getRelativeWaypoint());

        navigator.navigate("R90");
        navigator.navigate("R360");
        Assert.assertEquals(new Coordinate.Builder().east(170).north(38).build(), navigator.getLocation());
        Assert.assertEquals(new Coordinate.Builder().east(4).south(10).build(), navigator.getRelativeWaypoint());

        navigator.navigate("F11");
        Assert.assertEquals(new Coordinate.Builder().east(214).south(72).build(), navigator.getLocation());
        Assert.assertEquals(new Coordinate.Builder().east(4).south(10).build(), navigator.getRelativeWaypoint());
        Assert.assertEquals(286, navigator.getManhattanDistanceTraveled());
    }

    @Test
    public void testWaypointRotation() {
        WaypointNavigator navigator = new WaypointNavigator(new Coordinate.Builder().east(5).north(10).build());
        Assert.assertEquals(new Coordinate.Builder().east(5).north(10).build(), navigator.getRelativeWaypoint());
        navigator.navigate("R90");
        Assert.assertEquals(new Coordinate.Builder().east(10).south(5).build(), navigator.getRelativeWaypoint());
        navigator.navigate("R90");
        Assert.assertEquals(new Coordinate.Builder().west(5).south(10).build(), navigator.getRelativeWaypoint());
        navigator.navigate("R90");
        Assert.assertEquals(new Coordinate.Builder().west(10).north(5).build(), navigator.getRelativeWaypoint());
        navigator.navigate("R90");
        Assert.assertEquals(new Coordinate.Builder().east(5).north(10).build(), navigator.getRelativeWaypoint());
        navigator.navigate("L90");
        Assert.assertEquals(new Coordinate.Builder().west(10).north(5).build(), navigator.getRelativeWaypoint());
        navigator.navigate("R90");
        Assert.assertEquals(new Coordinate.Builder().east(5).north(10).build(), navigator.getRelativeWaypoint());

        navigator.navigate("R-90");
        Assert.assertEquals(new Coordinate.Builder().west(10).north(5).build(), navigator.getRelativeWaypoint());
    }
}
