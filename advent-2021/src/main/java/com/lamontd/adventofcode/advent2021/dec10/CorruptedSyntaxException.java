package com.lamontd.adventofcode.advent2021.dec10;

public class CorruptedSyntaxException extends Exception {
    private SyntaxDelimiter expectedDelimiter;
    private SyntaxDelimiter actualDelimiter;
    public CorruptedSyntaxException(SyntaxDelimiter expectedDelimiter, SyntaxDelimiter actualDelimiter) {
        super("Expected " + expectedDelimiter + " but got " + actualDelimiter + " instead");
        this.expectedDelimiter = expectedDelimiter;
        this.actualDelimiter = actualDelimiter;
    }

    public SyntaxDelimiter getExpectedDelimiter() { return this.expectedDelimiter; }
    public SyntaxDelimiter getActualDelimiter() { return this.actualDelimiter; }

}
