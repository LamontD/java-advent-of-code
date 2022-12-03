package com.lamontd.aoc.advent2020.dec03;

import org.junit.Assert;
import org.junit.Test;

public class BiomeEntryTester {
    @Test
    public void testTreeChecking() {
        BiomeEntry entry = new BiomeEntry("..##.......");
        Assert.assertFalse(entry.hasTree(0));
        Assert.assertFalse(entry.hasTree(1));
        Assert.assertTrue(entry.hasTree(2));
        Assert.assertTrue(entry.hasTree(3));
        Assert.assertFalse(entry.hasTree(4));
        Assert.assertFalse(entry.hasTree(5));
        Assert.assertFalse(entry.hasTree(6));
        Assert.assertFalse(entry.hasTree(7));
        Assert.assertFalse(entry.hasTree(8));
        Assert.assertFalse(entry.hasTree(9));
        Assert.assertFalse(entry.hasTree(10));

        BiomeEntry entry2 = new BiomeEntry("#...#...#..");
        Assert.assertTrue(entry2.hasTree(0));
        Assert.assertFalse(entry2.hasTree(1));
        Assert.assertFalse(entry2.hasTree(2));
        Assert.assertFalse(entry2.hasTree(3));
        Assert.assertTrue(entry2.hasTree(4));
        Assert.assertFalse(entry2.hasTree(5));
        Assert.assertFalse(entry2.hasTree(6));
        Assert.assertFalse(entry2.hasTree(7));
        Assert.assertTrue(entry2.hasTree(8));
        Assert.assertFalse(entry2.hasTree(9));
        Assert.assertFalse(entry2.hasTree(10));
    }

    @Test
    public void testTreeExpansion() {
        BiomeEntry entry = new BiomeEntry(".#....#..#.");
        Assert.assertFalse(entry.hasTree(0));
        Assert.assertTrue(entry.hasTree(1+11));
        Assert.assertFalse(entry.hasTree(2+11));
        Assert.assertFalse(entry.hasTree(3+11));
        Assert.assertFalse(entry.hasTree(4+22));
        Assert.assertFalse(entry.hasTree(5+22));
        Assert.assertTrue(entry.hasTree(6+33));
        Assert.assertFalse(entry.hasTree(7+44));
        Assert.assertFalse(entry.hasTree(8+55));
        Assert.assertTrue(entry.hasTree(9+66));
        Assert.assertTrue(entry.hasTree(9+121));
        Assert.assertFalse(entry.hasTree(10+77));
    }
}
