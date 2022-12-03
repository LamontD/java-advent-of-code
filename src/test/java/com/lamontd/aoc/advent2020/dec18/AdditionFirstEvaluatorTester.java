package com.lamontd.aoc.advent2020.dec18;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AdditionFirstEvaluatorTester {
    @Test
    public void testBasicExpressionEvaluation() {
        MathHomeworkEvaluator evaluator = new AdditionFirstEvaluator();
        List<MathToken> basicExpression = ExpressionTokenizer.tokenize("2 + 4");
        Assert.assertEquals(6L, evaluator.evaluate(basicExpression));

        basicExpression = ExpressionTokenizer.tokenize("1 + 2 * 3 + 4 * 5 + 6");
        Assert.assertEquals(231L, evaluator.evaluate(basicExpression));
    }

    @Test
    public void testNestedExpressionEvaluation() {
        MathHomeworkEvaluator evaluator = new AdditionFirstEvaluator();
        List<MathToken> expression = ExpressionTokenizer.tokenize("1 + (2 * 3) + (4 * (5 + 6))");
        Assert.assertEquals(51L, evaluator.evaluate(expression));
        expression = ExpressionTokenizer.tokenize("2 * 3 + (4 * 5)");
        Assert.assertEquals(46L, evaluator.evaluate(expression));
        expression = ExpressionTokenizer.tokenize("5 + (8 * 3 + 9 + 3 * 4 * 3)");
        Assert.assertEquals(1445L, evaluator.evaluate(expression));
        expression = ExpressionTokenizer.tokenize("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))");
        Assert.assertEquals(669060L, evaluator.evaluate(expression));
        expression = ExpressionTokenizer.tokenize("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2");
        Assert.assertEquals(23340L, evaluator.evaluate(expression));
    }
}
