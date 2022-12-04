package com.lamontd.adventofcode.advent2020.dec22;


import junit.framework.Assert;
import org.junit.jupiter.api.Test;

public class CardTester {

    @Test
    public void testCardComparison() {
        Card card1 = new Card(7);
        Card card2 = new Card(17);
        Assert.assertEquals(7, card1.getFaceValue());
        Assert.assertEquals(17, card2.getFaceValue());
        Assert.assertTrue(card2.isHigher(card1));
    }
}
