package com.lamontd.aoc.advent2020.dec18;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ExpressionTokenizerTester {
    @Test
    public void testBasicExpressionTokenizer() {
        List<MathToken> mathTokenList = ExpressionTokenizer.tokenize("1 + 2 * 3 + 4 * 5 + 6");
        Assert.assertEquals(11, mathTokenList.size());
        ListIterator<MathToken> tokenIter = mathTokenList.listIterator();
        Assert.assertTrue(tokenIter.next() instanceof LiteralValueToken);
        Assert.assertEquals(MathOperator.ADD, ((MathOperatorToken)tokenIter.next()).getMathOperator());
        Assert.assertTrue(tokenIter.next() instanceof LiteralValueToken);
        Assert.assertEquals(MathOperator.MULTIPLY, ((MathOperatorToken)tokenIter.next()).getMathOperator());
        Assert.assertTrue(tokenIter.next() instanceof LiteralValueToken);
        Assert.assertEquals(MathOperator.ADD, ((MathOperatorToken)tokenIter.next()).getMathOperator());
        Assert.assertTrue(tokenIter.next() instanceof LiteralValueToken);
        Assert.assertEquals(MathOperator.MULTIPLY, ((MathOperatorToken)tokenIter.next()).getMathOperator());
        Assert.assertTrue(tokenIter.next() instanceof LiteralValueToken);
        Assert.assertEquals(MathOperator.ADD, ((MathOperatorToken)tokenIter.next()).getMathOperator());
        Assert.assertTrue(tokenIter.next() instanceof LiteralValueToken);
    }

    @Test
    public void testParensExpressionTokenizer() {
        List<MathToken> tokenList = ExpressionTokenizer.tokenize("1 + (2 * 3) + (4 * (5 + 6))");
        Assert.assertEquals(5, tokenList.size());
        ListIterator<MathToken> iter = tokenList.listIterator();
        Assert.assertTrue(iter.next() instanceof LiteralValueToken);
        Assert.assertEquals(MathOperator.ADD, ((MathOperatorToken)iter.next()).getMathOperator());
        Assert.assertEquals(3, ((ParenthesisToken)iter.next()).getTokenList().size());
        Assert.assertEquals(MathOperator.ADD, ((MathOperatorToken)iter.next()).getMathOperator());
        Assert.assertEquals(3, ((ParenthesisToken)iter.next()).getTokenList().size());

        ParenthesisToken firstParens = (ParenthesisToken)tokenList.get(2);
        Assert.assertEquals(3, firstParens.getTokenList().size());
        Iterator<MathToken> parenIter = firstParens.getTokenList().iterator();
        Assert.assertTrue(parenIter.next() instanceof LiteralValueToken);
        Assert.assertEquals(MathOperator.MULTIPLY, ((MathOperatorToken)parenIter.next()).getMathOperator());
        Assert.assertTrue(parenIter.next() instanceof LiteralValueToken);

        ParenthesisToken secondParens = (ParenthesisToken)tokenList.get(4);
        Assert.assertEquals(3, secondParens.getTokenList().size());
        parenIter = secondParens.getTokenList().iterator();
        Assert.assertTrue(parenIter.next() instanceof LiteralValueToken);
        Assert.assertEquals(MathOperator.MULTIPLY, ((MathOperatorToken)parenIter.next()).getMathOperator());
        Assert.assertTrue(parenIter.next() instanceof ParenthesisToken);

    }
}
