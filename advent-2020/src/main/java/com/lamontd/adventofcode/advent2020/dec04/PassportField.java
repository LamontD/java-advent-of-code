package com.lamontd.adventofcode.advent2020.dec04;

import org.apache.commons.lang3.StringUtils;

public enum PassportField {
    BIRTH_YEAR("byr"),
    ISSUE_YEAR("iyr"),
    EXPIRATION_YEAR("eyr"),
    HEIGHT("hgt"),
    EYE_COLOR("ecl"),
    PASSPORT_ID("pid"),
    COUNTRY_ID("cid"),
    HAIR_COLOR("hcl");
    private String abbreviation;
    private PassportField(String abbreviation) { this.abbreviation = abbreviation; }
    public static PassportField findByAbbreviation(String abbreviation) {
        for (PassportField field : PassportField.values()) {
            if (StringUtils.equalsIgnoreCase(field.abbreviation, abbreviation))
                return field;
        }
        return null;
    }
}
