package com.lamontd.adventofcode.advent2022.dec15;

import com.lamontd.adventofcode.utils.Coordinate;
import com.lamontd.adventofcode.utils.CoordinateLine;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestSensor {
    @Test
    public void testExclusionZoneForRow() {
        Sensor sensor = new Sensor(Coordinate.of(8, 7), Coordinate.of(2, 10));
        List<Coordinate> zeroRow = sensor.getExclusionZoneForRow(0);
        assertFalse(zeroRow.isEmpty());
        assertEquals(5, zeroRow.size());
        assertTrue(zeroRow.contains(Coordinate.of(6, 0)));
        assertTrue(zeroRow.contains(Coordinate.of(10, 0)));
        List<Coordinate> twelveRow = sensor.getExclusionZoneForRow(12);
        assertEquals(9, twelveRow.size());
        List<Coordinate> neg2Row = sensor.getExclusionZoneForRow(-2);
        assertEquals(1, neg2Row.size());
        List<Coordinate> sixteenRow = sensor.getExclusionZoneForRow(16);
        assertEquals(1, sixteenRow.size());
        assertTrue(sensor.getExclusionZoneForRow(-5).isEmpty());
        assertTrue(sensor.getExclusionZoneForRow(20).isEmpty());
    }

    @Test
    public void testExclusionLineForRow() {
        Sensor sensor = new Sensor(Coordinate.of(8, 7), Coordinate.of(2, 10));
        CoordinateLine zeroRow = sensor.getExclusionLineForRow(0);
        assertNotNull(zeroRow);
        assertEquals(5, zeroRow.length());
        assertTrue(zeroRow.contains(Coordinate.of(6, 0)));
        assertTrue(zeroRow.contains(Coordinate.of(10, 0)));
        CoordinateLine twelveRow = sensor.getExclusionLineForRow(12);
        assertEquals(9, twelveRow.length());
        CoordinateLine neg2Row = sensor.getExclusionLineForRow(-2);
        assertEquals(1, neg2Row.length());
        CoordinateLine sixteenRow = sensor.getExclusionLineForRow(16);
        assertEquals(1, sixteenRow.length());
        assertNull(sensor.getExclusionLineForRow(-5));
        assertNull(sensor.getExclusionLineForRow(20));
    }

    @Test
    public void testManhattanDistanceRing() {
        Sensor sensor = new Sensor(Coordinate.of(8, 7), Coordinate.of(2, 10));
        List<Coordinate> manhattanRing = sensor.getManhattanDistanceRing();
        assertFalse(manhattanRing.isEmpty());
        assertEquals(40, manhattanRing.size());
    }
}
