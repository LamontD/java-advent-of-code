package com.lamontd.aoc.advent2020.dec19;

import java.util.HashMap;
import java.util.List;

public class MonsterRegexRuleParser extends MonsterRuleParser {
    private final HashMap<Integer, String> straightRuleMap = new HashMap<>();
    @Override
    public void addRule(Integer ruleNumber, String ruleText) {
        straightRuleMap.put(ruleNumber, ruleText.trim());
    }

    @Override
    public boolean matches(String inputText) {
        return getValidMessageCount(List.of(inputText)) == 1;
    }

    @Override
    public int getValidMessageCount(List<String> inputMessages) {
        String ruleZeroRegex = straightRuleMap.get(0);
        long previousCount = 0;
        while (true) {
            final StringBuilder builder = new StringBuilder();
            for (String part : ruleZeroRegex.split(" ")) {
                if (isInteger(part)) {
                    builder.append("( ").append(straightRuleMap.get(Integer.parseInt(part))).append(" )");
                } else {
                    builder.append(part).append(' ');
                }
            }

            ruleZeroRegex = builder.toString();

            final String pattern = "^" + ruleZeroRegex.replaceAll("([ \"])|42|31", "") + "$";
            final long count = inputMessages.stream().filter(str -> str.matches(pattern)).count();
            if (count > 0 && count == previousCount) {
                return (int)count;
            }
            previousCount = count;
        }
    }
}
