package com.lamontd.adventofcode.advent2020.dec06;


import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class GroupResponseTester {

    @Test
    public void testSinglePersonCreation() {
        GroupResponse groupResponse = new GroupResponse();
        Assert.assertEquals(0, groupResponse.getGroupSize());
        Assert.assertTrue(groupResponse.getUnionOfYesAnswers().isEmpty());
        groupResponse.addResponseLine("abc");
        Assert.assertEquals(1, groupResponse.getGroupSize());
        Assert.assertFalse(groupResponse.getUnionOfYesAnswers().isEmpty());
        Assert.assertTrue(groupResponse.getUnionOfYesAnswers().equals(Set.of('a', 'b', 'c')));
    }

    @Test
    public void testMultiplePersonGroup() {
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.addResponseLine("a");
        groupResponse.addResponseLine("b");
        groupResponse.addResponseLine("c");
        Assert.assertEquals(3, groupResponse.getGroupSize());
        Assert.assertEquals(groupResponse.getUnionOfYesAnswers(), Set.of('a', 'b', 'c'));

        groupResponse = new GroupResponse();
        groupResponse.addResponseLine("ab");
        groupResponse.addResponseLine("ac");
        Assert.assertEquals(2, groupResponse.getGroupSize());
        Assert.assertEquals(groupResponse.getUnionOfYesAnswers(), Set.of('a', 'b', 'c'));

        groupResponse = new GroupResponse();
        groupResponse.addResponseLine("a");
        groupResponse.addResponseLine("a");
        groupResponse.addResponseLine("a");
        groupResponse.addResponseLine("a");
        Assert.assertEquals(4, groupResponse.getGroupSize());
        Assert.assertEquals(groupResponse.getUnionOfYesAnswers(), Set.of('a'));
    }

    @Test
    public void testIntersectionAnswers() {
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.addResponseLine("a");
        groupResponse.addResponseLine("b");
        groupResponse.addResponseLine("c");
        Assert.assertEquals(groupResponse.getIntersectionOfYesAnswers(), Set.of());

        groupResponse = new GroupResponse();
        groupResponse.addResponseLine("abc");
        Assert.assertEquals(groupResponse.getIntersectionOfYesAnswers(), Set.of('a', 'b', 'c'));

        groupResponse = new GroupResponse();
        groupResponse.addResponseLine("ab");
        groupResponse.addResponseLine("ac");
        Assert.assertEquals(groupResponse.getIntersectionOfYesAnswers(), Set.of('a'));

        groupResponse = new GroupResponse();
        groupResponse.addResponseLine("a");
        groupResponse.addResponseLine("a");
        groupResponse.addResponseLine("a");
        groupResponse.addResponseLine("a");
        Assert.assertEquals(4, groupResponse.getGroupSize());
        Assert.assertEquals(groupResponse.getUnionOfYesAnswers(), Set.of('a'));

        groupResponse = new GroupResponse();
        groupResponse.addResponseLine("b");
        Assert.assertEquals(groupResponse.getIntersectionOfYesAnswers(), Set.of('b'));
    }
}
