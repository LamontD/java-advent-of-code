package com.lamontd.adventofcode.advent2020.dec04;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Passport {
    private static final List<String> VALID_EYE_COLORS = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
    private Integer birthYear;
    private Integer issueYear;
    private Integer expirationYear;
    private String height;
    private String hairColor;
    private String eyeColor;
    private String passportId;
    private String countryId;

    public Passport() {}

    public boolean isValid() {
        return this.birthYear != null
                && this.issueYear != null
                && this.expirationYear != null
                && StringUtils.isNotBlank(this.height)
                && StringUtils.isNotBlank(this.hairColor)
                && StringUtils.isNotBlank(this.eyeColor)
                && StringUtils.isNotBlank(this.passportId)
                && StringUtils.isNotBlank(this.countryId);
    }

    public boolean isValidIfWeAreLenient() {
        return this.birthYear != null
                && this.issueYear != null
                && this.expirationYear != null
                && StringUtils.isNotBlank(this.height)
                && StringUtils.isNotBlank(this.hairColor)
                && StringUtils.isNotBlank(this.eyeColor)
                && StringUtils.isNotBlank(this.passportId);
    }

    public boolean isValidUnderValueValidation() {
        return birthYearIsValid()
                && issueYearIsValid()
                && expirationYearIsValid()
                && heightIsValid()
                && hairColorIsValid()
                && eyeColorIsValid()
                && passportIdIsValid();
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public boolean birthYearIsValid() {
        return this.birthYear != null && this.birthYear >= 1920 && this.birthYear <= 2002;
    }

    public boolean issueYearIsValid() {
        return this.issueYear != null && this.issueYear >= 2010 && this.issueYear <= 2020;
    }

    public boolean expirationYearIsValid() {
        return this.expirationYear != null && this.expirationYear >= 2020 && this.expirationYear <= 2030;
    }

    public boolean heightIsValid() {
        if (StringUtils.isEmpty(this.height))
            return false;
        if (this.height.endsWith("cm")) {
            int heightCm = Integer.parseInt(this.height.substring(0, this.height.length()-2));
            return heightCm >= 150 && heightCm <= 193;
        }
        if (this.height.endsWith("in")) {
            int heightIn = Integer.parseInt(this.height.substring(0, this.height.length()-2));
            return heightIn >= 59 && heightIn <= 76;
        }
        return false;
    }

    public boolean hairColorIsValid() {
        if (StringUtils.isBlank(this.hairColor))
            return false;
        return Pattern.matches("#([a-z0-9]){6}", this.hairColor);
    }

    public boolean eyeColorIsValid() {
        return StringUtils.isNotBlank(this.eyeColor) && VALID_EYE_COLORS.contains(eyeColor);
    }

    public boolean passportIdIsValid() {
        if (StringUtils.isBlank(this.passportId) || this.passportId.length() != 9)
            return false;
        try {
            Integer intValue = Integer.parseInt(this.passportId);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Integer getIssueYear() {
        return issueYear;
    }

    public void setIssueYear(Integer issueYear) {
        this.issueYear = issueYear;
    }

    public Integer getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
}
