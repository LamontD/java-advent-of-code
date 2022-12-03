package com.lamontd.aoc.advent2020.dec10;

import com.lamontd.aoc.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JoltagePathCounter {
    private static final Logger logger = LoggerFactory.getLogger(JoltagePathCounter.class);

    final List<JoltageAdapter> adapterList;
    public JoltagePathCounter(List<Long> adapterJoltages) {
        this.adapterList = adapterJoltages.stream().map(val -> new JoltageAdapter(val)).collect(Collectors.toList());
        Collections.sort(adapterList);
        connectJoltages();
    }

    private void connectJoltages() {
        for (int indx=0; indx < adapterList.size(); indx++) {
            JoltageAdapter currentAdapter = adapterList.get(indx);
            if (indx+1 < adapterList.size()) {
                // Assume it can connect to the next one
                currentAdapter.addConnection(adapterList.get(indx+1));
            }
            if (indx+2 < adapterList.size() && currentAdapter.canConnectToSource(adapterList.get(indx+2))) {
                currentAdapter.addConnection(adapterList.get(indx+2));
            }
            if (indx+3 < adapterList.size() && currentAdapter.canConnectToSource(adapterList.get(indx+3))) {
                currentAdapter.addConnection(adapterList.get(indx+3));
            }
        }
    }

    public Map<JoltageAdapter, Long> generatePathCountingMap() {
        Map<JoltageAdapter, Long> pathCounting = new HashMap<>();
        for (int indx=adapterList.size()-1; indx >= 0; indx--) {
            JoltageAdapter thisAdapter = adapterList.get(indx);
            if (thisAdapter.getNumAdapterConnections() == 0) {
                pathCounting.put(thisAdapter, 1L);
            } else {
                long count = 0L;
                for (JoltageAdapter connectingAdapter: thisAdapter.getAdapterConnections()) {
                    count += pathCounting.get(connectingAdapter);
                }
                pathCounting.put(thisAdapter, count);
            }
        }
        return pathCounting;
    }

    public List<JoltageConnectorBag> generatePathsFrom(long joltage) {
        List<JoltageConnectorBag> paths = new ArrayList<>();
        JoltageAdapter startingAdapter = null;
        for (JoltageAdapter adapter : adapterList) {
            if (adapter.getJoltageRating() == joltage) {
                startingAdapter = adapter;
                break;
            }
        }
        if (startingAdapter == null)
            return paths;
        for (List<Long> pathList : generatePathsToEnd(startingAdapter))
            paths.add(new JoltageConnectorBag(pathList));
        return paths;
    }

    private static List<List<Long>> generatePathsToEnd(JoltageAdapter adapter) {
        List<List<Long>> paths = new ArrayList<>();
        if (adapter.getNumAdapterConnections() == 0) {
            List<Long> baseList = new ArrayList<>();
            baseList.add(adapter.getJoltageRating());
            paths.add(baseList);
        }
        for(JoltageAdapter connection : adapter.getAdapterConnections()) {
            for (List<Long> subordinatePaths : generatePathsToEnd(connection)) {
                subordinatePaths.add(adapter.getJoltageRating());
                paths.add(subordinatePaths);
            }
        }
        return paths;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("JoltagePathCounter: ").append("\n");
        for (JoltageAdapter adapter : adapterList) {
            sb.append("\t").append(adapter.toString()).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to the Joltage PathCounter!");
        logger.info("Reading in the input...");
        LocalResourceInput resourceInput = new LocalResourceInput("advent2020/day10/advent-day10-input.txt");
        List<Long> inputJoltages = resourceInput.getInput().stream().map(Long::parseLong).collect(Collectors.toList());
        logger.info("Found " + inputJoltages.size() + " adapters in my bag");
        inputJoltages.add(0L);
        logger.info("Creating a path counter");
        JoltagePathCounter pathCounter = new JoltagePathCounter(inputJoltages);
//        logger.info(pathCounter.toString());
        logger.info("Generating a path counting map");
        Map<JoltageAdapter, Long> pathCountingMap = pathCounter.generatePathCountingMap();
        long maxValue = pathCountingMap.get(new JoltageAdapter(0));
        logger.info("Max value is " + maxValue);
//        logger.info("Path Counting Map looks like this: ");
//        pathCountingMap.forEach((key, value) -> {
//            logger.info("JoltageAdapter{"+key.getJoltageRating()+"}\t"+key.getNumAdapterConnections()+" connections\t" + +value+" paths");
//        });
//        // Brute force check
//        int pc = 1;
//        for (JoltageConnectorBag pathBag : pathCounter.generatePathsFrom(33L)) {
//            logger.info("Path " + pc++ + ": " + pathBag.toString());
//        }
        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");

    }
}
