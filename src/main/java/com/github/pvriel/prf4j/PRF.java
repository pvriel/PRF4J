package com.github.pvriel.prf4j;

import java.math.BigInteger;

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
        return compute(key, new BigInteger[]{element})[0];
    }

    /**
     * Method to apply the PRF function on a given array of elements.
     * @param   elements
     *          The elements as a not-null array of not-null {@link BigInteger}s.
     * @return  The result of the computations as a not-null array of not-null {@link BigInteger}s.
     */
    public BigInteger[] compute(BigInteger[] elements) {
        return compute(key, elements);
    }


    /**
     * Method to apply the PRF function with the given key on the given elements.
     * @param   key
     *          A not-null array of not-null {@link BigInteger}s, representing the key.
     * @param   elements
     *          The elements as a not-null {@link BigInteger}.
     * @return  The result of the computations are not-null @{@link BigInteger}s.
     */
    protected abstract BigInteger[] compute(BigInteger[] key, BigInteger[] elements);
}
