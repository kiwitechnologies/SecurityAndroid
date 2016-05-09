package security.tsg.com.tsgsecurityframwork;

import android.content.Context;
import android.test.InstrumentationTestCase;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;

import javax.crypto.NoSuchPaddingException;

/**
 * Created by Rajvanshi on 27-04-2016.
 */
public class KeyStoreTest extends InstrumentationTestCase {

    private final static String KEY = "kEyLI1Fy648tzWXGuRcxrg==";


    @Test
    public void testKeyStoreStringConversion() {

        Context context = getInstrumentation().getTargetContext();

        String str = "The quick brown fox jumps over the lazy dog.";
        KeyStoreSecurity keyStore = (KeyStoreSecurity) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.KEY_STORE);
        String encryptedData = null;

        encryptedData = keyStore.encrypt(context, KEY, str);

        String decryptedData = null;

        try {
            decryptedData = keyStore.decryptToString(context, KEY, encryptedData);
        } catch (NoSuchPaddingException e) {
            fail("KeyStoreSecurity String encryption failed with NoSuchPaddingException " + e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            fail("KeyStoreSecurity String encryption failed with NoSuchAlgorithmException " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            fail("KeyStoreSecurity String encryption failed with InvalidKeyException " + e.getMessage());
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            fail("KeyStoreSecurity String encryption failed with UnrecoverableEntryException " + e.getMessage());
            e.printStackTrace();
        } catch (KeyStoreException e) {
            fail("KeyStoreSecurity String encryption failed with KeyStoreException " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            fail("KeyStoreSecurity String encryption failed with IOException " + e.getMessage());
            e.printStackTrace();
        }

        Assert.assertEquals("KeyStoreSecurity string encryption and decryption not matched ", decryptedData, str);
    }


    @Test
    public void testKeyStoreByteArrayConversion() {

        Context context = getInstrumentation().getTargetContext();
        byte[] data = "The quick brown fox jumps over the lazy dog.".getBytes();
        KeyStoreSecurity keyStore = (KeyStoreSecurity) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.KEY_STORE);
        String encryptedData = null;

        encryptedData = keyStore.encrypt(context, KEY, data);


        byte[] decryptedArray = null;

        try {
            decryptedArray = keyStore.decryptToByteArray(context, KEY, encryptedData);
        } catch (NoSuchPaddingException e) {
            fail("KeyStoreSecurity bytes decryption failed with NoSuchPaddingException " + e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            fail("KeyStoreSecurity bytes decryption failed with NoSuchAlgorithmException " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            fail("KeyStoreSecurity bytes decryption failed with InvalidKeyException " + e.getMessage());
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            fail("KeyStoreSecurity bytes decryption failed with UnrecoverableEntryException " + e.getMessage());
            e.printStackTrace();
        } catch (KeyStoreException e) {
            fail("KeyStoreSecurity bytes decryption failed with KeyStoreException " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            fail("KeyStoreSecurity bytes decryption failed with IOException " + e.getMessage());
            e.printStackTrace();
        }

        Assert.assertArrayEquals("KeyStoreSecurity bytes encryption and decryption not matched ", decryptedArray, data);
    }

}
