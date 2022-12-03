package com.lamontd.aoc.advent2021.dec01;

import com.lamontd.aoc.utils.LocalResourceInput;
import com.lamontd.aoc.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SonarSweep {

    public static final Logger logger = LoggerFactory.getLogger(SonarSweep.class);
    private final List<Integer> integerList;

    public SonarSweep(String inputFile) throws IOException {
        LocalResourceInput resourceInput = new LocalResourceInput(inputFile);
        this.integerList = resourceInput.getInput().stream()
                .map(str -> Integer.parseInt(str))
                .collect(Collectors.toList());
    }

    public int calculateBasicUps() {
        int ups = 0;
        int downs = 0;
        int prev = integerList.get(0);
        for (Integer val : integerList) {
            if (val > prev) {
                ups++;
            } else if (val < prev) {
                downs++;
            }
            prev = val;
        }
        return ups;
    }

    public int calculateSlidingUps() {
        int prevValue = integerList.get(0) + integerList.get(1) + integerList.get(2);
        int slidingUps = 0;
        for (int i=0; i < integerList.size() - 2; i++) {
            int currentValue = integerList.get(i) + integerList.get(i+1) + integerList.get(i+2);
            if (currentValue > prevValue) {
                slidingUps++;
            }
            prevValue = currentValue;
        }
        return slidingUps;
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Sonar Sweep");
        logger.info("Part 1: Counting the uppers...");
        SonarSweep sonarSweep = new SonarSweep("advent2021/day1/input.txt");
        logger.info("Part 1: I see " + sonarSweep.calculateBasicUps() + " ups");
        logger.info("Part 2: I see " + sonarSweep.calculateSlidingUps() + " sliding ups");
        timer.finish();
    }
}
