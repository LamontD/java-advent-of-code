package com.lamontd.adventofcode.advent2021.dec15;

import com.lamontd.adventofcode.utils.SingleValueIntGrid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
