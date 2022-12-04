package com.lamontd.adventofcode.advent2020.dec07;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ColoredLuggage {
    private String luggageColor;
    private String colorDescription;
    private String baseColor;
    private Map<String, Integer> containedBags;

    public ColoredLuggage(String luggageColor) {
        this.luggageColor = luggageColor;
        if (luggageColor.contains(" ")) {
            String[] colorSplit = luggageColor.split(" ");
            this.colorDescription = colorSplit[0];
            this.baseColor = colorSplit[1];
        }
        this.containedBags = new HashMap<>();
    }

    public void addContainedBags(String containedLuggageColor, int luggageCount) {
        this.containedBags.put(containedLuggageColor, luggageCount);
    }

    public boolean containsBags(String containedLuggageColor) {
        return containedBags.containsKey(containedLuggageColor);
    }
    public boolean containsNoBags() { return containedBags.isEmpty(); }
    public int getContainedBags(String containedLuggageColor) {
        return containedBags.containsKey(containedLuggageColor) ? containedBags.get(containedLuggageColor) : 0;
    }
    public String getLuggageColor() { return luggageColor; }
    public String getColorDescription() { return colorDescription; }
    public String getBaseColor() { return baseColor; }
    public Map<String, Integer> getContainedBags() { return containedBags; }
    public Set<String> getAllContainedBagColors() { return containedBags.keySet(); }
}
