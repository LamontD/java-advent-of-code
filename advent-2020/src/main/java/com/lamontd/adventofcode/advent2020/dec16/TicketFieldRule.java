package com.lamontd.adventofcode.advent2020.dec16;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TicketFieldRule {
    private final String fieldName;
    private final List<ValueRange> validRanges = new ArrayList<>();

    private TicketFieldRule(String fieldName) {
        this.fieldName = fieldName;
    }

    public void addValueRange(ValueRange valueRange) {
        this.validRanges.add(valueRange);
    }

    public boolean isValueValid(int valueToCheck) {
        for (ValueRange range : validRanges) {
            if (range.isWithinRange(valueToCheck))
                return true;
        }
        return false;
    }

    public List<Integer> returnInvalidValuesForTicket(List<Integer> ticketValues) {
        final List<Integer> invalidValues = new ArrayList<>();
        ticketValues.forEach(value -> {
            if (!isValueValid(value))
                invalidValues.add(value);
        });
        return invalidValues;
    }

    public String getFieldName() { return this.fieldName; }

    public static TicketFieldRule buildFromEntryData(String entryData) {
        String[]nameAndValues = StringUtils.split(entryData, ":");
        if (nameAndValues.length != 2) {
            throw new IllegalArgumentException("Cannot parse ticket field rule from entry data '" + entryData + "'");
        }
        TicketFieldRule ticketFieldRule = new TicketFieldRule(nameAndValues[0]);
        String[]fieldRules = StringUtils.split(nameAndValues[1], " ");
        if (fieldRules.length != 3) {
            throw new IllegalArgumentException("Cannot parse ticket field rules from '" + nameAndValues[1] + "'");
        }
        ticketFieldRule.addValueRange(new ValueRange(fieldRules[0]));
        ticketFieldRule.addValueRange(new ValueRange(fieldRules[2]));
        return ticketFieldRule;
    }

    public static class ValueRange {
        final int minimum;
        final int maximum;

        public ValueRange(int minimum, int maximum) {
            this.minimum = minimum;
            this.maximum = maximum;
        }

        public ValueRange(String rangeString) {
            String[] rangeFields = StringUtils.split(rangeString, "-");
            if (rangeFields.length != 2) {
                throw new IllegalArgumentException("Invalid ValueRange '" + rangeString + "'");
            }
            this.minimum = Integer.parseInt(rangeFields[0]);
            this.maximum = Integer.parseInt(rangeFields[1]);
        }

        public boolean isWithinRange(int value) { return value >= this.minimum && value <= this.maximum; }
        public int getMinimum() { return this.minimum; }
        public int getMaximum() { return this.maximum; }
    }
}
