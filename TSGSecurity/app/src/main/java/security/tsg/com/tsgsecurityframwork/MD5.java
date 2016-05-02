package security.tsg.com.tsgsecurityframwork;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kiwitech on 14/04/16.
 */
public class MD5 implements DataSecurity {

    MD5() {
    }

    /**
     * encrypt the given Bitmap to MD5 hash
     *
     * @param bitmap bitmap instance to encrypt
     * @return converted MD5 hash
     * @throws NoSuchAlgorithmException
     */
    public String encrypt(Bitmap bitmap) throws NoSuchAlgorithmException {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] imageData = stream.toByteArray();

        return encrypt(new String(imageData));
    }

    /**
     * encrypt the given byte[] array to MD5 hash
     *
     * @param data byte[] array to encrypt
     * @return converted MD5 hash
     * @throws NoSuchAlgorithmException
     */
    public String encrypt(byte[] data) throws NoSuchAlgorithmException {
        if (data == null) {
            return null;
        }
        return encrypt(new String(data));
    }

    /**
     * encrypt the given data to MD5 hash
     *
     * @param data data to encrypt
     * @return converted MD5 hash
     * @throws NoSuchAlgorithmException
     */
    public String encrypt(String data) throws NoSuchAlgorithmException {
        if (data == null || data.equals("")) {
            return data;
        }
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data.getBytes());
        return String.format("%032x", new BigInteger(1, md5.digest()));
    }
}
