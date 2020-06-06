package test;

import hashing.MB4;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class MB4Test {
    Random random = new Random();

    @Test
    public void extendBytesTest() {
        for (int length = 0; length < 129; length++) {
            byte[] bytes = new byte[length];
            random.nextBytes(bytes);
            byte[] extendedBytes = MB4.extensionBytes(bytes);
            assertEquals(56, (length + extendedBytes.length) % 64);
        }
    }

    @Test
    public void hashLengthTest() {
        int iterations = 1000;
        int maxLength = 1000;
        for (int i = 0; i < iterations; i++) {
            int length = random.nextInt(maxLength);
            byte[] bytes = new byte[length];
            random.nextBytes(bytes);
            String message = new String(bytes, StandardCharsets.US_ASCII);
            String hash = MB4.hash(message);
            assertEquals(32, hash.length());
        }
    }
}
