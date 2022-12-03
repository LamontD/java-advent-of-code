package com.lamontd.aoc.advent2020.dec23;

import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkedCupCircleTester {
    private static final Logger logger = LoggerFactory.getLogger(LinkedCupCircleTester.class);

    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testBasicConstruction() {
        LinkedCupCircle linkedCircle = new LinkedCupCircle("389125467");
        Assert.assertEquals(3, linkedCircle.getCurrentCupValue());
        logger.info(linkedCircle.toString());
    }

    @Test
    public void testExecuteSingleTurn() {
        LinkedCupCircle linkedCircle = new LinkedCupCircle("389125467");
        linkedCircle.executeTurn();
        Assert.assertEquals(2, linkedCircle.getCurrentCupValue());
        logger.debug(linkedCircle.toString());
    }

    @Test
    public void testExecuteTenTurns() {
        LinkedCupCircle linkedCircle = new LinkedCupCircle("389125467");
        for (int turns=0; turns < 10; turns++) {
            logger.debug("-- move " + turns + " --");
            logger.debug(linkedCircle.toString());
            linkedCircle.executeTurn();
        }
        logger.info("Final: " + linkedCircle);
        Assert.assertEquals(8, linkedCircle.getCurrentCupValue());
    }

    @Test
    public void testPart1String() {
        LinkedCupCircle linkedCircle = new LinkedCupCircle("389125467");
        for (int turns=0; turns < 100; turns++) {
            linkedCircle.executeTurn();
        }
        Assert.assertEquals("67384529", linkedCircle.getOrderStringStartingWith(1).substring(1));
    }

    @Test
    public void testCreateMillionList() {
        LinkedCupCircle linkedCupCircle = new LinkedCupCircle("389125467", 1000000);
        Assert.assertEquals(3, linkedCupCircle.getCurrentCupValue());
        Assert.assertEquals(1000000, linkedCupCircle.getCircleSize());
    }

    @Test
    public void testValuesNextToOne() {
        LinkedCupCircle linkedCupCircle = new LinkedCupCircle("389125467", 1000000);
        Assert.assertEquals(10L, linkedCupCircle.getMultiplicandsAfterOne());
    }
}
