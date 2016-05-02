package security.tsg.com.tsgsecurityframwork;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.InstrumentationTestCase;

import org.cryptonode.jncryptor.CryptorException;
import org.junit.Assert;

/**
 * Created by Rajvanshi on 27-04-2016.
 */
public class AES256JNTest extends InstrumentationTestCase {

    private final static String KEY = "bbC2H19lkVbQDfakxcrtNMQdd0FloLyw";

    public void testAES256StringConversion() {
        String str = "The quick brown fox jumps over the lazy dog.";
        AES256JNEncryption aes256JNEncryption = (AES256JNEncryption) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.AES256);

        String encryptedData = null;

        try {
            encryptedData = aes256JNEncryption.encrypt(KEY, str);
        } catch (CryptorException e) {
            e.printStackTrace();
            fail("AES256JN String encryption failed with CryptorException " + e.getMessage());
        }

        String decryptedData = null;
        try {
            decryptedData = aes256JNEncryption.decryptToString(KEY, encryptedData);
        } catch (CryptorException e) {
            e.printStackTrace();
        }

        Assert.assertEquals("AES256JN string encryption and decryption not matched ", str, decryptedData);
    }

    public void testAES256ByteArrayConversion() {
        String str = "The quick brown fox jumps over the lazy dog.";
        AES256JNEncryption aes256JNEncryption = (AES256JNEncryption) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.AES256);

        String encryptedData = null;

        try {
            encryptedData = aes256JNEncryption.encrypt(KEY, str.getBytes());
        } catch (CryptorException e) {
            e.printStackTrace();
            fail("AES256JN byte array encryption failed with CryptorException " + e.getMessage());
        }

        String decryptedData = null;
        try {
            byte[] byt = aes256JNEncryption.decryptToByteArray(KEY, encryptedData);
            decryptedData = new String(byt);
        } catch (CryptorException e) {
            e.printStackTrace();
        }

        Assert.assertEquals("AES256JN byte Array encryption and decryption not matched ", str, decryptedData);
    }

    public void testAES256BitmapConversion() {

        Context context = this.getInstrumentation().getTargetContext();
        Bitmap bitmapOriginal = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

        AES256JNEncryption aes256JNEncryption = (AES256JNEncryption) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.AES256);

        String encryptedData = null;

        try {
            encryptedData = aes256JNEncryption.encrypt(KEY, bitmapOriginal);
        } catch (CryptorException e) {
            e.printStackTrace();
        }

        Bitmap bmpOutput = null;

        try {
            bmpOutput = aes256JNEncryption.decryptToBitmap(KEY, encryptedData);
        } catch (CryptorException e) {
            e.printStackTrace();
        }

    }

}
