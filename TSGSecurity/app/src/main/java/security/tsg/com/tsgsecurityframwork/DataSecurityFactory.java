package security.tsg.com.tsgsecurityframwork;

public class DataSecurityFactory {

    /**
     * returns the instance of DataSecurity module to encrypt and decrypt data
     *
     * @param type type of encryption that can be DataSecurity.Type.*
     * @return returns instance of DataSecurity class
     */
    public static DataSecurity getAlgoUtility(DataSecurity.TYPE type) {

        if (type == DataSecurity.TYPE.AES) {
            return new AES();
        } else if (type == DataSecurity.TYPE.AES256) {
            return new AES256JNEncryption();
        } else if (type == DataSecurity.TYPE.KEY_STORE) {
            return new KeyStoreSecurity();
        } else if (type == DataSecurity.TYPE.MD5){
            return new MD5();
        }

        throw new IllegalArgumentException("Invalid algo type");

    }
}
