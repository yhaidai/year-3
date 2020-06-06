package task4;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KaratsubaMultiplicationTest {
    private final static Random random = new Random();

    @Test
    public void KaratsubaMultiplicationWorksCorrectly() {
        final int TESTS = 10_000;
        final int BIT_LENGTH = 1024;

        for (int i = 0; i < TESTS; i++) {
            BigInteger x = new BigInteger(BIT_LENGTH, random);
            BigInteger y = new BigInteger(BIT_LENGTH, random);
            BigInteger expected = x.multiply(y);
            BigInteger found = KaratsubaMultiplication.multiply(x, y);

            assertEquals(expected, found, "Karatsuba multiplication returns correct result");
        }
    }
}
