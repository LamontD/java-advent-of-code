package com.lamontd.aoc.advent2020.dec18;

import java.util.ArrayList;
import java.util.List;

public class AdditionFirstEvaluator extends MathHomeworkEvaluator {

    @Override
    public long evaluate(List<MathToken> tokenList) {
        MathOperatorToken additionToken = new MathOperatorToken(MathOperator.ADD);
        if (tokenList.contains(additionToken)) {
            int indexOfAddition = tokenList.indexOf(additionToken);
            List<MathToken> remainderList = new ArrayList<>();
            remainderList.addAll(tokenList.subList(0, indexOfAddition - 1));
            long additionLHS = determineTokenValue(tokenList.get(indexOfAddition - 1));
            long additionRHS = determineTokenValue(tokenList.get(indexOfAddition + 1));
            LiteralValueToken additionResult = new LiteralValueToken(MathOperator.ADD.evaluate(additionLHS, additionRHS));
            remainderList.add(additionResult);
            if (indexOfAddition + 2 < tokenList.size()) {
                remainderList.addAll(tokenList.subList(indexOfAddition + 2, tokenList.size()));
            }
            return remainderList.size() == 1 ? additionResult.getValue() : evaluate(remainderList);
        }

        return niceAndEasyEvaluation(tokenList);
    }
}
