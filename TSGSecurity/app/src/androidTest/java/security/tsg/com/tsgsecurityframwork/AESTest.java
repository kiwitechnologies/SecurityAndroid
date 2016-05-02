package security.tsg.com.tsgsecurityframwork;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.InstrumentationTestCase;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kiwitech on 25/04/16.
 */
public class AESTest extends InstrumentationTestCase {
    private final static String KEY = "bbC2H19lkVbQDfakxcrtNMQdd0FloLyw";

    @Test
    public void testAESStringConversion() {
        String str = "The quick brown fox jumps over the lazy dog.";
        AES aes = (AES) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.AES);
        String encryptedData = null;

        try {
            encryptedData = aes.encrypt(KEY, str);
        } catch (IOException e) {
            fail("AES String encryption failed with IOException " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidCipherTextException e) {
            fail("AES String encryption failed with InvalidCipherTextExcepton " + e.getMessage());
            e.printStackTrace();
        }

        String decryptedData = null;

        try {
            decryptedData = aes.decryptToString(KEY, encryptedData);
        } catch (IOException e) {
            fail("AES String decryption failed with IOException " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidCipherTextException e) {
            fail("AES String decryption failed with InvalidCipherTextExcepton " + e.getMessage());
            e.printStackTrace();
        }

        Assert.assertEquals("AES string encryption and decryption not matched ", decryptedData, str);

    }


    @Test
    public void testAESByteArrayConversion() {
        byte[] data = "The quick brown fox jumps over the lazy dog.".getBytes();
        AES aes = (AES) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.AES);
        String encryptedData = null;

        try {
            encryptedData = aes.encrypt(KEY, data);
        } catch (IOException e) {
            fail("AES bytes encryption failed with IOException " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidCipherTextException e) {
            fail("AES bytes encryption failed with InvalidCipherTextExcepton " + e.getMessage());
            e.printStackTrace();
        }

        byte[] decryptedArray = null;

        try {
            decryptedArray = aes.decrypt(KEY, encryptedData);
        } catch (IOException e) {
            fail("AES bytes decryption failed with IOException " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidCipherTextException e) {
            fail("AES bytes decryption failed with InvalidCipherTextExcepton " + e.getMessage());
            e.printStackTrace();
        }

        Assert.assertArrayEquals("AES bytes encryption and decryption not matched ", decryptedArray, data);
    }


    @Test
    public void testAESBitmapConversion() {

        Context context = this.getInstrumentation().getTargetContext();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

//        String str = "The quick brown fox jumps over the lazy dog.";
        AES aes = (AES) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.AES);
        String encryptedData = null;

        try {
            encryptedData = aes.encrypt(KEY, bitmap);
        } catch (IOException e) {
            fail("AES Bitmap encryption failed with IOException " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidCipherTextException e) {
            fail("AES Bitmap encryption failed with InvalidCipherTextExcepton " + e.getMessage());
            e.printStackTrace();
        }

        Bitmap decryptedBitmap = null;

        try {
            decryptedBitmap = aes.decryptToBitmap(KEY, encryptedData);
        } catch (IOException e) {
            fail("AES Bitmap decryption failed with IOException " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidCipherTextException e) {
            fail("AES Bitmap decryption failed with InvalidCipherTextExcepton " + e.getMessage());
            e.printStackTrace();
        }

//        Assert.assertEquals("AES string encryption and decryption not matched ", decryptedBitmap, str);

    }


    @Test
    public void testAESStreamConversion() {
        String str = "The quick brown fox jumps over the lazy dog.";
        AES aes = (AES) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.AES);

        InputStream inData = new ByteArrayInputStream(str.getBytes());
        ByteArrayOutputStream outData = new ByteArrayOutputStream();

        String encryptedData = null;

        try {
            aes.encrypt(KEY, inData, outData);
            byte[] byt = outData.toByteArray();
            encryptedData = new String(org.bouncycastle.util.encoders.Base64.encode(byt));
        } catch (IOException e) {
            fail("AES stream encryption failed with IOException " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidCipherTextException e) {
            fail("AES stream encryption failed with InvalidCipherTextExcepton " + e.getMessage());
            e.printStackTrace();
        }

        String decryptedData = null;
        byte[] inputData = Base64.decode(encryptedData.getBytes());
        InputStream inDecryptData = new ByteArrayInputStream(inputData);
        ByteArrayOutputStream outDecryptData = new ByteArrayOutputStream();

        try {
            aes.decrypt(KEY, inDecryptData, outDecryptData);
            decryptedData = new String(outDecryptData.toByteArray());
        } catch (IOException e) {
            fail("AES stream decryption failed with IOException " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidCipherTextException e) {
            fail("AES stream decryption failed with InvalidCipherTextExcepton " + e.getMessage());
            e.printStackTrace();
        }
        Assert.assertEquals("AES stream encryption and decryption not matched ", decryptedData, str);
    }

}
