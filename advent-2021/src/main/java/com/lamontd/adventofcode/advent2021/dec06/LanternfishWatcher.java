package com.lamontd.adventofcode.advent2021.dec06;

import com.lamontd.adventofcode.utils.BasicCounter;
import com.lamontd.adventofcode.utils.LongCounter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanternfishWatcher {
//    private List<BasicCounter> fishList = new ArrayList<>();
    private BasicCounter currentDay = new BasicCounter();
    private Map<Integer, LongCounter> fishCounterMap = new HashMap<>();

    public LanternfishWatcher(List<Integer> initialValues) {
        for (int i=0; i <= 8; i++) {
            fishCounterMap.put(i, new LongCounter());
        }
        for (int val : initialValues) {
//            fishList.add(new BasicCounter(val));
            fishCounterMap.get(val).increment();
        }
    }

    public void ageOneDay() {
//        final BasicCounter fishCreated = new BasicCounter();
//        fishList.stream().forEach(fish -> {
//            if (fish.currentValue() == 0) {
//                fish.increment(6);
//                fishCreated.increment();
//            } else {
//                fish.decrement();
//            }
//        });
//        while (fishCreated.currentValue() > 0) {
//            fishList.add(new BasicCounter(8));
//            fishCreated.decrement();
//        }
//
        LongCounter longfishCreated = new LongCounter(fishCounterMap.get(0).currentValue());
        for (int i=1; i <= 8; i++) {
            fishCounterMap.get(i-1).setCurrentValue(fishCounterMap.get(i).currentValue());
        }
        fishCounterMap.get(6).increment(longfishCreated.currentValue());
        fishCounterMap.get(8).setCurrentValue(longfishCreated.currentValue());
        currentDay.increment();
    }

//    public int getFishCount() { return fishList.size(); }
    public long getLongFishCount() {
        final LongCounter longCounter = new LongCounter();
        fishCounterMap.values().stream().forEach(cnt -> longCounter.increment(cnt.currentValue()));
        return longCounter.currentValue();
    }
    public int getCurrentDay() { return currentDay.currentValue(); }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LanternfishWatcher has a total of ").append(getLongFishCount()).append(" fish\n");
        if (currentDay.currentValue() == 0) {
            sb.append("Initial State: ");
        } else if (currentDay.currentValue() == 1) {
            sb.append("After     1 day : \n");
        } else {
            sb.append("After ").append(String.format("%5d", currentDay.currentValue())).append(" days: \n");
        }
        for (int i=0; i <= 8; i++) {
            sb.append("  Age ").append(i).append(": ").append(String.format("%7d", fishCounterMap.get(i).currentValue())).append(" fish\n");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
