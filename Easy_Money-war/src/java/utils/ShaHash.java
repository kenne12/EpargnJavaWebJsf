package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ShaHash {

    private String plaintext;
    private String hash;

    public synchronized String hash(String plaintext) {
        /* 21 */ this.plaintext = plaintext;
        /* 22 */ MessageDigest md = null;
        try {
            /* 24 */ md = MessageDigest.getInstance("SHA");
            /* 25 */        } catch (NoSuchAlgorithmException e) {
            /* 26 */ e.printStackTrace();
        }
        try {
            /* 29 */ md.update(this.plaintext.getBytes("UTF-8"));
            /* 30 */        } catch (UnsupportedEncodingException e) {
            /* 31 */ e.printStackTrace();
        }
        /* 33 */ byte[] raw = md.digest();

        /* 36 */ this.hash = Base64.getMimeEncoder().encodeToString(raw);
        /* 37 */ return this.hash;
    }

    public String getPlaintext() {
        /* 41 */ return this.plaintext;
    }

    public void setPlaintext(String plaintext) {
        /* 45 */ this.plaintext = plaintext;
    }

    public String getHash() {
        /* 49 */ return this.hash;
    }

    public void setHash(String hash) {
        /* 53 */ this.hash = hash;
    }
}
