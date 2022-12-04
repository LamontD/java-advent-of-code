package com.lamontd.adventofcode.advent2020.dec16;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private final List<Integer> values = new ArrayList<>();

    public Ticket(String ticketData) {
        for (String str : StringUtils.split(ticketData, ","))
            values.add(Integer.parseInt(str));
    }

    public List<Integer> getFieldValues() { return this.values; }
    public int getNumberOfFields() { return this.values.size(); }
    public int getTicketFieldValue(int ticketField) { return values.get(ticketField - 1); }
    public List<Integer> getAllTicketFields() {
        List<Integer> fieldNumberList = new ArrayList<>();
        for (int i=0; i < values.size(); i++) {
            fieldNumberList.add(i+1);
        }
        return fieldNumberList;
    }

}
