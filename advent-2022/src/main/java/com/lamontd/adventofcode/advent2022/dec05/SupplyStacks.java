package com.lamontd.adventofcode.advent2022.dec05;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class SupplyStacks {
    private static final Logger logger = LoggerFactory.getLogger(SupplyStacks.class);

    final private Map<Integer, CargoStack> stackMap = new HashMap<>();
    final private List<CraneJob> craneJobs = new ArrayList<>();

    public Map<Integer, CargoStack> getStackMap() {
        return stackMap;
    }

    public List<CraneJob> getCraneJobs() {
        return craneJobs;
    }

    public void populateStackMap(LocalResourceInput resourceInput) {
        // Skip to the end to figure out how many stacks we have
        stackMap.clear();
        craneJobs.clear();

        // Figure out where they stop
        int splitRow = 0;
        for (int rowNum = 0; rowNum < resourceInput.getInput().size(); rowNum++) {
            if (StringUtils.isEmpty(resourceInput.getInput().get(rowNum))) {
                splitRow = rowNum;
                break;
            }
        }

        populateStacks(resourceInput.getInput().subList(0, splitRow));
        populateCraneJobs(resourceInput.getInput().subList(splitRow + 1, resourceInput.getInput().size()));
    }

    private void populateStacks(List<String> stacks) {
        stackMap.clear();
        String stackIdRow = stacks.get(stacks.size() - 1);
        stackIdRow.chars().filter(Character::isDigit).forEach(ch -> {
            CargoStack stack = new CargoStack(Character.getNumericValue(ch));
            stackMap.put(stack.getStackNumber(), stack);
        });
        for (String stackString : stacks) {
            if (stackString.contains("[")) {
                for (int indx = 0; indx < stackString.length(); indx++) {
                    if (stackString.charAt(indx) == '[') {
                        CargoStack stack = stackMap.get((indx / 4) + 1);
                        stack.bottomFeedCrate(stackString.charAt(++indx));
                    }
                }
            }
        }
    }

    public void runCrateMover9000() {
        logger.info("Running through " + craneJobs.size() + " crane jobs as a CrateMover 9000");
        for (CraneJob job : craneJobs) {
            logger.debug("Moving " + job.getQuantity() + " crates from " + job.getSourceStack() + " to " + job.getDestinationStack());
            CargoStack source = stackMap.get(job.getSourceStack());
            CargoStack destination = stackMap.get(job.getDestinationStack());
            for (int pulls = 0; pulls < job.getQuantity(); pulls++) {
                Character crate = source.pullCrate();
                destination.addCrate(crate);
            }
        }
    }

    public void runCrateMover9001() {
        logger.info("Running through " + craneJobs.size() + " create jobs as a CrateMover 9001");
        for (CraneJob job : craneJobs) {
            logger.debug("Moving " + job.getQuantity() + " crates from " + job.getSourceStack() + " to " + job.getDestinationStack());
            CargoStack source = stackMap.get(job.getSourceStack());
            CargoStack destination = stackMap.get(job.getDestinationStack());
            Deque<Character> crateStack = new ArrayDeque<>();
            for (int pulls = 0; pulls < job.getQuantity(); pulls++) {
                crateStack.addLast(source.pullCrate());
            }
            while (!crateStack.isEmpty()) {
                destination.addCrate(crateStack.removeLast());
            }
        }

    }

    public String getTopMessage() {
        StringBuilder sb = new StringBuilder();
        for (int stackNum = 1; stackNum <= stackMap.size(); stackNum++) {
            sb.append(stackMap.get(stackNum).peekCrate());
        }
        return sb.toString();
    }

    private void populateCraneJobs(List<String> moves) {
        craneJobs.clear();
        for (String move : moves) {
            if (move.startsWith("move")) {
                String[] jobFun = move.split(" ");
                craneJobs.add(new CraneJob(Integer.parseInt(jobFun[1]), Integer.parseInt(jobFun[3]), Integer.parseInt(jobFun[5])));
            }
        }
    }

    public void logCargoStack() {
        stackMap.values().stream().forEach(cs -> logger.info(cs.toString()));
    }


    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Supply Stacks");
        SupplyStacks warehouse = new SupplyStacks();

        logger.info("Doing the sample setup...");
        warehouse.populateStackMap(new LocalResourceInput("day05/sample.txt"));
        warehouse.logCargoStack();
        logger.info("Going through the initial moves for the CrateMover9000...");
        warehouse.runCrateMover9000();
        warehouse.logCargoStack();
        logger.info("Initial top message is: " + warehouse.getTopMessage());
        logger.info("Resetting to move as a CrateMover9001...");
        warehouse.populateStackMap(new LocalResourceInput("day05/sample.txt"));
        warehouse.runCrateMover9001();
        logger.info("The top message is now " + warehouse.getTopMessage());

        logger.info("Getting real..");
        warehouse.populateStackMap(new LocalResourceInput("day05/input.txt"));
        warehouse.runCrateMover9000();
        logger.info("Initial top message is: " + warehouse.getTopMessage());
        logger.info("Resetting for the CrateMover9001l...");
        warehouse.populateStackMap(new LocalResourceInput("day05/input.txt"));
        warehouse.runCrateMover9001();
        logger.info("The message is NOW " + warehouse.getTopMessage());
        timer.finish();
    }
}
