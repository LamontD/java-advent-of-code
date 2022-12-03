package com.lamontd.aoc.advent2020.dec14;

import com.lamontd.aoc.utils.LocalResourceInput;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DockingData {
    private static final Logger logger = LoggerFactory.getLogger(DockingData.class);

    private final SeaportComputer v1DockComputer;
    private final SeaportComputer v2DockComputer;

    public DockingData() {
        this.v1DockComputer = new SeaportComputer();
        this.v2DockComputer = new SeaportComputer();
    }

    public void executeV1Program(List<String> programText) throws IllegalSeaportMemoryException, IllegalArgumentException {
        logger.info("Executing V1 program with " + programText.size() + " lines");
        for (String programLine : programText) {
            if (StringUtils.startsWith(programLine, "mask =")) {
                String newProgramMask = programLine.substring(7);
                this.v1DockComputer.updateBitmask(newProgramMask);
            } else if (StringUtils.startsWith(programLine,"mem[") && StringUtils.contains(programLine, "] =")) {
                long memoryAddress = Long.parseLong(programLine.substring(4, programLine.indexOf("]")));
                long memoryValue = Long.parseLong(StringUtils.trim(programLine.split("=")[1]));
                this.v1DockComputer.setMemoryValueAtAddress(memoryAddress, memoryValue);
            } else {
                throw new IllegalArgumentException("Unknown program entry " + programLine);
            }
        }
    }

    public void executeV2Program(List<String> programText) throws IllegalSeaportMemoryException, IllegalArgumentException {
        logger.info("Executing V2 program with " + programText.size() + " lines");
        for (String programLine: programText) {
            if (StringUtils.startsWith(programLine, "mask =")) {
                String newProgramMask = programLine.substring(7);
                this.v2DockComputer.updateBitmask(programLine.substring(7));
            } else if (StringUtils.startsWith(programLine,"mem[") && StringUtils.contains(programLine, "] =")) {
                long memoryAddress = Long.parseLong(programLine.substring(4, programLine.indexOf("]")));
                long memoryValue = Long.parseLong(StringUtils.trim(programLine.split("=")[1]));
                this.v2DockComputer.setMemoryValueAtAddressUsingMemoryDecoder(memoryAddress, memoryValue);
            } else {
                throw new IllegalArgumentException("Unknown V2 program entry " + programLine);
            }
        }
    }

    public long getSumOfAllV1Values() { return this.v1DockComputer.getTotalMemoryValue(); }
    public long getSumOfAllV2Values() { return this.v2DockComputer.getTotalMemoryValue(); }

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to Docking Data!");
        logger.info("Initializing the docking computer...");
        DockingData dockingData = new DockingData();
        logger.info("Finding our input...");
        LocalResourceInput resourceInput = new LocalResourceInput("advent2020/day14/advent-day14-input.txt");
        logger.info("Running our V1 program!");
        dockingData.executeV1Program(resourceInput.getInput());
//        logger.info("Current program state:");
//        logger.info(dockingData.v1DockComputer.toString());
        logger.info("Sum of all V1 values is " + dockingData.getSumOfAllV1Values());
        logger.info("Running our V2 program...");
        dockingData.executeV2Program(resourceInput.getInput());
        logger.info("Sum of all V2 values is " + dockingData.getSumOfAllV2Values());

        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");

    }
}
