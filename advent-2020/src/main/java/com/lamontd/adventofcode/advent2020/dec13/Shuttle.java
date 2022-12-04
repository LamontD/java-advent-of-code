package com.lamontd.adventofcode.advent2020.dec13;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Shuttle {
    private static final Logger logger = LoggerFactory.getLogger(Shuttle.class);

    private final int shuttleId;

    public Shuttle(int shuttleId) { this.shuttleId = shuttleId; }

    public int firstDepartureAfterTimestamp(int timestamp) {
        if (timestamp % shuttleId == 0) {
            return timestamp;
        }
        int timestampMulti = timestamp / shuttleId;
        return (timestampMulti + 1) * shuttleId;
    }

    public int howLongWillIHaveToWait(int timestamp) {
        return timestamp % shuttleId == 0 ? 0 : shuttleId - timestamp % shuttleId;
    }

    public boolean leavesAtTimestamp(int departureTimestamp) { return departureTimestamp % shuttleId == 0; }

    public int getShuttleId() {
        return shuttleId;
    }

    @Override
    public String toString() {
        return "Shuttle #" + shuttleId;
    }
}
