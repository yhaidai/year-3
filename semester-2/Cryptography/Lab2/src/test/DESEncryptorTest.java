package test;

import des.DESEncryptor;
import des.DESKeyGenerator;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class DESEncryptorTest {
    Random random = new Random();

    @Test
    public void encryptDecryptTest() {
        final int iterations = 200;
        for (int i = 0; i < iterations; i++) {
            DESEncryptor des = new DESEncryptor();
            DESKeyGenerator keyGenerator = new DESKeyGenerator();
            String keyHexString = keyGenerator.getRandomKey();
            byte[] key = des.hexStringToByteArray(keyHexString);

            final int minMessageLength = 64;
            final int maxMessageLength = 1000;
            final int messageLength = random.nextInt(maxMessageLength - minMessageLength) + minMessageLength;
            byte[] message = getRandomMessage(messageLength);

            byte[] cipher = des.encrypt(message, key);
            byte[] plainText = des.decrypt(cipher, key);
            plainText = shrinkTo(plainText, messageLength);

            assertArrayEquals(message, plainText);
        }
    }

    @Test
    public void decryptEncryptTest() {
        final int iterations = 200;
        for (int i = 0; i < iterations; i++) {
            DESEncryptor des = new DESEncryptor();
            DESKeyGenerator keyGenerator = new DESKeyGenerator();
            String keyHexString = keyGenerator.getRandomKey();
            byte[] key = des.hexStringToByteArray(keyHexString);

            final int minMessageLength = 64;
            final int maxMessageLength = 1000;
            final int messageLength = random.nextInt(maxMessageLength - minMessageLength) + minMessageLength;
            byte[] message = getRandomMessage(messageLength);

            byte[] plainText = des.decrypt(message, key);
            byte[] cipher = des.encrypt(plainText, key);
            cipher = shrinkTo(cipher, messageLength);

            assertArrayEquals(message, cipher);
        }
    }

    private byte[] getRandomMessage(final int length) {
        byte[] message = new byte[length];
        random.nextBytes(message);
        return message;
    }

    private byte[] shrinkTo(byte[] plainText, final int requiredLength) {
        return Arrays.copyOf(plainText, requiredLength);
    }
}
