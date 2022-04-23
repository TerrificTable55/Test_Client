package xyz.terrifictable.util;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.regex.Pattern;

public class EncryptionUtil {

    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final static int ASCII_SHIFT = 33;
    private static int[] BASE85_PW = {
            1,
            85,
            85 * 85,
            85 * 85 * 85,
            85 * 85 * 85 * 85
    };
    private static Pattern REMOVE_WHITESPACE = Pattern.compile("\\s+");

    // TODO
    // <=== BASE85 ===>
    public static String Base85_Encrypt(byte[] in) {
        if (in == null) { throw new IllegalArgumentException("null value"); }

        StringBuffer stringBuffer = new StringBuffer(in.length * 5 / 4);
        byte[] chunk = new byte[4];
        int chunkIndex = 0;

        for (int i=0; i < in.length; i++) {

            byte currByte = in[i];

            chunk[chunkIndex++] = currByte;

            if (chunkIndex == 4) {
                int value = byteToInt(chunk);

                if (value == 0) {
                    stringBuffer.append("z");
                } else {
                    stringBuffer.append(encodeChunk(value));
                }
                Arrays.fill(chunk, (byte) 0);
                chunkIndex = 0;
            }
        }

        if (chunkIndex > 0) {
            int numPadded = chunk.length - chunkIndex;
            Arrays.fill(chunk, chunkIndex, chunk.length, (byte) 0);
            int value = byteToInt(chunk);
            char[] encodedChunk = encodeChunk(value);
            for (int i=0; i < encodedChunk.length - numPadded; i++) {
                stringBuffer.append(encodedChunk[i]);
            }
        }

        return stringBuffer.toString();
    }

    public static byte[] Base85_Decrypt(String in) {
        if (in == null) { throw new IllegalArgumentException("null value"); }

        final int inputLength = in.length();
        long zCount = in.chars().filter(c -> c == 'z').count();

        BigDecimal uncompressedZLength = BigDecimal.valueOf(zCount).multiply(BigDecimal.valueOf(4));
        BigDecimal uncompressedNonZLength = BigDecimal.valueOf(inputLength - zCount)
                .multiply(BigDecimal.valueOf(4))
                .divide(BigDecimal.valueOf(5));
        BigDecimal uncompressedLength = uncompressedZLength.add(uncompressedNonZLength);
        ByteBuffer byteBuff = ByteBuffer.allocate(uncompressedLength.intValue());

        byte[] payload = in.getBytes(StandardCharsets.US_ASCII);
        byte[] chunk = new byte[5];

        int chunkIndex = 0;
        for (int i=0; i < payload.length; i++) {
            byte currByte = payload[i];

            if (currByte == 'z') {
                chunk[chunkIndex++] = '!';
                chunk[chunkIndex++] = '!';
                chunk[chunkIndex++] = '!';
                chunk[chunkIndex++] = '!';
                chunk[chunkIndex++] = '!';
            } else {
                chunk[chunkIndex++] = currByte;
            }

            if (chunkIndex == 5) {
                byteBuff.put(decodeChunk(chunk));
                Arrays.fill(chunk, (byte) 0);
                chunkIndex = 0;
            }
        }

        if (chunkIndex > 0) {
            int numPadded = chunk.length - chunkIndex;
            Arrays.fill(chunk, chunkIndex, chunk.length, (byte) 'u');
            byte[] paddedDecode = decodeChunk(chunk);
            for (int i=0; i < paddedDecode.length - numPadded; i++) {
                byteBuff.put(paddedDecode[i]);
            }
        }

        byteBuff.flip();
        return Arrays.copyOf(byteBuff.array(), byteBuff.limit());
    }
    // <!=== BASE85 ===>

    // <=== ROT ===>
    public static String Rot_Encrypt(String in, int offset) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < in.length(); i++) {
            try {
                int index = alphabet.indexOf(in.charAt(i)) + offset;

                if (index > alphabet.length()) {
                    index = index - alphabet.length();
                }

                if (in.charAt(i) != ' ') {
                    result.append(alphabet.charAt(index));
                } else {
                    result.append(" ");
                }
            } catch (Exception e) {
                result.append(in.charAt(i));
            }
        }

        return result.toString();
    }

    public static String Rot_Decrypt(String in, int offset) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < in.length(); i++) {
            try {
                int index = alphabet.indexOf(in.charAt(i)) - offset;

                if (index > alphabet.length()) {
                    index = index + alphabet.length();
                }

                if (in.charAt(i) != ' ') {
                    result.append(alphabet.charAt(index));
                } else {
                    result.append(" ");
                }
            } catch (Exception e) {
                result.append(in.charAt(i));
            }
        }

        return result.toString();
    }
    // <!=== ROT ===>



    private static int byteToInt(byte[] value) {
        if (value == null || value.length != 4) { throw new IllegalArgumentException("value not 4 bytes long"); }

        return ByteBuffer.wrap(value).getInt();
    }

    private static byte[] intToByte(int value) {
        return new byte[] {
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) (value)
        };
    }

    private static byte[] decodeChunk(byte[] chunk) {
        if (chunk.length != 5) { throw new IllegalArgumentException("chunk size not 5"); }

        int value = 0;
        value += (chunk[0] - ASCII_SHIFT) * BASE85_PW[4];
        value += (chunk[1] - ASCII_SHIFT) * BASE85_PW[3];
        value += (chunk[2] - ASCII_SHIFT) * BASE85_PW[2];
        value += (chunk[3] - ASCII_SHIFT) * BASE85_PW[1];
        value += (chunk[4] - ASCII_SHIFT) * BASE85_PW[0];

        return intToByte(value);
    }

    private static char[] encodeChunk(int value) {
        long longVal = value & 0x00000000ffffffffL;
        char[] encodedChunk = new char[5];
        for (int i=0; i < encodedChunk.length; i++) {
            encodedChunk[i] = (char) ((longVal / BASE85_PW[4 - i]) + ASCII_SHIFT);
            longVal = longVal % BASE85_PW[4 - i];
        }
        return encodedChunk;
    }
}
