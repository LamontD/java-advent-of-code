package com.lamontd.aoc.advent2020.dec10;

public class IncompatibleJoltageAdapterConnectionException extends Exception {
    public IncompatibleJoltageAdapterConnectionException(JoltageAdapter sourceJoltage, JoltageAdapter adapterJoltage) {
        super("Adapter with joltage [" + adapterJoltage.getJoltageRating()
                + "] can't connect to source with joltage [" + sourceJoltage.getJoltageRating() + "]");
    }
}
