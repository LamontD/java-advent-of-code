package com.lamontd.aoc.advent2020.dec06;

import com.lamontd.aoc.utils.BasicCounter;
import com.lamontd.aoc.utils.LocalResourceInput;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomCustoms {
    private static final Logger logger = LoggerFactory.getLogger(CustomCustoms.class);

    private final List<GroupResponse> groupResponses = new ArrayList<>();

    public void readInputData(LocalResourceInput input) {
        GroupResponse currentResponse = new GroupResponse();
        for (String inputLine : input.getInput()) {
            if (StringUtils.isEmpty(inputLine)) {
                this.groupResponses.add(currentResponse);
                currentResponse = new GroupResponse();
            } else {
                currentResponse.addResponseLine(inputLine);
            }
        }
        groupResponses.add(currentResponse);
    }

    public int getNumberOfGroups() { return groupResponses.size(); }
    public Set<Character> getCombinedYesAnswers() {
        final Set<Character> answerSet = new HashSet<>();
        groupResponses.forEach(groupResponse -> answerSet.addAll(groupResponse.getUnionOfYesAnswers()));
        return answerSet;
    }

    public int getSumOfYesAnswersForGroups() {
        final BasicCounter yesCounter = new BasicCounter();
        groupResponses.forEach(groupResponse -> yesCounter.increment(groupResponse.getUnionOfYesAnswers().size()));
        return yesCounter.currentValue();
    }

    public int getSumOfYesIntersectionAnswers() {
        final BasicCounter yesCounter = new BasicCounter();
        groupResponses.forEach(groupResponse -> yesCounter.increment(groupResponse.getIntersectionOfYesAnswers().size()));
        return yesCounter.currentValue();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to Custom Customs!");
        logger.info("Reading the input data...");
        CustomCustoms customs = new CustomCustoms();
        customs.readInputData(new LocalResourceInput("advent2020/day6/advent-day6-input.txt"));
        logger.info("I found " + customs.groupResponses.size() + " groups with answers");
        logger.info("The sum of union yes answers for the groups is: " + customs.getSumOfYesAnswersForGroups());
        logger.info("The sum of intersection yes answers for the groups is: " + customs.getSumOfYesIntersectionAnswers());
        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");
    }
}
