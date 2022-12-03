package com.lamontd.aoc.advent2020.dec19;

import java.util.Queue;

public abstract class MonsterExpression {
    private final int expressionId;
    public MonsterExpression(int expressionId) { this.expressionId = expressionId; }
    abstract MonsterParserResponse chompAndEvaluate(Queue<Character> characterList);

    public int getExpressionId() {
        return expressionId;
    }

    @Override public String toString() { return "Expression #" + Integer.toString(expressionId); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ) return false;
        if (o instanceof MonsterExpression) {
            MonsterExpression that = (MonsterExpression)o;
            return that.getExpressionId() == getExpressionId();
        }
        return false;
    }

}
