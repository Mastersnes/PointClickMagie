package com.bebel.game.utils;

import com.badlogic.gdx.Gdx;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64DecoderStream;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64EncoderStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Outils pour la securité
 */
public class SecurityUtils {
    private static String password = "SeCie1E1efenRaze";
    private static Cipher ecipher;
    private static Cipher dcipher;
    static {
        try {
            final SecretKey key = new SecretKeySpec(password.getBytes(), "AES");
            ecipher = Cipher.getInstance("AES");
            ecipher.init(Cipher.ENCRYPT_MODE, key);

            dcipher = Cipher.getInstance("AES");
            dcipher.init(Cipher.DECRYPT_MODE, key);
        } catch (final Exception e) {
            Gdx.app.error("SecurityUtils", "Erreur lors de l'initialisation du cipher", e);
        }
    }

    public synchronized static String encrypt(final String text) {
        if (text == null || text.isEmpty()) return "";

        String encrypt = "";
        try {
            final byte[] enc = ecipher.doFinal(text.getBytes(UTF_8));
            encrypt = new String(BASE64EncoderStream.encode(enc));
        } catch (Exception e) {
            Gdx.app.error("SecurityUtils", "Erreur lors du cryptage", e);
        }
//        Gdx.app.debug("SecurityUtils", "encrypt : " + text + " to : " + encrypt);
        return encrypt;
    }

    public synchronized static String decrypt(final String text, final String defVal) {
        if (text == null || text.isEmpty()) return defVal;

        String decrypt = defVal;
        try {
            final byte[] dec = BASE64DecoderStream.decode(text.getBytes());
            decrypt = new String(dcipher.doFinal(dec), UTF_8);
        } catch (Exception e) {
            Gdx.app.error("SecurityUtils", "Erreur lors du decryptage", e);
        }
//        Gdx.app.debug("SecurityUtils", "decrypt : " + text + " to : " + decrypt);
        return decrypt;
    }
}
