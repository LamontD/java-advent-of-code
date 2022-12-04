package com.lamontd.adventofcode.advent2020.dec16;

import junit.framework.Assert;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TicketTranslationTester {

    @BeforeAll
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testTicketTranslation() {
        TicketValidator validator = new TicketValidator(List.of(
                TicketFieldRule.buildFromEntryData("class: 1-3 or 5-7"),
                TicketFieldRule.buildFromEntryData("row: 6-11 or 33-44"),
                TicketFieldRule.buildFromEntryData("seat: 13-40 or 45-50")
        ));

        TicketTranslation translation = new TicketTranslation();
        translation.setTicketValidator(validator);
        translation.setMyTicket(new Ticket("7,1,14"));
        translation.addNearbyTicket(new Ticket("7,3,47"));
        translation.addNearbyTicket(new Ticket("40,4,50"));
        translation.addNearbyTicket(new Ticket("55,2,20"));
        translation.addNearbyTicket(new Ticket("38,6,12"));

        Assert.assertEquals(71, translation.sumInvalidValuesAcrossAllTickets());
    }

    @Test
    public void testTranslationCreationFromEntryData() {
        TicketTranslation translation = TicketTranslation.createFromEntryData(List.of(
                "class: 1-3 or 5-7",
                        "row: 6-11 or 33-44",
                        "seat: 13-40 or 45-50",
                        "",
                        "your ticket:",
                        "7,1,14",
                        "",
                        "nearby tickets:",
                        "7,3,47",
                        "40,4,50",
                        "55,2,20",
                        "38,6,12"
        ));
        Assert.assertEquals(3, translation.getNumberOfValidationRules());
        Assert.assertEquals(71, translation.sumInvalidValuesAcrossAllTickets());
    }
}
