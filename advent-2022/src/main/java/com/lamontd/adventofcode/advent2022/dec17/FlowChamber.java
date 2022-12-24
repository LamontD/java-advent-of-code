package com.lamontd.adventofcode.advent2022.dec17;

import com.lamontd.adventofcode.utils.coord.BigCoordinate;
import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class FlowChamber {
    private static final Logger logger = LoggerFactory.getLogger(FlowChamber.class);

    private final int chamberWidth;

    private final HashSet<BigCoordinate> rockyPoints = new HashSet<>();
    private long highestRock = 0;

    private final String jetPattern;
    private int jetPatternIndex = 0;

    private final List<FallingRock> rockPattern;

    private FallingRock currentRock;

    private long rockCount;

    private Iterator<FallingRock> rockPatternIterator;

    private final List<Quartet<Long, String, Long, String>> patternFlipRecords = new ArrayList<>();

    public FlowChamber(int width, String jetPatternString, List<FallingRock> rockPattern) {
        this.chamberWidth = width;
        this.highestRock = 0;
        this.jetPattern = jetPatternString;
        this.jetPatternIndex = 0;
        this.rockPattern = rockPattern;
        this.rockPatternIterator = rockPattern.listIterator();
        this.rockCount = 0;
    }

    public void resetState() {
        this.rockCount = 0;
        this.highestRock = 0;
        this.jetPatternIndex = 0;
        this.rockPatternIterator = rockPattern.listIterator();
        this.currentRock = null;
        this.patternFlipRecords.clear();
        this.rockyPoints.clear();
    }

    public void dropRocks(int totalRocksToDrop) {
        for (rockCount = 1; rockCount <= totalRocksToDrop; rockCount++) {
            currentRock = getNextRock();
            currentRock.orient(2, highestRock + 4);
            boolean rockIsFalling = true;
            while (rockIsFalling) {
                switch (getNextMoveFromPattern()) {
                    case LEFT:
                        if (canMoveLeft(currentRock)) {
                            currentRock.shiftLeft();
                        }
                        break;
                    case RIGHT:
                        if (canMoveRight(currentRock)) {
                            currentRock.shiftRight();
                        }
                }
                if (canFall(currentRock)) {
                    currentRock.drop();
                } else {
                    rockIsFalling = false;
                }
            }
            recalculateFloor(currentRock);
        }
    }

    public Pair<Pair<Long, Long>, Pair<Long, Long>> findFlipPattern() {
        this.resetState();
        this.dropRocks(10000);
        for (int index = 0; index < patternFlipRecords.size(); index++) {
            Quartet<Long, String, Long, String> currentFlipRecord = patternFlipRecords.get(index);
            int flipRecordDistance = 0;
            for (flipRecordDistance = 1; index + flipRecordDistance < patternFlipRecords.size(); flipRecordDistance++) {
                if (StringUtils.equals(currentFlipRecord.getValue1(), patternFlipRecords.get(index + flipRecordDistance).getValue1())) {
                    Quartet<Long, String, Long, String> nextMatchingFlipRecord = patternFlipRecords.get(index + flipRecordDistance);
                    long provisionalRockCountDelta = nextMatchingFlipRecord.getValue0() - currentFlipRecord.getValue0();
                    long provisionalHeightDelta = nextMatchingFlipRecord.getValue2() - currentFlipRecord.getValue2();
                    int confirmationIndex = index + flipRecordDistance + flipRecordDistance;
                    if (confirmationIndex < patternFlipRecords.size()
                            && patternFlipRecords.get(confirmationIndex).equals(Quartet.with(nextMatchingFlipRecord.getValue0() + provisionalRockCountDelta,
                            nextMatchingFlipRecord.getValue1(), nextMatchingFlipRecord.getValue2() + provisionalHeightDelta, currentFlipRecord.getValue3()))) {
                        return Pair.with(Pair.with(currentFlipRecord.getValue0(), currentFlipRecord.getValue2()),
                                Pair.with(provisionalRockCountDelta, provisionalHeightDelta));
                    }
                }
            }
        }
        return null;
    }

    public long getHighestRock() {
        return highestRock;
    }

    protected boolean canMoveRight(FallingRock trackingRock) {
        for (BigCoordinate trackingPoint : trackingRock.getPoints()) {
            if (trackingPoint.getX() == chamberWidth - 1) {
                return false;
            }
            if (rockyPoints.contains(BigCoordinate.of(trackingPoint.getX() + 1, trackingPoint.getY()))) {
                return false;
            }
        }
        return true;
    }

    protected boolean canMoveLeft(FallingRock trackingRock) {
        for (BigCoordinate trackingPoint : trackingRock.getPoints()) {
            if (trackingPoint.getX() == 0) {
                return false;
            }
            if (rockyPoints.contains(BigCoordinate.of(trackingPoint.getX() - 1, trackingPoint.getY()))) {
                return false;
            }
        }
        return true;
    }

    protected boolean canFall(FallingRock trackingRock) {
        for (BigCoordinate trackingPoint : trackingRock.getPoints()) {
            if (trackingPoint.getY() == 1) {
                return false;
            }
            if (rockyPoints.contains(BigCoordinate.of(trackingPoint.getX(), trackingPoint.getY() - 1))) {
                return false;
            }
        }
        return true;
    }

    protected RockMove getNextMoveFromPattern() {
        if (jetPatternIndex == jetPattern.length()) {
            jetPatternIndex = 0;
            patternFlipRecords.add(Quartet.with(rockCount, currentRock.getName(), highestRock,
                    getRowString(highestRock) + "|" + getRowString(highestRock - 1) + "|" + getRowString(highestRock - 2)));
        }
        char nextMove = jetPattern.charAt(jetPatternIndex++);
        switch (nextMove) {
            case '<':
                return RockMove.LEFT;
            case '>':
                return RockMove.RIGHT;
        }
        throw new IllegalArgumentException("Unsupported next move of " + nextMove);
    }

    protected String getRowString(long rowNum) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < chamberWidth; i++) {
            sb.append(rockyPoints.contains(BigCoordinate.of(i, rowNum)) ? '#' : '.');
        }
        return sb.toString();
    }

    protected FallingRock getNextRock() {
        if (!rockPatternIterator.hasNext()) {
            rockPatternIterator = rockPattern.listIterator();
        }
        return rockPatternIterator.next();
    }

    protected void recalculateFloor(FallingRock currentRock) {
        rockyPoints.addAll(currentRock.getPoints());
        for (BigCoordinate rockyTops : currentRock.getPoints()) {
            this.highestRock = Math.max(this.highestRock, rockyTops.getY());
        }
    }

    public String getVisualChamber() {
        StringBuilder sb = new StringBuilder();
        for (long processingRow = highestRock + 8; processingRow >= 1; processingRow--) {
            sb.append("\n|");
            for (int column = 0; column < chamberWidth; column++) {
                char pointToDraw = '.';
                if (currentRock != null && currentRock.getPoints().contains(BigCoordinate.of(column, processingRow))) {
                    pointToDraw = '@';
                } else if (rockyPoints.contains(BigCoordinate.of(column, processingRow))) {
                    pointToDraw = '#';
                }
                sb.append(pointToDraw);
            }
            sb.append("|");
        }
        sb.append("\n+-------+");
        return sb.toString();

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FlowChamber contains ").append(rockyPoints.size()).append(" discrete points").append("\n");
        sb.append("Pattern size is ").append(jetPattern.length()).append("\n");
        sb.append("FlowChamber: Highest rock is at height ").append(highestRock);
        return sb.toString();
    }

}
