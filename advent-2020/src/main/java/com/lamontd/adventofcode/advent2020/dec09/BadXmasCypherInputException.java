package com.lamontd.adventofcode.advent2020.dec09;

public class BadXmasCypherInputException extends Exception {
    final long badInput;
    public BadXmasCypherInputException(long badInput) {
        super("Input '" + badInput + "' does not meed the cypher conditions");
        this.badInput = badInput;
    }

    public long getBadInput() {
        return badInput;
    }
}
