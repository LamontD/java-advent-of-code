package com.lamontd.adventofcode.advent2020.dec17;

import junit.framework.Assert;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ConwayHyperdimensionTester {
    private static final Logger logger = LoggerFactory.getLogger(ConwayHyperdimensionTester.class);

    @BeforeAll
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testConwayConstruction() {
        ConwayHyperdimension dimension = new ConwayHyperdimension(List.of(
                ".#.",
                "..#",
                "###"
        ));
        logger.info(dimension.toString());
        Assert.assertEquals(CubeState.INACTIVE, dimension.getState(0, 0, 0, 0));
        Assert.assertEquals(CubeState.ACTIVE, dimension.getState(1, 0, 0, 0));
        Assert.assertEquals(CubeState.INACTIVE, dimension.getState(2, 0, 0, 0));
        Assert.assertEquals(CubeState.INACTIVE, dimension.getState(0, 1, 0, 0));
        Assert.assertEquals(CubeState.INACTIVE, dimension.getState(1, 1, 0, 0));
        Assert.assertEquals(CubeState.ACTIVE, dimension.getState(2, 1, 0, 0));
        Assert.assertEquals(CubeState.ACTIVE, dimension.getState(0, 2, 0, 0));
        Assert.assertEquals(CubeState.ACTIVE, dimension.getState(1, 2, 0, 0));
        Assert.assertEquals(CubeState.ACTIVE, dimension.getState(2, 2, 0, 0));
        Assert.assertEquals(CubeState.INACTIVE, dimension.getState(-1, -1, -1, -1));
    }

    @Test
    public void testActiveNeighborCount() {
        ConwayHyperdimension dimension = new ConwayHyperdimension(List.of(
                ".#.",
                "..#",
                "###"
        ));
        Assert.assertEquals(1, dimension.getActiveNeighborCount(0, 0, 0, 0));
        Assert.assertEquals(1, dimension.getActiveNeighborCount(1, 0, 0, 0));
        Assert.assertEquals(2, dimension.getActiveNeighborCount(2, 0, 0, 0));
        Assert.assertEquals(3, dimension.getActiveNeighborCount(0, 1, 0, 0));
        Assert.assertEquals(5, dimension.getActiveNeighborCount(1, 1, 0, 0));
        Assert.assertEquals(3, dimension.getActiveNeighborCount(2, 1, 0, 0));
        Assert.assertEquals(1, dimension.getActiveNeighborCount(0, 2, 0, 0));
        Assert.assertEquals(3, dimension.getActiveNeighborCount(1, 2, 0, 0));
        Assert.assertEquals(2, dimension.getActiveNeighborCount(2, 2, 0, 0));
        Assert.assertEquals(0, dimension.getActiveNeighborCount(-1, 0, 0, 0));
    }

    @Test
    public void testGetActiveCubeCount() {
        ConwayHyperdimension dimension = new ConwayHyperdimension(List.of(
                ".#.",
                "..#",
                "###"
        ));
        Assert.assertEquals(5, dimension.getActiveCubeCount());
    }

    @Test
    public void testGetActiveNeighbors() {
        ConwayHyperdimension dimension = new ConwayHyperdimension(List.of(
                ".#.",
                "..#",
                "###"
        ));
        List<ConwayHypercoordinate> onesToCheck = List.of(ConwayHypercoordinate.at(0, 0, 0, 0), ConwayHypercoordinate.at(0, 1, -1, 0), ConwayHypercoordinate.at(0, 0, -1, 0));
        for (ConwayHypercoordinate checkOnThis : onesToCheck) {
            logger.info("Active neighbors of " + checkOnThis + ": " + dimension.getActiveNeighbors(checkOnThis).toString());
        }
    }

    @Test
    public void testExecuteSingleCycle() {
        ConwayHyperdimension dimension = new ConwayHyperdimension(List.of(
                ".#.",
                "..#",
                "###"
        ));
        dimension.executeCycle();
        logger.info(dimension.toString());
        Assert.assertEquals(29, dimension.getActiveCubeCount());
    }

    @Test
    public void testExecuteMultipleCycles() {
        ConwayHyperdimension dimension = new ConwayHyperdimension(List.of(
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
        Assert.assertEquals(848, dimension.getActiveCubeCount());
    }
}
