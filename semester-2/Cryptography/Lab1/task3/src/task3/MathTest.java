package task3;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathTest {
    private final static Random random = new Random();

    @Test
    public void modPowWorksCorrectly() {
        final int TESTS = 100;
        final int BIT_LENGTH = 1024;

        for (int i = 0; i < TESTS; i++) {
            BigInteger base = new BigInteger(BIT_LENGTH, random);
            BigInteger exponent = new BigInteger(BIT_LENGTH, random);
            BigInteger m = new BigInteger(BIT_LENGTH, random);

            BigInteger expected = base.modPow(exponent, m);
            BigInteger found = Math.modPow(base, exponent, m);
            assertEquals(expected, found, "modPow must return correct result");
        }
    }
}
