package com.lamontd.adventofcode.advent2020.dec19;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonsterMessages {
    private static final Logger logger = LoggerFactory.getLogger(MonsterMessages.class);

    MonsterRuleBruteForceParser ruleParser = new MonsterRuleBruteForceParser();
    MonsterReferenceMapParser referenceMapParser = new MonsterReferenceMapParser();

    protected void readRulesIntoRuleParser(List<String> rules) {
        for (String potentialRule : rules) {
            if (StringUtils.isEmpty(potentialRule)) {
                break;
            }
            ruleParser.addRule(potentialRule);
        }
        ruleParser.evaluateForRuleZero();
    }

    protected void readRulesIntoReferenceMapParser(List<String> rules) {
        for (String potentialRule : rules) {
            if (StringUtils.isEmpty(potentialRule)) {
                break;
            }
            referenceMapParser.addRule(potentialRule);
        }
    }

    protected int countValidRuleParserMessages(List<String> messages) {
        int validMessageCount = 0;
        for (String messageString : messages) {
            if (ruleParser.matches(messageString)) {
                validMessageCount++;
            }
        }
        return validMessageCount;
    }

    protected int countValidReferenceMapParserMessages(List<String> messages) {
        return referenceMapParser.getValidMessageCount(messages);
    }

    protected Map<Boolean, List<String>> mapValidReferenceMapParserMessages(List<String> messages) {
        Map<Boolean, List<String>> referenceMapResults = new HashMap<>();
        referenceMapResults.put(Boolean.TRUE, new ArrayList<>());
        referenceMapResults.put(Boolean.FALSE, new ArrayList<>());
        for (String messageString : messages) {
            boolean result = referenceMapParser.matches(messageString);
            referenceMapResults.get(result).add(messageString);
        }
        return referenceMapResults;
    }

    public static List<String> extractRules(LocalResourceInput resourceInput) {
        List<String> extractedRules = new ArrayList<>();
        for (String str : resourceInput.getInput()) {
            if (str.contains(":")) {
                extractedRules.add(str);
            }
        }
        return extractedRules;
    }

    public static List<String> extractMessages(LocalResourceInput resourceInput) {
        List<String> extractedMessages = new ArrayList<>();
        for (String str : resourceInput.getInput()) {
            if (!str.contains(":") && StringUtils.isNotBlank(str)) {
                extractedMessages.add(str);
            }
        }
        return extractedMessages;
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to Monster Messages!");
        MonsterMessages monsterMessages = new MonsterMessages();
        LocalResourceInput resourceInput = new LocalResourceInput("day19/advent-day19-input.txt");
//        LocalResourceInput resourceInput = new LocalResourceInput("advent-day19-small-input.txt");
        logger.info("Setting things up...");

        List<String> extractedRules = extractRules(resourceInput);
        logger.info("I found " + extractedRules.size() + " rules in the file");
        monsterMessages.readRulesIntoReferenceMapParser(extractedRules);

        List<String> extractedMessages = extractMessages(resourceInput);
        logger.info("I found " + extractedMessages.size() + " messages");

        logger.info("Part 1: Counting the valid messages...");
        logger.info("The reference parser sees "
                + monsterMessages.countValidReferenceMapParserMessages(extractedMessages) + " valid messages");

        logger.info("Part 2: Changing rules 8 and 11 and moving to regex (sn: I feel very dirty)");
        final MonsterRegexRuleParser regexRuleParser = new MonsterRegexRuleParser();
        extractedRules.stream().forEach(rule -> regexRuleParser.addRule(rule));
        regexRuleParser.addRule("8: 42 | 42 8");
        regexRuleParser.addRule("11: 42 31 | 42 11 31");
//        regexRuleParser.readRulesIntoReferenceMapParser(List.of(
//                "8: 42 | 42 8",
//                "11: 42 31 | 42 11 31"
//        ));
        logger.info("Looking for valid messages with the changed rules...");
        logger.info("The regex parser sees "
                + regexRuleParser.getValidMessageCount(extractedMessages) + " valid messages");

        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");
    }
}
