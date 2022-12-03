package com.lamontd.aoc.advent2020.dec18;

import java.util.List;

public class NoPrecedenceEvaluator extends MathHomeworkEvaluator {
    @Override public long evaluate(List<MathToken> tokenList) {
        return niceAndEasyEvaluation(tokenList);
    }

}
