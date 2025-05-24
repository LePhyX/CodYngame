package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for password-related operations, such as hashing.
 * Currently uses SHA-256 to hash user passwords securely.
 */
public class PasswordUtils {

    /**
     * Hashes a password string using the SHA-256 algorithm.
     *
     * @param password The plaintext password to hash
     * @return A hexadecimal string representing the SHA-256 hash
     * @throws RuntimeException If SHA-256 algorithm is not available (should not occur on standard JVMs)
     */
    public static String hashPassword(String password) {
        try {
            // Create a SHA-256 message digest instance
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Convert the password to bytes using UTF-8 encoding and compute the hash
            byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b)); // format each byte as two hexadecimal digits
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // If SHA-256 is not supported by the JVM, wrap and throw a runtime exception
            throw new RuntimeException("Hashing error: " + e.getMessage(), e);
        }
    }
}
