package com.lamontd.adventofcode.advent2022.dec16;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Volcanium {
    private static final Logger logger = LoggerFactory.getLogger(Volcanium.class);

    private final Map<String, Valve> inputValves = new HashMap<>();

    public void processInput(LocalResourceInput resourceInput) {
        String inputRegex = "Valve (\\w+) has flow rate=(\\d+); (tunnel leads to valve|tunnels lead to valves) (.*)$";
        Pattern inputPattern = Pattern.compile(inputRegex);
        for (String input : resourceInput.getInput()) {
            Matcher inputMatcher = inputPattern.matcher(input);
            if (inputMatcher.find()) {
                Valve newValve = new Valve(inputMatcher.group(1), Integer.parseInt(inputMatcher.group(2)));
                inputValves.put(newValve.getName(), newValve);
            }
        }

        for (String input : resourceInput.getInput()) {
            Matcher connectionMatcher = inputPattern.matcher(input);
            if (connectionMatcher.find()) {
                Valve source = inputValves.get(connectionMatcher.group(1));
                for (String valveName : connectionMatcher.group(4).split(",")) {
                    Valve destination = inputValves.get(valveName.trim());
                    source.addConnection(destination);
                }
            }
        }

        logger.info("Read " + inputValves.size() + " valves from input");
    }

    public ValvePath findBestPath() {
        SinglePathfinder pathfinder = new SinglePathfinder(inputValves.values());
        pathfinder.buildOptimalPaths();
        return pathfinder.getBestPath();
    }

    public ValvePath findBestDualPath() {
        DualPathfinder pathfinder = new DualPathfinder(inputValves.values());
        pathfinder.buildOptimalPaths();
        return pathfinder.getBestPath();
    }

    public void showValves() {
        for (Valve valve : inputValves.values()) {
            logger.info(valve.toString());
        }
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Proboscidea Volcanium");
        Volcanium sampleVolcano = new Volcanium();
        sampleVolcano.processInput(new LocalResourceInput("day16/sample.txt"));
        sampleVolcano.showValves();
        ValvePath bestPath = sampleVolcano.findBestPath();
        bestPath.logPathDetails(30);

        Volcanium inputVolcano = new Volcanium();
        inputVolcano.processInput(new LocalResourceInput("day16/input.txt"));
        inputVolcano.showValves();
        logger.info("Looking for the best path for the input data...");
        ValvePath inputBestPath = inputVolcano.findBestPath();
        inputBestPath.logPathDetails( 30);

        logger.info("Trying a dual pathfinder!");
        ValvePath sampleDualPath = sampleVolcano.findBestDualPath();
        logger.info("Best dual path is " + sampleDualPath);
        sampleDualPath.logPathDetails(26);

        ValvePath inputDualPath = inputVolcano.findBestDualPath();
        inputDualPath.logPathDetails(26);
        timer.finish();
    }
}
