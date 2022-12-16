package com.lamontd.adventofcode.advent2022.dec12;

import com.google.common.graph.ValueGraph;
import com.lamontd.adventofcode.utils.astar.AStarWithTreeSet;
import com.lamontd.adventofcode.utils.astar.HeuristicForNodesWithCoordinate;
import com.lamontd.adventofcode.utils.astar.NodeWithCoordinate;
import com.lamontd.adventofcode.utils.Coordinate;
import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class HillClimbing {
    private static final Logger logger = LoggerFactory.getLogger(HillClimbing.class);

    private SignalHeightmap heightmap;

    public void loadMap(LocalResourceInput resourceInput) {
        this.heightmap = new SignalHeightmap(resourceInput.getInput());
    }

    public void printHeightmap() {
        logger.info("Heightmap follows as: \n" + heightmap.toString());
        logger.info("Starting position: " + heightmap.getStartingPosition());
        logger.info("Best signal location: " + heightmap.getBestSignal());
    }

    public List<Coordinate> findShortestPath() {
        ValueGraph<NodeWithCoordinate, Double> valueGraph = heightmap.toDirectedEdgeGraph();
        NodeWithCoordinate source = new NodeWithCoordinate(heightmap.getStartingPosition());
        NodeWithCoordinate dest = new NodeWithCoordinate(heightmap.getBestSignal());
        HeuristicForNodesWithCoordinate graphHeuristic = new HeuristicForNodesWithCoordinate(valueGraph, dest);
        List<NodeWithCoordinate> shortestPath = AStarWithTreeSet.findShortestPath(valueGraph, source, dest, graphHeuristic);
        return shortestPath.stream().map(NodeWithCoordinate::getCoord).collect(Collectors.toList());
    }

    public Pair<Coordinate, Integer> findShortestValleyPath() {
        Pair<Coordinate, Integer> valleyPair = null;
        ValueGraph<NodeWithCoordinate, Double> valueGraph = heightmap.toDirectedEdgeGraph();
        NodeWithCoordinate summit = new NodeWithCoordinate(heightmap.getBestSignal());
        HeuristicForNodesWithCoordinate graphHeuristic = new HeuristicForNodesWithCoordinate(valueGraph, summit);
        for (Coordinate valleyPoint : heightmap.findAllValleys()) {
            NodeWithCoordinate valley = new NodeWithCoordinate(valleyPoint);
            List<NodeWithCoordinate> valleyPath = AStarWithTreeSet.findShortestPath(valueGraph, valley, summit, graphHeuristic);
            if ((valleyPair == null) || (valleyPath != null && (valleyPath.size() < valleyPair.getValue1()))){
                valleyPair = Pair.with(valleyPoint, valleyPath.size());
            }
        }
        return valleyPair;
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Hill Climbing Algorithm");
        logger.info("Creating sample map");
        HillClimbing sampleMap = new HillClimbing();
        sampleMap.loadMap(new LocalResourceInput("day12/sample.txt"));
        sampleMap.printHeightmap();
        logger.info("Finding the shortest path...");
        List<Coordinate> shortestPath = sampleMap.findShortestPath();
        logger.info("Shortest path is " + shortestPath);
        logger.info("The length of the path is " + shortestPath.size());
        Pair<Coordinate, Integer> bestValley = sampleMap.findShortestValleyPath();
        logger.info("Part 2: The shortest valley path starts at " + bestValley.getValue0() + " and has length of " + bestValley.getValue1());

        logger.info("Let's take a look at the input data!");
        HillClimbing inputMap = new HillClimbing();
        inputMap.loadMap(new LocalResourceInput("day12/input.txt"));
        List<Coordinate> shortestInputPath = inputMap.findShortestPath();
        logger.info("Part 1: The length of the shortest input path is " + shortestInputPath.size());
        Pair<Coordinate, Integer> bestInputValley = inputMap.findShortestValleyPath();
        logger.info("Part 2: The shortest valley path starts at " + bestInputValley.getValue0() + " and has length of " + bestInputValley.getValue1());
        timer.finish();
    }
}
