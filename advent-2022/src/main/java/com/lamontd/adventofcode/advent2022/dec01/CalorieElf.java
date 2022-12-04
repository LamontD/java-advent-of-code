package com.lamontd.adventofcode.advent2022.dec01;

public class CalorieElf {
    private long totalCalories = 0;
    private final int elfNum;

    public CalorieElf(int elfNum) {
        this.elfNum = elfNum;
    }

    public void addCalories(long newCalories) {
        totalCalories += newCalories;
    }

    public long getTotalCalories() {
        return totalCalories;
    }

    public int getElfNum() {
        return elfNum;
    }
}
