package com.lamontd.adventofcode.advent2023.day01;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalibrationLineTest {

    @BeforeAll
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testBasicFunctionality() {
        CalibrationLine line = new CalibrationLine("1abc2");
        assertEquals(1, line.getFirstDigit());
        assertEquals(2, line.getLastDigit());
        assertEquals(12, line.getCalibratedDigitalValue());

        line = new CalibrationLine("pqr3stu8vwx");
        assertEquals(3, line.getFirstDigit());
        assertEquals(8, line.getLastDigit());
        assertEquals(38, line.getCalibratedDigitalValue());

        line = new CalibrationLine("a1b2c3d4e5f");
        assertEquals(1, line.getFirstDigit());
        assertEquals(5, line.getLastDigit());
        assertEquals(15, line.getCalibratedDigitalValue());

        line = new CalibrationLine("treb7uchet");
        assertEquals(7, line.getFirstDigit());
        assertEquals(7, line.getLastDigit());
        assertEquals(77, line.getCalibratedDigitalValue());
    }

    @Test
    public void testSpelledDigits() {
        CalibrationLine line = new CalibrationLine("two1nine");
        assertEquals(2, line.getFirstSpelledDigit());

        line = new CalibrationLine("eightwothree");
        assertEquals(8, line.getFirstSpelledDigit());
        assertEquals(3, line.getLastSpelledDigit());
        assertEquals(83, line.getCalibratedSpelledValue());

        line = new CalibrationLine("abcone2threexyz");
        assertEquals(1, line.getFirstSpelledDigit());
        assertEquals(3, line.getLastSpelledDigit());
        assertEquals(13, line.getCalibratedSpelledValue());

        line = new CalibrationLine("xtwone3four");
        assertEquals(2, line.getFirstSpelledDigit());
        assertEquals(4, line.getLastSpelledDigit());
        assertEquals(24, line.getCalibratedSpelledValue());

        line = new CalibrationLine("4nineeightseven2");
        assertEquals(4, line.getFirstSpelledDigit());
        assertEquals(2, line.getLastSpelledDigit());
        assertEquals(42, line.getCalibratedSpelledValue());

        line = new CalibrationLine("zoneight234");
        assertEquals(1, line.getFirstSpelledDigit());
        assertEquals(4, line.getLastSpelledDigit());
        assertEquals(14, line.getCalibratedSpelledValue());

        line = new CalibrationLine("7pqrstsixteen");
        assertEquals(7, line.getFirstSpelledDigit());
        assertEquals(6, line.getLastSpelledDigit());
        assertEquals(76, line.getCalibratedSpelledValue());

        line = new CalibrationLine("fktwone4ninennnjdccftwothreetwo");
        assertEquals(2, line.getFirstSpelledDigit());
        assertEquals(2, line.getLastSpelledDigit());
        assertEquals(22, line.getCalibratedSpelledValue());
    }
}
