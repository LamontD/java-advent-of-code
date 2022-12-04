package com.lamontd.adventofcode.advent2020.dec17;

import junit.framework.Assert;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ConwayDimensionTester {
    private static final Logger logger = LoggerFactory.getLogger(ConwayDimensionTester.class);

    @BeforeAll
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testConwayConstruction() {
        ConwayDimension dimension = new ConwayDimension(List.of(
                ".#.",
                "..#",
                "###"
        ));
        logger.info(dimension.toString());
        Assert.assertEquals(CubeState.INACTIVE, dimension.getState(0, 0, 0));
        Assert.assertEquals(CubeState.ACTIVE, dimension.getState(1, 0, 0));
        Assert.assertEquals(CubeState.INACTIVE, dimension.getState(2, 0, 0));
        Assert.assertEquals(CubeState.INACTIVE, dimension.getState(0, 1, 0));
        Assert.assertEquals(CubeState.INACTIVE, dimension.getState(1, 1, 0));
        Assert.assertEquals(CubeState.ACTIVE, dimension.getState(2, 1, 0));
        Assert.assertEquals(CubeState.ACTIVE, dimension.getState(0, 2, 0));
        Assert.assertEquals(CubeState.ACTIVE, dimension.getState(1, 2, 0));
        Assert.assertEquals(CubeState.ACTIVE, dimension.getState(2, 2, 0));
        Assert.assertEquals(CubeState.INACTIVE, dimension.getState(-1, -1, -1));
    }

    @Test
    public void testActiveNeighborCount() {
        ConwayDimension dimension = new ConwayDimension(List.of(
                ".#.",
                "..#",
                "###"
        ));
        Assert.assertEquals(1, dimension.getActiveNeighborCount(0, 0, 0));
        Assert.assertEquals(1, dimension.getActiveNeighborCount(1, 0, 0));
        Assert.assertEquals(2, dimension.getActiveNeighborCount(2, 0, 0));
        Assert.assertEquals(3, dimension.getActiveNeighborCount(0, 1, 0));
        Assert.assertEquals(5, dimension.getActiveNeighborCount(1, 1, 0));
        Assert.assertEquals(3, dimension.getActiveNeighborCount(2, 1, 0));
        Assert.assertEquals(1, dimension.getActiveNeighborCount(0, 2, 0));
        Assert.assertEquals(3, dimension.getActiveNeighborCount(1, 2, 0));
        Assert.assertEquals(2, dimension.getActiveNeighborCount(2, 2, 0));
        Assert.assertEquals(0, dimension.getActiveNeighborCount(-1, 0, 0));
    }

    @Test
    public void testGetActiveCubeCount() {
        ConwayDimension dimension = new ConwayDimension(List.of(
                ".#.",
                "..#",
                "###"
        ));
        Assert.assertEquals(5, dimension.getActiveCubeCount());
    }

    @Test
    public void testGetActiveNeighbors() {
        ConwayDimension dimension = new ConwayDimension(List.of(
                ".#.",
                "..#",
                "###"
        ));
        List<ConwayCoordinate> onesToCheck = List.of(ConwayCoordinate.at(0, 0, 0), ConwayCoordinate.at(0, 1, -1), ConwayCoordinate.at(0, 0, -1));
        for (ConwayCoordinate checkOnThis : onesToCheck) {
            logger.info("Active neighbors of " + checkOnThis + ": " + dimension.getActiveNeighbors(checkOnThis).toString());
        }
    }

    @Test
    public void testExecuteSingleCycle() {
        ConwayDimension dimension = new ConwayDimension(List.of(
                ".#.",
                "..#",
                "###"
        ));
        dimension.executeCycle();
        logger.info(dimension.toString());
        Assert.assertEquals(11, dimension.getActiveCubeCount());
    }

    @Test
    public void testExecuteMultipleCycles() {
        ConwayDimension dimension = new ConwayDimension(List.of(
                ".#.",
                "..#",
                "###"
        ));
        dimension.executeCycle();
        dimension.executeCycle();
        dimension.executeCycle();
        dimension.executeCycle();
        dimension.executeCycle();
        dimension.executeCycle();
        Assert.assertEquals(112, dimension.getActiveCubeCount());
    }
}
