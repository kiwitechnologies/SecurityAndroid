package security.tsg.com.tsgsecurityframwork;

/**
 * Created by kiwitech on 13/04/16.
 */
public interface DataSecurity {

    /**
     * Keys for possible encryption and decryption type
     */
    enum TYPE {
        AES,
        AES256,
        KEY_STORE,
        MD5
    }


}
