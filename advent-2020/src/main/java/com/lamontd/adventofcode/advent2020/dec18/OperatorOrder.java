package com.lamontd.adventofcode.advent2020.dec18;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class OperatorOrder {
    private static final Logger logger = LoggerFactory.getLogger(OperatorOrder.class);

    private static long sumAllOfTheOperations(MathHomeworkEvaluator evaluator, List<String> operations) {
        long sum = 0L;
        for (String line : operations) {
            List<MathToken> tokens = ExpressionTokenizer.tokenize(line);
            sum += evaluator.evaluate(tokens);
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to Operator Order!");
        logger.info("Getting input data...");
        LocalResourceInput resourceInput = new LocalResourceInput("day18/advent-day18-input.txt");
        logger.info("I see " + resourceInput.getInput().size() + " expressions to evaluate");
        logger.info("Part 1: Sum all of the expressions with no order");
        logger.info("Part 1 answer is: " + sumAllOfTheOperations(new NoPrecedenceEvaluator(), resourceInput.getInput()));
        logger.info("Part 2: Sum all of the expressions with aditoin first!");
        logger.info("Part 2 answer is: " + sumAllOfTheOperations(new AdditionFirstEvaluator(), resourceInput.getInput()));
        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");
    }
}
