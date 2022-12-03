package com.lamontd.aoc.advent2021.dec10;

public enum SyntaxDelimiter {
    OPEN_PAREN('(', true),
    CLOSE_PAREN(')', false),
    OPEN_BRACKET('[', true),
    CLOSE_BRACKET(']', false),
    OPEN_CURLYBRACE('{', true),
    CLOSE_CURLYBRACE('}', false),
    LESS_THAN('<', true),
    GREATER_THAN('>', false);
    private char value;
    private boolean opener;
    private SyntaxDelimiter(char value, boolean opener) {
        this.value = value;
        this.opener = opener;
    }
    public char getValue() { return value; }
    public boolean isOpener() { return opener; }

    public static SyntaxDelimiter findDelimiter(char value) {
        for (SyntaxDelimiter delimiter : values()) {
            if (delimiter.value == value) {
                return delimiter;
            }
        }
        return null;
    }
}
