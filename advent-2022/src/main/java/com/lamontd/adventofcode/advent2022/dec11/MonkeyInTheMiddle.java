package com.lamontd.adventofcode.advent2022.dec11;

import com.lamontd.adventofcode.utils.BasicCounter;
import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MonkeyInTheMiddle {
    private static final Logger logger = LoggerFactory.getLogger(MonkeyInTheMiddle.class);

    private final Map<Integer, PlayfulMonkey> monkeyMap = new HashMap<>();
    private final BasicCounter roundCounter = new BasicCounter();

    public void stopReducingWorry() {
        monkeyMap.values().forEach(monkey -> monkey.setReduceWorry(false));
    }

    public void loadMonkeysFromFile(LocalResourceInput resourceInput) {
        Map<Integer, List<String>> monkeyDefinitions = splitMonkeyDefinitions(resourceInput.getInput());
        // Define all of the monkeys
        for (Integer monkeyNum : monkeyDefinitions.keySet()) {
            monkeyMap.put(monkeyNum, createBasicMonkeyFromDefinitions(monkeyNum, monkeyDefinitions.get(monkeyNum)));
        }

        monkeyMap.entrySet()
                .forEach(entry -> {
                    List<String> monkeyDefs = monkeyDefinitions.get(entry.getKey());
                    Integer divisor = Integer.parseInt(monkeyDefs.get(2).substring(19));
                    int trueMonkeyNum = Integer.parseInt(monkeyDefs.get(3).substring(25));
                    int falseMonkeyNum = Integer.parseInt(monkeyDefs.get(4).substring(26));
                    entry.getValue().receiveConditions(divisor, monkeyMap.get(trueMonkeyNum), monkeyMap.get(falseMonkeyNum));
                });

        // Figure out the upper limit for the divisors and set it for all operations
        long divisorLimit = 1L;
        for (PlayfulMonkey monkey : monkeyMap.values()) {
            divisorLimit *= monkey.getTestDivisor();
        }
        for (PlayfulMonkey monkey : monkeyMap.values()) {
            monkey.getOperation().setUpperLimit(divisorLimit);
        }
    }

    private static Map<Integer, List<String>> splitMonkeyDefinitions(List<String> inputData) {
        Map<Integer, List<String>> monkeyDefs = new HashMap<>();
        ListIterator<String> inputIterator = inputData.listIterator();
        while (inputIterator.hasNext()) {
            String currentString = inputIterator.next();

            if (currentString.startsWith("Monkey")) {
                int monkeyNum = Integer.parseInt(currentString.split(" ")[1].split(":")[0]);
                monkeyDefs.put(monkeyNum,
                        List.of(StringUtils.trim(inputIterator.next()),
                                StringUtils.trim(inputIterator.next()),
                                StringUtils.trim(inputIterator.next()),
                                StringUtils.trim(inputIterator.next()),
                                StringUtils.trim(inputIterator.next())));
            }
        }
        return monkeyDefs;
    }

    private void logWorryLabels() {
        for (PlayfulMonkey monkey : monkeyMap.values()) {
            logger.info(monkey.getStateString());
        }
    }

    private void logInspectionData() {
        logger.info("== After round " + roundCounter.currentValue() + " ==");
        for (PlayfulMonkey monkey : monkeyMap.values()) {
            logger.info("Monkey " + monkey.getMonkeyNumber() + " inspected items " + monkey.getInspectionCount() + " times");
        }
    }

    private long calculateMonkeyBusiness() {
        List<Long> inspectionCounts = monkeyMap.values()
                .stream().map(PlayfulMonkey::getInspectionCount)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        return inspectionCounts.get(0) * inspectionCounts.get(1);
    }

    private static PlayfulMonkey createBasicMonkeyFromDefinitions(int monkeyNum, List<String> definitions) {
        MonkeyOperation operation = toMonkeyOperation(definitions.get(1));
        PlayfulMonkey newMonkey = new PlayfulMonkey(monkeyNum, operation);
        String[] defs = definitions.get(0).split(":");
        for (String itemStr : defs[1].split(",")) {
            long startingItem = Long.parseLong(itemStr.trim());
            newMonkey.catchItem(new StolenItem(startingItem));
        }
        return newMonkey;
    }

    private static MonkeyOperation toMonkeyOperation(String definition) {
        String equation = StringUtils.trim(definition.split("=")[1]);
        MonkeyOperation monkeyOperation = null;
        if (StringUtils.equals("old * old", equation)) {
            monkeyOperation = new MonkeyOperation(MonkeyOperation.Type.SQUARE);
        } else {
            String starting = equation.substring(0, 5);
            int ending = Integer.parseInt(equation.substring(6));
            MonkeyOperation.Type opType = null;
            switch (starting) {
                case "old *":
                    opType = MonkeyOperation.Type.MULTIPLY;
                    break;
                case "old +":
                    opType = MonkeyOperation.Type.ADD;
                    break;
                case "old /":
                    opType = MonkeyOperation.Type.DIVIDE;
                    break;
                case "old -":
                    opType = MonkeyOperation.Type.SUBTRACT;
                    break;
            }
            monkeyOperation = new MonkeyOperation(opType, ending);
        }
        return monkeyOperation;
    }

    public void executeTurns(int numTurns) {
        for (int i = 0; i < numTurns; i++) {
            roundCounter.increment();
            logger.debug("Round " + roundCounter.currentValue());
            monkeyMap.values().stream().forEach(PlayfulMonkey::checkOutItems);
        }
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Monkey In The Middle");
        MonkeyInTheMiddle sample = new MonkeyInTheMiddle();
        sample.loadMonkeysFromFile(new LocalResourceInput("day11/sample.txt"));
        sample.logWorryLabels();
        sample.executeTurns(20);
        sample.logWorryLabels();
        sample.logInspectionData();
        logger.info("Part 1: Sample monkey business is " + sample.calculateMonkeyBusiness());

        MonkeyInTheMiddle part2Sample = new MonkeyInTheMiddle();
        part2Sample.loadMonkeysFromFile(new LocalResourceInput("day11/sample.txt"));
        part2Sample.stopReducingWorry();
        part2Sample.executeTurns(10000);
        part2Sample.logInspectionData();
        logger.info("Part 2: Sample monkey business is " + part2Sample.calculateMonkeyBusiness());

        MonkeyInTheMiddle inputMonkeys = new MonkeyInTheMiddle();
        inputMonkeys.loadMonkeysFromFile(new LocalResourceInput("day11/input.txt"));
        inputMonkeys.executeTurns(20);
        inputMonkeys.logInspectionData();
        logger.info("Part 1: Input monkey business is " + inputMonkeys.calculateMonkeyBusiness());

        MonkeyInTheMiddle part2Input = new MonkeyInTheMiddle();
        part2Input.loadMonkeysFromFile(new LocalResourceInput("day11/input.txt"));
        part2Input.stopReducingWorry();
        part2Input.executeTurns(10000);
        part2Input.logInspectionData();
        logger.info("Part 2: After 10000 turns, input monkey business is " + part2Input.calculateMonkeyBusiness());
        timer.finish();
    }
}
