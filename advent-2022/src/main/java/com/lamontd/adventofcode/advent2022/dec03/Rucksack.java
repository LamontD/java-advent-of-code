package com.lamontd.adventofcode.advent2022.dec03;

import com.lamontd.adventofcode.utils.StringCombiner;

public class Rucksack {
    private final String firstCompartment;
    private final String secondCompartment;

    public Rucksack(String input) {
        this.firstCompartment = input.substring(0, (input.length() / 2));
        this.secondCompartment = input.substring(input.length() / 2);
        if (firstCompartment.length() != secondCompartment.length()) {
            throw new IllegalArgumentException("Bad parsing of Rucksack " + input + "(length " + input.length()
                    + "), first compartment is " + firstCompartment + "(len " + firstCompartment.length()
                    + ") and second is " + secondCompartment + "(len " + secondCompartment.length() + ")");
        }
    }

    public String getEntireRucksack() {
        return firstCompartment + secondCompartment;
    }

    public String getSharedItems() {
        return StringCombiner.getCommonCharacters(firstCompartment, secondCompartment);
    }
}
