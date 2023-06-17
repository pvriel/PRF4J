package com.github.pvriel.prf4j;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class PRFTest {

    private final Map<Pair<PRF, BigInteger>, BigInteger> expectedValuesForInstancesAndElements;

    protected PRFTest(Map<Pair<PRF, BigInteger>, BigInteger> expectedValuesForInstancesAndElements) {
        this.expectedValuesForInstancesAndElements = expectedValuesForInstancesAndElements;
    }

    @DisplayName("Compute the PRF values for different instances and elements, and compare the computated values with" +
            " the predefined ones from the test classes.")
    @Test
    void compute() {
        for (Map.Entry<Pair<PRF, BigInteger>, BigInteger> entry : expectedValuesForInstancesAndElements.entrySet()) {
            var key = entry.getKey();
            var instance = key.getLeft();
            var element = key.getRight();
            var result = entry.getValue();

            assertEquals(result, instance.compute(element));
        }
    }
}