package task2;

import java.math.BigInteger;
import java.util.Random;

public class MillerRabinTest {
    private static final Random RANDOM = new Random();
    final static BigInteger ZERO = BigInteger.valueOf(0);
    final static BigInteger ONE = BigInteger.valueOf(1);
    final static BigInteger TWO = BigInteger.valueOf(2);
    final static BigInteger THREE = BigInteger.valueOf(3);
    final static BigInteger FOUR = BigInteger.valueOf(4);

    public static boolean isPrime(BigInteger candidate, int tests) {
        if (candidate.equals(TWO) || candidate.equals(THREE)) {
            return true;
        }
        if (candidate.equals(FOUR)) {
            return false;
        }

        int exp = 0;
        BigInteger d = candidate.subtract(ONE);
        while (d.mod(TWO).equals(ZERO)) {
            exp++;
            d = d.divide(TWO);
        }

        for (int i = 0; i < tests; i++) {
            BigInteger witness = randomBigInteger(candidate);

            BigInteger x = witness.modPow(d, candidate);
            if (x.equals(ONE) || x.equals(candidate.subtract(ONE))) {
                continue;
            }

            boolean isComposite = true;
            for (int j = 1; j < exp; j++) {
                x = x.modPow(TWO, candidate);
                if (x.equals(candidate.subtract(ONE))) {
                    isComposite = false;
                }
            }

            if (isComposite) {
                return false;
            }
        }

        return true;
    }

    private static BigInteger randomBigInteger(BigInteger candidate) {

        BigInteger lowerBound = TWO;
        BigInteger upperBound = candidate.subtract(TWO);
        BigInteger difference = upperBound.subtract(lowerBound);
        BigInteger result = new BigInteger(candidate.bitLength(), RANDOM);

        if (result.compareTo(upperBound) >= 1) {
            result = result.mod(upperBound);
        }

        if (result.compareTo(lowerBound) < 0) {
            result = result.mod(difference).add(lowerBound);
        }

        return result;
    }
}
