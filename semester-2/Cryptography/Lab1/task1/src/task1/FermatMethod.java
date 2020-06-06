package task1;

import java.math.BigInteger;
import java.util.Random;

public class FermatMethod {
    private static final Random random = new Random();

    public static boolean isPrime(BigInteger number, int tests) {
        final BigInteger ONE = BigInteger.valueOf(1);
        if (number.compareTo(ONE) < 1) {
            return false;
        }

        if (number.compareTo(BigInteger.valueOf(3)) <= 0) {
            return true;
        }

        if (number.equals(BigInteger.valueOf(4))) {
            return false;
        }

        for (int i = 0; i < tests; i++) {
            BigInteger other = randomBigInteger(number);
            if (!number.gcd(other).equals(ONE)) {
                return false;
            }
            if (!other.modPow(number.subtract(ONE), number).equals(ONE)) {
                return false;
            }
        }

        return true;
    }

    private static BigInteger randomBigInteger(BigInteger number) {
        BigInteger upperBound = number.subtract(BigInteger.valueOf(2));
        BigInteger lowerBound = BigInteger.valueOf(2);
        BigInteger result = new BigInteger(number.bitLength(), random);

        if (result.compareTo(lowerBound) < 0) {
            result = result.add(lowerBound);
        } else if (result.compareTo(upperBound) > 0) {
            result = result.mod(upperBound).add(lowerBound);
        }

        return result;
    }
}
