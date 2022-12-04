package com.lamontd.adventofcode.advent2020.dec02;


import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class PasswordRuleTester {
    @Test
    public void testValidPasswords() {
        PasswordRule rule = new PasswordRule(1, 3, 'a');
        assertTrue(rule.isPasswordValidByOriginalRule("abcde"));
        assertTrue(rule.isPasswordValidByOriginalRule("abcdaeeea"));
        assertFalse(rule.isPasswordValidByOriginalRule("aaaaa"));
        assertFalse(rule.isPasswordValidByOriginalRule("John JAcob Jingleheimer Smith"));

        rule = new PasswordRule(1, 3, 'b');
        assertFalse(rule.isPasswordValidByOriginalRule("cdefg"));

        rule = new PasswordRule(2, 9, 'c');
        assertTrue(rule.isPasswordValidByOriginalRule("ccccccccc"));
    }

    @Test
    public void testModifiedRule() {
        PasswordRule rule = new PasswordRule(1, 3, 'a');
        assertTrue(rule.isPasswordValidByModifiedRule("abcde"));
        assertFalse(rule.isPasswordValidByModifiedRule("cdefg"));
        assertFalse(rule.isPasswordValidByModifiedRule("ccccccccc"));
    }
}
