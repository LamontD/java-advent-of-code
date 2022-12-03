package com.lamontd.aoc.advent2021.dec15;

import com.lamontd.aoc.utils.*;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class Chiton {
    private static final Logger logger = LoggerFactory.getLogger(Chiton.class);

    private SingleValueIntGrid riskMap;

    public void loadChitonMap(String inputfile) throws IOException {
        LocalResourceInput resourceInput = new LocalResourceInput(inputfile);
        riskMap = new RiskMap(resourceInput.getInput());
    }

    public int getLowestRiskPath() {
        logger.info("Converting to a coordinate graph...");
        CoordinateGraph coordinateGraph = new CoordinateGraph();
        coordinateGraph.buildFromGrid(riskMap);
        logger.info("Running Dijkstra...");
        Coordinate startingCoordinate = new Coordinate(0, 0);
        Coordinate endingCoordinate = new Coordinate(riskMap.getNumRows()-1, riskMap.getNumColumns()-1);
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(coordinateGraph);
        dijkstraAlgorithm.execute(new Coordinate(0, 0));
        List<Coordinate> shortestPath = dijkstraAlgorithm.getPath(endingCoordinate);
        logger.info("Shortest path is " + shortestPath);
        final BasicCounter counter = new BasicCounter();
        shortestPath.stream().forEach(path -> counter.increment(riskMap.getGridValue(path)));
        counter.decrement(riskMap.getGridValue(startingCoordinate));
        return counter.currentValue();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Chiton");
        logger.info("Loading sample file");
        Chiton chit = new Chiton();
        chit.loadChitonMap("advent2021/day15/sample.txt");
        logger.info("Looking at a part 1 sample...");
        logger.info("Shortest sample is " + chit.getLowestRiskPath());
        logger.info("Looking at real data!");
        Chiton realData = new Chiton();
        realData.loadChitonMap("advent2021/day15/input.txt");
        logger.info("Shortest risk path is " + realData.getLowestRiskPath());
        timer.finish();
    }
}
