package com.lamontd.aoc.advent2020.dec19;

public class MonsterParserResponse {
    final boolean response;
    final int charactersEaten;
    int failedRule = 0;

    public MonsterParserResponse(boolean response, int charactersEaten) {
        this.response = response;
        this.charactersEaten = charactersEaten;
    }

    public boolean getResponse() {
        return response;
    }

    public int getCharactersEaten() {
        return charactersEaten;
    }

    public void setFailedRule(int failedRule) { this.failedRule = failedRule; }

    public int getFailedRule() { return failedRule; }

    public static MonsterParserResponse createFalse(int failedRule) {
        MonsterParserResponse response = new MonsterParserResponse(false, 0);
        response.setFailedRule(failedRule);
        return response;
    }

    public static MonsterParserResponse createTrue(int charactersEaten) {
        return new MonsterParserResponse(true, charactersEaten);
    }

    @Override public String toString() {
        return response
                ? "Passed (eaten " + charactersEaten + " characters)"
                : "Failed Rule #" + failedRule;
    }
}
