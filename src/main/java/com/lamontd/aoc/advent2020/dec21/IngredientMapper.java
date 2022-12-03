package com.lamontd.aoc.advent2020.dec21;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class IngredientMapper {
    private static final Logger logger = LoggerFactory.getLogger(IngredientMapper.class);

    private final Map<Integer, IngredientEntry> numberedFoodLines = new HashMap<>();
    private final Map<String, List<Integer>> nativeIngredientsByLine = new HashMap<>();
    private final Map<String, List<Integer>> allergensByLine = new HashMap<>();

    public void addEntry(IngredientEntry entry) {
        numberedFoodLines.put(entry.getLineNumber(), entry);
        for (String nativeIngredient : entry.getNativeIngredients()) {
            if (!nativeIngredientsByLine.containsKey(nativeIngredient)) {
                nativeIngredientsByLine.put(nativeIngredient, new ArrayList<>());
            }
            nativeIngredientsByLine.get(nativeIngredient).add(entry.getLineNumber());
        }

        for (String allergen : entry.getAllergens()) {
            if (!allergensByLine.containsKey(allergen)) {
                allergensByLine.put(allergen, new ArrayList<>());
            }
            allergensByLine.get(allergen).add(entry.getLineNumber());
        }
    }

    public List<Translation> determineTranslationsForAllergens() {
        List<Translation> translationsFound = new ArrayList<>();
        Map<String, TreeSet<String>> potentialAllergenTranslations = createPotentialAllergenTranslationMap();
        while(potentialAllergenTranslations.size() > 0) {
            for (Map.Entry<String, TreeSet<String>> potentialTranslation : potentialAllergenTranslations.entrySet()) {
                if (potentialTranslation.getValue().size() == 1) {
                    translationsFound.add(new Translation.Builder()
                            .englishText(potentialTranslation.getKey())
                            .nativeText(potentialTranslation.getValue().first()).build());
                }
            }

            for (Translation translation : translationsFound) {
                potentialAllergenTranslations.remove(translation.getEnglishText());
                for (Set<String> nativeEntries : potentialAllergenTranslations.values()) {
                    nativeEntries.remove(translation.getNativeText());
                }
            }
        }
        return translationsFound;
    }

    public int countNonAllergenAppearances() {
        // First, determine the translations
        List<Translation> translations = determineTranslationsForAllergens();
        final Map<String, String> translationMap = new HashMap<>();
        translations.forEach(translation -> translationMap.put(translation.getNativeText(), translation.getNativeText()));
        int nonAllergenAppearances = 0;
        for (Map.Entry<String, List<Integer>> nativeIngredientLines: nativeIngredientsByLine.entrySet()) {
            if (!translationMap.containsKey(nativeIngredientLines.getKey())) {
                nonAllergenAppearances += nativeIngredientLines.getValue().size();
                logger.info("Found untranslated word <" + nativeIngredientLines.getKey() + "> on lines " + nativeIngredientLines.getValue());
            }
        }
        return nonAllergenAppearances;
    }

    public String getCanonicalDangerousIngredientList() {
        List<Translation> translations = determineTranslationsForAllergens();
        final Map<String, String> dangerousIngredientMap = new HashMap<>();
        for (Translation translation : translations) {
            dangerousIngredientMap.put(translation.getEnglishText(), translation.getNativeText());
        }
        List<String> dangerousIngredientList = new ArrayList<>(dangerousIngredientMap.keySet());
        Collections.sort(dangerousIngredientList);
        final StringBuilder builder = new StringBuilder();
        dangerousIngredientList.forEach(str -> builder.append(",").append(dangerousIngredientMap.get(str)));
        return builder.substring(1);
    }

    public Map<String, TreeSet<String>> createPotentialAllergenTranslationMap() {
        final Map<String, TreeSet<String>> allergensMap = new HashMap<>();
        for(Map.Entry<String, List<Integer>> allergenEntry : allergensByLine.entrySet()) {
            for (int ingredientLine : allergenEntry.getValue()) {
                Set<String> nativeIngredients = numberedFoodLines.get(ingredientLine).getNativeIngredients();
                if (!allergensMap.containsKey(allergenEntry.getKey())) {
                    allergensMap.put(allergenEntry.getKey(), new TreeSet<>(nativeIngredients));
                } else {
                    allergensMap.get(allergenEntry.getKey()).retainAll(nativeIngredients);
                }
            }
        }
        return allergensMap;
    }
}
