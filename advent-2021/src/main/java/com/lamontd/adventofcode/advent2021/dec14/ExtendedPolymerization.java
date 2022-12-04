package com.lamontd.adventofcode.advent2021.dec14;

import com.lamontd.adventofcode.utils.BasicCounter;
import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ExtendedPolymerization {
    private static final Logger logger = LoggerFactory.getLogger(ExtendedPolymerization.class);

    private Map<String, String> pairInsertionRules = new HashMap<>();
    private String originalTemplate;
    private String polymerTemplate;
    private int currentStep = 0;

    public ExtendedPolymerization(String inputFile) throws IOException {
        LocalResourceInput resourceInput = new LocalResourceInput(inputFile);
        this.polymerTemplate = resourceInput.getInput().get(0);
        this.originalTemplate = polymerTemplate;

        for (int index=2; index < resourceInput.getInput().size(); index++) {
            String[] insertionRule = resourceInput.getInput().get(index).split(" -> ");
            this.pairInsertionRules.put(insertionRule[0], insertionRule[1]);
        }
    }

    public int getPartOneQuantity() {
        List<Pair<Character, Integer>> polymerFrequency = calculatePolymerFrequency();
        return polymerFrequency.get(polymerFrequency.size()-1).getValue1() - polymerFrequency.get(0).getValue1();
    }

    public List<Pair<Character, Integer>> calculatePolymerFrequency() {
        final Map<Character, BasicCounter> frequencyMap = new HashMap<>();
        polymerTemplate.chars().mapToObj(ch -> (char)ch).forEach(ch -> {
            if (!frequencyMap.containsKey(ch)) {
                frequencyMap.put(ch, new BasicCounter());
            }
            frequencyMap.get(ch).increment();
        });
        List<Pair<Character, Integer>> polymerFrequency = new ArrayList<>();
        frequencyMap.entrySet().stream().forEach(entry -> polymerFrequency.add(Pair.with(entry.getKey(), entry.getValue().currentValue())));
        Collections.sort(polymerFrequency, new Comparator<Pair<Character, Integer>>() {
            @Override
            public int compare(Pair<Character, Integer> o1, Pair<Character, Integer> o2) {
                return Integer.compare(o1.getValue1(), o2.getValue1());
            }
        });
        return polymerFrequency;
    }

    public void fastForwardToStep(int stepNumber) {
        while (currentStep < stepNumber) {
            performStepOperation();
        }
    }

    public void performStepOperation() {
        currentStep++;
        List<Character> templateChars = polymerTemplate.chars().mapToObj(e -> (char)e).collect(Collectors.toList());
        for (int index = 0; index+1 < templateChars.size(); ) {
            String pair = new StringBuilder().append(templateChars.get(index)).append(templateChars.get(index+1)).toString();
            if (pairInsertionRules.containsKey(pair)) {
                templateChars.add(index+1, pairInsertionRules.get(pair).charAt(0));
                index = index+2;
            }
            else {
                index = index+1;
            }
        }
        final StringBuilder sb = new StringBuilder();
        templateChars.stream().forEach(ch -> sb.append(ch));
        this.polymerTemplate = sb.toString();
        logger.info("Step " + String.format("%3d", currentStep) + " completed");
    }

    public void tryRecursivePolymer(int stepsToRun) {
        logger.info("Creating a recursive polymer");
        RecursivePolymer recursivePolymer = new RecursivePolymer(originalTemplate, pairInsertionRules);
        logger.info("Recursing to step " + stepsToRun);
        List<Pair<Character, Long>> frequencies = recursivePolymer.getFrequenciesAfterSteps(stepsToRun);
        logger.info("Recursive frequency map is " + frequencies);
        logger.info("Recursive quantity is " + (frequencies.get(frequencies.size()-1).getValue1() - frequencies.get(0).getValue1()));
        recursivePolymer.showLakeStats();
    }

    public void trySplittingPolymer(int stepsToRun) {
        logger.info("Creating a splitting polymer");
        SplittingPolymer splittingPolymer = new SplittingPolymer(originalTemplate, pairInsertionRules);
        logger.info("Recursing to step " + stepsToRun);
        List<Pair<Character, Long>> freq = splittingPolymer.getFrequenciesAfterStep(stepsToRun);
        logger.info("Splitting frequency map is " + freq);
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Extended Polymerization");
        logger.info("loading the sample...");
        ExtendedPolymerization samplePolymer = new ExtendedPolymerization("day14/sample.txt");
        samplePolymer.fastForwardToStep(2);
        logger.info("Frequency map is " + samplePolymer.calculatePolymerFrequency());
        logger.info("Part 1 quantity is " + samplePolymer.getPartOneQuantity());
        logger.info("Response is " + samplePolymer.polymerTemplate);
        samplePolymer.tryRecursivePolymer(2);
        samplePolymer.fastForwardToStep(10);
        logger.info("Frequency map is " + samplePolymer.calculatePolymerFrequency());
        logger.info("Part 1 quantity is " + samplePolymer.getPartOneQuantity());
        samplePolymer.tryRecursivePolymer(10);


        logger.info("Running Part 1 against real data");
        ExtendedPolymerization inputPolymer = new ExtendedPolymerization("day14/input.txt");
        inputPolymer.tryRecursivePolymer(10);

        logger.info("Now with real input data!");
        inputPolymer.tryRecursivePolymer(40);
        timer.finish();
    }
}
