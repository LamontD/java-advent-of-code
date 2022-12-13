package com.lamontd.adventofcode.advent2022.dec13;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class DistressSignal {
    private static final Logger logger = LoggerFactory.getLogger(DistressSignal.class);

    private Map<Integer, Pair<String, String>> signalPair = new HashMap<>();

    public void loadSignals(LocalResourceInput resourceInput) {
        int index = 1;
        for(ListIterator<String> inputIterator = resourceInput.getInput().listIterator(); inputIterator.hasNext(); ) {
            String firstRowString = inputIterator.next();
            if (StringUtils.isEmpty(firstRowString)) {
                continue;
            }
            String secondRowString = inputIterator.next();
            signalPair.put(index++, Pair.with(firstRowString, secondRowString));
        }
    }

    public int getIndexPairSums() {
        int indexPairSums = 0;
        for (Map.Entry<Integer, Pair<String, String>> signalEntry : signalPair.entrySet()) {
            ReceivedSignal left = new ReceivedSignal(signalEntry.getValue().getValue0());
            ReceivedSignal right = new ReceivedSignal(signalEntry.getValue().getValue1());
            if (left.compareTo(right) < 0) {
                logger.debug("Pair " + signalEntry.getKey() + " is in order!");
                indexPairSums += signalEntry.getKey();
            } else {
                logger.debug("Pair " + signalEntry.getKey() + " is out of order!");
            }
        }
        return indexPairSums;
    }

    public int findDecoderKey() {
        List<ReceivedSignal> orderedSignals = new LinkedList<>();
        for (Pair<String, String> signalPair : signalPair.values()) {
            orderedSignals.add(new ReceivedSignal(signalPair.getValue0()));
            orderedSignals.add(new ReceivedSignal(signalPair.getValue1()));
        }
        ReceivedSignal dividerOne = new ReceivedSignal("[[2]]");
        ReceivedSignal dividerTwo = new ReceivedSignal("[[6]]");
        orderedSignals.add(dividerOne);
        orderedSignals.add(dividerTwo);

        Collections.sort(orderedSignals);

        int firstIndex = orderedSignals.indexOf(dividerOne);
        logger.info("Found first divider at array index " + firstIndex);
        int secondIndex = orderedSignals.indexOf(dividerTwo);
        logger.info("Found second divider at array index " + secondIndex);
        return (firstIndex + 1) * (secondIndex + 1);
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Distress Signal");
        logger.info("Trying the sample data...");
        DistressSignal signal = new DistressSignal();
        signal.loadSignals(new LocalResourceInput("day13/sample.txt"));
        logger.info("The index pair sums is " + signal.getIndexPairSums());
        logger.info("Part 2: The decoder key is " + signal.findDecoderKey());

        logger.info("Let's try some real data!");
        DistressSignal realDistress = new DistressSignal();
        realDistress.loadSignals(new LocalResourceInput("day13/input.txt"));
        logger.info("The index pair sums is " + realDistress.getIndexPairSums());
        logger.info("Part 2: The decoder key is " + realDistress.findDecoderKey());
        timer.finish();
    }
}
