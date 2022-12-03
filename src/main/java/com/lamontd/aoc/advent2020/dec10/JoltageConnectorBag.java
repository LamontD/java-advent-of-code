package com.lamontd.aoc.advent2020.dec10;

import com.lamontd.aoc.utils.BasicCounter;

import java.util.*;

public class JoltageConnectorBag {
    private final List<JoltageAdapter> availableAdapters;
    private final JoltageAdapter maximumJoltage;

    public JoltageConnectorBag(List<Long> adapterJoltages) {
        this.availableAdapters = new ArrayList<>();
        long highestJoltage = 0L;
        for (Long adapterJoltage : adapterJoltages) {
            this.availableAdapters.add(new JoltageAdapter(adapterJoltage));
            highestJoltage = Math.max(highestJoltage, adapterJoltage);
        }
        Collections.sort(this.availableAdapters);
        this.maximumJoltage = new JoltageAdapter(highestJoltage + 3);
    }

    public JoltageConnectorBag(final Collection<JoltageAdapter> joltages, JoltageAdapter maximumJoltage) {
        this.availableAdapters = new ArrayList<>(joltages);
        Collections.sort(availableAdapters);
        this.maximumJoltage = maximumJoltage;
    }

    public boolean canConnectEndToEnd() {
        JoltageAdapter sourceAdapter = new JoltageAdapter(0L);
        for (JoltageAdapter adapter : availableAdapters) {
            if (!adapter.canConnectToSource(sourceAdapter)) {
                return false;
            }
            sourceAdapter = adapter;
        }
        return maximumJoltage.canConnectToSource(sourceAdapter);
    }

    public List<JoltageAdapter> getSuperfluousAdapters() {
        JoltageAdapter sourceAdapter = new JoltageAdapter(0L);
        List<JoltageAdapter> superfluousAdapters = new ArrayList<>();
        for (int index = 0; index < availableAdapters.size() - 1; index++) {
            if (availableAdapters.get(index+1).canConnectToSource(sourceAdapter)) {
                superfluousAdapters.add(availableAdapters.get(index));
            }
            sourceAdapter = availableAdapters.get(index);
        }
        Collections.sort(superfluousAdapters);
        return superfluousAdapters;
    }

    public Map<Integer, BasicCounter> calculateDifferenceMap() {
        Map<Integer, BasicCounter> differenceMap = new HashMap<>();
        long sourceJoltage = 0L;
        for (JoltageAdapter adapter : availableAdapters) {
            incrementDifferenceMapForJoltage(differenceMap, adapter.calculateJoltageDifference(sourceJoltage));
            sourceJoltage = adapter.getJoltageRating();
        }
        incrementDifferenceMapForJoltage(differenceMap, maximumJoltage.calculateJoltageDifference(sourceJoltage));
        return differenceMap;
    }


    private static void incrementDifferenceMapForJoltage(Map<Integer, BasicCounter> differenceMap, int joltageDifference) {
        if (!differenceMap.containsKey(joltageDifference))
            differenceMap.put(joltageDifference, new BasicCounter());
        differenceMap.get(joltageDifference).increment();
    }

    public List<JoltageAdapter> getAvailableAdapters() {
        return availableAdapters;
    }

    public JoltageAdapter getMaximumJoltage() {
        return maximumJoltage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(0), ");
        for (JoltageAdapter adapter : availableAdapters) {
            sb.append(adapter.getJoltageRating()).append(", ");
        }
        sb.append("(").append(maximumJoltage.getJoltageRating()).append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoltageConnectorBag that = (JoltageConnectorBag) o;
        return availableAdapters.size() == that.availableAdapters.size()
        && availableAdapters.containsAll(that.availableAdapters)
                && maximumJoltage.equals(that.maximumJoltage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(availableAdapters, maximumJoltage);
    }
}
