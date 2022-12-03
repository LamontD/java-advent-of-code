package com.lamontd.aoc.utils;

import java.util.HashSet;
import java.util.stream.Stream;

public class StringCombiner {
    public static String getCommonCharacters(String firstString, String... additionalStrings) {
        final HashSet<Character> combinerSet = toCharHashSet(firstString);

        Stream.of(additionalStrings).forEach( str -> {
            HashSet<Character> nextSet = toCharHashSet(str);
            combinerSet.retainAll(nextSet);
        });

        return toString(combinerSet);

    }

    public static HashSet<Character> toCharHashSet(String str) {
        HashSet<Character> characterHashSet = new HashSet<>();
        for (Character ch : str.toCharArray()) {
            characterHashSet.add(Character.valueOf(ch));
        }
        return characterHashSet;
    }

    public static String toString(HashSet<Character> characterHashSet) {
        StringBuilder sb = new StringBuilder(characterHashSet.size());
        for (Character ch : characterHashSet) {
            sb.append(ch);
        }
        return sb.toString();
    }
}
