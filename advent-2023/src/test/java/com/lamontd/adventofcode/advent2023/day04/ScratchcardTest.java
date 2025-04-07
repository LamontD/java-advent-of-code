package com.lamontd.adventofcode.advent2023.day04;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ScratchcardTest {
    private final static Map<Integer, Scratchcards> inputMap = new HashMap<>();

    @BeforeAll
    public static void init() throws IOException {
        LocalResourceInput resourceInput = new LocalResourceInput("day04/sample.txt");
        for (String line : resourceInput.getInput()) {
            Scratchcards card = new Scratchcards(line);
            inputMap.put(card.getCardNum(), card);
        }
    }

    @Test
    public void testScratchcardInitialization() {
        Scratchcards card1 =inputMap.get(1);
        assertEquals(1, card1.getCardNum());
        assertEquals(5, card1.getWinningNumbers().size());
        assertEquals(8, card1.getScratchedNumbers().size());
        List<Integer> matches = card1.getMatches();
        assertEquals(4, matches.size());
        assertTrue(matches.contains(48));
        assertTrue(matches.contains(83));
        assertTrue(matches.contains(17));
        assertTrue(matches.contains(86));
        assertFalse(matches.contains(6));
        assertEquals(8, card1.getInitialScore());

        Scratchcards card2 = inputMap.get(2);
        assertEquals(2, card2.getMatches().size());
        assertEquals(2, card2.getInitialScore());

        assertEquals(2, inputMap.get(3).getInitialScore());
        assertEquals(1, inputMap.get(4).getInitialScore());
        assertEquals(0, inputMap.get(5).getInitialScore());
        assertEquals(0, inputMap.get(6).getInitialScore());
    }
}
