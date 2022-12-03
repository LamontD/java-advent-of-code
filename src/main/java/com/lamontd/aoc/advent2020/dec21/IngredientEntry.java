package com.lamontd.aoc.advent2020.dec21;

import java.util.HashSet;
import java.util.Set;

public class IngredientEntry {
    private final int lineNumber;
    private final String rawIngredients;
    private final Set<String> nativeIngredients = new HashSet<>();
    private final Set<String> allergens = new HashSet<>();

    public IngredientEntry(int lineNumber, String rawIngredients) {
        this.rawIngredients = rawIngredients;
        this.lineNumber = lineNumber;

        if (rawIngredients.contains("(contains ")) {
            for (String basicIngredient : rawIngredients.substring(0, rawIngredients.indexOf("(")).trim().split(" ")) {
                nativeIngredients.add(basicIngredient.trim());
            }
            for (String allergen : rawIngredients.substring(rawIngredients.indexOf("(")+10, rawIngredients.indexOf(")")).split(" ")) {
                allergens.add(allergen.replaceAll(",", ""));
            }
        }
    }

    public boolean containsAllergen(String allergen) { return allergen.contains(allergen); }
    public boolean containsNativeIngredient(String nativeIngredient) { return nativeIngredients.contains(nativeIngredient); }

    public int getLineNumber() { return lineNumber; }

    public String getRawIngredients() { return rawIngredients; }

    public Set<String> getNativeIngredients() { return nativeIngredients; }

    public Set<String> getAllergens() { return allergens; }
}
