package com.lamontd.adventofcode.advent2020.dec19;

import junit.framework.Assert;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MonsterReferenceMapParserTest {
    @BeforeAll
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testBasicMapParserAssembly() {
        MonsterReferenceMapParser ruleParser = new MonsterReferenceMapParser();
        ruleParser.addRule("0: 1 2");
        ruleParser.addRule("1: \"a\"");
        ruleParser.addRule("2: 1 3 | 3 1");
        ruleParser.addRule("3: \"b\"");
        Assert.assertTrue(MonsterRuleMap.getMonsterExpression(0) instanceof MonsterSetExpression);
        Assert.assertTrue(MonsterRuleMap.getMonsterExpression(1) instanceof MonsterLiteral);
        Assert.assertTrue(MonsterRuleMap.getMonsterExpression(2) instanceof MonsterSplitExpression);
        Assert.assertTrue(MonsterRuleMap.getMonsterExpression(3) instanceof MonsterLiteral);
    }

    @Test
    public void testBasicMapParserValidation() {
        MonsterReferenceMapParser ruleParser = new MonsterReferenceMapParser();
        ruleParser.addRule("0: 1 2");
        ruleParser.addRule("1: \"a\"");
        ruleParser.addRule("2: 1 3 | 3 1");
        ruleParser.addRule("3: \"b\"");
        Assert.assertTrue(ruleParser.matches("aab"));
        Assert.assertTrue(ruleParser.matches("aba"));
        Assert.assertFalse(ruleParser.matches("aabb"));
    }

    @Test
    public void testLongerParserValidation() {
        MonsterReferenceMapParser ruleParser = new MonsterReferenceMapParser();
        ruleParser.addRule("0: 4 1 5");
        ruleParser.addRule("1: 2 3 | 3 2");
        ruleParser.addRule("2: 4 4 | 5 5");
        ruleParser.addRule("3: 4 5 | 5 4");
        ruleParser.addRule("4: \"a\"");
        ruleParser.addRule("5: \"b\"");
        Assert.assertTrue(ruleParser.matches("ababbb"));
        Assert.assertFalse(ruleParser.matches("bababa"));
        Assert.assertTrue(ruleParser.matches("abbbab"));
        Assert.assertFalse(ruleParser.matches("aaabbb"));
        Assert.assertFalse(ruleParser.matches("aaaabbb"));

    }
}
