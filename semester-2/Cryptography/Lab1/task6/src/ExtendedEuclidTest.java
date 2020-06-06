import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtendedEuclidTest {
    private static final Random random = new Random();

    @Test
    public void gcdWorksCorrectly() {
        final int TESTS = 10_000;
        final int BIT_LENGTH = 1024;

        for (int i = 0; i < TESTS; i++) {
            BigInteger a = new BigInteger(BIT_LENGTH, random);
            BigInteger b = new BigInteger(BIT_LENGTH, random);
            BigInteger expected = a.gcd(b);
            BigInteger[] values = ExtendedEuclid.gcd(a, b);
            BigInteger found = values[0];
            BigInteger x = values[1];
            BigInteger y = values[2];

            assertEquals(expected, found, "gcd must return correct result");
            assertEquals(found, x.multiply(a).add(y.multiply(b)), "x*a + y*b = gcd(a, b)");
        }
    }
}
