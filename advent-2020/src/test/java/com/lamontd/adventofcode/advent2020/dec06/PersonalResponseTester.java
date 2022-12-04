package com.lamontd.adventofcode.advent2020.dec06;


import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class PersonalResponseTester {
    @Test
    public void testBasicAnswers() {
        PersonalResponse answers = new PersonalResponse("abcx");
        Assert.assertTrue(answers.getAnswer('a'));
        Assert.assertTrue(answers.getAnswer('b'));
        Assert.assertTrue(answers.getAnswer('c'));
        Assert.assertFalse(answers.getAnswer('d'));
        Assert.assertFalse(answers.getAnswer('e'));
        Assert.assertFalse(answers.getAnswer('f'));
        Assert.assertFalse(answers.getAnswer('g'));
        Assert.assertFalse(answers.getAnswer('h'));
        Assert.assertFalse(answers.getAnswer('i'));
        Assert.assertFalse(answers.getAnswer('j'));
        Assert.assertFalse(answers.getAnswer('k'));
        Assert.assertFalse(answers.getAnswer('l'));
        Assert.assertFalse(answers.getAnswer('m'));
        Assert.assertFalse(answers.getAnswer('n'));
        Assert.assertFalse(answers.getAnswer('o'));
        Assert.assertFalse(answers.getAnswer('p'));
        Assert.assertFalse(answers.getAnswer('q'));
        Assert.assertFalse(answers.getAnswer('r'));
        Assert.assertFalse(answers.getAnswer('s'));
        Assert.assertFalse(answers.getAnswer('t'));
        Assert.assertFalse(answers.getAnswer('u'));
        Assert.assertFalse(answers.getAnswer('v'));
        Assert.assertFalse(answers.getAnswer('w'));
        Assert.assertTrue(answers.getAnswer('x'));
        Assert.assertFalse(answers.getAnswer('y'));
        Assert.assertFalse(answers.getAnswer('z'));
        Assert.assertEquals(answers.getYesAnswers(), Set.of('a', 'b', 'c', 'x'));

        answers = new PersonalResponse("abcz");
        Assert.assertTrue(answers.getAnswer('a'));
        Assert.assertTrue(answers.getAnswer('b'));
        Assert.assertTrue(answers.getAnswer('c'));
        Assert.assertFalse(answers.getAnswer('d'));
        Assert.assertFalse(answers.getAnswer('e'));
        Assert.assertFalse(answers.getAnswer('f'));
        Assert.assertFalse(answers.getAnswer('g'));
        Assert.assertFalse(answers.getAnswer('h'));
        Assert.assertFalse(answers.getAnswer('i'));
        Assert.assertFalse(answers.getAnswer('j'));
        Assert.assertFalse(answers.getAnswer('k'));
        Assert.assertFalse(answers.getAnswer('l'));
        Assert.assertFalse(answers.getAnswer('m'));
        Assert.assertFalse(answers.getAnswer('n'));
        Assert.assertFalse(answers.getAnswer('o'));
        Assert.assertFalse(answers.getAnswer('p'));
        Assert.assertFalse(answers.getAnswer('q'));
        Assert.assertFalse(answers.getAnswer('r'));
        Assert.assertFalse(answers.getAnswer('s'));
        Assert.assertFalse(answers.getAnswer('t'));
        Assert.assertFalse(answers.getAnswer('u'));
        Assert.assertFalse(answers.getAnswer('v'));
        Assert.assertFalse(answers.getAnswer('w'));
        Assert.assertFalse(answers.getAnswer('x'));
        Assert.assertFalse(answers.getAnswer('y'));
        Assert.assertTrue(answers.getAnswer('z'));
        Assert.assertEquals(answers.getYesAnswers(), Set.of('a', 'b', 'c', 'z'));
    }
}
