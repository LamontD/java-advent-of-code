package com.lamontd.adventofcode.advent2020.dec21;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class AllergenAssessment {
    private static final Logger logger = LoggerFactory.getLogger(AllergenAssessment.class);

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Allergen Assessment");

        logger.info("Loading data...");
        LocalResourceInput resourceInput = new LocalResourceInput("day21/input.txt");
        IngredientMapper mapper = new IngredientMapper();
        for (Map.Entry<Integer, String> entryLine : resourceInput.getRawInput().entrySet()) {
            mapper.addEntry(new IngredientEntry(entryLine.getKey(), entryLine.getValue()));
        }
        logger.info("Part 1: Doing the translation work...");
        logger.info("Part 1: I found " + mapper.countNonAllergenAppearances() + " non-allergen appearances in the input data");

        logger.info("Part 2: Getting the canonical ingredients list");
        logger.info("That list is: " + mapper.getCanonicalDangerousIngredientList());
        timer.finish();
    }
}
