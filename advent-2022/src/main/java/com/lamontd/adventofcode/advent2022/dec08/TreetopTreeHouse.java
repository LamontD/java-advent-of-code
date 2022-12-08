package com.lamontd.adventofcode.advent2022.dec08;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TreetopTreeHouse {
    private static final Logger logger = LoggerFactory.getLogger(TreetopTreeHouse.class);

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Treetop Tree House");
        QuadcopterMap sampleMap = new QuadcopterMap(new LocalResourceInput("day08/sample.txt").getInput());
        logger.info("Read sample map as:");
        logger.info(sampleMap.toString());
        logger.info("Part 1: We see " + sampleMap.getVisibleTreeCount() + " trees from the outside");
        logger.info("Part 2: Highest scenic score for sample is " + sampleMap.getHighestScenicScore());
        logger.info("Part 2: Scenic map is " + sampleMap.getScenicMap());

        QuadcopterMap inputMap = new QuadcopterMap(new LocalResourceInput("day08/input.txt").getInput());
        logger.info("Part 1: We see " + inputMap.getVisibleTreeCount() + " trees from the outside");
        logger.info("Part 2: Highest scenic score for input is " + inputMap.getHighestScenicScore());
        timer.finish();
    }
}
