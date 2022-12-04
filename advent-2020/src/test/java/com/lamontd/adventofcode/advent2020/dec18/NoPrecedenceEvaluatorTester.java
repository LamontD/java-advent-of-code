package com.lamontd.adventofcode.advent2020.dec18;


import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

public class NoPrecedenceEvaluatorTester {
    @Test
    public void testBasicExpressionEvaluation() {
        MathHomeworkEvaluator evaluator = new NoPrecedenceEvaluator();
        List<MathToken> basicExpression = ExpressionTokenizer.tokenize("2 + 4");
        Assert.assertEquals(6L, evaluator.evaluate(basicExpression));

        basicExpression = ExpressionTokenizer.tokenize("1 + 2 * 3 + 4 * 5 + 6");
        Assert.assertEquals(71L, evaluator.evaluate(basicExpression));
    }

    @Test
    public void testNestedExpressionEvaluation() {
        MathHomeworkEvaluator evaluator = new NoPrecedenceEvaluator();
        List<MathToken> expression = ExpressionTokenizer.tokenize("1 + (2 * 3) + (4 * (5 + 6))");
        Assert.assertEquals(51L, evaluator.evaluate(expression));
        expression = ExpressionTokenizer.tokenize("2 * 3 + (4 * 5)");
        Assert.assertEquals(26L, evaluator.evaluate(expression));
        expression = ExpressionTokenizer.tokenize("5 + (8 * 3 + 9 + 3 * 4 * 3)");
        Assert.assertEquals(437L, evaluator.evaluate(expression));
        expression = ExpressionTokenizer.tokenize("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))");
        Assert.assertEquals(12240L, evaluator.evaluate(expression));
        expression = ExpressionTokenizer.tokenize("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2");
        Assert.assertEquals(13632L, evaluator.evaluate(expression));
    }
}
