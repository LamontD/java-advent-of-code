package com.lamontd.adventofcode.advent2022.dec16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class SinglePathfinder {
    private static final Logger logger = LoggerFactory.getLogger(SinglePathfinder.class);

    protected final Map<String, Valve> valveNameMap = new HashMap<>();

    protected final Set<Valve> positiveFlowValves = new TreeSet<>(Comparator.comparing(Valve::getFlowRate).reversed());

    protected final List<SingleValvePath> optimalPaths = new ArrayList<>();


    public SinglePathfinder(Collection<Valve> valves) {
        for (Valve valve : valves) {
            valveNameMap.put(valve.getName(), valve);
            if (valve.getFlowRate() > 0) {
                positiveFlowValves.add(valve);
            }
        }
        optimalPaths.add(new SingleValvePath(valveNameMap.get("AA")));
    }

    public void buildOptimalPaths() {
        int expansionCount = 0;
        Comparator<ValvePath> greatestPotentialFlowFirst = Comparator.comparing(ValvePath::getPotentialFlow).reversed();
        while (extendOptimalPaths()) {
            expansionCount++;
            logger.debug("Expansion " + expansionCount + " has " + optimalPaths.size() + " optimal paths");
            optimalPaths.sort(greatestPotentialFlowFirst);
            logger.debug("Sorted expanding debris..");
            prunePathsThatCannotCatchUp();
            logger.debug("Pruning " + expansionCount + " reduced the optimal paths to " + optimalPaths.size());
            if (expansionCount >= 5) {
                logger.info("Working more substantive changes.");
                pruneUnluckyPaths();
            }
            if (expansionCount >= 10) {
                pruneNonMaxValves();
            }
        }
    }

    private boolean extendOptimalPaths() {
        boolean changedSomething = false;
        for (ListIterator<SingleValvePath> pathIterator = optimalPaths.listIterator(); pathIterator.hasNext(); ) {
            SingleValvePath currentPath = pathIterator.next();
            // If there isn't time left, skip this one.
            if (currentPath.getTimeRemaining() <= 1) {
                continue;
            }

            List<SingleValvePath> extendedPaths = currentPath.getExtendedPaths();
            if (!extendedPaths.isEmpty()) {
                pathIterator.remove();
                for (SingleValvePath extension : extendedPaths) {
                    pathIterator.add(extension);
                }
                changedSomething = true;
            }
        }
        return changedSomething;
    }

    protected void pruneNonMaxValves() {
        logger.info("Pruning those paths that have less than the maximum valves...");
        int currentMostValvesOpen = optimalPaths.get(0).getValvesOpenedOnPath().size();
        int lowerThanMaxValves = 0;
        for(ListIterator<SingleValvePath> pathIter = optimalPaths.listIterator(); pathIter.hasNext(); ) {
            ValvePath evalPath = pathIter.next();
            if (evalPath.getValvesOpenedOnPath().size() < currentMostValvesOpen) {
                pathIter.remove();
                lowerThanMaxValves++;
            }
        }
        logger.info("Purged " + lowerThanMaxValves + " paths with less than max valves");
    }

    protected void pruneUnluckyPaths() {
        logger.info("Looking to prune unlucky paths...");
        ValvePath currentBestPath = optimalPaths.get(0);
        ValvePath currentWorstPath = optimalPaths.get(optimalPaths.size() - 1);
        int currentHighestPotentialFlow = currentBestPath.getPotentialFlow();
        int currentLowestPotentialFlow = currentWorstPath.getPotentialFlow();
        logger.info("We have " + optimalPaths.size() + " total paths ");
        logger.info("Best path has a potential flow of " + currentHighestPotentialFlow + " and already has " + currentBestPath.getValvesOpenedOnPath().size() + " valves open");
        logger.info("Worst path has a potential flow of " + currentLowestPotentialFlow + " and already has " + currentWorstPath.getValvesOpenedOnPath().size() + " valves open");
        int missingTopFlowFilePaths = 0;
        int lowerOutputProducers = 0;
        List<String> topFiveFlowValves = positiveFlowValves.stream().map(Valve::getName).collect(Collectors.toList()).subList(0, 5);
        for(ListIterator<SingleValvePath> pathIterator = optimalPaths.listIterator(); pathIterator.hasNext(); ) {
            ValvePath evalPath = pathIterator.next();
            if (Collections.disjoint(evalPath.getValvesOpenedOnPath(), topFiveFlowValves)) {
                pathIterator.remove();
                missingTopFlowFilePaths++;
            } else if ((currentHighestPotentialFlow - currentLowestPotentialFlow) > 900 && evalPath.getPotentialFlow() < (currentHighestPotentialFlow + currentLowestPotentialFlow) / 2) {
                pathIterator.remove();
                lowerOutputProducers++;
            }
        }
        logger.info("Pruned " + missingTopFlowFilePaths + " paths that didn't contain any of the top flow valves");
        logger.info("Pruned " + lowerOutputProducers + " lower output producers");
    }

    protected void prunePathsThatCannotCatchUp() {
        ListIterator<SingleValvePath> pruningIterator = optimalPaths.listIterator();
        SingleValvePath currentBestPath = pruningIterator.next();
        int bestCurrentFlow = currentBestPath.getPotentialFlow();
        while (pruningIterator.hasNext()) {
            ValvePath evalPath = pruningIterator.next();
            List<String> valvesOpenedOnPath = evalPath.getValvesOpenedOnPath();

            // This heuristic assumes that the remaining open valves will be perfect moves.
            // This should allow us to quickly determine the impossible without evaluating the actual path
            int bestPossibleFutureFlow = 0;
            int timeToWorkWith = evalPath.getTimeRemaining();

            for (Valve positiveFlowValve : positiveFlowValves) {
                if (!valvesOpenedOnPath.contains(positiveFlowValve.getName()) && timeToWorkWith > 2) {
                    timeToWorkWith -= 2;
                    bestPossibleFutureFlow += positiveFlowValve.getFlowRate() * timeToWorkWith;
                }
            }
            if (evalPath.getPotentialFlow() + bestPossibleFutureFlow < bestCurrentFlow) {
                pruningIterator.remove();
            }

        }
    }

    public ValvePath getBestPath() {
        return optimalPaths.get(0);
    }

}
