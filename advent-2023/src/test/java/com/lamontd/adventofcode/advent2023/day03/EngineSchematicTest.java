package com.lamontd.adventofcode.advent2023.day03;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EngineSchematicTest {

    private static List<String> sampleInput;
    private static final Logger logger = LoggerFactory.getLogger(EngineSchematicTest.class);

    @BeforeAll
    public static void init() throws IOException {
        LocalResourceInput resourceInput = new LocalResourceInput("day03/sample.txt");
        sampleInput = resourceInput.getInput();
        BasicConfigurator.configure();
    }

    @Test
    public void testInitialLoad() {
        EngineSchematic schematic = new EngineSchematic(sampleInput);
        assertEquals(10, schematic.getNumRows());
        assertEquals(10, schematic.getNumColumns());
        logger.info("Schematic is {}", schematic);
        List<EngineSchematic.SchematicNumber> schematicNumbers = schematic.getSchematicNumbers();
        assertEquals(10, schematicNumbers.size());
        schematicNumbers.forEach(num -> logger.info(num.toString()));
    }
}
