package com.lamontd.adventofcode.advent2020.dec19;

import java.util.LinkedList;
import java.util.Queue;

public class MonsterSplitExpression extends MonsterExpression {
    private final MonsterExpression lhs;
    private final MonsterExpression rhs;

    public MonsterSplitExpression(Integer expressionId, MonsterExpression lhs, MonsterExpression rhs) {
        super(expressionId);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public MonsterParserResponse chompAndEvaluate(Queue<Character> characterList) {
        MonsterParserResponse lhsResponse = lhs.chompAndEvaluate(new LinkedList<>(characterList));
        if (lhsResponse.getResponse()) {
            for (int chompChars = lhsResponse.charactersEaten; chompChars > 0; chompChars--) {
                characterList.poll();
            }
            return lhsResponse;
        }
        MonsterParserResponse rhsResponse = rhs.chompAndEvaluate(new LinkedList<>(characterList));
        if (rhsResponse.getResponse()) {
            for (int chompChars = rhsResponse.charactersEaten; chompChars > 0; chompChars--) {
                characterList.poll();
            }
        }
        return rhsResponse;
    }

    @Override public String toString() {
        return super.toString() + ": [" + lhs.toString() + " | " + rhs.toString();
    }
}
