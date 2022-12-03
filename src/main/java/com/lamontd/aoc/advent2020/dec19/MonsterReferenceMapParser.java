package com.lamontd.aoc.advent2020.dec19;

import java.util.LinkedList;
import java.util.Queue;

public class MonsterReferenceMapParser extends MonsterRuleParser {
    @Override
    public void addRule(Integer ruleNumber, String ruleText) {
        MonsterRuleMap.addMonsterExpression(ruleNumber, ruleText);
    }

    @Override
    public boolean matches(String inputText) {
        MonsterExpression monsterExpression = MonsterRuleMap.getMonsterExpression(0);
        if (monsterExpression == null) {
            return false;
        }
        Queue<Character> characterQueue = convertInputString(inputText);
        MonsterParserResponse response = monsterExpression.chompAndEvaluate(characterQueue);
        return response.getResponse() && response.getCharactersEaten() == inputText.length();
    }

    public static Queue<Character> convertInputString(String inputString) {
        final Queue<Character> characterQueue = new LinkedList<>();
        for (char ch : inputString.toCharArray()) {
            characterQueue.add(ch);
        }
        return characterQueue;
    }
}
