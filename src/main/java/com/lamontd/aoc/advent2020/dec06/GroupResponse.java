package com.lamontd.aoc.advent2020.dec06;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupResponse {
    private final List<PersonalResponse> individualResponses;
    public GroupResponse() {
        this.individualResponses = new ArrayList<>();
    }

    public void addResponseLine(String responseLine) {
        this.individualResponses.add(new PersonalResponse(responseLine));
    }

    public int getGroupSize() { return individualResponses.size(); }

    public Set<Character> getUnionOfYesAnswers() {
        final Set<Character> combinedYesAnswers = new HashSet<>();
        this.individualResponses.forEach(personalResponse -> combinedYesAnswers.addAll(personalResponse.getYesAnswers()));
        return combinedYesAnswers;
    }

    public Set<Character> getIntersectionOfYesAnswers() {
        final Set<Character> intersectionOfYesAnswers = getUnionOfYesAnswers();
        this.individualResponses.forEach(personalResponse -> intersectionOfYesAnswers.retainAll(personalResponse.getYesAnswers()));
        return intersectionOfYesAnswers;
    }
}
