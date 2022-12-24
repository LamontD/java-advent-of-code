package com.lamontd.adventofcode.advent2022.dec16;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Valve implements Comparable<Valve> {
    private final String name;
    private final int flowRate;
    private final List<Valve> tunnels = new ArrayList<>();

    public Valve(String name, int flowRate) {
        this.name = name;
        this.flowRate = flowRate;
    }

    public void addConnection(Valve outboundValve) {
        tunnels.add(outboundValve);
    }

    public String getName() {
        return name;
    }

    public int getFlowRate() {
        return flowRate;
    }

    public List<Valve> getTunnels() {
        return tunnels;
    }

    public boolean canBeOpenedForFlow() {
        return flowRate > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valve valve = (Valve) o;
        return Objects.equals(name, valve.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Valve o) {
        return StringUtils.compare(this.name, o.name);
    }

    @Override
    public String toString() {
        List<String> valveNames = tunnels.stream().map(Valve::getName).collect(Collectors.toList());
        return "Valve " + name + " has flow rate=" + flowRate +
                (valveNames.size() == 1 ? "; tunnel leads to valve " : "; tunnels lead to valves ")
                + String.join(", ", valveNames);
    }
}
