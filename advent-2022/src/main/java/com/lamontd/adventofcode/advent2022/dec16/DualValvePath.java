package com.lamontd.adventofcode.advent2022.dec16;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class DualValvePath extends ValvePath implements Comparable {
    private static final Logger logger = LoggerFactory.getLogger(DualValvePath.class);

    private final SingleValvePath operatorPath;
    private final SingleValvePath elephantPath;

    private int lastSeenPotentialFlow;

    protected int elapsedTime = 0;

    protected DualValvePath(SingleValvePath operatorPath, SingleValvePath elephantPath) {
        this.operatorPath = operatorPath.clone();
        this.elephantPath = elephantPath.clone();
        this.elapsedTime = Math.max(operatorPath.elapsedTime, elephantPath.elapsedTime);
        this.setMaxTimeForPath(26);
    }

    public DualValvePath(Valve startingValve) {
        operatorPath = new SingleValvePath(startingValve);
        operatorPath.setMaxTimeForPath(26);
        elephantPath = new SingleValvePath(startingValve);
        elephantPath.setMaxTimeForPath(26);
        elapsedTime = 0;
        setMaxTimeForPath(26);
    }

    @Override
    public DualValvePath clone() {
        DualValvePath clonedValuePath = new DualValvePath(this.operatorPath, this.elephantPath);
        return clonedValuePath;
    }

    @Override
    public int getPotentialFlow() {
        lastSeenPotentialFlow = operatorPath.getPotentialFlow() + elephantPath.getPotentialFlow();
        return operatorPath.getPotentialFlow() + elephantPath.getPotentialFlow();
    }

    @Override
    public int getElapsedTime() {
        return elapsedTime;
    }

    @Override
    public int getTimeRemaining() {
        return maxTimeForPath - elapsedTime;
    }

    @Override
    public ValveContainer getEdgeContainer() {
        return null;
    }

    @Override
    public boolean valid() {
        return operatorPath.valid() && elephantPath.valid() && Collections.disjoint(operatorPath.getValvesOpenedOnPath(), elephantPath.getValvesOpenedOnPath());
    }

    @Override
    public List<DualValvePath> getExtendedPaths() {
        final ArrayList<DualValvePath> extendedPaths = new ArrayList<>();
        List<ValvePath> operatorExtensions = operatorPath.getExtendedPaths().stream()
                .filter(ext -> !ext.getEdgeContainer().needToOpen
                        || !elephantPath.openedValveOnPath(ext.getEdgeContainer().valve))
                .collect(Collectors.toList());
        operatorExtensions.forEach(ext -> ext.setMaxTimeForPath(maxTimeForPath));
        List<ValvePath> elephantExtensions = elephantPath.getExtendedPaths().stream()
                .filter(ext -> !ext.getEdgeContainer().needToOpen
                        || !operatorPath.openedValveOnPath(ext.getEdgeContainer().getValve()))
                .collect(Collectors.toList());
        elephantExtensions.forEach(ext -> ext.setMaxTimeForPath(maxTimeForPath));

        if (elephantExtensions.isEmpty()) {
            operatorExtensions.forEach(op -> extendedPaths.add(new DualValvePath((SingleValvePath) op, elephantPath)));
        } else if (operatorExtensions.isEmpty()) {
            elephantExtensions.forEach(op -> extendedPaths.add(new DualValvePath(operatorPath, (SingleValvePath) op)));
        } else {
            // Filter out the paths where they are opening an already open valve
            operatorExtensions.removeIf(thisExtension ->
                    thisExtension.getEdgeContainer().needToOpen
                            && elephantPath.openedValveOnPath(thisExtension.getEdgeContainer().getValve()));
            elephantExtensions.removeIf(thisExtension ->
                    thisExtension.getEdgeContainer().needToOpen
                            && operatorPath.openedValveOnPath(thisExtension.getEdgeContainer().getValve()));

            // Add the extensions where both are doing the same thing
            final List<DualValvePath> sameActionsPaths = new ArrayList<>();
            operatorExtensions
                    .forEach(op -> {
                        elephantExtensions.stream()
                                .filter(el -> !op.getEdgeContainer().equals(el.getEdgeContainer()) &&
                                        !op.equals(el)
                                        && el.getEdgeContainer().needToOpen == op.getEdgeContainer().needToOpen)
                                .forEach(el -> sameActionsPaths.add(new DualValvePath((SingleValvePath) op, (SingleValvePath) el)));
                    });
            extendedPaths.addAll(filterDuplicatePaths(sameActionsPaths));

            // Add the operator extensions where you need to extend the elephant
            final List<DualValvePath> operatorOpenOnly = new ArrayList<>();
            operatorExtensions.forEach(op -> {
                elephantExtensions.stream()
                        .filter(el -> op.getElapsedTime() != el.getElapsedTime())
                        .forEach(el -> operatorOpenOnly.addAll(followUnevenTrails(op, el)));
            });
            extendedPaths.addAll(filterDuplicatePaths(operatorOpenOnly));

            // Add the elephant extensions where you need to extend the operator
            final List<DualValvePath> elephantOpenOnly = new ArrayList<>();
            elephantExtensions.forEach(el -> {
                operatorExtensions.stream()
                        .filter(op -> op.getElapsedTime() != el.getElapsedTime())
                        .forEach(op -> elephantOpenOnly.addAll(followUnevenTrails(op, el)));
            });
            extendedPaths.addAll(filterDuplicatePaths(elephantOpenOnly));
        }
        return extendedPaths.stream().filter(ValvePath::valid).collect(Collectors.toList());
    }

    private List<DualValvePath> followUnevenTrails(ValvePath operatorPath, ValvePath elephantPath) {
        if (operatorPath.getElapsedTime() == elephantPath.getElapsedTime()) {
            return List.of(new DualValvePath((SingleValvePath) operatorPath, (SingleValvePath) elephantPath));
        }

        final List<DualValvePath> unevenTrails = new ArrayList<>();
        if (operatorPath.getElapsedTime() > elephantPath.getElapsedTime()) {
            elephantPath.getExtendedPaths().forEach(extendedElephant -> unevenTrails.addAll(followUnevenTrails(operatorPath, extendedElephant)));
        } else {
            for (ValvePath extendedOperator : operatorPath.getExtendedPaths()) {
                unevenTrails.addAll(followUnevenTrails(extendedOperator, elephantPath));
            }
        }
        return unevenTrails;
    }

    private static List<DualValvePath> filterDuplicatePaths(List<DualValvePath> extendedPaths) {
        final Set<Pair<String, String>> uniquePairs = new HashSet<>();
        final List<DualValvePath> filteredPaths = new ArrayList<>();
        for (DualValvePath dualPath : extendedPaths) {
            if (!uniquePairs.contains(Pair.with(dualPath.operatorPath.getHashableString(), dualPath.elephantPath.getHashableString()))
            && !uniquePairs.contains(Pair.with(dualPath.elephantPath.getHashableString(), dualPath.operatorPath.getHashableString()))) {
                filteredPaths.add(dualPath);
                uniquePairs.add(Pair.with(dualPath.operatorPath.getHashableString(), dualPath.elephantPath.getHashableString()));
            }
        }
        return filteredPaths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DualValvePath that = (DualValvePath) o;
        return elapsedTime == that.elapsedTime
                && (operatorPath.equals(that.operatorPath) && elephantPath.equals(that.elephantPath)
                || (operatorPath.equals(that.elephantPath) && elephantPath.equals(operatorPath)));
    }

    @Override
    public int hashCode() {
        int operatorHashcode = operatorPath.hashCode();
        int elephantHashcode = elephantPath.hashCode();
        Pair<Integer, Integer> hashablePair = operatorHashcode < elephantHashcode
                ? Pair.with(operatorHashcode, elephantHashcode)
                : Pair.with(elephantHashcode, operatorHashcode);
        return Objects.hashCode(hashablePair);
    }

    @Override
    public List<String> getValvesOpenedOnPath() {
        final List<String> openValves = new ArrayList<>();
        openValves.addAll(operatorPath.getValvesOpenedOnPath());
        openValves.addAll(elephantPath.getValvesOpenedOnPath());
        return openValves;
    }

    @Override
    public String toString() {
        return "Operator " + operatorPath.toString() + "\n" +
                "Elephant " + elephantPath.toString();
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        if (this == o) {
            return 0;
        }
        if (o instanceof DualValvePath) {
            DualValvePath that = (DualValvePath) o;
            if ((this.operatorPath.equals(that.operatorPath) && this.elephantPath.equals(that.elephantPath))
                    || (this.operatorPath.equals(that.elephantPath) && this.elephantPath.equals(that.operatorPath))) {
                return 0;
            }
            return 1;
        }
        return -1;
    }

    @Override
    public void logPathDetails(int timeToElapse) {
        int currentMinute = 0;
        int currentPressure = 0;
        int totalPressure = 0;
        List<String> openValves = new ArrayList<>();

        Iterator<ValveContainer> operatorIterator = operatorPath.getValvePath().iterator();
        Iterator<ValveContainer> elephantIterator = elephantPath.getValvePath().iterator();
        boolean openOperator = false;
        boolean moveOperator = true;
        boolean openElephant = false;
        boolean moveElephant = true;
        ValveContainer operatorValve = operatorIterator.next();
        ValveContainer elephantValve = elephantIterator.next();

        while (operatorIterator.hasNext() || elephantIterator.hasNext()) {
            currentMinute++;
            if (currentMinute > timeToElapse) {
                break;
            }
            totalPressure += currentPressure;
            logCurrentPathState(logger, currentMinute, currentPressure, openValves);

            if (moveOperator) {
                logger.info("You move to valve " + operatorValve.valve.getName());
                openOperator = operatorValve.needToOpen;
                moveOperator = !openOperator;
            } else if (openOperator) {
                logger.info("You open valve " + operatorValve.valve.getName());
                openValves.add(operatorValve.valve.getName());
                currentPressure += operatorValve.valve.getFlowRate();
                openOperator = false;
                moveOperator = true;
            } else {
                logger.info("You remain at valve " + operatorValve.valve.getName());
            }

            if (moveElephant) {
                logger.info("The elephant moves to valve " + elephantValve.valve.getName());
                openElephant = elephantValve.needToOpen;
                moveElephant = !openElephant;
            } else if (openElephant) {
                logger.info("Elephant opens valve " + elephantValve.valve.getName());
                openValves.add(elephantValve.valve.getName());
                currentPressure += elephantValve.valve.getFlowRate();
                openElephant = false;
                moveElephant = true;
            } else {
                logger.info("The elephant remains at valve " + elephantValve.valve.getName());
            }

            if (moveOperator && operatorIterator.hasNext()) {
                operatorValve = operatorIterator.next();
            } else {
                moveOperator = false;
            }

            if (moveElephant && elephantIterator.hasNext()) {
                elephantValve = elephantIterator.next();
            } else {
                moveElephant = false;
            }
        }
        for (int time = currentMinute; time <= timeToElapse; time++) {
            logCurrentPathState(logger, time, currentPressure, openValves);
            logger.info("You remain at valve " + operatorValve.valve.getName());
            logger.info("The elephant remains at valve " + elephantValve.valve.getName());
            totalPressure += currentPressure;
        }
        logger.info("Total pressure released is " + totalPressure);
    }
}
