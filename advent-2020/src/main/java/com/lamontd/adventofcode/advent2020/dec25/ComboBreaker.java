package com.lamontd.adventofcode.advent2020.dec25;

import com.lamontd.adventofcode.utils.LocalResourceInput;
import com.lamontd.adventofcode.utils.ProblemTimer;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ComboBreaker {
    private static final Logger logger = LoggerFactory.getLogger(ComboBreaker.class);

    private static final int SUBJECT_VALUE = 20201227;

    private final long subjectNumber;
    private int cardSecretLoopSize = 1;
    private int doorSecretLoopSize = 1;
    private final long cardPublicKey;
    private final long doorPublicKey;

    public ComboBreaker(long subjectNumber, long cardPublicKey, long doorPublicKey) {
        this.subjectNumber = subjectNumber;
        this.cardPublicKey = cardPublicKey;
        this.doorPublicKey = doorPublicKey;
    }

    public void calculateSecretCardLoopSize() {
        for (long transformedNumber = transformNumber(1, subjectNumber);
             transformedNumber != cardPublicKey;
             transformedNumber = transformNumber(transformedNumber, subjectNumber)) {
            cardSecretLoopSize++;
        }
    }

    public void calculateSecretDoorLoopSize() {
        for (long transformedNumber = transformNumber(1, subjectNumber);
             transformedNumber != doorPublicKey;
             transformedNumber = transformNumber(transformedNumber, subjectNumber)) {
            doorSecretLoopSize++;
        }
    }

    public long getDoorEncryptionKey() {
        long result = 1L;
        for (int i=0; i < doorSecretLoopSize; i++) {
            result = transformNumber(result, cardPublicKey);
        }
        return result;
    }

    public long getCardEncryptionKey() {
        long result = 1L;
        for (int i=0; i < cardSecretLoopSize; i++) {
            result = transformNumber(result, doorPublicKey);
        }
        return result;
    }

    private static long transformNumber(long transformNumber, long subjectNumber) {
        long result = (transformNumber * subjectNumber) % SUBJECT_VALUE;
        return result;
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        ProblemTimer timer = new ProblemTimer("C-C-C-Combo Breaker");
        logger.info("Reading the input...");
        LocalResourceInput resourceInput = new LocalResourceInput("day25/input.txt");
        long cardPublicKey = Long.parseLong(resourceInput.getInput().get(0));
        long doorPublicKey = Long.parseLong(resourceInput.getInput().get(1));
        logger.info("Seeding part 1 with card public key of " + cardPublicKey + " and door public key of " + doorPublicKey);
        ComboBreaker comboBreaker = new ComboBreaker(7L, cardPublicKey, doorPublicKey);
        comboBreaker.calculateSecretCardLoopSize();
        logger.info("Calculated Card loop size of " + comboBreaker.cardSecretLoopSize);
        comboBreaker.calculateSecretDoorLoopSize();
        logger.info("Calculated Door loop size of " + comboBreaker.doorSecretLoopSize);
        logger.info("Part 1: Card encryption key is " + comboBreaker.getCardEncryptionKey() + " (door is " + comboBreaker.getDoorEncryptionKey() + ")");
        timer.finish();
    }
}
