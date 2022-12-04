package com.lamontd.adventofcode.advent2021.dec12;

import java.util.HashMap;
import java.util.Map;

public class CaveMap {
    private Map<String, Cave> caveNameMap = new HashMap<>();

    public Cave findCaveByName(String caveName) { return caveNameMap.get(caveName); }

    public void addCavePath(String pathSpecificaiton) {
        String[] caveNameSplit = pathSpecificaiton.split("-");
        if (caveNameSplit.length != 2) {
            throw new IllegalArgumentException("Invalid cave path specification " + pathSpecificaiton);
        }
        Cave leftCave = findExistingCaveOrCreateNewCave(caveNameSplit[0]);
        Cave rightCave = findExistingCaveOrCreateNewCave(caveNameSplit[1]);
        leftCave.connect(rightCave);
        rightCave.connect(leftCave);
    }

    public Cave getStartCave() { return caveNameMap.get(Cave.START_NAME); }
    public Cave getEndCave() { return caveNameMap.get(Cave.END_NAME); }

    protected Cave findExistingCaveOrCreateNewCave(String caveName) {
        if (!caveNameMap.containsKey(caveName)) {
            caveNameMap.put(caveName, new Cave(caveName));
        }
        return caveNameMap.get(caveName);
    }

    public int getNumberOfCaves() { return caveNameMap.size(); }

}
