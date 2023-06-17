package com.github.pvriel.prf4j.utils.math;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

class MultiplicativeGroupTest {

    @Test
    void getElementOfOrder() {
        for (int i = 0; i < 10; i ++) {

            BigInteger p = BigInteger.probablePrime(2048, new SecureRandom());
            BigInteger pMinusOne = p.subtract(BigInteger.ONE);
            BigInteger q = BigInteger.TWO;
            while (!pMinusOne.mod(q).equals(BigInteger.ZERO)) q = q.nextProbablePrime();

            MultiplicativeGroup multiplicativeGroup = new MultiplicativeGroup(p);
            BigInteger g = multiplicativeGroup.getElementOfOrder(q);

            BigInteger modAndPowResult = g.modPow(q, p);
            assertEquals(BigInteger.ONE, modAndPowResult);
        }
    }

    @Test
    void getRandomElements() {
        BigInteger p = BigInteger.probablePrime(2048, new SecureRandom());
        MultiplicativeGroup multiplicativeGroup = new MultiplicativeGroup(p);

        BigInteger[] randomElements = multiplicativeGroup.getRandomElements(1000);
        for (int i = 0; i < randomElements.length; i++) {
            for (int j = i + 1; j < randomElements.length; j++) {
                assertNotEquals(randomElements[i], randomElements[j]);
            }
        }
    }
}