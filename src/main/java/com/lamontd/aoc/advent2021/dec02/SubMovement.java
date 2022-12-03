package com.lamontd.aoc.advent2021.dec02;

import lombok.Value;

@Value
public class SubMovement {
    Direction direction;
    int units;

    public SubMovement(String input) throws IllegalArgumentException {
        String[] vals = input.split(" ");
        if (vals.length != 2) {
            throw new IllegalArgumentException("Illegal movement input of " +input);
        }
        this.direction = Direction.findByDirection(vals[0]);
        if (this.direction == null) {
            throw new IllegalArgumentException("Invalid sub direction of " + vals[0]);
        }
        try {
            this.units = Integer.parseInt(vals[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid sub movement units of " + vals[1]);
        }
    }

}
