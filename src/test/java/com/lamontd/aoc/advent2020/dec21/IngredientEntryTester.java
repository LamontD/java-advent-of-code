package com.lamontd.aoc.advent2020.dec21;

import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

public class IngredientEntryTester {
    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testIngredientEntryConstruction() {
        IngredientEntry entry = new IngredientEntry(1, "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)");
        Assert.assertEquals(Set.of("mxmxvkd", "kfcds", "sqjhc", "nhms"), entry.getNativeIngredients());
        Assert.assertEquals(Set.of("dairy", "fish"), entry.getAllergens());

        entry = new IngredientEntry(2, "trh fvjkl sbzzf mxmxvkd (contains dairy)");
        Assert.assertEquals(Set.of("trh", "fvjkl", "sbzzf", "mxmxvkd"), entry.getNativeIngredients());
        Assert.assertEquals(Set.of("dairy"), entry.getAllergens());

        entry = new IngredientEntry(3, "sqjhc fvjkl (contains soy)");
        Assert.assertEquals(Set.of("sqjhc", "fvjkl"), entry.getNativeIngredients());
        Assert.assertEquals(Set.of("soy"), entry.getAllergens());

        entry = new IngredientEntry(4, "sqjhc mxmxvkd sbzzf (contains fish)");
        Assert.assertEquals(Set.of ("sqjhc", "mxmxvkd", "sbzzf"), entry.getNativeIngredients());
        Assert.assertEquals(Set.of("fish"), entry.getAllergens());
    }
}
