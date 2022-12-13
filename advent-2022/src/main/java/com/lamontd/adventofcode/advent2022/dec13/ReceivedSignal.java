package com.lamontd.adventofcode.advent2022.dec13;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ReceivedSignal implements Comparable<ReceivedSignal> {
    private final List<Object> packets;

    public ReceivedSignal(String inputSignal) {
        this.packets = parseSignalList(inputSignal);
    }

    public static List<Object> parseSignalList(String signal) {
        List<Object> outputList = new ArrayList<>();
        for (int index = 1; index < signal.length() - 1; index++) {
            if (signal.charAt(index) == '[') {
                int closingParen = getIndexOfBalancingParenthesis(signal, index);
                outputList.add(parseSignalList(signal.substring(index, closingParen)));
                index = closingParen;
            } else if (signal.charAt(index) == ']') {
                // Do nothing; this item is empty and should be okay
            } else {
                int initialIndex = index;
                while (signal.charAt(index) != ',' && signal.charAt(index) != ']') {
                    index++;
                }
                outputList.add(Integer.parseInt(signal, initialIndex, index, 10));
            }
        }
        return outputList;
    }

    public static int getIndexOfBalancingParenthesis(String input, int startingParen) {
        int index;
        int depth = 1;
        for (index = startingParen + 1; depth > 0; index++) {
            switch (input.charAt(index)) {
                case '[':
                    depth++;
                    break;
                case ']':
                    depth--;
                    break;
            }
        }
        return index;
    }

    @Override
    public int compareTo(ReceivedSignal rhs) {
        return compareLists(packets, rhs.packets);
    }

    private static int compareLists(List left, List right) {
        ListIterator<Object> leftIterator = left.listIterator();
        ListIterator<Object> rightIterator = right.listIterator();
        while (leftIterator.hasNext() && rightIterator.hasNext()) {
            Object leftValue = leftIterator.next();
            Object rightValue = rightIterator.next();
            int compareValue = 0;
            if (leftValue instanceof Integer && rightValue instanceof Integer) {
                Integer leftInteger = (Integer) leftValue;
                Integer rightInteger = (Integer) rightValue;
                compareValue = leftInteger.compareTo(rightInteger);
            } else if (leftValue instanceof List && rightValue instanceof List) {
                compareValue = compareLists((List)leftValue, (List)rightValue);
            } else if (leftValue instanceof Integer) {
                compareValue = compareLists(List.of(leftValue), (List)rightValue);
            } else if (rightValue instanceof Integer) {
                compareValue = compareLists((List)leftValue, List.of(rightValue));
            }
            if (compareValue == 0) {
                continue;
            }
            return compareValue;
        }
        if (leftIterator.hasNext()) {
            return 1;
        }
        if (rightIterator.hasNext()) {
            return -1;
        }
        return 0;
    }
}
