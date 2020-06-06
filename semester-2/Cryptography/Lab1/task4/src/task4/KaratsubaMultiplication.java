package task4;

import java.math.BigInteger;

public class KaratsubaMultiplication {
    private static final int BRUTE_FORCE_THRESHOLD = 256;

    public static BigInteger multiply(BigInteger x, BigInteger y) {
        // cutoff to brute force
        int maxLength = Math.max(x.bitLength(), y.bitLength());
        if (maxLength <= BRUTE_FORCE_THRESHOLD) return x.multiply(y);

        // number of bits divided by 2, rounded up
        maxLength = maxLength / 2 + maxLength % 2;

        // x = a + 2^N b,   y = c + 2^N d
        BigInteger a = upperHalf(x, maxLength);
        BigInteger b = lowerHalf(x, maxLength);
        BigInteger c = upperHalf(y, maxLength);
        BigInteger d = lowerHalf(y, maxLength);

        // compute sub-expressions
        BigInteger ac = multiply(a, c);
        BigInteger bd = multiply(b, d);
        BigInteger abcd = multiply(a.add(b), c.add(d));

        return bd
                .add(abcd.subtract(bd).subtract(ac).shiftLeft(maxLength))
                .add(ac.shiftLeft(2 * maxLength));
    }

    private static BigInteger upperHalf(BigInteger target, final int maxLength) {
        return target.shiftRight(maxLength);
    }

    private static BigInteger lowerHalf(BigInteger target, final int maxLength) {
        return target.subtract(upperHalf(target, maxLength).shiftLeft(maxLength));
    }
}
