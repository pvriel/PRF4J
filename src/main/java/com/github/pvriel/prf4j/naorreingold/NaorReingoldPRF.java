package com.github.pvriel.prf4j.naorreingold;

import com.github.pvriel.prf4j.PRF;

import java.math.BigInteger;

/**
 * Class representing <a href="https://dl.acm.org/doi/10.1145/972639.972643">Naor-Reingold PRF functions</a>.
 * <br>This implementation is more generic compared to the original definition of the NR-PRF functions:
 * <ol>
 *     <li>Three keys are being used here. The initialKey is equal to a_0 from the original definition.
 *     The a0 key array contains the exponents that are used for the active bits of the elements, and therefore
 *     are equal to a_1, a_2, etc. . The a1 key array contains the exponents that are used for the non-active
 *     bits of the elements (in the original definition, these exponents were replaces with ones).</li>
 *     <li>During a computation, the result of the exponent product is replaced with (exponent product) mod q.
 *     This allows for faster computation.</li>
 * </ol>
 * <br>Therefore, the computation of the NR-PRF functions occurs as follows:
 * <ol>
 *     <li>The computation of the exponent product starts with the value initialKey. For every active bit at index i of the provided
 *     element, the current value of the exponent product is multiplied with a0[i]; otherwise, a1[i] is used.
 *     After every multiplication, the product is adjusted to modulo q.</li>
 *     <li>The exponent product is used as an exponent for the generator (== result).</li>
 *     <li>The computation returns result mod p.</li>
 * </ol>
 */
public class NaorReingoldPRF extends PRF {

    /*
    The a1 key array of the NR-PRF function.
    <br>These keys are used as exponents for the non-active bits of the elements.
    <br>The a0 key array for the active bits, which were the only ones used in the original definition of the NR-PRF functions,
    are stored in the superclass.
     */
    private final BigInteger[] a1;
    /*
     * The a_0 key from the original definition of the NR-PRF functions.
     * <br>This key is always used as key for the generator during the computations, no matter the bit representations of the elements.
     */
    private final BigInteger initialKey;
    /**
     * The prime number p.
     * <br>This prime number is used before returning a computation, so that the return value of the compute(...) method
     * is always a value mod p.
     */
    private final BigInteger p;
    /**
     * The prime number q, which is a prime divider of p - 1.
     * <br>This prime number is used to apply a modulo to the exponents for the generator, to improve the performance
     * of the compute(...) method.
     */
    private final BigInteger q;
    /**
     * The generator of the NR-PRF function.
     */
    private final BigInteger g;

    /**
     * Constructor for the {@link NaorReingoldPRF} class.
     * @param       initialKey
     *              The a_0 from the original definition of the NR-PRF functions.
     *              No matter the bits of the elements, this key is always used as an exponent for the generator.
     *              <br>This key should not be null, should be smaller than q and should at least have a value of 1.
     * @param       a0
     *              The a0 key array of the PRF function.
     *              This key array contains the keys that are used as exponents for the generator in case of active bits of the elements.
     *              <br>The a0 parameter should not be null (or contain any null values) and should at least contain one element.
     *              <br>The keys should not be null, should be smaller than q and should at least have a value of 1.
     *              <br>After invoking this constructor, the array is owned by the instance.
     * @param       a1
     *              The a1 key array of the PRF function.
     *              This key array contains the keys that are used as exponents for the generator in case of non-active bits of the elements.
     *              <br>The a1 parameter should not be null (or contain any null values) and should have the same length as the a0 key array.
     *              <br>The keys should not be null, should be smaller than q and should at least have a value of 1.
     *              <br>After invoking this constructor, the array is owned by the instance.
     * @param       p
     *              The prime value from the original definition of the NR-PRF functions.
     *              After applying the exponents to the generator (== result), this prime is used to return
     *              result mod p as return value for the compute(...) method.
     *              <br>The p parameter should not be null and the assigned argument should be a prime number.
     * @param       q
     *              The prime value l from the original definition of the NR-PRF functions.
     *              However, in contrast to the original definition, q is also used here for modulo operations on the
     *              exponents product for the generator. This is done to improve the performance of the compute(...) method.
     *              <br>The q parameter should not be null, should be assigned to a prime number, for which q | p - 1
     *              (== is a prime divider of p - 1).
     * @param       g
     *              The generator for the NR-PRF function.
     *              <br>The g parameter should not be null, should be smaller than p, should at least have a value of 1
     *              and should be of multiplicative order q (== g^q mod p = 1).
     */
    protected NaorReingoldPRF(BigInteger initialKey, BigInteger[] a0, BigInteger[] a1, BigInteger p, BigInteger q, BigInteger g) {
        super(a0);

        this.initialKey = initialKey;
        this.a1 = a1;
        this.p = p;
        this.q = q;
        this.g = g;
    }


    @Override
    protected BigInteger compute(BigInteger[] a0, BigInteger element) {
        BigInteger exponent = initialKey;
        for (int i = 0; i < a0.length; i ++) {
            if (element.testBit(i)) exponent = exponent.multiply(a0[i]);
            else exponent = exponent.multiply(a1[i]);

            exponent = exponent.mod(q);
        }

        return g.modPow(exponent, p);
    }
}
