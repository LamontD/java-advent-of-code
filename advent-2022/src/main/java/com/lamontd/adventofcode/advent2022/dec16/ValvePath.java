package com.lamontd.adventofcode.advent2022.dec16;

import org.slf4j.Logger;

import java.util.List;

public abstract class ValvePath implements Cloneable {

    public abstract boolean valid();

    protected int maxTimeForPath = 30;

    public abstract int getPotentialFlow();

    public abstract int getElapsedTime();

    public abstract int getTimeRemaining();

    public abstract List<? extends ValvePath> getExtendedPaths();

    public abstract List<String> getValvesOpenedOnPath();

    public abstract ValveContainer getEdgeContainer();

    public abstract void logPathDetails(int timeToElapse);

    public void setMaxTimeForPath(int maxTimeForPath) {
        this.maxTimeForPath = maxTimeForPath;
    }

    public static void logCurrentPathState(Logger logger, int currentMinute, int currentPressure, List<String> openValves) {
        logger.info("");
        logger.info("== Minute " + currentMinute + " ==");
        if (openValves.isEmpty()) {
            logger.info("No valves are open.");
        } else if (openValves.size() == 1) {
            logger.info("Valve " + openValves.get(0) + " is open, releasing " + currentPressure + " pressure");
        } else {
            logger.info("Valves " + String.join(", ", openValves) + " are open, releasing " + currentPressure + " pressure");
        }
    }

}
