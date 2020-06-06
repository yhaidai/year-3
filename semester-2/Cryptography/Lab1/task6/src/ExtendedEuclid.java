import java.math.BigInteger;

public class ExtendedEuclid {

    public static BigInteger[] gcd(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return new BigInteger[]{a, BigInteger.ONE, BigInteger.ZERO};
        }

        BigInteger[] values = gcd(b, a.mod(b));
        BigInteger d = values[0];
        BigInteger x = values[2];
        BigInteger y = values[1].subtract(a.divide(b).multiply(x));

        return new BigInteger[]{d, x, y};
    }
}
