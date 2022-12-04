package com.lamontd.adventofcode.utils;

public class BasicCounter {
    private int count = 0;
    public BasicCounter() {}
    public BasicCounter(int initialValue) { count = initialValue; }
    public void increment() {
        count++;
    }
    public void increment(int size) { count += size; }
    public void decrement() {
        count--;
    }
    public void decrement(int size) { count -= size; }

    public int currentValue() { return count; }
}
