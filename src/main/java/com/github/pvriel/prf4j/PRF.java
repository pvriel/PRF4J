package com.github.pvriel.prf4j;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Abstract class representing PRF functions.
 */
public abstract class PRF {

    /*
    The key of the PRF function.
     */
    private final BigInteger[] key;

    /**
     * Constructor for the {@link PRF} class.
     * @param   key
     *          The key of the PRF function.
     *          <br>The key parameter should not be null (or contain any null values).
     *          <br>After invoking this constructor, the key is owned by the instance.
     */
    protected PRF(BigInteger[] key) {
        this.key = key;
    }

    /**
     * Method to apply the PRF function on a given element.
     * @param   element
     *          The element as a not-null {@link BigInteger}.
     * @return  The result of the computation as a not-null {@link BigInteger}.
     */
    public BigInteger compute(BigInteger element) {
        return compute(key, element);
    }

    /**
     * Method to apply the PRF function with the given key on the given element.
     * @param   key
     *          A not-null array of not-null {@link BigInteger}s, representing the key.
     * @param   element
     *          The element as a not-null {@link BigInteger}.
     * @return  The result of the computation as a not-null @{@link BigInteger}.
     */
    protected abstract BigInteger compute(BigInteger[] key, BigInteger element);
}
