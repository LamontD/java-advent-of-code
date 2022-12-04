package com.lamontd.adventofcode.advent2021.dec13;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.TreeSet;

public class TransparentPaper {
    private static final Logger logger = LoggerFactory.getLogger(TransparentPaper.class);
    private Set<GraphPoint> visibleDots = new TreeSet<>();
    private int maxX = 0;
    private int maxY = 0;

    public void addVisibleDot(int xCoord, int yCoord) {
        this.visibleDots.add(new GraphPoint(xCoord, yCoord));
        if (xCoord > maxX) {
            maxX = xCoord;
        }
        if (yCoord > maxY) {
            maxY = yCoord;
        }
    }

    public void foldAlongLine(String dimension, int foldLine) {
        Set<GraphPoint> foldSet = new TreeSet<>();
        for (GraphPoint originalPoint : visibleDots) {
            if (StringUtils.equalsIgnoreCase(dimension, "X") && (originalPoint.getX() > foldLine)) {
                GraphPoint newPoint = GraphPoint.builder()
                        .x(foldLine - (originalPoint.getX() - foldLine))
                        .y(originalPoint.getY()).build();
                logger.info("Folded " + originalPoint + " to " + newPoint);
                foldSet.add(newPoint);
            } else if (StringUtils.equalsIgnoreCase(dimension, "Y") && (originalPoint.getY() > foldLine)) {
                GraphPoint newPoint = GraphPoint.builder()
                        .x(originalPoint.getX())
                        .y(foldLine - (originalPoint.getY() - foldLine))
                        .build();
                logger.info("Folded " + originalPoint + " to " + newPoint);
                foldSet.add(newPoint);
            } else {
                foldSet.add(originalPoint);
            }
        }
        this.visibleDots = foldSet;
        this.maxY = foldLine;
    }

    public String getPaperContents() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                if (visibleDots.contains(GraphPoint.builder().x(x).y(y).build())) {
                    sb.append('#');
                } else {
                    sb.append('.');
                }
            }
            sb.append("\n");
        }
        return sb.toString();

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TransparentPaper (" + maxX + "x" + maxY + ") has " + visibleDots.size() + " dots\n");
        sb.append("Points are: " + visibleDots);
        return sb.toString();
    }
}
