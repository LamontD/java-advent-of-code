package com.lamontd.adventofcode.advent2023.day02;

import lombok.Getter;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CubeGame {
    @Getter
    private final int gameId;

    @Getter
    private final List<BagPull> pulls = new ArrayList<>();

    private final int maximumRedCubesUsed;
    private final int maximumGreenCubesUsed;
    private final int maximumBlueCubesUsed;

    public CubeGame(String gameDescription) {
        String[]gameTokenizer = gameDescription.split(":");
        gameId = Integer.parseInt(gameTokenizer[0].split(" ")[1]);

        for (String pull : gameTokenizer[1].split(";")) {
            pulls.add(new BagPull(pull.trim()));
        }
        AtomicInteger blueMax = new AtomicInteger(0);
        AtomicInteger redMax = new AtomicInteger(0);
        AtomicInteger greenMax = new AtomicInteger(0);
        pulls.forEach(bagPull -> {
            redMax.set(Math.max(redMax.get(), bagPull.getRedCubes()));
            blueMax.set(Math.max(blueMax.get(), bagPull.getBlueCubes()));
            greenMax.set(Math.max(greenMax.get(), bagPull.getGreenCubes()));
        });
        maximumRedCubesUsed = redMax.get();
        maximumGreenCubesUsed = greenMax.get();
        maximumBlueCubesUsed = blueMax.get();
    }

    public boolean isPossible(int redCubes, int greenCubes, int blueCubes) {
        return maximumRedCubesUsed <= redCubes
                && maximumGreenCubesUsed <= greenCubes
                && maximumBlueCubesUsed <= blueCubes;
    }

    public Triplet<Integer, Integer, Integer> getMinimumCubesNeeded() {
        return Triplet.with(maximumRedCubesUsed, maximumGreenCubesUsed, maximumBlueCubesUsed);
    }

    public long getMinimumPower() {
        Triplet<Integer, Integer, Integer> minimumCubes = getMinimumCubesNeeded();
        return (long) minimumCubes.getValue0() * minimumCubes.getValue1() * minimumCubes.getValue2();
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Game ").append(gameId).append(": ");
        pulls.forEach(pull -> sb.append(pull.toString()).append("; "));
        return sb.toString();
    }
}
