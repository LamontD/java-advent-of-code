package com.lamontd.adventofcode.advent2022.dec16;

import java.util.Objects;

public class ValveContainer {
    public Valve valve;
    public boolean needToOpen;

    protected static ValveContainer of(Valve valve, boolean needToOpen) {
        ValveContainer container = new ValveContainer();
        container.valve = valve;
        container.needToOpen = needToOpen;
        return container;
    }

    public Valve getValve() {
        return valve;
    }

    public boolean isNeedToOpen() {
        return needToOpen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValveContainer that = (ValveContainer) o;
        boolean isEqual = needToOpen == that.needToOpen && valve.equals(that.valve);
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valve, needToOpen);
    }
}
