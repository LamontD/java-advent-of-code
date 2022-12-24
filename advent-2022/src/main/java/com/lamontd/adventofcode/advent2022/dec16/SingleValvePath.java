package com.lamontd.adventofcode.advent2022.dec16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class SingleValvePath extends ValvePath {
    private static final Logger logger = LoggerFactory.getLogger(SingleValvePath.class);
    protected final List<ValveContainer> valvePath = new ArrayList<>();

    protected final List<String> openValves = new ArrayList<>();

    protected int potentialFlow;
    protected int elapsedTime = 0;

    private final StringBuilder hashablePath = new StringBuilder();

    protected SingleValvePath() {

    }
    public SingleValvePath(Valve startingValve) {
        valvePath.add(ValveContainer.of(startingValve, false));
        hashablePath.append(startingValve.getName());
        potentialFlow = 0;
        elapsedTime = 0;
    }

    @Override
    public SingleValvePath clone()  {
        SingleValvePath clonedValvePath = new SingleValvePath();
        clonedValvePath.maxTimeForPath = maxTimeForPath;
        clonedValvePath.valvePath.addAll(valvePath);
        clonedValvePath.potentialFlow = this.potentialFlow;
        clonedValvePath.elapsedTime = this.elapsedTime;
        clonedValvePath.openValves.addAll(this.openValves);
        clonedValvePath.hashablePath.append(this.hashablePath.toString());
        return clonedValvePath;
    }

    public void addValve(Valve valveToAdd, boolean shouldOpen) {
        valvePath.add(ValveContainer.of(valveToAdd, shouldOpen));
        if (hashablePath.length() > 0) {
            hashablePath.append("-");
        }
        hashablePath.append(valveToAdd);
        if (shouldOpen) {
            hashablePath.append("[O]");
        }
        elapsedTime++;
        if (shouldOpen) {
            elapsedTime++;
            potentialFlow += valveToAdd.getFlowRate() * (maxTimeForPath - elapsedTime - 1);
            openValves.add(valveToAdd.getName());
        }
    }

    public List<ValveContainer> getValvePath() {
        return valvePath;
    }

    @Override
    public ValveContainer getEdgeContainer() {
        return valvePath.isEmpty() ? null : valvePath.get(valvePath.size() - 1);
    }

    @Override
    public int getPotentialFlow() {
        return potentialFlow;
    }

    @Override
    public int getElapsedTime() {
        return elapsedTime;
    }



    @Override
    public int getTimeRemaining() {
        return maxTimeForPath - elapsedTime;
    }

    public String getImmediateBacktrackValveName() {
        return valvePath.size() > 1 ? valvePath.get(valvePath.size() - 2).valve.getName() : "";
    }

    public boolean openedValveOnPath(Valve valveToCheck) {
        return openValves.contains(valveToCheck.getName());
    }

    public List<String> getValvesOpenedOnPath() {
        return openValves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleValvePath that = (SingleValvePath) o;
        return valvePath.equals(that.valvePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashablePath.toString());
    }

    @Override
    public String toString() {
        List<String> valvesInOrder = valvePath.stream()
                .map(container -> container.getValve().getName() + (container.needToOpen ? " (open)" : ""))
                .collect(Collectors.toList());
        return "ValuePath: " + String.join(" --> ", valvesInOrder);
    }

    @Override
    public boolean valid() {
        List<String> allValvesOpened = getValvesOpenedOnPath();
        Set<String> uniqueValvesOpened = new HashSet<>(allValvesOpened);
        return uniqueValvesOpened.size() == getValvesOpenedOnPath().size();
    }

    public String getHashableString() {
        return hashablePath.toString();
    }

    @Override
    public List<SingleValvePath> getExtendedPaths() {
        List<SingleValvePath> extendedPaths = new ArrayList<>();
        ValveContainer edgeContainer = valvePath.get(valvePath.size() - 1);
        Valve backtrackValve = valvePath.size() > 1 ? valvePath.get(valvePath.size() - 2).valve : null;
        for (Valve connectedValve : edgeContainer.valve.getTunnels()) {
            if (!edgeContainer.needToOpen && Objects.equals(connectedValve, backtrackValve)) {
                continue;
            }
            SingleValvePath extension = this.clone();
            extension.addValve(connectedValve, false);
            extendedPaths.add(extension);

            if (connectedValve.canBeOpenedForFlow() && !openedValveOnPath(connectedValve)) {
                SingleValvePath extendAndOpen = this.clone();
                extendAndOpen.addValve(connectedValve, true);
                extendedPaths.add(extendAndOpen);
            }
        }
        return extendedPaths;
    }

    @Override
    public void logPathDetails(int timeToElapse) {
        int currentMinute = 0;
        int currentPressure = 0;
        int totalPressure = 0;
        List<String> openValves = new ArrayList<>();

        for (ValveContainer container : valvePath) {
            currentMinute++;
            if (currentMinute > timeToElapse) {
                break;
            }
            totalPressure += currentPressure;
            logCurrentPathState(logger, currentMinute, currentPressure, openValves);
            logger.info("You move to valve " + container.getValve().getName());
            if (container.isNeedToOpen()) {
                currentMinute++;
                totalPressure += currentPressure;
                logCurrentPathState(logger, currentMinute, currentPressure, openValves);
                logger.info("You open valve " + container.getValve().getName());
                currentPressure += container.getValve().getFlowRate();
                openValves.add(container.getValve().getName());
            }
        }
        for (int time = currentMinute; time <= timeToElapse; time++) {
            logCurrentPathState(logger, time, currentPressure, openValves);
            totalPressure += currentPressure;
        }
        logger.info("Total pressure released is " + totalPressure);

    }

}
