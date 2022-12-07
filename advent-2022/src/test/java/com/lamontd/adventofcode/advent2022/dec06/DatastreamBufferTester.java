package com.lamontd.adventofcode.advent2022.dec06;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatastreamBufferTester {

    @Test
    public void testStartOfPacketMarker() {
        DatastreamBuffer buffer1 = new DatastreamBuffer("mjqjpqmgbljsphdztnvjfqwrcgsmlb");
        assertEquals(7, buffer1.findStartOfPacketMarker());

        DatastreamBuffer buffer2 = new DatastreamBuffer("bvwbjplbgvbhsrlpgdmjqwftvncz");
        assertEquals(5, buffer2.findStartOfPacketMarker());

        DatastreamBuffer buffer3 = new DatastreamBuffer("nppdvjthqldpwncqszvftbrmjlhg");
        assertEquals(6, buffer3.findStartOfPacketMarker());

        DatastreamBuffer buffer4 = new DatastreamBuffer("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg");
        assertEquals(10, buffer4.findStartOfPacketMarker());

        DatastreamBuffer buffer5 = new DatastreamBuffer("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw");
        assertEquals(11, buffer5.findStartOfPacketMarker());
    }

    @Test
    public void testStartOfMessageMarker() {
        DatastreamBuffer buffer1 = new DatastreamBuffer("mjqjpqmgbljsphdztnvjfqwrcgsmlb");
        assertEquals(19, buffer1.findStartOfMessageMarker());

        DatastreamBuffer buffer2 = new DatastreamBuffer("bvwbjplbgvbhsrlpgdmjqwftvncz");
        assertEquals(23, buffer2.findStartOfMessageMarker());

        DatastreamBuffer buffer3 = new DatastreamBuffer("nppdvjthqldpwncqszvftbrmjlhg");
        assertEquals(23, buffer3.findStartOfMessageMarker());

        DatastreamBuffer buffer4 = new DatastreamBuffer("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg");
        assertEquals(29, buffer4.findStartOfMessageMarker());

        DatastreamBuffer buffer5 = new DatastreamBuffer("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw");
        assertEquals(26, buffer5.findStartOfMessageMarker());
    }
}
