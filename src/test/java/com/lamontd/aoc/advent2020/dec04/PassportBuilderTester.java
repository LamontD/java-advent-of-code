package com.lamontd.aoc.advent2020.dec04;

import org.junit.Assert;
import org.junit.Test;

public class PassportBuilderTester {
    @Test
    public void testParsingEntries() {
        PassportBuilder builder = new PassportBuilder();
        builder.parseEntry("ecl:gry");
        Assert.assertTrue(builder.build().getEyeColor().equals("gry"));
        builder.parseEntry("pid:860033327");
        Assert.assertTrue(builder.build().getPassportId().equals("860033327"));
        builder.parseEntry("cid:147");
        Assert.assertTrue(builder.build().getCountryId().equals("147"));
        builder.parseEntry("eyr:2020");
        Assert.assertTrue(builder.build().getExpirationYear() == 2020);
        builder.parseEntry("hcl:#fffffd");
        Assert.assertTrue(builder.build().getHairColor().equals("#fffffd"));
        builder.parseEntry("iyr:2017");
        Assert.assertTrue(builder.build().getIssueYear() == 2017);
        builder.parseEntry("hgt:183cm");
        Assert.assertTrue(builder.build().getHeight().equals("183cm"));
    }

    @Test
    public void testInvalidBuiltPassports() {
        String[] invalidPassports = {"eyr:1972 cid:100 hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926",
                "iyr:2019 " +
                        "hcl:#602927 eyr:1967 hgt:170cm " +
                        "ecl:grn pid:012533040 byr:1946 ",
                "hcl:dab227 iyr:2012 " +
                        "ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277",
                "hgt:59cm ecl:zzz " +
                        "eyr:2038 hcl:74454a iyr:2023 " +
                        "pid:3556412378 byr:2007"
        };
        for (String str : invalidPassports) {
            Passport buildPassport = createPassportFromString(str);
            Assert.assertFalse(buildPassport.isValidUnderValueValidation());
        }
    }

    @Test
    public void testValidBuildPassports() {
        String[] validPassports = {
                "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980 " +
                        "hcl:#623a2f",
                        "eyr:2029 ecl:blu cid:129 byr:1989 " +
                        "iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm",
                        "hcl:#888785 " +
                        "hgt:164cm byr:2001 iyr:2015 cid:88 " +
                        "pid:545766238 ecl:hzl " +
                        "eyr:2022 ",
                        "iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719"
        };
        for (String str : validPassports) {
            Passport buildPassport = createPassportFromString(str);
            Assert.assertTrue(buildPassport.isValidUnderValueValidation());
        }
    }

    private static Passport createPassportFromString(String passportEntryString) {
        PassportBuilder passportBuilder = new PassportBuilder();
        for (String str : passportEntryString.split(" ")) {
            try {
                passportBuilder.parseEntry(str);
            } catch (IllegalArgumentException e) {
            }
        }
        return passportBuilder.build();
    }
}
