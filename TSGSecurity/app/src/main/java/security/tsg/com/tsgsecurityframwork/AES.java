package security.tsg.com.tsgsecurityframwork;

//import com.sun.org.apache.xml.internal.security.utils.Base64;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.util.encoders.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;

/**
 * Created by kiwitech on 13/04/16.
 */
public class AES implements DataSecurity {

    public static Cipher cipher;
    private byte[] initializationVector = "gqLOHUioQ0QjhuvI".getBytes();
    private BouncyCastleAPI_AES_CBC bcAES;

    AES() {
    }


    public void setInitializationVector(byte[] initializationVector) {
        this.initializationVector = initializationVector;
    }


    /**
     * encrypt the bitmap data and return the encrypted as String
     *
     * @param key Key to encrypt with eg: "bbC2H19lkVbQDfakxcrtNMQdd0FloLyw"
     * @param bitmap instance of bitmap object
     * @return encrypted data
     * @throws IOException
     * @throws InvalidCipherTextException
     */
    public String encrypt(String key, Bitmap bitmap) throws IOException, InvalidCipherTextException {
        if (!bitmap.equals("")) {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            byte[] imageData = stream.toByteArray();

            return encrypt(key, imageData);
        }
        return null;
    }


    /**
     * encrypt the String data and return the encrypted data as String
     *
     * @param key Key to encrypt with eg: "bbC2H19lkVbQDfakxcrtNMQdd0FloLyw"
     * @param data String data to encrypt
     * @return encrypted data
     * @throws IOException
     * @throws InvalidCipherTextException
     */
    public String encrypt(String key, String data) throws IOException, InvalidCipherTextException {
        if (!data.equals("")) {
            return encrypt(key, data.getBytes());
        }
        return null;
    }

    /**
     * encrypt the byte[] array data and return the encrypted data as String
     *
     * @param key Key to encrypt with eg: "bbC2H19lkVbQDfakxcrtNMQdd0FloLyw"
     * @param data byte[] array data to encrypt
     * @return encrypted string data
     * @throws IOException
     * @throws InvalidCipherTextException
     */
    public String encrypt(String key, byte[] data) throws IOException, InvalidCipherTextException {
        if (!data.equals("")) {

            InputStream inData = new ByteArrayInputStream(data);
            ByteArrayOutputStream outData = new ByteArrayOutputStream();
            encrypt(key, inData, outData);

            byte[] byt = outData.toByteArray();

            return new String(org.bouncycastle.util.encoders.Base64.encode(byt));
        }
        return null;
    }


    /**
     * encrypt the input stream data and copy it to output stream in bytes
     *
     * @param key Key to encrypt with eg: "bbC2H19lkVbQDfakxcrtNMQdd0FloLyw"
     * @param inputDataStream
     * @param outDataStream
     * @throws IOException
     * @throws InvalidCipherTextException
     */
    public void encrypt(String key, InputStream inputDataStream, OutputStream outDataStream) throws IOException, InvalidCipherTextException {
        bcAES = new BouncyCastleAPI_AES_CBC(key.getBytes(), initializationVector);
        bcAES.InitCiphers();
        bcAES.CBCEncrypt(inputDataStream, outDataStream);
    }

    /**
     * decrypt the encrypted bitmap data and return the object of Bitmap
     *
     * @param key Key to encrypt with eg: "bbC2H19lkVbQDfakxcrtNMQdd0FloLyw"
     * @param encryptedBitmapData it is Encrypted bitmap data
     * @return Object of Bitmap
     * @throws IOException
     * @throws InvalidCipherTextException
     */
    public Bitmap decryptToBitmap(String key, String encryptedBitmapData) throws IOException, InvalidCipherTextException {
        if (encryptedBitmapData != null) {
            byte[] decryptedByte = decrypt(key, encryptedBitmapData);
            return BitmapFactory.decodeByteArray(decryptedByte, 0, decryptedByte.length);
        }
        return null;
    }

    /**
     * decrypt the encrypted String data and return the decrypted String
     *
     * @param key Key to encrypt with eg: "bbC2H19lkVbQDfakxcrtNMQdd0FloLyw"
     * @param encryptedText it is Encrypted data
     * @return decrypted String
     * @throws IOException
     * @throws InvalidCipherTextException
     */
    public String decryptToString(String key, String encryptedText) throws IOException, InvalidCipherTextException {
        if (encryptedText != null) {
            byte[] inputData = Base64.decode(encryptedText.getBytes());
            InputStream fis = new ByteArrayInputStream(inputData);

            ByteArrayOutputStream fos = new ByteArrayOutputStream();
            decrypt(key, fis, fos);
            byte[] byt = fos.toByteArray();
            return new String(byt);
        }
        return null;
    }

    /**
     * decrypt the encrypted String data and return the decrypted byte[] array
     *
     * @param key Key to encrypt with eg: "bbC2H19lkVbQDfakxcrtNMQdd0FloLyw"
     * @param encryptedText it is Encrypted data
     * @return decrypted base64 byte[] array
     * @throws IOException
     * @throws InvalidCipherTextException
     */
    public byte[] decrypt(String key, String encryptedText) throws IOException, InvalidCipherTextException {

        if (encryptedText != null) {
            byte[] inputData = Base64.decode(encryptedText.getBytes());
            InputStream fis = new ByteArrayInputStream(inputData);

            ByteArrayOutputStream fos = new ByteArrayOutputStream();
            decrypt(key, fis, fos);
            byte[] byt = fos.toByteArray();
            return byt;
        }
        return null;
    }


    /**
     * decrypt the encrypted String data and return the decrypted byte[] array
     *
     * @param key Key to encrypt with eg: "bbC2H19lkVbQDfakxcrtNMQdd0FloLyw"
     * @param inputDataStream it is Encrypted data in InputStream
     * @param outDataStream   it is Encrypted data in OutputStream
     * @return decrypted base64 byte[] array
     * @throws IOException
     * @throws InvalidCipherTextException
     */
    public void decrypt(String key, InputStream inputDataStream, OutputStream outDataStream) throws IOException, InvalidCipherTextException {
        bcAES = new BouncyCastleAPI_AES_CBC(key.getBytes(), initializationVector);
        bcAES.InitCiphers();
        bcAES.CBCDecrypt(inputDataStream, outDataStream);
    }
}
