package com.lamontd.adventofcode.advent2021.dec15;

import com.lamontd.adventofcode.utils.coord.Coordinate;

import java.util.*;

public class DijkstraAlgorithm {
    private final List<Coordinate> nodes;
    private final List<MapEdge> edges;
    private Set<Coordinate> settledNodes;
    private Set<Coordinate> unsettledNodes;
    private Map<Coordinate, Coordinate> predecessors;
    private Map<Coordinate, Integer> distance;

    public DijkstraAlgorithm(CoordinateGraph graph) {
        nodes = new ArrayList<>(graph.getVertexes());
        edges = new ArrayList<>(graph.getEdges());
    }

    public void execute(Coordinate source) {
        settledNodes = new HashSet<>();
        unsettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 0);
        unsettledNodes.add(source);
        while (!unsettledNodes.isEmpty()) {
            Coordinate node = getMinimum(unsettledNodes);
            settledNodes.add(node);
            unsettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Coordinate node) {
        List<Coordinate> adjacentNodes = getNeighbors(node);
        for (Coordinate target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unsettledNodes.add(target);
            }
        }
    }

    private int getDistance(Coordinate node, Coordinate target) {
        for (MapEdge edge : edges) {
            if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Bad things happened and we lost edge control...");
    }

    private List<Coordinate> getNeighbors(Coordinate node) {
        List<Coordinate> neighbors = new ArrayList<>();
        for (MapEdge edge : edges) {
            if (edge.getSource().equals(node) && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Coordinate getMinimum(Set<Coordinate> vertexes) {
        Coordinate minimum = null;
        for (Coordinate vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Coordinate vertex) {
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(Coordinate destination) {
        return distance.getOrDefault(destination, Integer.MAX_VALUE);
    }

    public LinkedList<Coordinate> getPath(Coordinate target) {
        LinkedList<Coordinate> path = new LinkedList<>();
        Coordinate step = target;
        if (!predecessors.containsKey(step)) {
            return null;
        }
        path.add(step);
        while (predecessors.containsKey(step)) {
            step = predecessors.get(step);
            path.add(step);
        }

        // Correct the order
        Collections.reverse(path);
        return path;
    }

}
