package com.lamontd.adventofcode.advent2020.dec24;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class LobbyLayout {
    private static final Logger logger = LoggerFactory.getLogger(LobbyLayout.class);

    private final HexFloor hexFloor;
    public LobbyLayout() {
        this.hexFloor = new HexFloor();
    }

    public void traverseAndFlipFloorTiles(List<String> traversals) {
        for (String traversal : traversals) {
            HexTile thisTile = hexFloor.traverseFloor(traversal);
            thisTile.flipTile();
        }
    }

    public void performDailyOperation(int numDays) {
        for (int cnt=0; cnt < numDays; cnt++) {
            hexFloor.performDailyOperations();
        }
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Lobby Layout");
        logger.info("Initializing our floor...");
        LobbyLayout layout = new LobbyLayout();
        logger.info("Getting the traversal strings...");
        LocalResourceInput resourceInput = new LocalResourceInput("day24/input.txt");
        logger.info("We have " + resourceInput.getInput().size() + " traversal strings");
        logger.info("Part 1: Traversing our floor and flipping as we go...");
        layout.traverseAndFlipFloorTiles(resourceInput.getInput());
        logger.info("We have " + layout.hexFloor.getBlackTileCount() + " black tiles after traversing the floor");
        logger.info("Part 2: Make the exhibit alive!");
        layout.performDailyOperation(100);
        logger.info("Part 2: After 100 days, I see " + layout.hexFloor.getBlackTileCount() + " black tiles");
        timer.finish();
    }
}
