package com.lamontd.aoc.advent2021.dec02;

import org.apache.commons.lang3.StringUtils;

public enum Direction {
    FORWARD("forward"),
    UP("up"),
    DOWN("down");

    private String direction;

    private Direction(String direction) { this.direction = direction; }
    public static Direction findByDirection(String direction) {
        for (Direction cmd : Direction.values()) {
            if (StringUtils.equalsIgnoreCase(cmd.direction, direction)) {
                return cmd;
            }
        }
        return null;
    }
}
