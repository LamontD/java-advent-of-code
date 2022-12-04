package com.lamontd.adventofcode.advent2020.dec16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TicketValidator {
    private static final Logger logger = LoggerFactory.getLogger(TicketValidator.class);
    private final Map<String, TicketFieldRule> ticketRules = new HashMap<>();
    private final Map<String, Integer> ruleToTicketFieldMapping = new HashMap<>();

    public TicketValidator(List<TicketFieldRule> ticketRules) {
        for (TicketFieldRule rule : ticketRules) {
            this.ticketRules.put(rule.getFieldName(), rule);
        }
    }

    public List<Integer> findValuesNotValidForAnyRule(Ticket ticketToCheck) {
        List<Integer> invalidValuesForAnyRule = new ArrayList<>(ticketToCheck.getFieldValues());
        for (ListIterator<Integer> invalidIter = invalidValuesForAnyRule.listIterator(); invalidIter.hasNext(); ) {
            int valueToCheck = invalidIter.next();
            for (TicketFieldRule ticketRule : ticketRules.values()) {
                if (ticketRule.isValueValid(valueToCheck)) {
                    invalidIter.remove();
                    break;
                }
            }
        }
        return invalidValuesForAnyRule;
    }

    public boolean isValid(Ticket ticketToCheck) {
        return findValuesNotValidForAnyRule(ticketToCheck).isEmpty();
    }

    public void determineValidRuleToFieldMapping(List<Ticket> ticketValidationList) {

        final Map<String, List<Integer>> workingRuleMappings = new HashMap<>();

        List<Integer> existingTicketFields = ticketValidationList.get(0).getAllTicketFields();
        ticketRules.keySet().forEach(key -> workingRuleMappings.put(key, new ArrayList<>(existingTicketFields)));
        while (!isRuleMappingFinalized(workingRuleMappings)) {
            boolean ruleMappingChanged = false;
            for (Ticket ticketToCheck : ticketValidationList) {
                for (Map.Entry<String, List<Integer>> ruleMapEntry : workingRuleMappings.entrySet()) {
                    TicketFieldRule ticketFieldRule = ticketRules.get(ruleMapEntry.getKey());
                    for (ListIterator<Integer> ticketFieldIter = ruleMapEntry.getValue().listIterator(); ticketFieldIter.hasNext(); ) {
                        int ticketFieldToCheck = ticketFieldIter.next();
                        if (!ticketFieldRule.isValueValid(ticketToCheck.getTicketFieldValue(ticketFieldToCheck))) {
                            ticketFieldIter.remove();
                            ruleMappingChanged = true;
                            break;
                        }
                    }
                    if (ruleMappingChanged && ruleMapEntry.getValue().size() == 1) {
                        logger.info("Found sole valid mapping for rule [" + ruleMapEntry.getKey() + "]");
                        ruleToTicketFieldMapping.put(ruleMapEntry.getKey(), ruleMapEntry.getValue().get(0));
                        break;
                    }
                }
                makeWorkingMappingsInternallyConsistent(workingRuleMappings);
                ruleToTicketFieldMapping.keySet().forEach(workingRuleMappings::remove);
            }
        }

        workingRuleMappings.forEach((key, value) -> ruleToTicketFieldMapping.put(key, value.get(0)));
    }

    private static void makeWorkingMappingsInternallyConsistent(Map<String, List<Integer>> workingRuleMapping) {
        List<Integer> singleValuesToRemove = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> mapEntry : workingRuleMapping.entrySet()) {
            if (mapEntry.getValue().size() == 1) {
                logger.info("Found valid mapping for rule [" + mapEntry.getKey() + "] by process of elimination");
                singleValuesToRemove.add(mapEntry.getValue().get(0));
            }
        }

        if (!singleValuesToRemove.isEmpty()) {
            for (int singleValue : singleValuesToRemove) {
                for (List<Integer> mapEntryValues : workingRuleMapping.values()) {
                    if (mapEntryValues.size() > 1 && mapEntryValues.contains(singleValue)) {
                        mapEntryValues.remove((Integer) singleValue);
                    }
                }
            }
        }
    }

    private static boolean isRuleMappingFinalized(Map<String, List<Integer>> ruleMappings) {
        boolean ruleMappingFinalized = true;
        for (Map.Entry<String, List<Integer>> ruleMapEntry : ruleMappings.entrySet()) {
            if (ruleMapEntry.getValue().size() > 1) {
                ruleMappingFinalized = false;
                break;
            }
        }
        return ruleMappingFinalized;
    }

    public int getNumberOfValidationRules() {
        return this.ticketRules.size();
    }

    public Map<String, Integer> getRuleToTicketFieldMapping() {
        return ruleToTicketFieldMapping;
    }

    public Map<String, Integer> mapTicketToFieldsByName(Ticket ticketToMap) {
        Map<String, Integer> nameToValueMap = new HashMap<>();
        ruleToTicketFieldMapping.keySet().forEach(key -> nameToValueMap.put(key, ticketToMap.getTicketFieldValue(ruleToTicketFieldMapping.get(key))));
        return nameToValueMap;
    }
}
