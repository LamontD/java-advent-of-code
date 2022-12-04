package com.lamontd.adventofcode.advent2020.dec19;

import java.util.Queue;

public class MonsterReferenceExpression extends MonsterExpression {
    private final int ruleNumber;

    public MonsterReferenceExpression(int ruleNumber) {
        super(ruleNumber);
        this.ruleNumber = ruleNumber;
    }

    @Override
    public MonsterParserResponse chompAndEvaluate(Queue<Character> characterList) {
        MonsterExpression referenceExpression = MonsterRuleMap.getMonsterExpression(this.ruleNumber);
        if (referenceExpression == null) {
            return MonsterParserResponse.createFalse(ruleNumber);
        }
        return referenceExpression.chompAndEvaluate(characterList);
    }

    @Override public String toString() { return "Reference to " + ruleNumber; }
}
