package com.lamontd.adventofcode.advent2020.dec18;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ExpressionTokenizer {

    public ExpressionTokenizer() { }
    
    public static List<MathToken> tokenize(String tokenString) {
        List<MathToken> tokenList = new ArrayList<>();
        for(Iterator<String> stringIterator = Arrays.stream(tokenString.split(" ")).iterator(); stringIterator.hasNext(); ) {
            String evalString = stringIterator.next();
            if (evalString.startsWith("(")) {
                int parenDepth = StringUtils.countMatches(evalString, '(');
                StringBuilder expressionStringBuilder = new StringBuilder();
                expressionStringBuilder.append(evalString);
                while (parenDepth > 0) {
                    String nextExpr = stringIterator.next();
                    if (nextExpr.startsWith("(")) {
                        parenDepth++;
                    } else if (nextExpr.endsWith(")")) {
                        parenDepth -= StringUtils.countMatches(nextExpr, ')');
                    }
                    expressionStringBuilder.append(" ").append(nextExpr);
                }
                tokenList.add(new ParenthesisToken(tokenize(expressionStringBuilder.substring(1, expressionStringBuilder.lastIndexOf(")")))));
            } else if (MathOperator.fromRepresentation(evalString) != null) {
                tokenList.add(new MathOperatorToken(evalString));
            } else {
                tokenList.add(new LiteralValueToken(evalString));
            }
        }
        return tokenList;
    }
    
}
