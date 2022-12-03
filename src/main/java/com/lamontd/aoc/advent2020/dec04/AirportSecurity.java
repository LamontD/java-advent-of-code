package com.lamontd.aoc.advent2020.dec04;

import com.lamontd.aoc.utils.BasicCounter;
import com.lamontd.aoc.utils.LocalResourceInput;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AirportSecurity {
    private static final Logger logger = LoggerFactory.getLogger(AirportSecurity.class);

    private List<Passport> passportList;

    public AirportSecurity() { this.passportList = new ArrayList<>(); }

    public void addPassportFromString(String passportEntryString) {
        PassportBuilder passportBuilder = new PassportBuilder();
        for (String str : passportEntryString.split(" ")) {
            try {
                passportBuilder.parseEntry(str);
            } catch (IllegalArgumentException e) {
                logger.info("Error processing line '" + str + '"', e);
            }
        }
        passportList.add(passportBuilder.build());
    }
    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        logger.info("Welcome to Advent2020 Airport Security!");
        long milliStart = System.currentTimeMillis();
        logger.info("Reading raw input...");
        LocalResourceInput localResourceInput = new LocalResourceInput("advent2020/day4/advent-day4-input.txt");
        logger.info("Processing passports...");
        AirportSecurity airportSecurity = new AirportSecurity();
        StringBuilder passportLine = new StringBuilder();
        for (String entryLine : localResourceInput.getInput()) {
            if (StringUtils.isNotEmpty(entryLine)) {
                passportLine.append(" ").append(entryLine);
            } else {
                airportSecurity.addPassportFromString(passportLine.toString().trim());
                passportLine = new StringBuilder();
            }
        }
        // Fence post problem
        if (passportLine.length() > 0)
            airportSecurity.addPassportFromString(passportLine.toString().trim());
        logger.info("I read " + airportSecurity.passportList.size() + " passports from the input data");
        final BasicCounter lenientCounter = new BasicCounter();
        final BasicCounter strictCounter = new BasicCounter();
        final BasicCounter deepValidationCounter = new BasicCounter();
        airportSecurity.passportList.stream().forEach(passport -> {
            if (passport.isValidIfWeAreLenient()) lenientCounter.increment();
            if (passport.isValid()) strictCounter.increment();
            if (passport.isValidUnderValueValidation()) deepValidationCounter.increment();
        });
        logger.info("I found " + lenientCounter.currentValue() + " leniently valid passports");
        logger.info("I found " + strictCounter.currentValue() + " strictly valid passports");
        logger.info("I found " + deepValidationCounter.currentValue() + " deeply valid passports");
        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");
    }
}
