package com.lamontd.adventofcode.advent2023.day02;

import lombok.Getter;

public class BagPull {
    @Getter private int redCubes;

    @Getter private int greenCubes;

    @Getter private int blueCubes;

    public BagPull(String inputString) {
        for (String input : inputString.split(",")) {
            String[] splitInput = input.trim().split(" ");
            int value = Integer.parseInt(splitInput[0]);
            switch(splitInput[1]) {
                case "blue":
                    blueCubes = value;
                    break;
                case "green":
                    greenCubes = value;
                    break;
                case "red":
                    redCubes = value;
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return redCubes + " red, " + greenCubes + " green, " + blueCubes + " blue";
    }
}
