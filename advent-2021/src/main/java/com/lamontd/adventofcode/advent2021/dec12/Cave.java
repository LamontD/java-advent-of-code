package com.lamontd.adventofcode.advent2021.dec12;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cave {
    public static final String START_NAME = "start";
    public static final String END_NAME = "end";

    private String name;
    private Type caveType;
    private List<Cave> connectedCaves = new ArrayList<>();

    public Cave(String caveName) {
        this.name = caveName;
        if (StringUtils.equalsIgnoreCase(name, START_NAME)) {
            this.caveType = Type.START;
        } else if (StringUtils.equalsIgnoreCase(name, END_NAME)) {
            this.caveType = Type.END;
        } else if (StringUtils.isAllUpperCase(name)) {
            this.caveType = Type.LARGE;
        } else {
            this.caveType = Type.SMALL;
        }
    }

    public void connect(Cave otherCave) {
        this.connectedCaves.add(otherCave);
    }

    public List<Cave> getConnectedCaves() { return this.connectedCaves; }

    public String getName() { return this.name; }
    public Type getCaveType() { return this.caveType; }

    public enum Type {
        START, SMALL, LARGE, END;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cave cave = (Cave) o;
        return Objects.equals(name, cave.name) && caveType == cave.caveType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, caveType);
    }
}
