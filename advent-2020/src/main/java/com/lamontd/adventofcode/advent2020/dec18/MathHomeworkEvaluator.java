package com.lamontd.adventofcode.advent2020.dec18;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public abstract class MathHomeworkEvaluator  {
    public abstract long evaluate(List<MathToken> tokenList);

    protected long niceAndEasyEvaluation(List<MathToken> tokenList) {
        ListIterator<MathToken> tokenIter = tokenList.listIterator();
        long lhsValue = determineTokenValue(tokenIter.next());
        MathOperator operator = ((MathOperatorToken)tokenIter.next()).getMathOperator();
        long rhsValue = determineTokenValue(tokenIter.next());

        long combinedValue = operator.evaluate(lhsValue, rhsValue);
        if (tokenIter.hasNext()) {
            List<MathToken> remainderList = new ArrayList<>();
            remainderList.add(new LiteralValueToken(combinedValue));
            remainderList.addAll(tokenList.subList(tokenIter.nextIndex(), tokenList.size()));
            return evaluate(remainderList);
        }

        return combinedValue;

    }

    protected long determineTokenValue(MathToken token) {
        if (token instanceof LiteralValueToken) {
            return ((LiteralValueToken)token).getValue();
        }
        if (token instanceof ParenthesisToken) {
            return evaluate(((ParenthesisToken)token).getTokenList());
        }
        throw new IllegalArgumentException("Invalid position value of " + token);
    }

}
