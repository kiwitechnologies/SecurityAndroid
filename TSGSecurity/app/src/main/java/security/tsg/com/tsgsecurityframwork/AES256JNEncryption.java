package security.tsg.com.tsgsecurityframwork;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.cryptonode.jncryptor.AES256JNCryptor;
import org.cryptonode.jncryptor.CryptorException;
import org.cryptonode.jncryptor.JNCryptor;

import java.io.ByteArrayOutputStream;

/**
 * Created by kiwitech on 14/04/16.
 */
public class AES256JNEncryption implements DataSecurity {

    AES256JNEncryption() {
    }

    /**
     * encrypt the bitmap data and return the encrypted as String
     * @param key It should be of 256 bit eg: "V3z1gYur18m3lxS1PqAVf3z8sFoJpleW"
     * @param bitmap instance of bitmap object to encrypt
     * @return encrypted data
     * @throws CryptorException
     */
    public String encrypt(String key, Bitmap bitmap) throws CryptorException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] imageData = stream.toByteArray();

        return encrypt(key, imageData);
    }

    /**
     * encrypt the String data and return the encrypted data as String
     * @param key It should be of 256 bit eg: "V3z1gYur18m3lxS1PqAVf3z8sFoJpleW"
     * @param data String data to encrypt
     * @return encrypted data
     * @throws CryptorException
     */
    public String encrypt(String key, String data) throws CryptorException {
        return encrypt(key, data.getBytes());
    }

    /**
     * encrypt the byte[] array data and return the encrypted data as String
     * @param key It should be of 256 bit eg: "V3z1gYur18m3lxS1PqAVf3z8sFoJpleW"
     * @param data byte[] array data to encrypt
     * @return encrypted data
     * @throws CryptorException
     */
    public String encrypt(String key, byte[] data) throws CryptorException {
        JNCryptor cryptor = new AES256JNCryptor();

        byte[] ciphertext = cryptor.encryptData(data, key.toCharArray());
        byte[] encryptedBytes = Base64.encode(ciphertext, 0);
        String encryptedData = new String(encryptedBytes);
        return encryptedData;
    }

    /**
     * decrypt the encrypted bitmap data and return the object of Bitmap
     * @param key It should be of 256 bit eg: "V3z1gYur18m3lxS1PqAVf3z8sFoJpleW"
     * @param data encypted data
     * @return decrypted bitmap
     * @throws CryptorException
     */
    public Bitmap decryptToBitmap(String key, String data) throws CryptorException {
        byte[] decryptedByte = decryptToByteArray(key, data);
        return BitmapFactory.decodeByteArray(decryptedByte, 0, decryptedByte.length);
    }

    /**
     * decrypt the encrypted String data and return the decrypted String
     * @param key It should be of 256 bit eg: "V3z1gYur18m3lxS1PqAVf3z8sFoJpleW"
     * @param data encrypted data
     * @return decrypted string
     * @throws CryptorException
     */
    public String decryptToString(String key, String data) throws CryptorException {
        return new String(decryptToByteArray(key, data));
    }

    /**
     * decrypt the encrypted String data and return the decrypted byte[] array
     * @param key It should be of 256 bit eg: "V3z1gYur18m3lxS1PqAVf3z8sFoJpleW"
     * @param data encrypted data
     * @return decrypted byte[] array
     * @throws CryptorException
     */
    public byte[] decryptToByteArray(String key, String data) throws CryptorException {
        JNCryptor cryptor = new AES256JNCryptor();
        byte[] plaintext = Base64.decode(data.getBytes(), 0);
        return cryptor.decryptData(plaintext, key.toCharArray());
    }
}
