package com.lamontd.aoc.advent2020.dec02;

import org.junit.Assert;
import org.junit.Test;

public class PasswordRuleTester {
    @Test
    public void testValidPasswords() {
        PasswordRule rule = new PasswordRule(1, 3, 'a');
        Assert.assertTrue(rule.isPasswordValidByOriginalRule("abcde"));
        Assert.assertTrue(rule.isPasswordValidByOriginalRule("abcdaeeea"));
        Assert.assertFalse(rule.isPasswordValidByOriginalRule("aaaaa"));
        Assert.assertFalse(rule.isPasswordValidByOriginalRule("John JAcob Jingleheimer Smith"));

        rule = new PasswordRule(1, 3, 'b');
        Assert.assertFalse(rule.isPasswordValidByOriginalRule("cdefg"));

        rule = new PasswordRule(2, 9, 'c');
        Assert.assertTrue(rule.isPasswordValidByOriginalRule("ccccccccc"));
    }

    @Test
    public void testModifiedRule() {
        PasswordRule rule = new PasswordRule(1, 3, 'a');
        Assert.assertTrue(rule.isPasswordValidByModifiedRule("abcde"));
        Assert.assertFalse(rule.isPasswordValidByModifiedRule("cdefg"));
        Assert.assertFalse(rule.isPasswordValidByModifiedRule("ccccccccc"));
    }
}
