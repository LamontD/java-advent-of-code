package com.lamontd.adventofcode.advent2022.dec04;

public class ElfSection {
    private final long firstStart;
    private final long firstFinish;
    private final long secondStart;
    private final long secondFinish;

    public ElfSection(String sectionDefinition) {
        String[]section = sectionDefinition.split(",");
        String[]first = section[0].split("-");
        this.firstStart = Long.parseLong(first[0]);
        this.firstFinish = Long.parseLong(first[1]);
        String[]second = section[1].split("-");
        this.secondStart = Long.parseLong(second[0]);
        this.secondFinish = Long.parseLong(second[1]);
    }

    public boolean overlapsCompletely() {
        return (secondStart >= firstStart && secondFinish <= firstFinish) || (firstStart >= secondStart && firstFinish <= secondFinish);
    }

    public boolean overlapsAtAll() {
        return (firstStart <= secondStart && secondStart <= firstFinish)
                || (firstStart <= secondFinish && secondFinish <= firstFinish)
                || (secondStart <= firstStart && firstStart <= secondFinish)
                || (secondStart <= firstFinish && firstFinish <= secondFinish);
    }

    @Override
    public String toString() {
        return "ElfSection: " +
                firstStart +
                " - " + firstFinish +
                ", " + secondStart +
                " - " + secondFinish;
    }
}
