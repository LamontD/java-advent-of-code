package com.lamontd.adventofcode.advent2020.dec16;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class TicketTranslation {
    private static final Logger logger = LoggerFactory.getLogger(TicketTranslation.class);

    private TicketValidator ticketValidator;
    private Ticket myTicket;
    private List<Ticket> nearbyTickets = new ArrayList<>();


    public void setTicketValidator(TicketValidator validator) { this.ticketValidator = validator; }
    public void setMyTicket(Ticket ticket) { this.myTicket = ticket; }
    public void addNearbyTicket(Ticket nearbyTicket) { this.nearbyTickets.add(nearbyTicket); }
    public int getNumberOfValidationRules() { return this.ticketValidator.getNumberOfValidationRules();}

    public int sumInvalidValuesAcrossAllTickets() {
        int sumOfInvalidValues = 0;
        for (int invalidVal : ticketValidator.findValuesNotValidForAnyRule(myTicket)) {
            sumOfInvalidValues += invalidVal;
        }
        for (Ticket nearbyTicket : nearbyTickets) {
            for (int invalidVal : ticketValidator.findValuesNotValidForAnyRule(nearbyTicket)) {
                sumOfInvalidValues += invalidVal;
            }
        }
        return sumOfInvalidValues;
    }

    public static TicketTranslation createFromEntryData(List<String> entryData) {
        Iterator<String> entryIterator = entryData.iterator();
        logger.info("Reading the ticket rules...");
        List<TicketFieldRule> ticketRules = new ArrayList<>();
        for (String ruleString = entryIterator.next(); StringUtils.isNotBlank(ruleString); ruleString = entryIterator.next()) {
            ticketRules.add(TicketFieldRule.buildFromEntryData(ruleString));
        }
        logger.info("Read " + ticketRules.size() + " rules from input data");

        TicketTranslation translation = new TicketTranslation();
        translation.setTicketValidator(new TicketValidator(ticketRules));

        entryIterator.next(); // your ticket:
        translation.setMyTicket(new Ticket(entryIterator.next()));
        entryIterator.next();
        entryIterator.next(); // nearby tickets:
        for (String nearbyTicketString = entryIterator.next(); StringUtils.isNotBlank(nearbyTicketString); nearbyTicketString = entryIterator.hasNext() ? entryIterator.next() : null) {
            translation.addNearbyTicket(new Ticket(nearbyTicketString));
        }

        logger.info("Found my ticket and " + translation.nearbyTickets.size() + " nearby tickets from input data");
        return translation;
    }

    public long multiplyValuesStartingWithDeparture() {
        List<Ticket> validNearbyTickets = new ArrayList<>(nearbyTickets);
        for (ListIterator<Ticket> nearbyTicketIter = validNearbyTickets.listIterator(); nearbyTicketIter.hasNext(); ) {
            if (!ticketValidator.isValid(nearbyTicketIter.next()))
                nearbyTicketIter.remove();
        }
        ticketValidator.determineValidRuleToFieldMapping(validNearbyTickets);
        Map<String, Integer> myTicketToFieldMap = ticketValidator.mapTicketToFieldsByName(myTicket);
        long multiplicand = 1L;
        for (Map.Entry<String, Integer> ticketField : myTicketToFieldMap.entrySet()) {
            if (ticketField.getKey().startsWith("departure")) {
                multiplicand *= ticketField.getValue();
            }
        }
        return multiplicand;
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        long milliStart = System.currentTimeMillis();
        logger.info("Welcome to Ticket Translation!");
        logger.info("Creating our translation...");
        LocalResourceInput resourceInput = new LocalResourceInput("day16/advent-day16-input.txt");
        TicketTranslation translation = createFromEntryData(resourceInput.getInput());
        logger.info("The Part 1 error scanning rate is " + translation.sumInvalidValuesAcrossAllTickets());
        logger.info("Part 2: Determine mappings and do the multiplication...");
        long finalAnswer = translation.multiplyValuesStartingWithDeparture();
        logger.info("I found an answer of " + finalAnswer);
        long milliEnd = System.currentTimeMillis();
        logger.info("This took all of " + (milliEnd - milliStart) + "ms");

    }
}
