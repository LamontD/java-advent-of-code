package com.lamontd.adventofcode.advent2020.dec14;

import junit.framework.Assert;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DockingDataTester {
    @BeforeAll
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
