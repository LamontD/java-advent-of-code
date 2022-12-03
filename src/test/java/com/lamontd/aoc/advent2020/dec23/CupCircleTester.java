package com.lamontd.aoc.advent2020.dec23;

import org.apache.log4j.BasicConfigurator;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CupCircleTester {
    private static final Logger logger = LoggerFactory.getLogger(CupCircleTester.class);

    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testBasicCupConstruction() {
        CupCircle circle = new CupCircle("389125467");
        Assert.assertEquals(3, circle.getCurrentCupValue());
        Assert.assertEquals(List.of(3, 8, 9, 1, 2, 5, 4, 6, 7), circle.getCircle());
    }

    @Test
    public void testExecuteSingleTurn() {
        CupCircle circle = new CupCircle("389125467");
        circle.executeTurn();
        Assert.assertEquals(2, circle.getCurrentCupValue());
        Assert.assertEquals(List.of(3, 2, 8, 9, 1, 5, 4, 6, 7), circle.getCircle());
    }

    @Test
    public void testExecute100Turns() {
        CupCircle circle = new CupCircle("389125467");
        for (int i=0; i < 100; i++) {
            logger.debug("-- move " + i + " --");
            circle.executeTurn();
            logger.debug("after: " + circle.toString());
        }
        Assert.assertEquals("67384529", circle.getOrderStringStartingWith(1).substring(1));
    }

    @Test
    public void testMillionsMode() {
        CupCircle circle = new CupCircle("389125467");
        logger.debug("Setting millions mode...");
        circle.setMillionsMode();
        logger.debug("Going through the turnstyles...");
        for (int i=0; i < 10000; i++) {
            circle.executeTurn();
        }
        Pair<Long, Long> answerPair = circle.findPairToTheRightOfOne();
        Assert.assertEquals(149245887792L, answerPair.getValue0() * answerPair.getValue1());

    }
}
