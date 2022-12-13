package com.lamontd.adventofcode.advent2022.dec13;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestReceivedSignalParsing {

    @Test
    public void testPureIntParsing() {
        List<Object> intList = ReceivedSignal.parseSignalList("[1,1,3,1,1]");
        assertEquals(5, intList.size());
        assertTrue(intList.get(0) instanceof Integer);
        assertTrue(intList.get(1) instanceof Integer);
        assertTrue(intList.get(2) instanceof Integer);
        assertTrue(intList.get(3) instanceof Integer);
        assertTrue(intList.get(4) instanceof Integer);
    }

    @Test
    public void testEmptyList() {
        List<Object> emptyList = ReceivedSignal.parseSignalList("[]");
        assertTrue(emptyList.isEmpty());
    }

    @Test
    public void testSingleElementList() {
        List<Object> singleList = ReceivedSignal.parseSignalList("[9]");
        assertFalse(singleList.isEmpty());
        assertEquals(1, singleList.size());
        assertTrue(singleList.get(0) instanceof Integer);
        assertEquals(singleList.get(0), 1);
    }

    @Test
    public void testListOfLists() {
        List<Object> listOfLists = ReceivedSignal.parseSignalList("[[1],[2,3,4]");
        assertEquals(2, listOfLists.size());
        assertTrue(listOfLists.get(0) instanceof List);
        assertTrue(listOfLists.get(1) instanceof List);
        assertEquals(1, ((List<?>) listOfLists.get(0)).size());
        assertEquals(3, ((List<?>) listOfLists.get(1)).size());
    }

    @Test
    public void testMixedList() {
        List<Object> mixedList = ReceivedSignal.parseSignalList("[[1],4]");
        assertEquals(2, mixedList.size());
        assertTrue(mixedList.get(0) instanceof List);
        assertEquals(1, ((List<?>) mixedList.get(0)).size());
        assertTrue(mixedList.get(1) instanceof Integer);
        assertEquals(4, mixedList.get(1));
    }

    @Test
    public void testPureNestedList() {
        List<Object> pureNested = ReceivedSignal.parseSignalList("[[8,7,6]]");
        assertEquals(1, pureNested.size());
        assertTrue(pureNested.get(0) instanceof List);
        assertEquals(3, ((List<?>)pureNested.get(0)).size());
        assertEquals(List.of(8,7,6), (List<?>)pureNested.get(0));
    }

    @Test
    public void testReallyComplex() {
        List<Object> complex1 = ReceivedSignal.parseSignalList("[1,[2,[3,[4,[5,6,7]]]],8,9]");
        assertEquals(List.of(1, List.of(2, List.of(3, List.of(4, List.of(5, 6, 7)))), 8, 9), complex1);
    }

    @Test
    public void testActualComparisons() {
        ReceivedSignal firstLeft = new ReceivedSignal("[1,1,3,1,1]");
        ReceivedSignal firstRight = new ReceivedSignal("[1,1,5,1,1]");
        assertTrue(firstLeft.compareTo(firstRight) < 0);

        ReceivedSignal secondLeft = new ReceivedSignal("[[1],[2,3,4]");
        ReceivedSignal secondRight = new ReceivedSignal("[[1],4]");
        assertTrue(secondLeft.compareTo(secondRight) < 0);

        ReceivedSignal thirdLeft = new ReceivedSignal("[[4,4],4,4]");
        ReceivedSignal thirdRight = new ReceivedSignal("[[4,4],4,4,4]");
        assertTrue(thirdLeft.compareTo(thirdRight) < 0);

        ReceivedSignal fourthLeft = new ReceivedSignal("[7,7,7,7]");
        ReceivedSignal fourthRight = new ReceivedSignal("[7,7,7]");
        assertFalse(fourthLeft.compareTo(fourthRight) < 0);

        ReceivedSignal fifthLeft = new ReceivedSignal("[]");
        ReceivedSignal fifthRight = new ReceivedSignal("[3]");
        assertTrue(fifthLeft.compareTo(fifthRight) < 0);

        ReceivedSignal sixthLeft = new ReceivedSignal("[1,[2,[3,[4,[5,6,7]]]],8,9]");
        ReceivedSignal sixthRight = new ReceivedSignal("[1,[2,[3,[4,[5,6,0]]]],8,9]");
        assertFalse(sixthLeft.compareTo(sixthRight) < 0);

        ReceivedSignal seventhLeft = new ReceivedSignal("[[[]]]");
        ReceivedSignal seventhRight = new ReceivedSignal("[[]]");
        assertFalse(seventhLeft.compareTo(seventhRight) < 0);
    }
}
