package com.lamontd.adventofcode.advent2020.dec16;


import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TicketFieldRuleTester {
    @Test
    public void testFieldRuleConstruction() {
        TicketFieldRule rule = TicketFieldRule.buildFromEntryData("class: 1-3 or 5-7");
        Assert.assertEquals("class", rule.getFieldName());
        Assert.assertTrue(rule.isValueValid(1));
        Assert.assertTrue(rule.isValueValid(7));
        Assert.assertFalse(rule.isValueValid(14));
    }

    @Test
    public void testFindingInvalidValues() {
        TicketFieldRule rule = TicketFieldRule.buildFromEntryData("class: 1-3 or 5-7");
        Assert.assertEquals(List.of(14), rule.returnInvalidValuesForTicket(List.of(7, 1, 14)));
    }
}
