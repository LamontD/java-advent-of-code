package com.lamontd.adventofcode.advent2020.dec19;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class MonsterSetExpression extends MonsterExpression {
    private final List<MonsterExpression> expressionSet;

    public MonsterSetExpression(Integer expressionId, List<MonsterExpression> expressionSet) {
        super(expressionId);
        this.expressionSet = expressionSet;
    }

    @Override
    public MonsterParserResponse chompAndEvaluate(Queue<Character> characterList) {
        int expectedCharactersEaten = 0;
        for (MonsterExpression expression : expressionSet) {
            MonsterParserResponse parserResponse = expression.chompAndEvaluate(characterList);
            if (!parserResponse.getResponse()) {
                return MonsterParserResponse.createFalse(expression.getExpressionId());
            }
            expectedCharactersEaten += parserResponse.charactersEaten;
        }
        return MonsterParserResponse.createTrue(expectedCharactersEaten);
    }

    @Override public String toString() {
        return super.toString() + ": " + Arrays.toString(expressionSet.toArray());
    }
}
