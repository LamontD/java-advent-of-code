package com.lamontd.adventofcode.advent2020.dec06;


import com.lamontd.adventofcode.utils.LocalResourceInput;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

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
