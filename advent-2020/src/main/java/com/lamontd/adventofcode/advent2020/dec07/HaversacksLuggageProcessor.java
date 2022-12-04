package com.lamontd.adventofcode.advent2020.dec07;

import com.lamontd.adventofcode.utils.BasicCounter;
import com.lamontd.adventofcode.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HaversacksLuggageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(HaversacksLuggageProcessor.class);
    private static final Pattern BAG_COUNT_PATTERN = Pattern.compile("(\\d+) (.*) bag");
    private static final Pattern LINE_MATCHING_PATTERN = Pattern.compile("(.*) bags contain (.*)\\.");
    private static final String NO_OTHER_BAGS = "no other bags";

    private final Map<String, ColoredLuggage> luggageMap = new HashMap<>();

    public void addLuggage(ColoredLuggage luggage) { luggageMap.put(luggage.getLuggageColor(), luggage); }
    public void addLuggage(String luggageLineDescription) {
        addLuggage(processLuggageDescriptionLine(luggageLineDescription));
    }

    public Set<String> findAllBagsForOutermostBag(String outermostLuggageColor) {
        final Set<String> bagColors = new HashSet<>();
        if (luggageMap.containsKey(outermostLuggageColor)) {
            ColoredLuggage luggage = luggageMap.get(outermostLuggageColor);
            luggage.getAllContainedBagColors().forEach(color -> {
                bagColors.add(color);
                bagColors.addAll(findAllBagsForOutermostBag(color));
            });
        }
        return bagColors;
    }

    public boolean bagCanContainOtherBag(String outerBag, String searchBag) {
        if (luggageMap.containsKey(outerBag)) {
            ColoredLuggage luggage = luggageMap.get(outerBag);
            if (luggage.containsBags(searchBag)) {
                return true;
            }
            return findAllBagsForOutermostBag(outerBag).contains(searchBag);
        }
        return false;
    }

    public Set<String> bagsThatContainColor(String color) {
        final Set<String> containingBags = new HashSet<>();
        for (String searchColor : luggageMap.keySet()) {
            if (bagCanContainOtherBag(searchColor, color)) {
                containingBags.add(searchColor);
            }
        }
        return containingBags;
    }

    public boolean containsBag(String bagColor) {
        return luggageMap.containsKey(bagColor);
    }

    public int countOtherBagsContained(String bagColor) {
        final BasicCounter otherBagsContained = new BasicCounter();
        if (luggageMap.containsKey(bagColor)) {
            ColoredLuggage luggage = luggageMap.get(bagColor);
            for (Map.Entry<String, Integer> containedEntry : luggage.getContainedBags().entrySet()) {
                otherBagsContained.increment(containedEntry.getValue());
                otherBagsContained.increment(containedEntry.getValue() * countOtherBagsContained(containedEntry.getKey()));
            }
        }
        return otherBagsContained.currentValue();
    }

    public static List<Pair<String, Integer>> processRemainingLineItems(String remainingLineItems) {
        List<Pair<String, Integer>> lineItems = new ArrayList<>();
        for(String item : remainingLineItems.split(", ")) {
            Matcher matcher = BAG_COUNT_PATTERN.matcher(item);
            if (matcher.find()) {
                String bagColor = matcher.group(2).trim();
                Integer bagCount = Integer.valueOf(matcher.group(1).trim());
                lineItems.add(new Pair<>(bagColor, bagCount));
            } else if (NO_OTHER_BAGS.equals(item)) {
                // This is fine; no other bags...
            } else {
                throw new IllegalArgumentException("Unexpected remaining line item: " + item);
            }
        }
        return lineItems;
    }

    public static ColoredLuggage processLuggageDescriptionLine(String descriptionLine) {
        Matcher lineMatcher = LINE_MATCHING_PATTERN.matcher(descriptionLine);
        if (lineMatcher.find()) {
            String mainColor = lineMatcher.group(1);
            String remainingStringLine = lineMatcher.group(2);
            final ColoredLuggage luggage = new ColoredLuggage(mainColor);
            List<Pair<String, Integer>> remainingLine = processRemainingLineItems(remainingStringLine);
            remainingLine.forEach(luggagePair -> luggage.addContainedBags(luggagePair.getValue0(), luggagePair.getValue1()));
            return luggage;
        }

        throw new IllegalArgumentException("Description line does not contain base elements: " + descriptionLine);
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to the Haversacks Luggage Processor!");
        logger.info("Reading input data...");
        HaversacksLuggageProcessor luggageProcessor = new HaversacksLuggageProcessor();
        LocalResourceInput resourceInput = new LocalResourceInput("day7/advent-day7-input.txt");
        for (String luggageLine : resourceInput.getInput()){
            luggageProcessor.addLuggage(luggageLine);
        }
        logger.info("Read in " + luggageProcessor.luggageMap.size() + " lines of luggage data");

        Set<String> bagsThatContainShinyGold = luggageProcessor.bagsThatContainColor("shiny gold");
        logger.info("I found " + bagsThatContainShinyGold.size() + " bags that can contain shiny gold");
        logger.info("My shiny gold bag contains " + luggageProcessor.countOtherBagsContained("shiny gold") + " other bags!");
        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");
    }

}
