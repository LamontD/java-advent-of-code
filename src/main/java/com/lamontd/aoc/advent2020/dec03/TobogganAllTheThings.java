package com.lamontd.aoc.advent2020.dec03;

import com.lamontd.aoc.utils.BasicCounter;
import com.lamontd.aoc.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TobogganAllTheThings {
    private static final Logger logger = LoggerFactory.getLogger(TobogganAllTheThings.class);

    private List<BiomeEntry> entryMap;

    public TobogganAllTheThings(List<String> entryStrings) {
        entryMap = entryStrings.stream().map(str -> new BiomeEntry(str))
                .collect(Collectors.toList());
        logger.info("Created entry map of length " + entryMap.size());
    }

    public int treesFoundDuringTraversal(int right, int down) {
        logger.info("Traversing the slope: Right " + right + ", down " + down);
        BasicCounter treeCounter = new BasicCounter();
        int currentRightPosition = 0;
        for (int downPosition = 0; downPosition < entryMap.size(); downPosition += down) {
            if (entryMap.get(downPosition).hasTree(currentRightPosition)) {
                treeCounter.increment();
            }
            currentRightPosition += right;
        }
        return treeCounter.currentValue();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to the grand toboggan run of 2020!");
        LocalResourceInput input = new LocalResourceInput("advent2020/day3/advent-day3-input.txt");
        TobogganAllTheThings tobogganRun = new TobogganAllTheThings(input.getInput());
        int partOneTrees = tobogganRun.treesFoundDuringTraversal(3, 1);
        logger.info("Our Part 1 traversal found " + partOneTrees + " trees");
        logger.info("Working on Part 2:");
        long totalProduct = 1;
        totalProduct *= tobogganRun.treesFoundDuringTraversal(1, 1);
        totalProduct *= tobogganRun.treesFoundDuringTraversal(3, 1);
        totalProduct *= tobogganRun.treesFoundDuringTraversal(5, 1);
        totalProduct *= tobogganRun.treesFoundDuringTraversal(7, 1);
        totalProduct *= tobogganRun.treesFoundDuringTraversal(1, 2);
        logger.info("The product of the Part 2 traversals is " + totalProduct);

        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");
    }
}
