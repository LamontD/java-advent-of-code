package com.lamontd.adventofcode.advent2022.dec04;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CampCleanup {
    private static final Logger logger = LoggerFactory.getLogger(CampCleanup.class);

    private List<ElfSection> sections = new ArrayList<>();

    public void loadSections(LocalResourceInput resourceInput) {
        sections = resourceInput.getInput().stream().map(ElfSection::new).collect(Collectors.toList());
        logger.info("Loaded " + sections.size() + " sections from input");
    }

    public long countBasicOverlaps() {
        return sections.stream().filter(ElfSection::overlapsCompletely).count();
    }

    public long countAnyOverlaps() {
        return sections.stream().filter(ElfSection::overlapsAtAll).count();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Camp Cleanup");

        CampCleanup cleanup = new CampCleanup();
        cleanup.loadSections(new LocalResourceInput("day04/sample.txt"));
        logger.info("We see " + cleanup.countBasicOverlaps() + " total overlaps in the sample");
        logger.info("We see " + cleanup.countAnyOverlaps() + " even partial overlaps in the sample");
        cleanup.loadSections(new LocalResourceInput("day04/input.txt"));
        logger.info("We see " + cleanup.countBasicOverlaps() + " total overlaps in the main input");
        logger.info("We see " + cleanup.countAnyOverlaps() + " even partial overlaps in the main input");
        timer.finish();
    }
}
