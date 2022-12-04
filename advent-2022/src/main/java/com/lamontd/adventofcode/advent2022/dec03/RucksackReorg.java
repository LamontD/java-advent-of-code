package com.lamontd.adventofcode.advent2022.dec03;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import com.lamontd.adventofcode.utils.StringCombiner;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class RucksackReorg {
    private static final Logger logger = LoggerFactory.getLogger(RucksackReorg.class);

    private final List<Rucksack> rucksacks;

    public RucksackReorg(LocalResourceInput resourceInput) {
        this.rucksacks = resourceInput.getInput().stream().map(Rucksack::new).collect(Collectors.toList());
    }

    private static long getPriorityOfString(String priorityString) {
        long priority = 0;
        for (Character ch : priorityString.toCharArray()) {
            priority += Character.isLowerCase(ch) ? ch - 'a' + 1 : ch - 'A' + 27;
        }
        return priority;
    }

    public long getBasicPriority() {
        return rucksacks.stream().mapToLong(sack -> {
            String sharedItems = sack.getSharedItems();
            long priority = getPriorityOfString(sharedItems);
            return priority;
        }).sum();
    }

    public long getPriorityForGroupsOfThree() {
        long groupPriority = 0;
        for (int index = 0; index < rucksacks.size(); ) {
            Rucksack firstSack = rucksacks.get(index++);
            Rucksack secondSack = rucksacks.get(index++);
            Rucksack thirdSack = rucksacks.get(index++);
            String commonString = StringCombiner.getCommonCharacters(firstSack.getEntireRucksack(), secondSack.getEntireRucksack(), thirdSack.getEntireRucksack());
            groupPriority += getPriorityOfString(commonString);
        }
        return groupPriority;
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Rucksack Reorganization");
        LocalResourceInput sampleInput = new LocalResourceInput("day03/sample.txt");
        RucksackReorg reorg = new RucksackReorg(sampleInput);
        logger.info("Sample priority is " + reorg.getBasicPriority());
        logger.info("Group priority is " + reorg.getPriorityForGroupsOfThree());
        LocalResourceInput realInput = new LocalResourceInput("day03/input.txt");
        RucksackReorg realReorg = new RucksackReorg(realInput);
        logger.info("Real basic priority is " + realReorg.getBasicPriority());
        logger.info("Real group priority is " + realReorg.getPriorityForGroupsOfThree());
        timer.finish();
    }
}
