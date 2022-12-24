package com.lamontd.adventofcode.utils.astar;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import com.lamontd.adventofcode.utils.coord.Coordinate;

import java.util.concurrent.ThreadLocalRandom;

public class TestGraphFactory {
    public static NodeWithCoordinate[] createNodes(int numNodes) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        // Add all nodes with random X and Y position from 0 to 10,000
        // (and store them in a temporary array, so we can access them in the next step)
        NodeWithCoordinate[] nodes = new NodeWithCoordinate[numNodes];
        for (int i = 0; i < numNodes; i++) {
            NodeWithCoordinate node =
                    new NodeWithCoordinate(
                            "Node" + i, Coordinate.builder().x(random.nextInt(10_000)).y(random.nextInt(10_000)).build());
            nodes[i] = node;
        }

        return nodes;
    }

    public static ValueGraph<NodeWithCoordinate, Double> createGraph(
            NodeWithCoordinate[] nodes, int numEdges) {
        MutableValueGraph<NodeWithCoordinate, Double> graph = ValueGraphBuilder.undirected().build();

        for (NodeWithCoordinate node : nodes) {
            graph.addNode(node);
        }

        // Add random edges with costs at a random speed between 15 and 100
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int numNodes = nodes.length;
        for (int i = 0; i < numEdges; i++) {
            int nodeIndex1 = random.nextInt(numNodes);
            int nodeIndex2;
            do {
                nodeIndex2 = random.nextInt(numNodes);
            } while (nodeIndex2 == nodeIndex1);

            NodeWithCoordinate node1 = nodes[nodeIndex1];
            NodeWithCoordinate node2 = nodes[nodeIndex2];

            double speed = random.nextDouble(15, 100);
            double distance = HeuristicForNodesWithCoordinate.calculateEuclideanDistance(node1, node2);
            double cost = distance / speed;

            graph.putEdgeValue(node1, node2, cost);
        }
        return graph;
    }

}
