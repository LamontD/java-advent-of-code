package com.lamontd.adventofcode.advent2022.dec06;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TuningTrouble {
    private static final Logger logger = LoggerFactory.getLogger(TuningTrouble.class);

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Tuning Trouble");

        logger.info("Trying the simple approach...");
        LocalResourceInput resourceInput = new LocalResourceInput("day06/input.txt");
        DatastreamBuffer inputBuffer = new DatastreamBuffer(resourceInput.getInput().get(0));
        logger.info("First start of packet marker is after character " + inputBuffer.findStartOfPacketMarker());
        logger.info("First start of message marker is after character " + inputBuffer.findStartOfMessageMarker());

        timer.finish();
    }
}
