package com.lamontd.adventofcode.advent2023.day01;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Trebuchet {
    private static final Logger logger = LoggerFactory.getLogger(Trebuchet.class);

    private final List<CalibrationLine> lines = new ArrayList<>();
    private void init(LocalResourceInput resourceInput) {
        lines.clear();
        for (String inputLine : resourceInput.getInput()) {
            lines.add(new CalibrationLine(inputLine));
        }
    }

    private int getCalibrationValue() {
        return lines.stream().mapToInt(CalibrationLine::getCalibratedDigitalValue).sum();
    }

    private int getEnhancedCalibrationValue() {
        return lines.stream().mapToInt(CalibrationLine::getCalibratedSpelledValue).sum();
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("Trebuchet");

        Trebuchet trebuchet = new Trebuchet();
        logger.info("Working the test case...");
        trebuchet.init(new LocalResourceInput("day01/sample.txt"));
        logger.info("Sample answer for part 1 is {}", trebuchet.getCalibrationValue());
        trebuchet.init(new LocalResourceInput("day01/spelledsample.txt"));
        logger.info("Enhanced answer for part 2 is {}", trebuchet.getEnhancedCalibrationValue());
        logger.info("Working the real case");
        trebuchet.init(new LocalResourceInput("day01/input.txt"));
        logger.info("Real answer for part 1 is {}", trebuchet.getCalibrationValue());
        logger.info("Real answer for part 2 is {}", trebuchet.getEnhancedCalibrationValue());
        timer.finish();
    }
}
