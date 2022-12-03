package com.lamontd.aoc.advent2020.dec09;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class XmasEncoderTester {

    @Test
    public void testBasicXmasCypher() {
        XmasEncoder cipher = new XmasEncoder(List.of(35L, 20L, 15L, 25L, 47L));
        Assert.assertEquals(5, cipher.getPreambleLength());
        try {
            cipher.processNumber(40);
            cipher.processNumber(62);
            cipher.processNumber(55);
            cipher.processNumber(65);
            cipher.processNumber(95);
            cipher.processNumber(102);
            cipher.processNumber(117);
            cipher.processNumber(150);
            cipher.processNumber(182);
        } catch (BadXmasCypherInputException bad) {
            Assert.fail(bad.getMessage());
        }

        try {
            cipher.processNumber(127);
            Assert.fail("127 should be a bad input");
        } catch (BadXmasCypherInputException bad) {
        }
    }
}
