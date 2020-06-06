package task2;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MillerRabinTestTests {
    private static final Random random = new Random();

    @Test
    public void millerRabinTestWorksCorrectly() {
        final int TESTS = 100000;
        final int CERTAINTY = 100;
        BigInteger upperBound = new BigInteger("99999999999999");


        for (int i = 0; i < TESTS; i++) {
            BigInteger candidate = new BigInteger(upperBound.bitLength(), random);
            boolean expected = candidate.isProbablePrime(CERTAINTY);
            boolean found = MillerRabinTest.isPrime(candidate, CERTAINTY);
            assertEquals(expected, found, "Test result should be the same");
        }
    }
}
