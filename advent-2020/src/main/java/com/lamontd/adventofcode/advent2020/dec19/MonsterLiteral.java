package com.lamontd.adventofcode.advent2020.dec19;

import java.util.Objects;
import java.util.Queue;

public class MonsterLiteral extends MonsterExpression {
    private final Character token;
    public MonsterLiteral(Integer expressionId, char token) {
        super(expressionId);
        this.token = token; }

    @Override
    public MonsterParserResponse chompAndEvaluate(Queue<Character> characterList) {
        return new MonsterParserResponse(characterList.poll() == token, 1);
    }

    @Override
    public String toString() { return super.toString() + ": \"" + token + "\""; }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

}
