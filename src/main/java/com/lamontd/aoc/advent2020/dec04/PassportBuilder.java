package com.lamontd.aoc.advent2020.dec04;

public class PassportBuilder {
    private Passport internalPassport;
    public PassportBuilder() {
        this.internalPassport = new Passport();
    }

    public void parseEntry(String entry) throws IllegalArgumentException {
        String[] entries = entry.split(":");
        if (entries.length != 2) {
            throw new IllegalArgumentException("Entry '" + entry + "' has " + entries.length + " entries");
        }
        switch(PassportField.findByAbbreviation(entries[0])) {
            case BIRTH_YEAR:
                birthYear(Integer.parseInt(entries[1]));
                break;
            case COUNTRY_ID:
                countryId(entries[1]);
                break;
            case EYE_COLOR:
                eyeColor(entries[1]);
                break;
            case EXPIRATION_YEAR:
                expirationYear(Integer.parseInt(entries[1]));
                break;
            case HAIR_COLOR:
                hairColor(entries[1]);
                break;
            case HEIGHT:
                height(entries[1]);
                break;
            case ISSUE_YEAR:
                issueYear(Integer.parseInt(entries[1]));
                break;
            case PASSPORT_ID:
                passportId(entries[1]);
                break;
            default:
                throw new IllegalArgumentException("Could not find abbreviation for passport field '" + entries[0] + "'");
        }
    }

    public Passport build() {
        return internalPassport;
    }

    public PassportBuilder birthYear(int birthYear) {
        internalPassport.setBirthYear(birthYear);
        return this;
    }

    public PassportBuilder countryId(String value) {
        internalPassport.setCountryId(value);
        return this;
    }

    public PassportBuilder expirationYear(int expirationYear) {
        internalPassport.setExpirationYear(expirationYear);
        return this;
    }

    public PassportBuilder eyeColor(String value) {
        internalPassport.setEyeColor(value);
        return this;
    }

    public PassportBuilder hairColor(String value) {
        internalPassport.setHairColor(value);
        return this;
    }

    public PassportBuilder height(String heightString) {
        internalPassport.setHeight(heightString);
        return this;
    }

    public PassportBuilder issueYear(int issueYear) {
        internalPassport.setIssueYear(issueYear);
        return this;
    }

    public PassportBuilder passportId(String value) {
        internalPassport.setPassportId(value);
        return this;
    }

}
