package com.lamontd.aoc.advent2020.dec14;

import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class DockingDataTester {
    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testBasicSample() throws Exception {
        DockingData dockingData = new DockingData();
        List<String> basicProgram = List.of(
                "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",
                "mem[8] = 11",
                "mem[7] = 101",
                "mem[8] = 0");
        dockingData.executeV1Program(basicProgram);
        Assert.assertEquals(165L, dockingData.getSumOfAllV1Values());
    }
}
