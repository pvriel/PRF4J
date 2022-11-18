package com.github.pvriel.prf4j.naorreingold;

import com.github.pvriel.prf4j.PRF;
import com.github.pvriel.prf4j.PRFTest;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@DisplayName("Naor-Reingold PRF")
class NaorReingoldPRFTest extends PRFTest {

    private final static Map<Pair<PRF, BigInteger>, BigInteger> expectedValuesForInstancesAndElements = new HashMap<>();

    static {
        addTestCaseOne();
    }

    private static void addTestCaseOne() {
        // The Wikipedia example: https://en.wikipedia.org/wiki/Naor–Reingold_pseudorandom_function (November 18th, 2022).
        BigInteger initialKey = BigInteger.ONE;
        BigInteger a0[] = new BigInteger[]{BigInteger.ONE, BigInteger.TWO, BigInteger.ONE};
        BigInteger a1[] = new BigInteger[]{BigInteger.ONE, BigInteger.ONE, BigInteger.ONE};
        BigInteger p = BigInteger.valueOf(7);
        BigInteger q = BigInteger.valueOf(3);
        BigInteger g = BigInteger.valueOf(4);

        NaorReingoldPRF naorReingoldPRF = new NaorReingoldPRF(initialKey, a0, a1, p, q, g);
        BigInteger element = BigInteger.valueOf(5);
        BigInteger result = BigInteger.valueOf(4);
        expectedValuesForInstancesAndElements.put(Pair.of(naorReingoldPRF, element), result);
    }

    public NaorReingoldPRFTest() {
        super(expectedValuesForInstancesAndElements);
    }
}