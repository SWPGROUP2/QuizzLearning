package utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PasswordUtils {
    private static final int ITERATIONS = 10000; // Increase this as per security requirement
    private static final int KEY_LENGTH = 256;   // Length of the generated hash in bits
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    // Method to hash the password
    public static String hashPassword(String password, String salt) {
        char[] chars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();
        PBEKeySpec spec = new PBEKeySpec(chars, saltBytes, ITERATIONS, KEY_LENGTH);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    // Method to generate a random salt (for production use, secure generation should be used)
    public static String generateSalt() {
        return Base64.getEncoder().encodeToString(java.util.UUID.randomUUID().toString().getBytes());
    }

    // Method to verify password
    public static boolean verifyPassword(String enteredPassword, String storedHash, String salt) {
        String hashedPassword = hashPassword(enteredPassword, salt);
        return hashedPassword.equals(storedHash);
    }
}
