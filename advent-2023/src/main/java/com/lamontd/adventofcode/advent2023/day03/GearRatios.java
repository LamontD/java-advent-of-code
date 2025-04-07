package com.lamontd.adventofcode.advent2023.day03;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class GearRatios {
    private static final Logger logger = LoggerFactory.getLogger(GearRatios.class);

    private EngineSchematic schematic;

    public GearRatios(LocalResourceInput resourceInput) {
        schematic = new EngineSchematic(resourceInput.getInput());
    }

    public int getSumOfGearRatios() {
        final List<EngineSchematic.Gear> gearList = schematic.getGears();
//        gearList.forEach(gear -> logger.info("Gear spotted: {}", gear));
        return gearList.stream()
                .mapToInt(EngineSchematic.Gear::getGearRatio)
                .sum();
    }

    public int getSumOfPartNumbers() {
        final List<EngineSchematic.SchematicNumber> schematicNumbers = schematic.getSchematicNumbers();
//        schematicNumbers.forEach(num -> logger.info(num.toString()));
        return schematicNumbers.stream()
                .filter(EngineSchematic.SchematicNumber::isPartNumber)
                .mapToInt(EngineSchematic.SchematicNumber::getNumber)
                .sum();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Gear Ratios");

        LocalResourceInput sampleInput = new LocalResourceInput("day03/sample.txt");
        GearRatios sampleRatios = new GearRatios(sampleInput);
        logger.info("Loaded sample ratios; sum of part numbers is {}", sampleRatios.getSumOfPartNumbers());
        logger.info("Sum of gear ratios is {}", sampleRatios.getSumOfGearRatios());

        LocalResourceInput problemInput = new LocalResourceInput("day03/input.txt");
        GearRatios problemRatios = new GearRatios(problemInput);
        logger.info("Loaded problem ratios; sum of part numbers is {}", problemRatios.getSumOfPartNumbers());
        logger.info("Sum of gear ratios is {}", problemRatios.getSumOfGearRatios());
        timer.finish();

    }
}
