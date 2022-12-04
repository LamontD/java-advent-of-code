package com.lamontd.adventofcode.advent2020.dec07;


import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class LuggageProcessorTester {
    @Test
    public void testBagWithNoItems() {
        ColoredLuggage luggage = HaversacksLuggageProcessor.processLuggageDescriptionLine("dotted black bags contain no other bags.");
        Assert.assertEquals("dotted black", luggage.getLuggageColor());
        Assert.assertEquals("dotted", luggage.getColorDescription());
        Assert.assertEquals("black", luggage.getBaseColor());
        Assert.assertTrue(luggage.containsNoBags());

        luggage = HaversacksLuggageProcessor.processLuggageDescriptionLine("faded blue bags contain no other bags.");
        Assert.assertEquals("faded blue", luggage.getLuggageColor());
        Assert.assertEquals("faded", luggage.getColorDescription());
        Assert.assertEquals("blue", luggage.getBaseColor());
        Assert.assertTrue(luggage.containsNoBags());
    }

    @Test
    public void testBagWithASingleContainedBag() {
        ColoredLuggage luggage = HaversacksLuggageProcessor.processLuggageDescriptionLine("bright white bags contain 1 shiny gold bag.");
        Assert.assertEquals("bright white", luggage.getLuggageColor());
        Assert.assertEquals("bright", luggage.getColorDescription());
        Assert.assertEquals("white", luggage.getBaseColor());
        Assert.assertFalse(luggage.containsNoBags());
        Assert.assertEquals(Set.of("shiny gold"), luggage.getAllContainedBagColors());
        Assert.assertEquals(1, luggage.getContainedBags("shiny gold"));
        Assert.assertEquals(0, luggage.getContainedBags("dull fuschia"));
    }

    @Test
    public void testBagWithMultipleContainedBags() {
        ColoredLuggage luggage = HaversacksLuggageProcessor.processLuggageDescriptionLine("light red bags contain 1 bright white bag, 2 muted yellow bags.");
        Assert.assertEquals("light red", luggage.getLuggageColor());
        Assert.assertFalse(luggage.containsNoBags());
        Assert.assertEquals(2, luggage.getAllContainedBagColors().size());
        Assert.assertEquals(Set.of("bright white", "muted yellow"), luggage.getAllContainedBagColors());
        Assert.assertEquals(1, luggage.getContainedBags("bright white"));
        Assert.assertEquals(2, luggage.getContainedBags("muted yellow"));

        luggage = HaversacksLuggageProcessor.processLuggageDescriptionLine("faded yellow bags contain 3 posh lime bags, 4 wavy blue bags, 3 faded crimson bags, 2 shiny lavender bags.");
        Assert.assertEquals("faded yellow", luggage.getLuggageColor());
        Assert.assertFalse(luggage.containsNoBags());
        Assert.assertEquals(4, luggage.getAllContainedBagColors().size());
        Assert.assertEquals(Set.of("posh lime", "wavy blue", "faded crimson", "shiny lavender"), luggage.getAllContainedBagColors());
        Assert.assertEquals(3, luggage.getContainedBags("posh lime"));
        Assert.assertEquals(4, luggage.getContainedBags("wavy blue"));
        Assert.assertEquals(3, luggage.getContainedBags("faded crimson"));
        Assert.assertEquals(2, luggage.getContainedBags("shiny lavender"));
    }

    @Test
    public void testLuggageProcessorWithMultipleColors() {
        HaversacksLuggageProcessor luggageProcessor = new HaversacksLuggageProcessor();
        luggageProcessor.addLuggage("light red bags contain 1 bright white bag, 2 muted yellow bags.");
        luggageProcessor.addLuggage("dark orange bags contain 3 bright white bags, 4 muted yellow bags.");
        luggageProcessor.addLuggage("bright white bags contain 1 shiny gold bag.");
        luggageProcessor.addLuggage("muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.");
        luggageProcessor.addLuggage("shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.");
        luggageProcessor.addLuggage("dark olive bags contain 3 faded blue bags, 4 dotted black bags.");
        luggageProcessor.addLuggage("vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.");
        luggageProcessor.addLuggage("faded blue bags contain no other bags.");
        luggageProcessor.addLuggage("dotted black bags contain no other bags.");
        Assert.assertTrue(luggageProcessor.containsBag("light red"));
        Assert.assertTrue(luggageProcessor.containsBag("dark orange"));
        Assert.assertTrue(luggageProcessor.containsBag("bright white"));
        Assert.assertTrue(luggageProcessor.containsBag("muted yellow"));
        Assert.assertTrue(luggageProcessor.containsBag("shiny gold"));
        Assert.assertTrue(luggageProcessor.containsBag("dark olive"));
        Assert.assertTrue(luggageProcessor.containsBag("vibrant plum"));
        Assert.assertTrue(luggageProcessor.containsBag("dotted black"));
        Assert.assertEquals(Set.of("shiny gold", "dark olive", "vibrant plum", "faded blue", "dotted black"), luggageProcessor.findAllBagsForOutermostBag("bright white"));
    }

    @Test
    public void testLuggageProcessorBagContainment() {
        HaversacksLuggageProcessor luggageProcessor = new HaversacksLuggageProcessor();
        luggageProcessor.addLuggage("light red bags contain 1 bright white bag, 2 muted yellow bags.");
        luggageProcessor.addLuggage("dark orange bags contain 3 bright white bags, 4 muted yellow bags.");
        luggageProcessor.addLuggage("bright white bags contain 1 shiny gold bag.");
        luggageProcessor.addLuggage("muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.");
        luggageProcessor.addLuggage("shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.");
        luggageProcessor.addLuggage("dark olive bags contain 3 faded blue bags, 4 dotted black bags.");
        luggageProcessor.addLuggage("vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.");
        luggageProcessor.addLuggage("faded blue bags contain no other bags.");
        luggageProcessor.addLuggage("dotted black bags contain no other bags.");
        Assert.assertTrue(luggageProcessor.bagCanContainOtherBag("bright white", "shiny gold"));
        Assert.assertTrue(luggageProcessor.bagCanContainOtherBag("muted yellow", "shiny gold"));
        Assert.assertTrue(luggageProcessor.bagCanContainOtherBag("dark orange", "shiny gold"));
        Assert.assertTrue(luggageProcessor.bagCanContainOtherBag("light red", "shiny gold"));
        Assert.assertFalse(luggageProcessor.bagCanContainOtherBag("dark olive", "shiny gold"));
    }

    @Test
    public void testRecursiveLuggageBagContainment() {
        HaversacksLuggageProcessor luggageProcessor = new HaversacksLuggageProcessor();
        luggageProcessor.addLuggage("light red bags contain 1 bright white bag, 2 muted yellow bags.");
        luggageProcessor.addLuggage("dark orange bags contain 3 bright white bags, 4 muted yellow bags.");
        luggageProcessor.addLuggage("bright white bags contain 1 shiny gold bag.");
        luggageProcessor.addLuggage("muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.");
        luggageProcessor.addLuggage("shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.");
        luggageProcessor.addLuggage("dark olive bags contain 3 faded blue bags, 4 dotted black bags.");
        luggageProcessor.addLuggage("vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.");
        luggageProcessor.addLuggage("faded blue bags contain no other bags.");
        luggageProcessor.addLuggage("dotted black bags contain no other bags.");
        Assert.assertEquals(Set.of("bright white", "muted yellow", "dark orange", "light red"), luggageProcessor.bagsThatContainColor("shiny gold"));
    }

    @Test
    public void testRecursiveCountOfContainedBags() {
        HaversacksLuggageProcessor luggageProcessor = new HaversacksLuggageProcessor();
        luggageProcessor.addLuggage("light red bags contain 1 bright white bag, 2 muted yellow bags.");
        luggageProcessor.addLuggage("dark orange bags contain 3 bright white bags, 4 muted yellow bags.");
        luggageProcessor.addLuggage("bright white bags contain 1 shiny gold bag.");
        luggageProcessor.addLuggage("muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.");
        luggageProcessor.addLuggage("shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.");
        luggageProcessor.addLuggage("dark olive bags contain 3 faded blue bags, 4 dotted black bags.");
        luggageProcessor.addLuggage("vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.");
        luggageProcessor.addLuggage("faded blue bags contain no other bags.");
        luggageProcessor.addLuggage("dotted black bags contain no other bags.");
        Assert.assertEquals(32, luggageProcessor.countOtherBagsContained("shiny gold"));
    }
}
