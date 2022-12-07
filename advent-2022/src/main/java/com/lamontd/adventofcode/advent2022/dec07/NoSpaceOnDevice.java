package com.lamontd.adventofcode.advent2022.dec07;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class NoSpaceOnDevice {
    private static final Logger logger = LoggerFactory.getLogger(NoSpaceOnDevice.class);

    public static DeviceDir createDeviceTree(LocalResourceInput resourceInput) {
        // Let's assume the first line is CD to the root directory
        DeviceDir rootDir = new DeviceDir(null, "/");
        DeviceDir currentDir = rootDir;
        Iterator<String> resourceIter = resourceInput.getInput().listIterator();
        resourceIter.next();
        while (resourceIter.hasNext()) {
            String input = resourceIter.next();
            if (input.startsWith("$ ls")) {
                // The next things will be input for is
            } else if (input.startsWith("$ cd")) {
                String destination = input.substring(5);
                if (StringUtils.equals(destination, "..")) {
                    currentDir = currentDir.getParentDir();
                } else {
                    currentDir = (DeviceDir)currentDir.findChild(destination);
                }
            } else if (input.startsWith("dir")) {
                currentDir.addChild(new DeviceDir(currentDir, input.substring(4)));
            } else {
                String []inputSplit = input.split(" ");
                currentDir.addChild(new DeviceFile(currentDir, inputSplit[1], Long.parseLong(inputSplit[0])));
            }
        }
        return rootDir;
    }

    public static void checkSizeOfAllDirectories(DeviceDir rootDirectory) {
        logger.info("Directory " + rootDirectory.getAbsolutePath() + " has size of " + rootDirectory.getSize());
        for (DeviceItem item : rootDirectory.getChildren()) {
            if (item instanceof DeviceDir) {
                checkSizeOfAllDirectories((DeviceDir) item);
            }
        }
    }

    public static List<DeviceDir> getAllDirectories(DeviceDir rootDirectory) {
        List<DeviceDir> dirList = new ArrayList<>();
        dirList.add(rootDirectory);
        for (DeviceItem childItem : rootDirectory.getChildren()) {
            if (childItem instanceof DeviceDir) {
                dirList.addAll(getAllDirectories((DeviceDir) childItem));
            }
        }
        return dirList;
    }

    public static long getCombinedSmallDirectorySize(DeviceDir rootDirectory, long maxSize) {
        return getAllDirectories(rootDirectory).stream()
                .filter(dd -> dd.getSize() <= maxSize)
                .mapToLong(DeviceDir::getSize)
                .sum();
    }

    public static List<DeviceDir> getDirectoriesBigEnoughToFreeUpSpaceForUpdate(DeviceDir rootDirectory, long updateSize) {
        long currentFreeSpace = 70000000L - rootDirectory.getSize();
        long extraSpaceNeeded = updateSize - currentFreeSpace;
        return getAllDirectories(rootDirectory).stream()
                .filter(dd -> dd.getSize() >= extraSpaceNeeded)
                .sorted(Comparator.comparingLong(DeviceDir::getSize))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("No Space Left On Device");
        logger.info("Trying the sample");
        DeviceDir sampleTree = createDeviceTree(new LocalResourceInput("day07/sample.txt"));
        logger.info("Part 1 sample answer: " + getCombinedSmallDirectorySize(sampleTree, 100000));
        List<DeviceDir> spaceClearers = getDirectoriesBigEnoughToFreeUpSpaceForUpdate(sampleTree, 30000000L);
        logger.info("Part 2: The best directory to delete is " + spaceClearers.get(0).getAbsolutePath() + " which will free up " + spaceClearers.get(0).getSize());
        logger.info("Trying the real input...");
        DeviceDir inputTree = createDeviceTree(new LocalResourceInput("day07/input.txt"));
        logger.info("Part 1 input answer: " + getCombinedSmallDirectorySize(inputTree, 100000));
        List<DeviceDir> inputSpaceClearers = getDirectoriesBigEnoughToFreeUpSpaceForUpdate(inputTree, 30000000L);
        logger.info("Part 2: The best INPUT directory to delete is " + inputSpaceClearers.get(0).getAbsolutePath() + " which will free up " + inputSpaceClearers.get(0).getSize());
        timer.finish();
    }
}
