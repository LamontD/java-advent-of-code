package com.lamontd.adventofcode.advent2021.dec14;

import com.lamontd.adventofcode.utils.LongCounter;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class RecursivePolymer {
    private static final Logger logger = LoggerFactory.getLogger(RecursivePolymer.class);

    private Map<Pair<String, Integer>, Map<Character, LongCounter>> answerLake = new HashMap<>();
    private final String initialPolymerTemplate;
    private final Map<String, String> pairInsertionRules;
    private long lakeHits = 0;

    public RecursivePolymer(String initialTemplate, Map<String, String> pairInsertionRules) {
        this.initialPolymerTemplate = initialTemplate;
        this.pairInsertionRules = pairInsertionRules;
    }

    public List<Pair<Character, Long>> getFrequenciesAfterSteps(int stepsToRun) {
        List<RecursiveRunner> runners = new ArrayList<>();
        int index;
        for (index=0; index + 1 < initialPolymerTemplate.length(); index++) {
            String stringPair = initialPolymerTemplate.substring(index, index+2);
            runners.add(new RecursiveRunner(stringPair, 0));
        }
        if (index < initialPolymerTemplate.length() - 1) {
            runners.add(new RecursiveRunner(initialPolymerTemplate.substring(index), 0));
        }

        final Map<Character, LongCounter> groupResponse = new HashMap<>();

        for (int runnerNum = 0; runnerNum < runners.size(); runnerNum++) {
            RecursiveRunner thisRunner = runners.get(runnerNum);
            Map<Character, LongCounter> runnerResponse = thisRunner.cycleUntilLevel(stepsToRun);
            runnerResponse.forEach((key, value) -> {
                if(!groupResponse.containsKey(key)) {
                    groupResponse.put(key, new LongCounter());
                }
                groupResponse.get(key).increment(value.currentValue());
            });
            if (runnerNum+1 < runners.size()) {
                // We added an extra character so remove it
                groupResponse.get(thisRunner.checkedPair.charAt(1)).decrement();
            }
        }

        List<Pair<Character, Long>> frequencies =
                groupResponse.entrySet().stream().map(entry -> Pair.with(entry.getKey(), entry.getValue().currentValue())).collect(Collectors.toList());
        frequencies.sort(new Comparator<Pair<Character, Long>>() {
            @Override
            public int compare(Pair<Character, Long> o1, Pair<Character, Long> o2) {
                return Long.compare(o1.getValue1(), o2.getValue1());
            }
        });

        return frequencies;
    }

    protected static void addAnswerToMap(Map<Character, LongCounter> map, Character character, LongCounter valueToAdd) {
        if (!(map.containsKey(character))) {
            map.put(character, new LongCounter());
        }
        map.get(character).increment(valueToAdd.currentValue());
    }

    private class RecursiveRunner {
        private String checkedPair;
        private int currentLevel;
        protected RecursiveRunner(String checkedPair, int currentLevel) {
            this.checkedPair = checkedPair;
            this.currentLevel = currentLevel;
        }
        protected Map<Character, LongCounter> cycleUntilLevel(int stopLevel) {
            final Map<Character, LongCounter> answerMap = new HashMap<>();
//            logger.info("Recursive runner for " + checkedPair + " at level " + currentLevel + "/" + stopLevel);
            if (currentLevel == stopLevel) {
//                logger.info("[" + checkedPair + ", level="+currentLevel + ":Adding 1s for all of the good kids!");
                checkedPair.chars().mapToObj(ch -> (char)ch).forEach(ch -> {
                    if (!answerMap.containsKey(ch)) {
                        answerMap.put(ch, new LongCounter());
                    }
                    answerMap.get(ch).increment();
                });
            } else {
                currentLevel++;
                Pair<String, Integer> currentPlace = Pair.with(checkedPair, currentLevel);
                if (answerLake.containsKey(currentPlace)) {
                    answerLake.get(currentPlace).forEach((key, value) -> answerMap.put(key, value));
                    lakeHits++;
                } else {
                    if (pairInsertionRules.containsKey(checkedPair)) {
                        String insertedChar = pairInsertionRules.get(checkedPair);
                        String leftSubstring = checkedPair.charAt(0) + insertedChar;
                        RecursiveRunner leftRunner = new RecursiveRunner(leftSubstring, currentLevel);
                        Map<Character, LongCounter> leftSide = leftRunner.cycleUntilLevel(stopLevel);
                        leftSide.forEach((key, value) -> addAnswerToMap(answerMap, key, value));

//                        logger.info(currentPlace + "/" + stopLevel + ": Left is " + printMap(leftSide));

                        String rightSubstring = insertedChar + checkedPair.charAt(1);
                        RecursiveRunner rightRunner = new RecursiveRunner(rightSubstring, currentLevel);
                        Map<Character, LongCounter> rightSide = rightRunner.cycleUntilLevel(stopLevel);
                        rightSide.forEach((key, value) -> addAnswerToMap(answerMap, key, value));

//                        logger.info(currentPlace + "/" + stopLevel + ": right is " + printMap(rightSide));

//                        logger.info(currentPlace + "/" + stopLevel + ": Removing added character " + insertedChar);
                        answerMap.get(insertedChar.charAt(0)).decrement();

                        // Cache this answer!
                        answerLake.put(currentPlace, answerMap);
                    }
                }
            }
            return answerMap;
        }
    }

    public void showLakeStats() {
        logger.info("AnswerLake size=" + answerLake.size() + " and has generated " + lakeHits + " hits");
    }

    private static String printMap(Map<Character, LongCounter> mappy) {
        final StringBuilder sb = new StringBuilder();
        sb.append("[");
        mappy.forEach((key, value) -> sb.append("[").append(key).append("->").append(value.currentValue()).append("] "));
        return sb.toString();
    }
}
