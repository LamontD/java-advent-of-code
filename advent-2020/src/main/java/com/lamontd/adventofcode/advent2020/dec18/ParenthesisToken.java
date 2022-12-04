package com.lamontd.adventofcode.advent2020.dec18;

import java.util.List;

public class ParenthesisToken implements MathToken {
    private List<MathToken> tokenList;

    public ParenthesisToken(List<MathToken> tokenList) {
        this.tokenList = tokenList;
    }

    public static int getParenDepth(String expression) {
        int parenDepth = 0;
        for (char ch : expression.toCharArray()) {
            switch (ch) {
                case '(':
                    parenDepth++;
                    break;
                case ')':
                    parenDepth--;
                    break;
            }
        }
        return parenDepth;
    }

    public List<MathToken> getTokenList() { return tokenList; }

    @Override
    public boolean isValid() {
        return !tokenList.isEmpty();
    }
}

