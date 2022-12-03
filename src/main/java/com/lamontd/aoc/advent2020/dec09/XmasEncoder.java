package com.lamontd.aoc.advent2020.dec09;

import java.util.*;

public class XmasEncoder {
    public List<Long> preamble;

    public XmasEncoder(Collection<Long> preamble) {
        this.preamble = new ArrayList<>(preamble);
    }

    public int getPreambleLength() { return this.preamble.size(); }

    public void processNumber(long input) throws BadXmasCypherInputException {
        for (int i= 0; i < preamble.size() - 1; i++) {
            for (int j=i; j < preamble.size(); j++) {
                if(preamble.get(i) + preamble.get(j) == input) {
                    preamble.remove(0);
                    preamble.add(input);
                    return;
                }
            }
        }
        throw new BadXmasCypherInputException(input);
    }
}
