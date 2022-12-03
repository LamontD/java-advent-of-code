package com.lamontd.aoc.advent2020.dec06;

import com.lamontd.aoc.utils.LocalResourceInput;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

public class CustomCustomsTester {
    @Test
    public void testBasicInput() throws IOException {
        CustomCustoms customs = new CustomCustoms();
        customs.readInputData(new LocalResourceInput("dumb-input.txt"));
        Assert.assertEquals(5, customs.getNumberOfGroups());
        Assert.assertEquals(customs.getCombinedYesAnswers(), Set.of('a', 'b', 'c'));
        Assert.assertEquals(11, customs.getSumOfYesAnswersForGroups());
    }

    @Test
    public void testIntersectionInput() throws IOException {
        CustomCustoms customs = new CustomCustoms();
        customs.readInputData(new LocalResourceInput("dumb-input.txt"));
        Assert.assertEquals(5, customs.getNumberOfGroups());
    }
}
