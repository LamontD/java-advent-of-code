package com.lamontd.aoc.advent2020.dec16;

import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class TicketValidatorTester {
    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testFindInvalidValuesForAllRules() {
        TicketValidator validator = new TicketValidator(List.of(
                TicketFieldRule.buildFromEntryData("class: 1-3 or 5-7"),
                TicketFieldRule.buildFromEntryData("row: 6-11 or 33-44"),
                TicketFieldRule.buildFromEntryData("seat: 13-40 or 45-50")
        ));

        Assert.assertEquals(List.of(), validator.findValuesNotValidForAnyRule(new Ticket("7,1,14")));
        Assert.assertEquals(List.of(), validator.findValuesNotValidForAnyRule(new Ticket("7,3,47")));
        Assert.assertEquals(List.of(4), validator.findValuesNotValidForAnyRule(new Ticket("40,4,50")));
        Assert.assertEquals(List.of(55), validator.findValuesNotValidForAnyRule(new Ticket("55,2,20")));
        Assert.assertEquals(List.of(12), validator.findValuesNotValidForAnyRule(new Ticket("38,6,12")));
    }

    @Test
    public void testDetermineValidRuleToFieldMapping() {
        TicketValidator validator = new TicketValidator(List.of(
                TicketFieldRule.buildFromEntryData("class: 0-1 or 4-19"),
                TicketFieldRule.buildFromEntryData("row: 0-5 or 8-19"),
                TicketFieldRule.buildFromEntryData("seat: 0-13 or 16-19")
        ));

        List<Ticket> nearbyValidTickets = List.of(new Ticket("3,9,18"),
                new Ticket("15,1,5"),
                new Ticket("5,14,9"));
        validator.determineValidRuleToFieldMapping(nearbyValidTickets);
        Assert.assertEquals(Map.of("class", 2, "row", 1, "seat", 3), validator.getRuleToTicketFieldMapping());

        Ticket myTicket = new Ticket("11,12,13");
        Assert.assertEquals(Map.of("class", 12, "row", 11, "seat", 13), validator.mapTicketToFieldsByName(myTicket));
    }
}
