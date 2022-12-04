package com.lamontd.adventofcode.advent2020.dec19;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MonsterRuleBruteForceParser extends MonsterRuleParser {
    private static final Logger logger = LoggerFactory.getLogger(MonsterRuleBruteForceParser.class);
    private final Map<Integer, String> inputRules = new HashMap<>();
    private final Map<Integer, List<String>> parsingRules = new HashMap<>();
    private int aRule = 0;
    private int bRule = 0;
    private final List<String> ruleZero = new ArrayList<>();

    public MonsterRuleBruteForceParser() { }

    @Override
    public void addRule(Integer ruleNumber, String ruleText) {
        String replacedRuleText = ruleText.replaceAll("\"", "").trim();
        inputRules.put(ruleNumber, replacedRuleText.trim());

        if (StringUtils.equals(replacedRuleText, "a")) {
            aRule = ruleNumber;
        } else if (StringUtils.equals(replacedRuleText, "b")) {
            bRule = ruleNumber;
        } else {
            ArrayList<String> ruleList = new ArrayList<>();
            for (String piece : replacedRuleText.split("\\|")) {
                ruleList.add(piece.trim());
            }
            parsingRules.put(ruleNumber, ruleList);
        }
    }

    public void processRuleSet() {
        this.ruleZero.clear();
        this.ruleZero.addAll(evaluateForRuleZero());
    }

    @Override
    public boolean matches(String input) {
        return ruleZero.contains(input);
    }

    public List<String> evaluateForRuleZero() {
        // Substitute the a and b rules
        logger.info("Performing constant substitution...");
        substituteConstantsInParsingRules();
        logger.info("Performing rule substitution...");
        substituteRulesInParsingRules();
        logger.info("Removing spaces...");

        this.ruleZero.clear();
        for (String str : parsingRules.get(0)) {
            ruleZero.add(str.replaceAll(" ", "").trim());
        }
        logger.info("Evaluated rule zero to have " + ruleZero.size() + " rules");
        return ruleZero;
    }

    private void substituteConstantsInParsingRules() {
        String aRuleString = Integer.toString(aRule);
        String bRuleString = Integer.toString(bRule);
        for(List<String> stringList : parsingRules.values()) {
            for(ListIterator<String> strIter = stringList.listIterator(); strIter.hasNext(); ) {
                String currentString = strIter.next();
                strIter.remove();
                StringBuilder builder = new StringBuilder();
                for (String token : currentString.split(" ")) {
                    if (StringUtils.equals(token, aRuleString)) {
                        builder.append("a");
                    } else if (StringUtils.equals(token, bRuleString)) {
                        builder.append("b");
                    } else {
                        builder.append(token);
                    }
                    builder.append(" ");
                }
                strIter.add(builder.toString().trim());
            }
        }
    }

    private void substituteRulesInParsingRules() {
        boolean changeMade = true;
        while (changeMade) {
            changeMade = false;
            for (List<String> stringList : parsingRules.values()) {
                for(ListIterator<String> strIter = stringList.listIterator(); strIter.hasNext(); ) {
                    String currentString = strIter.next();
                    boolean needsSubstitution = false;
                    int ruleToSubstitute = -1;
                    for (String ruleToken : currentString.split(" ")) {
                        if (isInteger(ruleToken)) {
                            changeMade = true;
                            needsSubstitution = true;
                            ruleToSubstitute = Integer.parseInt(ruleToken);
                        }
                    }
                    if (needsSubstitution) {
                        changeMade = true;
                        strIter.remove();
                        String ruleNumberString = Integer.toString(ruleToSubstitute);
                        int beforeIndex = currentString.indexOf(ruleNumberString) - 1;
                        if (beforeIndex < 0) {
                            beforeIndex = 0;
                        }
                        String beforeString = currentString.substring(0, beforeIndex).trim();
                        String afterString = currentString.substring(currentString.indexOf(ruleNumberString) + ruleNumberString.length()).trim();
                        for (String rule : parsingRules.get(ruleToSubstitute)) {
                            StringBuilder builder = new StringBuilder();
                            builder.append(beforeString).append(" ").append(rule).append(" ").append(afterString);
                            strIter.add(builder.toString().trim());
                        }
                    }
                }
            }
        }
    }

    private static String collapseParentheses(String inputString) {
        if (!inputString.contains("(")) {
            return inputString;
        }
        int stringLength = inputString.length();
        int leftParenIndex = inputString.indexOf('(');
        int rightParenIndex = inputString.lastIndexOf(')');
        int orIndex = 0;
        int currentParenLevel = 0;
        for(int i=0; i < stringLength; i++) {
            char ch = inputString.charAt(i);
            if (ch == '(') {
                currentParenLevel++;
            } else if (ch == ')') {
                currentParenLevel--;
            } else if (ch == '|' && currentParenLevel == 1) {
                orIndex = i;
            }
        }
        String beforeString = inputString.substring(0, leftParenIndex - 1).trim();
        String leftOrString = inputString.substring(leftParenIndex+1, orIndex).trim();
        String rightOrString = inputString.substring(orIndex+1, rightParenIndex-1).trim();
        String remainingString = inputString.substring(rightParenIndex+1);

        StringBuilder builder = new StringBuilder();
        String frontHalf = builder.append(beforeString).append(" ").append(leftOrString).append(" ").append(remainingString).toString().trim();
        builder = new StringBuilder();
        String backHalf = builder.append(beforeString).append(" ").append(rightOrString).append(" ").append(remainingString).toString().trim();

        return collapseParentheses(frontHalf + " | " + backHalf);
    }

    public static String eliminateAmpersands(String inputString)
    {
        StringBuilder builder = new StringBuilder();
        for (String str : inputString.split(" ")) {
            if (!StringUtils.equals(str, "&")) {
                builder.append(str);
            }
        }
        return builder.toString();
    }

}
