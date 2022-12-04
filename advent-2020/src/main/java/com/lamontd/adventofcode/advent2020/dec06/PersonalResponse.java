package com.lamontd.adventofcode.advent2020.dec06;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonalResponse {
    private final String rawInput;
    private final Map<Character, Boolean> answerMap;
    private static final Character MIN_QUESTION = 'a';
    private static final Character MAX_QUESTION = 'z';

    public PersonalResponse(String rawInput) {
        this.rawInput = rawInput;
        this.answerMap = new HashMap<>(26);
        for(char ch = MIN_QUESTION; ch <= MAX_QUESTION; ch++) {
            answerMap.put(ch, false);
        }
        for(char yesAnswer : this.rawInput.toCharArray()) {
            answerMap.put(yesAnswer, true);
        }

    }

    public boolean getAnswer(Character question) {
        return question >= MIN_QUESTION && question <= MAX_QUESTION
                && answerMap.get(question);
    }

    public Set<Character> getYesAnswers() {
        return this.rawInput.chars().mapToObj(ch -> (char) ch).collect(Collectors.toSet());
    }
}
