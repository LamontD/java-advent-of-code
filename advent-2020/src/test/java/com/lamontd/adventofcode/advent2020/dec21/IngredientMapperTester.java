package com.lamontd.adventofcode.advent2020.dec21;

import junit.framework.Assert;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class IngredientMapperTester {
    @BeforeAll
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testDetermineTranslations() {
        IngredientMapper ingredientMapper = new IngredientMapper();
        ingredientMapper.addEntry(new IngredientEntry(1, "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)") );
        ingredientMapper.addEntry(new IngredientEntry(2, "trh fvjkl sbzzf mxmxvkd (contains dairy)"));
        ingredientMapper.addEntry(new IngredientEntry(3, "sqjhc fvjkl (contains soy)"));
        ingredientMapper.addEntry(new IngredientEntry(4, "sqjhc mxmxvkd sbzzf (contains fish)"));

        List<Translation> translations = ingredientMapper.determineTranslationsForAllergens();
        Assert.assertEquals(3, translations.size());
    }

    @Test
    public void testCountNonAllergenAppearances() {
        IngredientMapper ingredientMapper = new IngredientMapper();
        ingredientMapper.addEntry(new IngredientEntry(1, "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)") );
        ingredientMapper.addEntry(new IngredientEntry(2, "trh fvjkl sbzzf mxmxvkd (contains dairy)"));
        ingredientMapper.addEntry(new IngredientEntry(3, "sqjhc fvjkl (contains soy)"));
        ingredientMapper.addEntry(new IngredientEntry(4, "sqjhc mxmxvkd sbzzf (contains fish)"));

        Assert.assertEquals(5, ingredientMapper.countNonAllergenAppearances());
    }
}
