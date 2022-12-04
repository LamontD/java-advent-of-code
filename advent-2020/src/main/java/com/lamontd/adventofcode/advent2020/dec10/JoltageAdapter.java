package com.lamontd.adventofcode.advent2020.dec10;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class JoltageAdapter implements Comparable<JoltageAdapter>{
    private final long joltageRating;
    private final Set<JoltageAdapter> adapterConnections = new HashSet<>();
    public JoltageAdapter(long joltageRating) {
        this.joltageRating = joltageRating;
    }

    public boolean canConnectToSource(long sourceJoltage) {
        return Math.abs(joltageRating - sourceJoltage) <= 3;
    }

    public boolean canConnectToSource(JoltageAdapter adapterSource) {
        return canConnectToSource(adapterSource.joltageRating);
    }

    public void addConnection(JoltageAdapter adapter) {
        adapterConnections.add(adapter);
    }

    public Set<JoltageAdapter> getAdapterConnections() { return this.adapterConnections; }
    public int getNumAdapterConnections() { return this.adapterConnections.size(); }

    public int calculateJoltageDifference(JoltageAdapter adapterSource) {
        return (calculateJoltageDifference(adapterSource.joltageRating));
    }

    public int calculateJoltageDifference(long adapterJoltage) { return (int)(this.joltageRating - adapterJoltage); }

    public long getJoltageRating() {
        return joltageRating;
    }

    @Override
    public int compareTo(JoltageAdapter right) {
        return Long.compare(this.joltageRating, right.joltageRating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoltageAdapter that = (JoltageAdapter) o;
        return joltageRating == that.joltageRating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(joltageRating);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("JoltageAdapter{").append(joltageRating).append("}");
        if (!adapterConnections.isEmpty()) {
            sb.append(" has ").append(adapterConnections.size()).append(" connections [ ");
            adapterConnections.forEach(conn -> sb.append("JoltageAdapter{").append(conn.getJoltageRating()).append("} "));
            sb.append("]");
        }
        return sb.toString();
    }
}
