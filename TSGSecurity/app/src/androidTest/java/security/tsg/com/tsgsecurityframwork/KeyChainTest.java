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
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;

import javax.crypto.NoSuchPaddingException;

/**
 * Created by Rajvanshi on 27-04-2016.
 */
public class KeyChainTest extends InstrumentationTestCase {

    private final static String KEY = "kEyLI1Fy648tzWXGuRcxrg==";


    @Test
    public void testKeyChainStringConversion() {

        Context context = getInstrumentation().getTargetContext();

        String str = "The quick brown fox jumps over the lazy dog.";
        KeyChain keyChain = (KeyChain) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.KEY_CHAIN);
        String encryptedData = null;

        encryptedData = keyChain.encrypt(context, KEY, str);

        String decryptedData = null;

        try {
            decryptedData = keyChain.decryptToString(context, KEY, encryptedData);
        } catch (NoSuchPaddingException e) {
            fail("KeyChain String encryption failed with NoSuchPaddingException " + e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            fail("KeyChain String encryption failed with NoSuchAlgorithmException " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            fail("KeyChain String encryption failed with InvalidKeyException " + e.getMessage());
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            fail("KeyChain String encryption failed with UnrecoverableEntryException " + e.getMessage());
            e.printStackTrace();
        } catch (KeyStoreException e) {
            fail("KeyChain String encryption failed with KeyStoreException " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            fail("KeyChain String encryption failed with IOException " + e.getMessage());
            e.printStackTrace();
        }

        Assert.assertEquals("KeyChain string encryption and decryption not matched ", decryptedData, str);
    }


    @Test
    public void testKeyChainByteArrayConversion() {

        Context context = getInstrumentation().getTargetContext();
        byte[] data = "The quick brown fox jumps over the lazy dog.".getBytes();
        KeyChain keyChain = (KeyChain) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.KEY_CHAIN);
        String encryptedData = null;

        encryptedData = keyChain.encrypt(context, KEY, data);


        byte[] decryptedArray = null;

        try {
            decryptedArray = keyChain.decryptToByteArray(context, KEY, encryptedData);
        } catch (NoSuchPaddingException e) {
            fail("KeyChain bytes decryption failed with NoSuchPaddingException " + e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            fail("KeyChain bytes decryption failed with NoSuchAlgorithmException " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            fail("KeyChain bytes decryption failed with InvalidKeyException " + e.getMessage());
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            fail("KeyChain bytes decryption failed with UnrecoverableEntryException " + e.getMessage());
            e.printStackTrace();
        } catch (KeyStoreException e) {
            fail("KeyChain bytes decryption failed with KeyStoreException " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            fail("KeyChain bytes decryption failed with IOException " + e.getMessage());
            e.printStackTrace();
        }

        Assert.assertArrayEquals("KeyChain bytes encryption and decryption not matched ", decryptedArray, data);
    }


    /*@Test
    public void testKeychainBitmapConversion() {

        Context context = this.getInstrumentation().getTargetContext();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

//        String str = "The quick brown fox jumps over the lazy dog.";
        KeyChain keyChain = (KeyChain) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.KEY_CHAIN);
        String encryptedData = null;

        encryptedData = keyChain.encrypt(context, KEY, bitmap);

        Bitmap decryptedBitmap = null;


        try {
            decryptedBitmap = keyChain.decryptToBitmap(context, KEY, encryptedData);
        } catch (NoSuchPaddingException e) {
            fail("KeyChain Bitmap decryption failed with NoSuchPaddingException " + e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            fail("KeyChain Bitmap decryption failed with NoSuchAlgorithmException " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            fail("KeyChain Bitmap decryption failed with InvalidKeyException " + e.getMessage());
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            fail("KeyChain Bitmap decryption failed with UnrecoverableEntryException " + e.getMessage());
            e.printStackTrace();
        } catch (KeyStoreException e) {
            fail("KeyChain Bitmap decryption failed with KeyStoreException " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            fail("KeyChain Bitmap decryption failed with IOException " + e.getMessage());
            e.printStackTrace();
        }

//        Assert.assertEquals("KeyChain string encryption and decryption not matched ", decryptedBitmap, str);

    }*/

}
