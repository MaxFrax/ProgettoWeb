package it.unitn.disi.buybuy.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordHashing {

    private final String ALGORITHM = "SHA-256";
    private final SecureRandom RANDOM = new SecureRandom();
    
    public String hashPassword(String password, String salt) throws NoSuchAlgorithmException {     
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        md.update(salt.getBytes());
        md.update(password.getBytes());
        byte[] hash = md.digest();        
        return bytesToHex(hash); // Base16 encoding
    }

    public String getSalt() {
        byte bytes[] = new byte[32];
        RANDOM.nextBytes(bytes);
        return bytesToHex(bytes); // Base16 encoding
    }

    private String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    } 

}
