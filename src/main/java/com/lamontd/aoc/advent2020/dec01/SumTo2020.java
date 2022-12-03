package com.lamontd.aoc.advent2020.dec01;

import com.lamontd.aoc.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.javatuples.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SumTo2020 {
    private static final Logger logger = LoggerFactory.getLogger(SumTo2020.class);

    private final LocalResourceInput localResourceInput;

    public SumTo2020(String inputSource) throws IOException {
        this.localResourceInput = new LocalResourceInput(inputSource);
    }

    public Pair<Integer, Integer> findPairThatSumsTo2020() {
        List<Integer> inputs = localResourceInput.getInput().stream()
                .map(str -> Integer.parseInt(str))
                .collect(Collectors.toList());
        for (int left = 0; left < inputs.size() - 1; left++) {
            for (int right = left + 1; right < inputs.size(); right++) {
                if (inputs.get(left) + inputs.get(right) == 2020) {
                    return new Pair<>(inputs.get(left), inputs.get(right));
                }
            }
        }
        // Something just ain't right...
        return null;
    }

    public Triplet<Integer, Integer, Integer> findTripletThatSumsTo2020() {
        List<Integer> inputs = localResourceInput.getInput().stream()
                .map(str -> Integer.parseInt(str))
                .collect(Collectors.toList());
        for (int left = 0; left < inputs.size() - 2; left++) {
            for (int mid = left+1; mid < inputs.size() - 1; mid++) {
                for (int right = mid+1; right < inputs.size(); right++) {
                    if (inputs.get(left) + inputs.get(mid) + inputs.get(right) == 2020) {
                        return new Triplet<>(inputs.get(left), inputs.get(mid), inputs.get(right));
                    }
                }
            }
        }
        return null;
    }

    public static Integer calculateTupleProduct(Tuple tuple) {
        int product = 1;
        for (Object o : tuple) {
            product *= (Integer) o;
        }
        return product;
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        String inputFile = "advent2020/day1/input.txt";
        long milliStart = System.currentTimeMillis();
        logger.info("Working the SumTo2020 problem from input " + inputFile);
        try {
            SumTo2020 summer = new SumTo2020(inputFile);
            Pair<Integer, Integer> summingPair = summer.findPairThatSumsTo2020();
            if (summingPair == null) {
                logger.warn("Could not find summing pair in input " + inputFile);
            } else {
                logger.info("Summing pair is: " + summingPair.toString());
                logger.info("Product is " + calculateTupleProduct(summingPair));
                logger.info("Going for the triple...");
                Triplet<Integer, Integer, Integer> summingTriplet = summer.findTripletThatSumsTo2020();
                if (summingTriplet == null) {
                    logger.warn("Could not find triplet. Very strange.");
                } else {
                    logger.info("Summing triplet is " + summingTriplet.toString());
                    logger.info("Product is " + calculateTupleProduct(summingTriplet));
                }
            }
        } catch (IOException ioe) {
            logger.error("Problem with input file " + inputFile, ioe);
        }
        long milliFinish = System.currentTimeMillis();
        logger.info("Execution took " + (milliFinish - milliStart) + "ms");
    }
}
