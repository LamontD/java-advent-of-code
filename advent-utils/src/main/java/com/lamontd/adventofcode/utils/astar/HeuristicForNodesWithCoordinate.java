package com.lamontd.adventofcode.utils.astar;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.ValueGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * Adopted from original algorithm from happycoders.eu to add support for my Coordinate classes.
 */
public class HeuristicForNodesWithCoordinate implements Function<NodeWithCoordinate, Double> {
    private static final Logger LOG =
            LoggerFactory.getLogger(HeuristicForNodesWithCoordinate.class);

    private final double maxSpeed;
    private final NodeWithCoordinate target;

    /**
     * Constructs the heuristic function for the specified graph and target node.
     *
     * @param graph the graph
     * @param target the target node
     */
    public HeuristicForNodesWithCoordinate(
            ValueGraph<NodeWithCoordinate, Double> graph, NodeWithCoordinate target) {
        // We need the maximum speed possible on any path in the graph for the heuristic function to
        // calculate the cost for a euclidean distance
        this.maxSpeed = calculateMaxSpeed(graph);
        this.target = target;
    }

    /**
     * Calculates the maximum speed possible on any path in the graph. The speed of a path is
     * calculated as: euclidean distance between the path's nodes, divided by its cost.
     *
     * @param graph the graph
     * @return the maximum speed
     */
    private static double calculateMaxSpeed(ValueGraph<NodeWithCoordinate, Double> graph) {
        return graph.edges().stream()
                .map(edge -> calculateSpeed(graph, edge))
                .max(Double::compare)
                .orElseThrow(() -> new IllegalArgumentException("Graph is empty"));
    }

    /**
     * Calculates the speed on a path in the graph. The speed of a path is calculated as: euclidean
     * distance between the path's nodes, divided by its cost.
     *
     * @param graph the graph
     * @param edge the edge (= path)
     * @return the speed
     */
    private static double calculateSpeed(
            ValueGraph<NodeWithCoordinate, Double> graph, EndpointPair<NodeWithCoordinate> edge) {
        double euclideanDistance = calculateEuclideanDistance(edge.nodeU(), edge.nodeV());
        double cost =
                graph.edgeValue(edge).orElseThrow(() -> new IllegalArgumentException("Graph is empty"));
        double speed = euclideanDistance / cost;

        if (LOG.isDebugEnabled()) {
            LOG.debug(
                    "Calculated speed from {} to {}: euclideanDistance = {}, cost = {} --> speed = {}",
                    edge.nodeU(),
                    edge.nodeV(),
                    euclideanDistance,
                    cost,
                    speed);
        }

        return speed;
    }

    public static double calculateEuclideanDistance(
            NodeWithCoordinate source, NodeWithCoordinate target) {
        double distanceX = target.getCoord().getX() - source.getCoord().getY();
        double distanceY = target.getCoord().getX() - source.getCoord().getY();
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    /**
     * Applies the heuristic function to the specified node.
     *
     * @param node the node
     * @return the minimum cost for traveling from the specified node to the target
     */
    @Override
    public Double apply(NodeWithCoordinate node) {
        double euclideanDistance = calculateEuclideanDistance(node, target);
        double minimumCost = euclideanDistance / maxSpeed;

        LOG.debug(
                "Applied heuristic to node {}: euclideanDistance = {}, maxSpeed = {} --> minimumCost = {}",
                node,
                euclideanDistance,
                maxSpeed,
                minimumCost);

        return minimumCost;
    }

}
