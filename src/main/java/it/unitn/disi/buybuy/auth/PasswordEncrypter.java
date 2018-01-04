/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.disi.buybuy.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author maxfrax
 */
public class PasswordEncrypter {

    private byte[] salt;
    private byte[] plainPassword;

    private static SecureRandom random = new SecureRandom();

    public PasswordEncrypter(String plainPassword) {
        salt = getSalt();
        this.plainPassword = plainPassword.getBytes();
    }

    public PasswordEncrypter(byte[] salt, String plainPassword) {
        this.salt = salt;
        this.plainPassword = plainPassword.getBytes();
    }

    public PasswordEncrypter(String salt, String plainPassword) {
        this(salt.getBytes(), plainPassword);
    }

    public String getEncryptedPassword() throws NoSuchAlgorithmException {
        byte[] stringToEncrypt = prependSalt(salt, plainPassword);
        return sha256(stringToEncrypt);
    }

    public String getSaltString() {
        return new String(salt);
    }

    /**
     * Generate random 32-byte length salt to use with password hash.
     *
     * @return an array of byte representing salt
     */
    private byte[] getSalt() {
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Prepend salt to password.
     *
     * @param salt salt to prepend
     * @param password password
     * @return Password with salt prepended
     */
    private byte[] prependSalt(byte[] salt, byte[] password) {
        byte[] result = new byte[salt.length + password.length];
        System.arraycopy(salt, 0, result, 0, salt.length);
        System.arraycopy(password, 0, result, salt.length, password.length);
        return result;
    }

    /**
     * Generate SHA-256 string from an array of bytes.
     *
     * @param bytes input array of the SHA-256 function
     * @return hash string calculated from input array
     * @throws NoSuchAlgorithmException
     */
    private String sha256(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(bytes);
        String hash = DatatypeConverter.printHexBinary(md.digest());
        return hash;
    }
}
