package hashing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MB4 {
    private static int A = 0x67452301;
    private static int B = 0xefcdab89;
    private static int C = 0x98badcfe;
    private static int D = 0x10325476;
    private static final int BLOCK_SIZE = 64; // 512 / 8 = 64
    private static final int EXTENSION_RESIDUAL = 56;
    private static byte[] bytes;
    private static final int[] blocks32 = new int[16];

    public static String hash(String message) {
        bytes = message.getBytes();
        byte[] extensionBytes = extensionBytes(bytes);
        byte[] messageLengthBytes = messageLengthBytes(message.length());
        appendBytes(extensionBytes);
        appendBytes(messageLengthBytes);

        for (int i = 0; i < bytes.length; i += BLOCK_SIZE) {
            // block - 16 32-bit words ~ 64 bytes
            for (int j = 0; j < BLOCK_SIZE; j += 4) {
                byte[] block32 = Arrays.copyOfRange(bytes, i + j, i + j + 4);
                byte[] reversedBlock32 = new byte[block32.length];
                for (int k = 0; k < block32.length / 2; k++) {
                    reversedBlock32[k] = block32[block32.length - k - 1];
                    reversedBlock32[block32.length - k - 1] = block32[k];
                }
                blocks32[j / 4] = ByteBuffer.wrap(reversedBlock32).getInt();
            }

            final int AA = A;
            final int BB = B;
            final int CC = C;
            final int DD = D;

            A = round1(A, B, C, D, 0, 3);
            D = round1(D, A, B, C, 1, 7);
            C = round1(C, D, A, B, 2, 11);
            B = round1(B, C, D, A, 3, 19);
            A = round1(A, B, C, D, 4, 3);
            D = round1(D, A, B, C, 5, 7);
            C = round1(C, D, A, B, 6, 11);
            B = round1(B, C, D, A, 7, 19);
            A = round1(A, B, C, D, 8, 3);
            D = round1(D, A, B, C, 9, 7);
            C = round1(C, D, A, B, 10, 11);
            B = round1(B, C, D, A, 11, 19);
            A = round1(A, B, C, D, 12, 3);
            D = round1(D, A, B, C, 13, 7);
            C = round1(C, D, A, B, 14, 11);
            B = round1(B, C, D, A, 15, 19);

            A = round2(A, B, C, D, 0, 3);
            D = round2(D, A, B, C, 4, 5);
            C = round2(C, D, A, B, 8, 9);
            B = round2(B, C, D, A, 12, 13);
            A = round2(A, B, C, D, 1, 3);
            D = round2(D, A, B, C, 5, 5);
            C = round2(C, D, A, B, 9, 9);
            B = round2(B, C, D, A, 13, 13);
            A = round2(A, B, C, D, 2, 3);
            D = round2(D, A, B, C, 6, 5);
            C = round2(C, D, A, B, 10, 9);
            B = round2(B, C, D, A, 14, 13);
            A = round2(A, B, C, D, 3, 3);
            D = round2(D, A, B, C, 7, 5);
            C = round2(C, D, A, B, 11, 9);
            B = round2(B, C, D, A, 15, 13);

            A = round3(A, B, C, D, 0, 3);
            D = round3(D, A, B, C, 8, 9);
            C = round3(C, D, A, B, 4, 11);
            B = round3(B, C, D, A, 12, 15);
            A = round3(A, B, C, D, 2, 3);
            D = round3(D, A, B, C, 10, 9);
            C = round3(C, D, A, B, 6, 11);
            B = round3(B, C, D, A, 14, 15);
            A = round3(A, B, C, D, 1, 3);
            D = round3(D, A, B, C, 9, 9);
            C = round3(C, D, A, B, 5, 11);
            B = round3(B, C, D, A, 13, 15);
            A = round3(A, B, C, D, 3, 3);
            D = round3(D, A, B, C, 11, 9);
            C = round3(C, D, A, B, 7, 11);
            B = round3(B, C, D, A, 15, 15);

            A += AA;
            B += BB;
            C += CC;
            D += DD;

            resetRegisters();
        }

        return Integer.toHexString(A) + Integer.toHexString(B) + Integer.toHexString(C) + Integer.toHexString(D);
    }

    public static byte[] extensionBytes(byte[] bytes) {
        int size = bytes.length + 1;
        int mod = size % BLOCK_SIZE;
        int zerosCount = EXTENSION_RESIDUAL - mod;
        if (zerosCount < 0) {
            zerosCount += BLOCK_SIZE;
        }

        int extension_size = 1 + zerosCount;
        byte[] extension = new byte[extension_size];
        extension[0] = 0x0001;
        for (int i = 0; i < zerosCount; i++) {
            extension[i + 1] = 0x0;
        }

        return extension;
    }

    private static byte[] messageLengthBytes(long messageLength) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(messageLength);
        byte[] lengthBytes = buffer.array();
        int length = lengthBytes.length;
        byte[] result = new byte[length];
        for (int i = 0; i < length / 2; i++) {
            result[i] = lengthBytes[i + length / 2];
            result[i + length / 2] = lengthBytes[i];
        }
        return result;
    }

    private static void appendBytes(byte[] toAppend) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try {
            byteStream.write(bytes);
            byteStream.write(toAppend);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = byteStream.toByteArray();
    }

    private static int f(int x, int y, int z) {
        return (x & y) | ((~x) & z);
    }

    private static int g(int x, int y, int z) {
        return (x & y) | (x & z) | (y & z);
    }

    private static int h(int x, int y, int z) {
        return x ^ y ^ z;
    }

    private static int round1(int a, int b, int c, int d, int k, int s) {
        int t = a + f(b, c, d) + blocks32[k];
        return t << s | t >>> (32 - s);
    }

    private static int round2(int a, int b, int c, int d, int k, int s) {
        int t = a + g(b, c, d) + blocks32[k] + 0x5A827999;
        return t << s | t >>> (32 - s);
    }

    private static int round3(int a, int b, int c, int d, int k, int s) {
        int t = a + h(b, c, d) + blocks32[k] + 0x6ED9EBA1;
        return t << s | t >>> (32 - s);
    }

    private static void resetRegisters() {
        A = 0x67452301;
        B = 0xefcdab89;
        C = 0x98badcfe;
        D = 0x10325476;
    }

    public static void main(String[] args) {
        String hash = MB4.hash("Hello world!");
        System.out.println(hash);
    }
}
