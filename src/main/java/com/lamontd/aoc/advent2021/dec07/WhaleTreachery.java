package com.lamontd.aoc.advent2021.dec07;

import com.lamontd.aoc.utils.BasicCounter;
import com.lamontd.aoc.utils.LocalResourceInput;
import com.lamontd.aoc.utils.LongCounter;
import com.lamontd.aoc.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WhaleTreachery {
    private static final Logger logger = LoggerFactory.getLogger(WhaleTreachery.class);

    List<Crab> friendlyCrabs;
    int furthestAway = 0;

    public void loadData(String inputFile) throws IOException {
        friendlyCrabs = new ArrayList<>();
        furthestAway = 0;
        LocalResourceInput resourceInput = new LocalResourceInput(inputFile);
        for (String str : resourceInput.getInput().get(0).split(",")) {
            int crabPosition = Integer.parseInt(str.trim());
            friendlyCrabs.add(new Crab(crabPosition));
            if (crabPosition > furthestAway) {
                furthestAway = crabPosition;
            }
        }
    }

    private int calculateFuelToPosition(int position) {
        final BasicCounter counter = new BasicCounter();
        friendlyCrabs.stream().forEach(crab -> counter.increment(crab.fuelForMove(position)));
        return counter.currentValue();
    }

    private long calculateCrabbyFuelToPosition(int position) {
        final LongCounter counter = new LongCounter();
        friendlyCrabs.stream().forEach(crab -> counter.increment(crab.crabbyFuelForMove(position)));
        return counter.currentValue();
    }

    public int findOptimalPositionForMove() {
        Map<Integer, Integer> fuelMap = new HashMap<>();
        Integer optimalPosition = 0;
        for (int location=0; location <= furthestAway; location++) {
            fuelMap.put(location, calculateFuelToPosition(location));
            if (fuelMap.get(location) < fuelMap.get(optimalPosition)) {
                optimalPosition = location;
            }
        }
        return optimalPosition;
    }

    public int findOptimalPositionForCrabbyMove() {
        Map<Integer, Long> fuelMap = new HashMap<>();
        Integer optimalPosition = 0;
        for (int location=0; location <= furthestAway; location++) {
            fuelMap.put(location, calculateCrabbyFuelToPosition(location));
            if (fuelMap.get(location) < fuelMap.get(optimalPosition)) {
                optimalPosition = location;
            }
        }
        return optimalPosition;
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("The Treachery of Whales");
        logger.info("Setting up the sample");
        WhaleTreachery treachery = new WhaleTreachery();
        treachery.loadData("advent2021/day07/sample.txt");
        int optimalPosition = treachery.findOptimalPositionForMove();
        logger.info("The optimal position is " + optimalPosition + " which costs " + treachery.calculateFuelToPosition(optimalPosition) + " fuel");

        logger.info("Loading the real data...");
        treachery.loadData("advent2021/day07/input.txt");
        int realOptimalPosition = treachery.findOptimalPositionForMove();
        logger.info("Part 1: The optimal position is " + realOptimalPosition + " and it costs " + treachery.calculateFuelToPosition(realOptimalPosition) + " fuel");

        logger.info("Reloading the sample data for Part 2...");
        treachery.loadData("advent2021/day07/sample.txt");
        int sampleCrabbyPosition = treachery.findOptimalPositionForCrabbyMove();
        logger.info("Part 2: The sample's optimal crabby position is " + sampleCrabbyPosition + " and it costs " + treachery.calculateCrabbyFuelToPosition(sampleCrabbyPosition) + " fuel");

        logger.info("Loading the real data for Part 2...");
        treachery.loadData("advent2021/day07/input.txt");
        int realCrabbyPosition = treachery.findOptimalPositionForCrabbyMove();
        logger.info("Part 2: The real optimal crabby position is " + realCrabbyPosition + "' and it costs " + treachery.calculateCrabbyFuelToPosition(realCrabbyPosition) + " fuel");
        timer.finish();
    }
}
