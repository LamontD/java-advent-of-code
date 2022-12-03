package com.lamontd.aoc.advent2021.dec15;

import com.lamontd.aoc.utils.Coordinate;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestDijkstraAlgorithm {
    private List<Coordinate> nodes;
    private List<MapEdge> edges;

    @Test
    public void testExecute() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        for (int i=0; i < 11; i++) {
            Coordinate location = new Coordinate(i, 0);
            nodes.add(location);
        }

        addLane(0, 1, 85);
        addLane(0, 2, 217);
        addLane(0, 4, 173);
        addLane(2, 6, 186);
        addLane(2, 7, 103);
        addLane(3, 7, 183);
        addLane(5, 8, 250);
        addLane(8, 9, 84);
        addLane(7, 9, 167);
        addLane( 4, 9, 502);
        addLane( 9, 10, 40);
        addLane( 1, 10, 600);

        CoordinateGraph graph = new CoordinateGraph(nodes, edges);
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
        dijkstraAlgorithm.execute(nodes.get(0));
        LinkedList<Coordinate> path = dijkstraAlgorithm.getPath(nodes.get(10));

        Assert.assertNotNull(path);
        Assert.assertFalse(path.isEmpty());

        for (Coordinate vertex : path) {
            System.out.println(vertex);
        }
    }

    private void addLane(int sourceLocNo, int destLocNo, int duration) {
        MapEdge lane = new MapEdge(nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
        edges.add(lane);
    }
}
