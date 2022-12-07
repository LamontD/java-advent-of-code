package com.lamontd.adventofcode.advent2022.dec06;

import java.util.HashSet;
import java.util.Set;

public class DatastreamBuffer {
    private final String bufferString;

    public DatastreamBuffer(String buffer) { this.bufferString = buffer; }

    public String getBufferString() {
        return bufferString;
    }

    public int findStartOfPacketMarker() {
        int startMarker = 0;
        for (startMarker = 4; startMarker < bufferString.length(); startMarker++) {
            // This is my first evil solution. I'm not proud of this.
            try {
                Set<Character> fourSet = Set.of(bufferString.charAt(startMarker - 1), bufferString.charAt(startMarker - 2), bufferString.charAt(startMarker - 3), bufferString.charAt(startMarker - 4));
                if (fourSet.size() == 4) {
                    break;
                }
            } catch (IllegalArgumentException iae) {
                continue;
            }
        }
        return startMarker;
    }

    public int findStartOfMessageMarker() {
        int startMarker = 0;
        for (startMarker = 14; startMarker < bufferString.length(); startMarker++ ) {
            Set<Character> fourteenSet = new HashSet<>();
            try {
                for (int counter = 1; counter <= 14; counter++) {
                    if (fourteenSet.contains(bufferString.charAt(startMarker - counter))) {
                        throw new IllegalArgumentException();
                    }
                    fourteenSet.add(bufferString.charAt(startMarker - counter));
                }
                if (fourteenSet.size() == 14) {
                    break;
                }
            } catch (IllegalArgumentException iae) {
                continue;
            }
        }
        return startMarker;
    }
}
