package com.lamontd.aoc.advent2020.dec03;

import java.util.List;
import java.util.stream.Collectors;

public class BiomeEntry {
    private final List<Boolean> treePattern;
    private static final Character EMPTY_SPACE = '.';
    private static final Character TREE = '#';

    public BiomeEntry(String patternAsString) {
        this.treePattern = patternAsString.chars().mapToObj(ch -> ch == TREE).collect(Collectors.toList());
    }

    public boolean hasTree(int position) {
        return treePattern.get(position % treePattern.size());
    }
}
