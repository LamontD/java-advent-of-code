package com.lamontd.adventofcode.utils;

public class LongCounter {
    private long count = 0;
    public LongCounter() {}
    public LongCounter(long initialValue) { count = initialValue; }
    public void increment() {
        count++;
    }
    public void increment(long size) { count += size; }
    public void decrement() {
        count--;
    }
    public void decrement(long size) { count -= size; }

    public long currentValue() { return count; }
    public void setCurrentValue(long value) { count = value; }
}
