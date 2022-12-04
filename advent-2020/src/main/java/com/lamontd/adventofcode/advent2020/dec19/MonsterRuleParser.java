package com.lamontd.adventofcode.advent2020.dec19;

import java.util.List;

public abstract class MonsterRuleParser {
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for(; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean containsInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        for (int i=0; i < length; i++) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                return true;
            }
        }
        return false;
    }

    public void addRule(String ruleString) {
        String[] splitValues = ruleString.split(":");
        addRule(Integer.parseInt(splitValues[0]), splitValues[1].trim());
    }

    public abstract void addRule(Integer ruleNumber, String ruleText);
    public abstract boolean matches(String inputText);
    public int getValidMessageCount(List<String> inputMessages) {
        int validMessages = 0;
        for (String message : inputMessages) {
            if (matches(message)) {
                validMessages++;
            }
        }
        return validMessages;
    }


}
