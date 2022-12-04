package com.lamontd.adventofcode.advent2021.dec14;

import com.lamontd.adventofcode.utils.LongCounter;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class SplittingPolymer {
    private static final Logger logger = LoggerFactory.getLogger(SplittingPolymer.class);
    private String polymerTemplate;
    private SplitType splitType = SplitType.NEITHER;
    final private Map<String, String> pairInsertionRules;
    private int currentStep = 0;


    public SplittingPolymer(String template, Map<String, String> processingRules) {
        this.polymerTemplate = template;
        this.pairInsertionRules = processingRules;
    }

    public List<Pair<Character, Long>> getFrequenciesAfterStep(int stepToReach) {
        final Map<Character, LongCounter> aggregateMap = new HashMap<>();
        performPairInsertionSteps(stepToReach - currentStep);
        generateFrequencyMap().forEach((key, value) -> {
            if (!aggregateMap.containsKey(key)) {
                aggregateMap.put(key, new LongCounter());
            }
            aggregateMap.get(key).increment(value.currentValue());
        });
        List<Pair<Character, Long>> frequencies = new ArrayList<>();
                aggregateMap.entrySet().stream().forEach(entry -> frequencies.add(Pair.with(entry.getKey(), entry.getValue().currentValue())));
        frequencies.sort(new Comparator<Pair<Character, Long>>() {
            @Override
            public int compare(Pair<Character, Long> o1, Pair<Character, Long> o2) {
                return Long.compare(o1.getValue1(), o2.getValue1());
            }
        });

        return frequencies;
    }

    protected void performPairInsertionSteps(int numStepsToPerform) {
        for (int i=0; i < numStepsToPerform; i++) {
            currentStep++;
            List<Character> templateChars = polymerTemplate.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
            for (int index = 0; index + 1 < templateChars.size(); ) {
                String pair = String.valueOf(templateChars.get(index)) + templateChars.get(index + 1);
//            String pair = new StringBuilder().append(templateChars.get(index)).append(templateChars.get(index+1)).toString();
                if (pairInsertionRules.containsKey(pair)) {
                    templateChars.add(index + 1, pairInsertionRules.get(pair).charAt(0));
                    index = index + 2;
                } else {
                    index = index + 1;
                }
            }
            final StringBuilder sb = new StringBuilder();
            templateChars.forEach(sb::append);
//        templateChars.stream().forEach(ch -> sb.append(ch));
            this.polymerTemplate = sb.toString();
            logger.info("Step " + String.format("%3d", currentStep) + " completed");
        }
    }

    protected Map<Character, LongCounter> generateFrequencyMap() {
        final Map<Character, LongCounter> frequencyMap = new HashMap<>();
        polymerTemplate.chars().mapToObj(ch -> (char)ch).forEach(ch -> {
            if (!frequencyMap.containsKey(ch)) {
                frequencyMap.put(ch, new LongCounter());
            }
            frequencyMap.get(ch).increment();
        });
        return frequencyMap;
    }

    @Override
    public String toString() {
        return "After step " + currentStep + ": " + polymerTemplate;
    }

    public int getCurrentStep() { return currentStep; }
    public String getPolymerTemplate() { return polymerTemplate; }

    public enum SplitType { FIRST, LAST, NEITHER; }
}
