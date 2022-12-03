package com.lamontd.aoc.advent2020.dec19;

import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class MonsterRuleBruteForceParserTester {

    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testMonsterRuleCreation() {
        MonsterRuleBruteForceParser ruleParser = new MonsterRuleBruteForceParser();
        ruleParser.addRule("0: 1 2");
        ruleParser.addRule("1: \"a\"");
        ruleParser.addRule("2: 1 3 | 3 1");
        ruleParser.addRule("3: \"b\"");
        List<String> zeroString = ruleParser.evaluateForRuleZero();
        Assert.assertEquals(List.of("aab", "aba"), zeroString);
    }

    @Test
    public void testLongerRuleCreation() {
        MonsterRuleBruteForceParser ruleParser = new MonsterRuleBruteForceParser();
        ruleParser.addRule("0: 4 1 5");
        ruleParser.addRule("1: 2 3 | 3 2");
        ruleParser.addRule("2: 4 4 | 5 5");
        ruleParser.addRule("3: 4 5 | 5 4");
        ruleParser.addRule("4: \"a\"");
        ruleParser.addRule("5: \"b\"");
        List<String> zeroString = ruleParser.evaluateForRuleZero();
    }

    @Test
    public void testRuleMatch() {
        MonsterRuleBruteForceParser ruleParser = new MonsterRuleBruteForceParser();
        ruleParser.addRule("0: 4 1 5");
        ruleParser.addRule("1: 2 3 | 3 2");
        ruleParser.addRule("2: 4 4 | 5 5");
        ruleParser.addRule("3: 4 5 | 5 4");
        ruleParser.addRule("4: \"a\"");
        ruleParser.addRule("5: \"b\"");
        ruleParser.processRuleSet();
        Assert.assertTrue(ruleParser.matches("ababbb"));
        Assert.assertFalse(ruleParser.matches("bababa"));
        Assert.assertTrue(ruleParser.matches("abbbab"));
        Assert.assertFalse(ruleParser.matches("aaabbb"));
        Assert.assertFalse(ruleParser.matches("aaaabbb"));
    }
}
