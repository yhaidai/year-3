package task3;

import java.math.BigInteger;

public class Math {
    private final static BigInteger ZERO = BigInteger.valueOf(0);
    private final static BigInteger ONE = BigInteger.valueOf(1);
    private final static BigInteger TWO = BigInteger.valueOf(2);

    public static BigInteger modPow(BigInteger base, BigInteger exponent, BigInteger m) {
        if (exponent.equals(ONE)) {
            return base.mod(m);
        }

        BigInteger sqrt = modPow(base, exponent.divide(TWO), m);
        if (exponent.mod(TWO).equals(ZERO)) {
            return sqrt.multiply(sqrt).mod(m);
        } else {
            return sqrt.multiply(sqrt).multiply(base).mod(m);
        }
    }
}
