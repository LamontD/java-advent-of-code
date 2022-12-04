package com.lamontd.adventofcode.advent2020.dec19;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MonsterRuleMap {
    private final static Map<Integer, MonsterExpression> monsterExpressionMap = new HashMap<>();

    public static void addMonsterExpression(int ruleNumber, MonsterExpression expression) {
        monsterExpressionMap.put(ruleNumber, expression);
    }

    public static void addMonsterExpression(int ruleNumber, String expressionString) {
        MonsterExpression newExpression = null;
        if (expressionString.contains("|")) {
            String[] orSplitter = expressionString.split("\\|");
            MonsterExpression lhsExpression = generateExpressionFromStringToken(ruleNumber, orSplitter[0].trim());
            MonsterExpression rhsExpression = generateExpressionFromStringToken(ruleNumber, orSplitter[1].trim());
            newExpression = new MonsterSplitExpression(ruleNumber, lhsExpression, rhsExpression);
        } else if (expressionString.contains(" ")) {
            List<MonsterExpression> expressionList = new ArrayList<>();
            for (String inputStr : expressionString.split(" ")) {
                if (StringUtils.isNotEmpty(inputStr.trim())) {
                    expressionList.add(generateExpressionFromStringToken(ruleNumber, inputStr.trim()));
                }
            }
            newExpression = new MonsterSetExpression(ruleNumber, expressionList);
        } else {
            newExpression = generateExpressionFromStringToken(ruleNumber, expressionString.trim());
        }

        if (newExpression != null) {
            monsterExpressionMap.put(ruleNumber, newExpression);
        }
    }

    private static MonsterExpression generateExpressionFromStringToken(int ruleNumber, String expressionString) {
        if (expressionString.trim().contains(" ")) {
            List<String> splitExpressionStrings = List.of(expressionString.trim().split(" "));
            return new MonsterSetExpression(ruleNumber, splitExpressionStrings
                    .stream()
                    .map(str -> generateExpressionFromStringToken(ruleNumber, str.trim())).collect(Collectors.toList()));
        } else if (MonsterRuleParser.isInteger(expressionString.trim())) {
            return new MonsterReferenceExpression(Integer.parseInt(expressionString));
        } else if (expressionString.trim().length() == 1) {
            return new MonsterLiteral(ruleNumber, expressionString.trim().charAt(0));
        } else if (expressionString.contains("\"")) {
            return new MonsterLiteral(ruleNumber, expressionString.trim().replaceAll("\"", "").charAt(0));
        }
        throw new IllegalArgumentException("Cannot generate expression from string >" + expressionString + "<");
    }

    public static MonsterExpression getMonsterExpression(int ruleNumber) {
        return monsterExpressionMap.get(ruleNumber);
    }

    private List<MonsterExpression> getExpressions() { return new ArrayList<>(monsterExpressionMap.values()); }

}
