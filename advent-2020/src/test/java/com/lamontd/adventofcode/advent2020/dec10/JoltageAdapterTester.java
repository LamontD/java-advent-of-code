package com.lamontd.adventofcode.advent2020.dec10;

import com.lamontd.adventofcode.utils.BasicCounter;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class JoltageAdapterTester {
    @Test
    public void basicJoltageAdapterTester() {
        JoltageAdapter adapter = new JoltageAdapter(13);
        Assert.assertFalse(adapter.canConnectToSource(9));
        Assert.assertTrue(adapter.canConnectToSource(10));
        Assert.assertTrue(adapter.canConnectToSource(11));
        Assert.assertTrue(adapter.canConnectToSource(12));
        Assert.assertTrue(adapter.canConnectToSource(13));
        Assert.assertFalse(adapter.canConnectToSource(14));
    }

    @Test
    public void testJoltageDifferenceMapCalculation() {
        JoltageConnectorBag connectorBag = new JoltageConnectorBag(List.of(16L, 10L, 15L, 5L, 1L, 11L, 7L, 19L, 6L, 12L, 4L));
        Map<Integer, BasicCounter> differenceMap = connectorBag.calculateDifferenceMap();
        Assert.assertEquals(7, differenceMap.get(1).currentValue());
        Assert.assertNull(differenceMap.get(2));
        Assert.assertEquals(5, differenceMap.get(3).currentValue());

        connectorBag = new JoltageConnectorBag(List.of(28L, 33L, 18L, 42L, 31L, 14L, 46L, 20L, 48L, 47L, 24L, 23L, 49L, 45L, 19L, 38L, 39L, 11L, 1L, 32L, 25L, 35L, 8L, 17L, 7L, 9L, 4L, 2L, 34L, 10L, 3L));
        differenceMap = connectorBag.calculateDifferenceMap();
        Assert.assertEquals(22, differenceMap.get(1).currentValue());
        Assert.assertEquals(10, differenceMap.get(3).currentValue());
    }

}
