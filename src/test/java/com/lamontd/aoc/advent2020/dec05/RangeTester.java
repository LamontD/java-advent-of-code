package com.lamontd.aoc.advent2020.dec05;

import org.junit.Assert;
import org.junit.Test;

public class RangeTester {

    @Test
    public void testBasicRangeCapabilities() {
        Range basicRange = new Range(0, 127);
        Assert.assertEquals(128, basicRange.getScale());
        Assert.assertEquals(0, basicRange.getLower());
        Assert.assertEquals(127, basicRange.getUpper());
        basicRange.takeLowerHalf();
        Assert.assertEquals(0, basicRange.getLower());
        Assert.assertEquals(63, basicRange.getUpper());
        basicRange.takeUpperHalf();
        Assert.assertEquals(32, basicRange.getLower());
        Assert.assertEquals(63, basicRange.getUpper());
        basicRange.takeLowerHalf();
        Assert.assertEquals(32, basicRange.getLower());
        Assert.assertEquals(47, basicRange.getUpper());
        basicRange.takeUpperHalf();
        Assert.assertEquals(40, basicRange.getLower());
        Assert.assertEquals(47, basicRange.getUpper());
        basicRange.takeUpperHalf();
        Assert.assertEquals(44, basicRange.getLower());
        Assert.assertEquals(47, basicRange.getUpper());
        basicRange.takeLowerHalf();
        Assert.assertEquals(44, basicRange.getLower());
        Assert.assertEquals(45, basicRange.getUpper());
        basicRange.takeLowerHalf();
        Assert.assertEquals(44, basicRange.getLower());
        Assert.assertEquals(44, basicRange.getUpper());
    }
}
