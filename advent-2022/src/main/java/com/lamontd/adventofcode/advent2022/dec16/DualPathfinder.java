package com.lamontd.adventofcode.advent2022.dec16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DualPathfinder {
    private static final Logger logger = LoggerFactory.getLogger(DualPathfinder.class);

    protected final Map<String, Valve>valveNameMap = new HashMap<>();

    protected final Set<Valve> positiveFlowValves = new TreeSet<>(Comparator.comparing(Valve::getFlowRate).reversed());

    protected final List<DualValvePath> optimalPaths = new ArrayList<>();

    protected DualValvePath bestCompletedPath = null;

    protected final Set<Integer> encounteredPaths = new HashSet<>();

    public DualPathfinder(Collection<Valve> valves) {
        DualValvePath initialPath = new DualValvePath(valveNameMap.get("AA"));
        optimalPaths.add(initialPath);
        encounteredPaths.add(initialPath.hashCode());
    }

    public void buildOptimalPaths() {
        while (!optimalPaths.isEmpty()) {
            DualValvePath bestPotentialPath = optimalPaths.get(0);
            List<DualValvePath> newPathsToConsider = bestPotentialPath.getExtendedPaths();
            newPathsToConsider.removeIf(alreadyEncounteredPath());
            extractAndProcessCompletedPaths(newPathsToConsider);
            purgeNewPaths(newPathsToConsider);
            addNewPaths(newPathsToConsider);
        }
    }

    protected void extractAndProcessCompletedPaths(List<DualValvePath> potentialPaths) {
        for (ListIterator<DualValvePath> pathListIterator = potentialPaths.listIterator(); pathListIterator.hasNext();) {
            DualValvePath dualPath = pathListIterator.next();
            // IF potential path is done
            // -- remove from list
            // -- compare against the best done path so far
            // -- IF better, swap and mark new path as changed
        }
    }

    protected void purgeNewPaths(List<DualValvePath> paths) {
        paths.removeIf(pathWillNeverBeWorthy());
    }

    protected void eliminateUnworthyPaths(List<DualValvePath> potentialPaths) {
        for (ListIterator<DualValvePath> pathListIterator = potentialPaths.listIterator(); pathListIterator.hasNext(); ) {
            DualValvePath dualPath = pathListIterator.next();
            // IF best potential outcome is less than the best known path's actual
            // -- remove from the list
        }
    }

    protected void addNewPaths(List<DualValvePath> newPathsToAdd) {

    }

    protected void prunePathsThatCannotCatchUp() {
        ListIterator<DualValvePath> pruningIterator = optimalPaths.listIterator();
        ValvePath currentBestPath = pruningIterator.next();
        int bestCurrentFlow = currentBestPath.getPotentialFlow();
        while (pruningIterator.hasNext()) {
            DualValvePath evalPath = (DualValvePath) pruningIterator.next();
            int optimsticFlow = getOptimisticFutureFlow(evalPath);
            if (optimsticFlow < bestCurrentFlow) {
//                logger.debug("Pruning path with max possible of " + (evalPath.getPotentialFlow() + bestPossibleFutureFlow) + "\n" + evalPath );
                pruningIterator.remove();
            }
        }
    }

    private int getOptimisticFutureFlow(ValvePath valvePath) {
        int optimisticFlow = valvePath.getPotentialFlow();
        int timeRemaining = valvePath.getTimeRemaining();
        List<Valve> flowValvesStillAvailableOnPath = positiveFlowValves.stream()
                .filter(valve -> !valvePath.getValvesOpenedOnPath().contains(valve.getName()))
                .collect(Collectors.toList());
        Iterator<Valve> valveIterator = flowValvesStillAvailableOnPath.listIterator();
        while (timeRemaining >= 2 && valveIterator.hasNext()) {
            timeRemaining -= 2;
            optimisticFlow += valveIterator.next().getFlowRate() * timeRemaining;
        }
        return optimisticFlow;
    }

    public DualValvePath getBestPath() {
        return optimalPaths.get(0);
    }

    private Predicate<DualValvePath> alreadyEncounteredPath() {
        return new Predicate<DualValvePath>() {
            @Override
            public boolean test(DualValvePath dualValvePath) {
                return encounteredPaths.contains(dualValvePath.hashCode());
            }
        };
    }

    private Predicate<DualValvePath> pathWillNeverBeWorthy() {
        return new Predicate<DualValvePath>() {
            @Override
            public boolean test(DualValvePath dualValvePath) {
                if (bestCompletedPath != null) {
                    return getBestOptimisticPotentialFlow(dualValvePath) < bestCompletedPath.getPotentialFlow();
                }
                return false;
            }
        };
    }

    public int getBestOptimisticPotentialFlow(DualValvePath path) {
        return -1;
    }

//    private class OptimisticFutureFlowComparator implements Comparator<ValvePath> {
//
//        @Override
//        public int compare(ValvePath lhs, ValvePath rhs) {
//            int leftOptimisticFlow = lhs.getPotentialFlow();
//            int rightOptimisticFlow = rhs.getPotentialFlow();
//            for (Valve flowValve : positiveFlowValves) {
//                if ()
//            }
//            return 0;
//        }
//    }
}
