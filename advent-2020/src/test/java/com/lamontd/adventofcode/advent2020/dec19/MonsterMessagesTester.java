package com.lamontd.adventofcode.advent2020.dec19;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import junit.framework.Assert;
import org.apache.log4j.BasicConfigurator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MonsterMessagesTester {
    private static final Logger logger = LoggerFactory.getLogger(MonsterMessagesTester.class);

    @BeforeAll
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testTinyMessagesMap() throws IOException {
        MonsterMessages monsterMessages = new MonsterMessages();
        LocalResourceInput resourceInput = new LocalResourceInput("advent2020/day19/advent-day19-small-input.txt");
        List<String> rules = MonsterMessages.extractRules(resourceInput);
        List<String> messages = MonsterMessages.extractMessages(resourceInput);
        monsterMessages.readRulesIntoReferenceMapParser(rules);
        Assert.assertEquals(3, monsterMessages.countValidReferenceMapParserMessages(messages));
        Map<Boolean, List<String>> initialReferenceParserMap = monsterMessages.mapValidReferenceMapParserMessages(messages);
        logger.info("Part 1 TRUE answers: " + initialReferenceParserMap.get(Boolean.TRUE));

        monsterMessages.readRulesIntoReferenceMapParser(List.of(
                "8: 42 | 42 8",
                "11: 42 31 | 42 11 31"
        ));

        Map<Boolean, List<String>> part2ReferenceParserMap = monsterMessages.mapValidReferenceMapParserMessages(messages);
        logger.info("Part 2 TRUE answers: " + part2ReferenceParserMap.get(Boolean.TRUE));

        // Misses
        boolean result = monsterMessages.referenceMapParser.matches("babbbbaabbbbbabbbbbbaabaaabaaa");
        result = monsterMessages.referenceMapParser.matches("bbbbbbbaaaabbbbaaabbabaaa");
        result = monsterMessages.referenceMapParser.matches("bbbababbbbaaaaaaaabbababaaababaabab");
        result = monsterMessages.referenceMapParser.matches("abbbbabbbbaaaababbbbbbaaaababb");
        result = monsterMessages.referenceMapParser.matches("aaaaabbaabaaaaababaa");
        result = monsterMessages.referenceMapParser.matches("aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba");

        Assert.assertFalse(monsterMessages.referenceMapParser.matches("abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa"));
        Assert.assertTrue(monsterMessages.referenceMapParser.matches("bbabbbbaabaabba"));
        Assert.assertTrue(monsterMessages.referenceMapParser.matches("babbbbaabbbbbabbbbbbaabaaabaaa"));
        Assert.assertTrue(monsterMessages.referenceMapParser.matches("aaabbbbbbaaaabaababaabababbabaaabbababababaaa"));
        Assert.assertTrue(monsterMessages.referenceMapParser.matches("bbbbbbbaaaabbbbaaabbabaaa"));
        Assert.assertTrue(monsterMessages.referenceMapParser.matches("bbbababbbbaaaaaaaabbababaaababaabab"));
        Assert.assertTrue(monsterMessages.referenceMapParser.matches("ababaaaaaabaaab"));
        Assert.assertTrue(monsterMessages.referenceMapParser.matches("ababaaaaabbbaba"));
        Assert.assertTrue(monsterMessages.referenceMapParser.matches("baabbaaaabbaaaababbaababb"));
        Assert.assertTrue(monsterMessages.referenceMapParser.matches("abbbbabbbbaaaababbbbbbaaaababb"));
        Assert.assertTrue(monsterMessages.referenceMapParser.matches("aaaaabbaabaaaaababaa"));
        Assert.assertFalse(monsterMessages.referenceMapParser.matches("aaaabbaaaabbaaa"));
        Assert.assertTrue(monsterMessages.referenceMapParser.matches("aaaabbaabbaaaaaaabbbabbbaaabbaabaaa"));
        Assert.assertFalse(monsterMessages.referenceMapParser.matches("babaaabbbaaabaababbaabababaaab"));
        Assert.assertTrue(monsterMessages.referenceMapParser.matches("aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba"));
    }
}
