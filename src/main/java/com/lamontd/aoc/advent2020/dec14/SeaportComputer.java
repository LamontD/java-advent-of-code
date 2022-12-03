package com.lamontd.aoc.advent2020.dec14;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class SeaportComputer {
    private static final int DEFAULT_MAX_BITMASK_SIZE = 36;
    private final Map<Long, Long> internalMemory = new HashMap<>();
    private char[] currentBitmask;
    private final int maximumBitmaskSize;

    public SeaportComputer() { this(DEFAULT_MAX_BITMASK_SIZE); }

    public SeaportComputer(int bitmaskSize) {
        this.maximumBitmaskSize = bitmaskSize;
        currentBitmask = new char[maximumBitmaskSize];
    }

    public long getMemoryValueAtAddress(long memoryAddress) {
        return internalMemory.getOrDefault(memoryAddress, 0L);
    }

    public void setMemoryValueAtAddress(long memoryAddress, long memoryValue) {
        long mappedMemoryValue = applyBitmaskToValue(memoryValue, currentBitmask);
        internalMemory.put(memoryAddress, mappedMemoryValue);
    }

    public void setMemoryValueAtAddressUsingMemoryDecoder(long memoryAddress, long memoryValue) {
        List<Long> adjustedMemoryValues = calculateMemoryEncodedAddresses(memoryAddress, currentBitmask);
        adjustedMemoryValues.forEach(address -> internalMemory.put(address, memoryValue));
    }

    private static List<Long> calculateMemoryEncodedAddresses(long memoryAddress, char[] bitmask) {
        char[] basicBitmapAsArray = StringUtils.leftPad(Long.toBinaryString(memoryAddress), 36, '0').toCharArray();

        for (int i=bitmask.length-1; i >= 0; i--) {
            switch(bitmask[i]) {
                case '0':
                    break;
                case '1':
                case 'X':
                    basicBitmapAsArray[i] = bitmask[i];
                    break;
            }
        }

        // Do the transformation
        ArrayList<String> bitmapStrings = new ArrayList<>();
        bitmapStrings.add(new String(basicBitmapAsArray));
        boolean madeChange = true;
        while(madeChange) {
            madeChange = false;
            for (ListIterator<String> listIter = bitmapStrings.listIterator(); listIter.hasNext(); ) {
                String bitmapString = listIter.next();
                if (bitmapString.contains("X")) {
                    listIter.remove();
                    listIter.add(bitmapString.replaceFirst("X", "0"));
                    listIter.add(bitmapString.replaceFirst("X", "1"));
                    madeChange = true;
                }
            }
        }

        List<Long> memoryEncodedAddresses = bitmapStrings.stream()
                .map(val -> applyBitmaskToValue(0L, val.toCharArray())).collect(Collectors.toList());
        return memoryEncodedAddresses;
    }

    private static long applyBitmaskToValue(long memoryValue, char[] bitmask) {
        long transformedValue = memoryValue;
        for (int i=bitmask.length - 1; i >= 0; i--) {
            int bitToSet = bitmask.length - i - 1;
            switch(bitmask[i]) {
                case '0':
                    transformedValue &= ~(1L << bitToSet);
                    break;
                case '1':
                    transformedValue |= (1L << bitToSet);
                    break;
            }
        }
        return transformedValue;
    }

    public String getCurrentBitmaskAsString() {
        return new String(currentBitmask);
    }

    public long getTotalMemoryValue() {
        long totalMemoryValue = 0L;
        for(Long memoryValue : internalMemory.values()) {
            totalMemoryValue += memoryValue;
        }
        return totalMemoryValue;
    }

    public void updateBitmask(String bitmaskString) throws IllegalSeaportMemoryException {
        if (StringUtils.isEmpty(bitmaskString))
            throw new IllegalSeaportMemoryException("Null bitmask string cannot be applied");
        if (bitmaskString.length() > maximumBitmaskSize)
            throw new IllegalSeaportMemoryException("Bitmask of " + bitmaskString.length()
                    + " is larger than supported size of " + maximumBitmaskSize + " : [" + bitmaskString + "]");

        char[] newBitmaskString = new char[bitmaskString.length()];
        for(int indx=0; indx < bitmaskString.length(); indx++) {
            switch(bitmaskString.charAt(indx)) {
                case '0':
                case '1':
                case 'X':
                    newBitmaskString[indx] = bitmaskString.charAt(indx);
                    break;
                default:
                    throw new IllegalSeaportMemoryException("Bitmask can only contain 'X', '0', or '1': [" + bitmaskString + "]" );
            }
        }
        this.currentBitmask = newBitmaskString;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SeaportComputer {").append("\n");
        sb.append("  Current Bitmask: ").append(getCurrentBitmaskAsString()).append("]\n");
        sb.append("  Memory Values: [").append("\n");
        List<Long> memoryKeys = new ArrayList<>(internalMemory.keySet());
        Collections.sort(memoryKeys);
        memoryKeys.forEach(key -> sb.append("    mem[").append(key).append("]=").append(internalMemory.get(key)).append("\n"));
        sb.append("   ]").append("\n");
        sb.append("}");
        return sb.toString();
    }
}
