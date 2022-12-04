package com.lamontd.adventofcode.advent2020.dec14;


import junit.framework.Assert;
import org.junit.jupiter.api.Test;

public class SeaportComputerTester {
    @Test
    public void testBasicProgram() throws IllegalSeaportMemoryException {
        SeaportComputer computer = new SeaportComputer();
        computer.updateBitmask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X");
        computer.setMemoryValueAtAddress(8L, 11L);
        Assert.assertEquals(73L, computer.getMemoryValueAtAddress(8));
        computer.setMemoryValueAtAddress(7L, 101L);
        Assert.assertEquals(101L, computer.getMemoryValueAtAddress(7));
        computer.setMemoryValueAtAddress(8L, 0L);
        Assert.assertEquals(64L, computer.getMemoryValueAtAddress(8));
        Assert.assertEquals(165L, computer.getTotalMemoryValue());
    }

    @Test
    public void testMemoryDecoderType() throws IllegalSeaportMemoryException {
        SeaportComputer computer = new SeaportComputer();
        computer.updateBitmask("000000000000000000000000000000X1001X");
        computer.setMemoryValueAtAddressUsingMemoryDecoder(42L, 100L);
        Assert.assertEquals(100L, computer.getMemoryValueAtAddress(26L));
        Assert.assertEquals(100L, computer.getMemoryValueAtAddress(27L));
        Assert.assertEquals(100L, computer.getMemoryValueAtAddress(58L));
        Assert.assertEquals(100L, computer.getMemoryValueAtAddress(59L));
        Assert.assertEquals(400L, computer.getTotalMemoryValue());

        computer.updateBitmask("00000000000000000000000000000000X0XX");
        computer.setMemoryValueAtAddressUsingMemoryDecoder(26L, 1L);
        Assert.assertEquals(1L, computer.getMemoryValueAtAddress(16L));
        Assert.assertEquals(1L, computer.getMemoryValueAtAddress(17L));
        Assert.assertEquals(1L, computer.getMemoryValueAtAddress(18L));
        Assert.assertEquals(1L, computer.getMemoryValueAtAddress(19L));
        Assert.assertEquals(1L, computer.getMemoryValueAtAddress(24L));
        Assert.assertEquals(1L, computer.getMemoryValueAtAddress(25L));
        Assert.assertEquals(1L, computer.getMemoryValueAtAddress(26L));
        Assert.assertEquals(1L, computer.getMemoryValueAtAddress(27L));
        Assert.assertEquals(208L, computer.getTotalMemoryValue());
    }
}
