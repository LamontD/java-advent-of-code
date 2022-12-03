package com.lamontd.aoc.advent2020.dec05;

import com.lamontd.aoc.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BoardingPassReader {
    private static final Logger logger = LoggerFactory.getLogger(BoardingPassReader.class);

    private Map<String, BoardingPass> boardingPassMap = new HashMap<>();

    public void addBoardingPass(String rawInput) {
        boardingPassMap.put(rawInput, new BoardingPass(rawInput));
    }

    public int findMaximumSeatId() {
        int maxSeatId = -1;
        int maxRow = -1;
        for (BoardingPass pass : boardingPassMap.values()) {
            maxSeatId = Math.max(maxSeatId, pass.getSeatId());
            maxRow = Math.max(maxRow, pass.getRow());
        }
        logger.info("Max row found is " + maxRow);
        return maxSeatId;
    }

    public int findMySeatId() {
        List<Integer> existingSeatIds = boardingPassMap.values()
                .stream().map(BoardingPass::getSeatId).collect(Collectors.toList());
        Collections.sort(existingSeatIds);
        for (int seatIndex = 1; seatIndex < existingSeatIds.size(); seatIndex++) {
            if (existingSeatIds.get(seatIndex) - existingSeatIds.get(seatIndex - 1) > 1) {
                // There's a missing Seat ID in here
                return existingSeatIds.get(seatIndex) - 1;
            }
        }
        throw new IllegalArgumentException("The stupid plane is all full!");
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to the Boarding Pass reader!");
        logger.info("Reading raw input...");
        LocalResourceInput localResourceInput = new LocalResourceInput("advent2020/day5/advent-day5-input.txt");
        logger.info("Generating boarding passes...");
        BoardingPassReader boardingPassReader = new BoardingPassReader();
        localResourceInput.getInput().stream().forEach(line -> boardingPassReader.addBoardingPass(line));
        logger.info("We read " + boardingPassReader.boardingPassMap.size() + " boarding passes");
        logger.info("I think the maximum seat ID is " + boardingPassReader.findMaximumSeatId());
        logger.info("Looking for my seat now...");
        int mySeatId = boardingPassReader.findMySeatId();
        logger.info("My seat Id is probably " + mySeatId);
        logger.info("This translates to row " + mySeatId / 8 + " seat " + mySeatId % 8);
//        List<Integer> seatIdList = new ArrayList<>(boardingPassReader.boardingPassMap.keySet());
//        Collections.sort(seatIdList, Comparator.reverseOrder());
//        logger.info("I think the maximum Seat ID is " + seatIdList.get(0));
        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");

    }
}
