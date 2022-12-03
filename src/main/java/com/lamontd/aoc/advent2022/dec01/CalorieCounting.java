package com.lamontd.aoc.advent2022.dec01;

import com.lamontd.aoc.utils.LocalResourceInput;
import com.lamontd.aoc.utils.ProblemTimer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalorieCounting {
    private static final Logger logger = LoggerFactory.getLogger(CalorieCounting.class);
    private List<CalorieElf> elves = new ArrayList<>();

    private void setupElves(LocalResourceInput resourceInput) {
        int elfnum = 1;
        CalorieElf currentElf = null;

        for (String input : resourceInput.getInput()) {
            if (currentElf == null) {
                currentElf = new CalorieElf(elfnum++);
            }
            if (StringUtils.isEmpty(input)) {
                elves.add(currentElf);
                currentElf = null;
            } else {
                currentElf.addCalories(Long.parseLong(input));
            }
        }
        if (currentElf != null) {
            elves.add(currentElf);
        }
    }

    public void showcaseCalories() {
        logger.info("Finding the elf with the highest calorie count - I have " + elves.size() + " in total");
        Comparator<CalorieElf> heaviestComparator = new Comparator<CalorieElf>() {
            @Override
            public int compare(CalorieElf left, CalorieElf right) {
                return Long.compare(right.getTotalCalories(), left.getTotalCalories());
            }
        };
        List<CalorieElf> sortedElves = elves.stream()
                .sorted(heaviestComparator).collect(Collectors.toList());
        logger.info("Part 1: The heavy elf is " + sortedElves.get(0).getElfNum() + " with " + sortedElves.get(0).getTotalCalories() + " calories");
        long topThree = sortedElves.get(0).getTotalCalories() + sortedElves.get(1).getTotalCalories() + sortedElves.get(2).getTotalCalories();
        logger.info("Part 2: The top three calorie count is " + topThree);
    }


    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Calorie counting");
        CalorieCounting sampleCounting = new CalorieCounting();
        sampleCounting.setupElves(new LocalResourceInput("advent2022/day01/sample.txt"));
        sampleCounting.showcaseCalories();

        CalorieCounting realCalories = new CalorieCounting();
        realCalories.setupElves(new LocalResourceInput("advent2022/day01/input.txt"));
        realCalories.showcaseCalories();
        timer.finish();
    }
}
