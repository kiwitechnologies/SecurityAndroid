package security.tsg.com.tsgsecurityframwork;//package com.tsg.security;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;

/**
 * Created by kiwitech on 14/04/16.
 */
public class KeyStoreSecurity implements DataSecurity {

    private static java.security.KeyStore keyStore = null;

    KeyStoreSecurity() {
        try {
            keyStore = java.security.KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createNewKeys(Context context, String key) {
        String alias = key;
        try {
            if (!keyStore.containsAlias(alias)) {
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 1);
                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context.getApplicationContext())
                        .setAlias(alias)
                        .setSubject(new X500Principal("CN=Sample Name, O=Android Authority"))
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();
                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
                generator.initialize(spec);

                KeyPair keyPair = generator.generateKeyPair();

            }
        } catch (Exception e) {
        }
    }

    /**
     * encrypt the bitmap data and return the encrypted as String
     *
     * @param context instance of @Context
     * @param key     It should be of 128 bit eg: "kEyLI1Fy648tzWXGuRcxrg=="
     * @param bitmap  data to encrypt
     * @return encrypted data
     */
    /*public String encrypt(Context context, String key, Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] imageData = stream.toByteArray();
        return encrypt(context, key, imageData);
    }*/

    /**
     * encrypt the String data and return the encrypted data as String
     *
     * @param context instance of @Context
     * @param key     It should be of 128 bit eg: "kEyLI1Fy648tzWXGuRcxrg=="
     * @param data    data to encrypt
     * @return encrypted data
     */
    public String encrypt(Context context, String key, String data) {
        if (data == null || data.isEmpty()) {
            return data;
        }
        return new String(encrypt(context, key, data.getBytes()));
    }

    /**
     * encrypt the byte[] array data and return the encrypted data as String
     *
     * @param context instance of @Context
     * @param key     It should be of 128 bit eg: "kEyLI1Fy648tzWXGuRcxrg=="
     * @param data    data to encrypt
     * @return encrypted data
     */
    public String encrypt(Context context, String key, byte[] data) {
        if (data == null) {
            return null;
        }
        createNewKeys(context, key);
        try {
            java.security.KeyStore.PrivateKeyEntry privateKeyEntry = (java.security.KeyStore.PrivateKeyEntry) keyStore.getEntry(key, null);
            RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();


            Cipher inCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
            inCipher.init(Cipher.ENCRYPT_MODE, publicKey);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, inCipher);
            cipherOutputStream.write(data);
            cipherOutputStream.close();

            byte[] vals = outputStream.toByteArray();
            return new String(Base64.encode(vals, 0));

        } catch (Exception e) {
            if (e.getMessage() != null) {
                throw new IllegalStateException(e.getMessage());
            } else {
                throw new IllegalStateException();
            }
        }
    }

    /**
     * decrypt the encrypted bitmap data and return the object of Bitmap
     *
     * @param context instance of @Context
     * @param key     It should be of 128 bit eg: "kEyLI1Fy648tzWXGuRcxrg=="
     * @param data    data to decrypt
     * @return decrypted @Bitmap
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnrecoverableEntryException
     * @throws KeyStoreException
     * @throws IOException
     */
    /*public Bitmap decryptToBitmap(Context context, String key, String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, KeyStoreException, IOException {
        if (data == null || key == null) {
            return null;
        }
        byte[] decryptedByte = decryptToByteArray(context, key, data);
        return BitmapFactory.decodeByteArray(decryptedByte, 0, decryptedByte.length);
    }*/

    /**
     * decrypt the encrypted String data and return the decrypted String
     *
     * @param context instance of @Context
     * @param key     It should be of 128 bit eg: "kEyLI1Fy648tzWXGuRcxrg=="
     * @param data    data to decrypt
     * @return decrypted data
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnrecoverableEntryException
     * @throws KeyStoreException
     * @throws IOException
     */
    public String decryptToString(Context context, String key, String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, KeyStoreException, IOException {
        if (data == null || key == null) {
            return null;
        }
        return new String(decryptToByteArray(context, key, data));
    }

    /**
     * decrypt the encrypted String data and return the decrypted byte[] array
     *
     * @param context instance of @Context
     * @param key     It should be of 128 bit eg: "kEyLI1Fy648tzWXGuRcxrg=="
     * @param data    data to decrypt
     * @return decrypted data
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnrecoverableEntryException
     * @throws KeyStoreException
     * @throws IOException
     */
    public byte[] decryptToByteArray(Context context, String key, String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, KeyStoreException, IOException {

        if (data == null || key == null) {
            return null;
        }
        createNewKeys(context, key);

        java.security.KeyStore.PrivateKeyEntry privateKeyEntry = (java.security.KeyStore.PrivateKeyEntry) keyStore.getEntry(key, null);
        Cipher output = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        output.init(Cipher.DECRYPT_MODE, privateKeyEntry.getPrivateKey());

        CipherInputStream cipherInputStream = new CipherInputStream(
                new ByteArrayInputStream(Base64.decode(data.getBytes(), 0)), output);
        ArrayList<Byte> values = new ArrayList<>();
        int nextByte;
        while ((nextByte = cipherInputStream.read()) != -1) {
            values.add((byte) nextByte);
        }

        byte[] bytes = new byte[values.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = values.get(i).byteValue();
        }

        return bytes;
    }
}
