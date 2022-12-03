package com.lamontd.aoc.advent2020.dec02;

public class PasswordRule {
    private final int minimum;
    private final int maximum;
    private final Character requiredCharacter;


    public PasswordRule(int minimum, int maximum, Character requiredCharacter) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.requiredCharacter = requiredCharacter;
    }

    public boolean isPasswordValidByOriginalRule(String password) {
        if (this.minimum == 0)
            return true;
        if (this.maximum > password.length())
            return false;
        long charsThatMatch = password.chars().filter(ch -> ch == requiredCharacter).count();
        return charsThatMatch >= this.minimum && charsThatMatch <= this.maximum;
    }

    public boolean isPasswordValidByModifiedRule(String password) {
        if (this.maximum > password.length()) {
            return false;
        }

        boolean minIsTrue = password.charAt(this.minimum - 1) == this.requiredCharacter;
        boolean maxIsTrue = password.charAt(this.maximum - 1) == this.requiredCharacter;
        return minIsTrue ^ maxIsTrue;
    }
}
