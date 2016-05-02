package security.tsg.com.tsgsecurityframwork;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.InstrumentationTestCase;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Rajvanshi on 27-04-2016.
 */
public class MD5Test extends InstrumentationTestCase{


    @Test
    public void testMD5StringConversion() {
        String str = "The quick brown fox jumps over the lazy dog.";
        MD5 md5 = (MD5) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.MD5);
        String encryptedData = null;

        try {
            encryptedData = md5.encrypt( str);
        } catch (NoSuchAlgorithmException e) {
            fail("MD5 String encryption failed with NoSuchAlgorithmException " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testMD5ByteArrayConversion() {
        String str = "The quick brown fox jumps over the lazy dog.";
        MD5 md5 = (MD5) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.MD5);
        String encryptedData = null;

        try {
            encryptedData = md5.encrypt(str.getBytes());
        } catch (NoSuchAlgorithmException e) {
            fail("MD5 byte array encryption failed with NoSuchAlgorithmException " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Test
    public void testMD5BitmapConversion() {
        Context context = getInstrumentation().getTargetContext();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

        MD5 md5 = (MD5) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.MD5);
        String encryptedData = null;

        try {
            encryptedData = md5.encrypt(bitmap);
        } catch (NoSuchAlgorithmException e) {
            fail("MD5 Bitmap encryption failed with NoSuchAlgorithmException " + e.getMessage());
            e.printStackTrace();
        }
    }

}
