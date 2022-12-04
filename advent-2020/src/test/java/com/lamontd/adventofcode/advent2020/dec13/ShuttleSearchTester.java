package com.lamontd.adventofcode.advent2020.dec13;

import junit.framework.Assert;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShuttleSearchTester {
    @BeforeAll
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testStairstepTimestamp() {

        ShuttleSearch shuttleSearch = new ShuttleSearch(0);
        shuttleSearch.addShuttle(0, new Shuttle(17));
        shuttleSearch.addShuttle(2, new Shuttle(13));
        shuttleSearch.addShuttle(3, new Shuttle(19));
        Assert.assertEquals(3417L, shuttleSearch.findStairstepTimestamp());

        shuttleSearch = new ShuttleSearch(0);
        shuttleSearch.addShuttle(0, new Shuttle(67));
        shuttleSearch.addShuttle(1, new Shuttle(7));
        shuttleSearch.addShuttle(2, new Shuttle(59));
        shuttleSearch.addShuttle(3, new Shuttle(61));
        Assert.assertEquals(754018L, shuttleSearch.findStairstepTimestamp());

        shuttleSearch = new ShuttleSearch(0);
        shuttleSearch.addShuttle(0, new Shuttle(67));
        shuttleSearch.addShuttle(2, new Shuttle(7));
        shuttleSearch.addShuttle(3, new Shuttle(59));
        shuttleSearch.addShuttle(4, new Shuttle(61));
        Assert.assertEquals(779210L, shuttleSearch.findStairstepTimestamp());

        shuttleSearch = new ShuttleSearch(0);
        shuttleSearch.addShuttle(0, new Shuttle(67));
        shuttleSearch.addShuttle(1, new Shuttle(7));
        shuttleSearch.addShuttle(3, new Shuttle(59));
        shuttleSearch.addShuttle(4, new Shuttle(61));
        Assert.assertEquals(1261476L, shuttleSearch.findStairstepTimestamp());

        shuttleSearch = new ShuttleSearch(0);
        shuttleSearch.addShuttle(0, new Shuttle(1789));
        shuttleSearch.addShuttle(1, new Shuttle(37));
        shuttleSearch.addShuttle(2, new Shuttle(47));
        shuttleSearch.addShuttle(3, new Shuttle(1889));
        Assert.assertEquals(1202161486L, shuttleSearch.findStairstepTimestamp());
    }

    @Test
    public void testFindBestTimestampSinglePass() {

        ShuttleSearch shuttleSearch = new ShuttleSearch(0);
        shuttleSearch.addShuttle(0, new Shuttle(17));
        shuttleSearch.addShuttle(2, new Shuttle(13));
        shuttleSearch.addShuttle(3, new Shuttle(19));
        Assert.assertEquals(3417L, shuttleSearch.findStairstepTimestamp());

        shuttleSearch = new ShuttleSearch(0);
        shuttleSearch.addShuttle(0, new Shuttle(67));
        shuttleSearch.addShuttle(1, new Shuttle(7));
        shuttleSearch.addShuttle(2, new Shuttle(59));
        shuttleSearch.addShuttle(3, new Shuttle(61));
        Assert.assertEquals(754018L, shuttleSearch.findStairstepTimestamp());

        shuttleSearch = new ShuttleSearch(0);
        shuttleSearch.addShuttle(0, new Shuttle(67));
        shuttleSearch.addShuttle(2, new Shuttle(7));
        shuttleSearch.addShuttle(3, new Shuttle(59));
        shuttleSearch.addShuttle(4, new Shuttle(61));
        Assert.assertEquals(779210L, shuttleSearch.findStairstepTimestamp());

        shuttleSearch = new ShuttleSearch(0);
        shuttleSearch.addShuttle(0, new Shuttle(67));
        shuttleSearch.addShuttle(1, new Shuttle(7));
        shuttleSearch.addShuttle(3, new Shuttle(59));
        shuttleSearch.addShuttle(4, new Shuttle(61));
        Assert.assertEquals(1261476L, shuttleSearch.findStairstepTimestamp());

        shuttleSearch = new ShuttleSearch(0);
        shuttleSearch.addShuttle(0, new Shuttle(1789));
        shuttleSearch.addShuttle(1, new Shuttle(37));
        shuttleSearch.addShuttle(2, new Shuttle(47));
        shuttleSearch.addShuttle(3, new Shuttle(1889));
        Assert.assertEquals(1202161486L, shuttleSearch.findStairstepTimestamp());
    }


    //    @Test
    public void testFindIdealTimestamp() {
        ShuttleSearch shuttleSearch = new ShuttleSearch(0);
        shuttleSearch.addShuttle(0, new Shuttle(17));
        shuttleSearch.addShuttle(2, new Shuttle(13));
        shuttleSearch.addShuttle(3, new Shuttle(19));
        Assert.assertEquals(3417L, shuttleSearch.findIdealTimestamp());

    }
}
