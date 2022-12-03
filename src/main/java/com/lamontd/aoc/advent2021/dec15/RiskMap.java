package com.lamontd.aoc.advent2021.dec15;

import com.lamontd.aoc.utils.Coordinate;
import com.lamontd.aoc.utils.SingleValueIntGrid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RiskMap extends SingleValueIntGrid {
    private static final Logger logger = LoggerFactory.getLogger(RiskMap.class);

    public RiskMap(List<String> layoutGrid) {
        super(layoutGrid);
    }

    public int findLeastRiskyPathFromStart() {
        int lowestRisk = 0;
        return lowestRisk;
    }

}
