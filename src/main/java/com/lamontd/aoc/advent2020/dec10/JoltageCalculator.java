package com.lamontd.aoc.advent2020.dec10;

import com.google.common.collect.Sets;
import com.lamontd.aoc.utils.BasicCounter;
import com.lamontd.aoc.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JoltageCalculator {
    private static final Logger logger = LoggerFactory.getLogger(JoltageCalculator.class);
    private static BasicCounter timesIRan = new BasicCounter();

    private final JoltageConnectorBag fullConnectorBag;
    private final JoltageAdapter maxAdapter;

    public JoltageCalculator(List<Long> inputData) {
        this.fullConnectorBag = new JoltageConnectorBag(inputData);
        this.maxAdapter = fullConnectorBag.getMaximumJoltage();
    }

    public long getValidCombinations() {
        return gatherValidCombinations(fullConnectorBag, 0).size();
    }

    public long countValidCombiations() {
        return countValidCombinations(fullConnectorBag, 0);
    }


    public static long countValidCombinations(JoltageConnectorBag connectorBag, int startIndex) {
        if (!connectorBag.canConnectEndToEnd())
            return 0;
        long validValues = 1L;
        List<Integer> canRemoveIndices = new ArrayList<>();
        for (int removeCandidate = startIndex; removeCandidate < connectorBag.getAvailableAdapters().size()-1; removeCandidate++) {
            JoltageAdapter sourceAdapter = removeCandidate == 0 ? new JoltageAdapter(0) : connectorBag.getAvailableAdapters().get(removeCandidate-1);
            if (connectorBag.getAvailableAdapters().get(removeCandidate+1).canConnectToSource(sourceAdapter))
                canRemoveIndices.add(removeCandidate);
        }

        if (canRemoveIndices.isEmpty())
            return validValues;

        timesIRan.increment();

        for (int removeIndx : canRemoveIndices) {
            List<JoltageAdapter> newAdapters = new ArrayList<>(connectorBag.getAvailableAdapters());
            newAdapters.remove(removeIndx);
            validValues += countValidCombinations(new JoltageConnectorBag(newAdapters, connectorBag.getMaximumJoltage()), removeIndx);
        }

        return validValues;
    }

    public long countValidCombinationsThroughSetTheory() {
        long validCombinations = 1L;
        Set<JoltageAdapter> baseElementSet = new HashSet<>(fullConnectorBag.getAvailableAdapters());
        Set<Set<JoltageAdapter>> potetialRemovalSet = generateCombinations(new HashSet<>(fullConnectorBag.getSuperfluousAdapters()));
        System.out.println("Checking " + potetialRemovalSet.size() + " possibilities");
        for (Set<JoltageAdapter> removalSet : potetialRemovalSet) {
            Set<JoltageAdapter> potentialAdapters = Sets.difference(baseElementSet, removalSet);
            JoltageConnectorBag sampleBag = new JoltageConnectorBag(potentialAdapters, fullConnectorBag.getMaximumJoltage());
            if (sampleBag.canConnectEndToEnd())
                validCombinations++;
        }
        return validCombinations;
    }

    public static Set<Set<JoltageAdapter>> generateCombinations(Set<JoltageAdapter> adapterSet) {
        Set<Set<JoltageAdapter>> returnSet = new HashSet<>();
        returnSet.add(adapterSet);
        Set<Long> numberSet = adapterSet.stream().map(adapter -> adapter.getJoltageRating()).collect(Collectors.toSet());
        Set<Set<Long>> numberSetOfSets = new HashSet<>();
        for (int size=1; size < numberSet.size(); size++) {
            logger.info("Generating number combinations of size " + size);
            numberSetOfSets.addAll(Sets.combinations(numberSet, size));
            logger.info("Number sets has " + numberSetOfSets.size() + " entries");
        }
        logger.info("Adapter set size is " + adapterSet.size());
        for (int size=1; size < adapterSet.size(); size++) {
            logger.info("Generating combinations of size " + size);
            returnSet.addAll(Sets.combinations(adapterSet, size));
        }
        return returnSet;
    }

    public static Set<JoltageConnectorBag> gatherValidCombinations(JoltageConnectorBag connectorBag, int startIndex) {
        Set<JoltageConnectorBag> derivateBags = new HashSet<>();
        if (!connectorBag.canConnectEndToEnd())
            return derivateBags;

        derivateBags.add(connectorBag);
//        System.out.println("Working startIndex of " + startIndex + "/" + connectorBag.getAvailableAdapters().size());

        List<Integer> canRemoveIndices = new ArrayList<>();

        for (int removeCandidate = startIndex; removeCandidate < connectorBag.getAvailableAdapters().size()-1; removeCandidate++) {
            JoltageAdapter sourceAdapter = removeCandidate == 0 ? new JoltageAdapter(0) : connectorBag.getAvailableAdapters().get(removeCandidate-1);
            if (connectorBag.getAvailableAdapters().get(removeCandidate+1).canConnectToSource(sourceAdapter))
                canRemoveIndices.add(removeCandidate);
        }
//        System.out.println("I think that I can remove " + canRemoveIndices);
        for (int indx : canRemoveIndices) {
            List<JoltageAdapter> adapterList = new ArrayList<>(connectorBag.getAvailableAdapters());
            adapterList.remove(indx);
            derivateBags.addAll(gatherValidCombinations(new JoltageConnectorBag(adapterList, connectorBag.getMaximumJoltage()), indx));
//            derivateBags.add(new JoltageConnectorBag(adapterList, connectorBag.getMaximumJoltage()));
        }
//
//        // We have something to remove.
//        for (int removeCandidate)
//
//        System.out.println("Adding bag " + connectorBag.toString());
//        if (differenceMap.containsKey(1)) {
//            for (JoltageConnectorBag bagToAdd : createBagsMissingConnectorsOfDifference(connectorBag, 1)) {
////                System.out.println("Recursing on 1-bag " + bagToAdd);
//                derivateBags.addAll(gatherValidCombinations(bagToAdd));
//            }
//        }
//
//        if (differenceMap.containsKey(2)) {
//            for (JoltageConnectorBag bagToAdd: createBagsMissingConnectorsOfDifference(connectorBag, 2)) {
////                System.out.println("Recursing on 2-bag " + bagToAdd);
//                derivateBags.addAll(gatherValidCombinations(bagToAdd));
//            }
//        }
//
        return derivateBags;

    }


    public static Set<JoltageConnectorBag> createBagsMissingConnectorsOfDifference(JoltageConnectorBag connectorBag, int connectorDifference) {
        List<JoltageAdapter> adaptersToRemove = new ArrayList<>();
        JoltageAdapter sourceAdapter = new JoltageAdapter(0);
        for (JoltageAdapter adapter : connectorBag.getAvailableAdapters()) {
            if (adapter.calculateJoltageDifference(sourceAdapter) == connectorDifference) {
                adaptersToRemove.add(adapter);
            }
            sourceAdapter = adapter;
        }

        Set<JoltageConnectorBag> bagSet = new HashSet<>();
        for (JoltageAdapter adapterToRemove : adaptersToRemove) {
            List<JoltageAdapter> adapterList = new ArrayList<>(connectorBag.getAvailableAdapters());
            adapterList.remove(adapterToRemove);
            JoltageConnectorBag newBag = new JoltageConnectorBag(adapterList, connectorBag.getMaximumJoltage());
            if (newBag.canConnectEndToEnd())
                bagSet.add(newBag);
        }
        return bagSet;
    }

    public JoltageConnectorBag getFullConnectorBag() {
        return fullConnectorBag;
    }

    public JoltageAdapter getMaxAdapter() {
        return maxAdapter;
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to the Joltage Calculator!");
        logger.info("Reading in the input...");
        LocalResourceInput resourceInput = new LocalResourceInput("advent2020/day10/advent-day10-input.txt");
        List<Long> inputJoltages = resourceInput.getInput().stream().map(Long::parseLong).collect(Collectors.toList());
        logger.info("Found " + inputJoltages.size() + " adapters in my bag");
        JoltageCalculator calculator = new JoltageCalculator(inputJoltages);
        logger.info("Part 1: Find the difference msap");
        Map<Integer, BasicCounter> differenceMap = calculator.fullConnectorBag.calculateDifferenceMap();
        logger.info("1 Jolt differences [" + differenceMap.get(1).currentValue() + "] * 3-jolt differences " + differenceMap.get(3).currentValue()
                + " == " + (differenceMap.get(1).currentValue() * differenceMap.get(3).currentValue()));
        logger.info("Part 2: Finding all of the valid combinations...");

        JoltageCalculator calculator2 = new JoltageCalculator(List.of(28L, 33L, 18L, 42L, 31L, 14L, 46L, 20L, 48L, 47L, 24L, 23L, 49L, 45L, 19L, 38L, 39L, 11L, 1L, 32L, 25L, 35L, 8L, 17L, 7L, 9L, 4L, 2L, 34L, 10L, 3L));
        logger.info("Small fry this one I see " + calculator2.countValidCombiations() + " valid combinations");
        Map<Integer, BasicCounter> differenceMap2 = calculator2.fullConnectorBag.calculateDifferenceMap();
        logger.info("1 Jolt differences [" + differenceMap2.get(1).currentValue() + "] * 3-jolt differences " + differenceMap2.get(3).currentValue()
                + " == " + (differenceMap2.get(1).currentValue() * differenceMap2.get(3).currentValue()));
        logger.info("To find that I ran " + timesIRan.currentValue() + " times");
        logger.info("Small fry set theory I see " + calculator2.countValidCombinationsThroughSetTheory() + " valid combos");

        long validCombinations = calculator.countValidCombinationsThroughSetTheory();
        logger.info("For the big test I see " + validCombinations + " possible valid combinations");
        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");
    }

}
