package task1;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FermatMethodTest {
    private static final Random random = new Random();

    @Test
    public void fermatTestWorksCorrectly() {
        final int tests = 1000;
        final int certainty = 100;
        final int BIT_LENGTH = 1024;

        for (int i = 0; i < tests; i++) {
            BigInteger candidate = new BigInteger(BIT_LENGTH, random);
            boolean expected = candidate.isProbablePrime(certainty);
            boolean found = FermatMethod.isPrime(candidate, certainty);

            assertEquals(expected, found, "Fermat method should return correct result");
        }
    }
}
