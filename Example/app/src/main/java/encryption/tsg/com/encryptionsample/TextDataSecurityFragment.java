package encryption.tsg.com.encryptionsample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import security.tsg.com.tsgsecurityframwork.AES;
import security.tsg.com.tsgsecurityframwork.AES256JNEncryption;
import security.tsg.com.tsgsecurityframwork.DataSecurity;
import security.tsg.com.tsgsecurityframwork.DataSecurityFactory;
import security.tsg.com.tsgsecurityframwork.KeyStoreSecurity;
import security.tsg.com.tsgsecurityframwork.MD5;


/**
 * Created by kiwitech on 18/04/16.
 */
public class TextDataSecurityFragment extends Fragment implements View.OnClickListener {

    private Context mContext;

    private EditText mEdtInput;
    private TextView mTvOutput, mTvDecryptedData;

    private ScrollView mScrollview;
    private HomeActivity mHomeActivity;
    private Button mBtnEncrypt, mBtnDecrypt, mBtnClearAll;
    private LinearLayout mLayoutOutput, mLayoutDecryptedData;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_textual_security, null);
        mEdtInput = (EditText) view.findViewById(R.id.textual_security_input_edt);
        mTvOutput = (TextView) view.findViewById(R.id.textual_security_encryptedData_tv);
        mBtnEncrypt = (Button) view.findViewById(R.id.textual_security_encrypt_btn);
        mBtnDecrypt = (Button) view.findViewById(R.id.textual_security_decrypt_btn);
        mBtnClearAll = (Button) view.findViewById(R.id.textual_security_clearall_btn);
        mLayoutOutput = (LinearLayout) view.findViewById(R.id.textual_security_output_layout);
        mLayoutDecryptedData = (LinearLayout) view.findViewById(R.id.textual_security_decryptedData_layout);
        mTvDecryptedData = (TextView) view.findViewById(R.id.textual_security_decrypted_tv);
        mScrollview = (ScrollView) view.findViewById(R.id.textual_security_scrollView);

        mBtnEncrypt.setOnClickListener(this);
        mBtnDecrypt.setOnClickListener(this);
        mBtnClearAll.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mHomeActivity = (HomeActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnEncrypt) {
            try {
                encryptData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (view == mBtnDecrypt) {
            try {
                decryptData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (view == mTvOutput) {
            mEdtInput.setText(mTvOutput.getText());
        } else if (view == mBtnClearAll) {
            resetData(true);
        }
    }


    private void encryptData() throws Exception {
        if (mEdtInput.getText().toString().equals("")) {
            Toast.makeText(mContext, "Data is blank", Toast.LENGTH_SHORT).show();
            return;
        }
        String encryptedData = null;
        switch (mHomeActivity.mSpnrSecurityType.getSelectedItemPosition()) {
            case 0:
                AES aes128 = (AES) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.AES);
                encryptedData = aes128.encrypt(HomeActivity.AES_KEY, mEdtInput.getText().toString());
                mTvOutput.setText(encryptedData);
                break;
            case 1:
                AES256JNEncryption aes256JNEncryption = (AES256JNEncryption) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.AES256);
                encryptedData = aes256JNEncryption.encrypt(HomeActivity.AES256JN_KEY, mEdtInput.getText().toString());
                mTvOutput.setText(encryptedData);
                break;
            case 2:
                KeyStoreSecurity keyStore = (KeyStoreSecurity) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.KEY_STORE);
                encryptedData = keyStore.encrypt(mContext, HomeActivity.KEY_STORE_KEY, mEdtInput.getText().toString());
                mTvOutput.setText(encryptedData);
                break;
            case 3:
                MD5 md5 = (MD5) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.MD5);
                encryptedData = md5.encrypt(mEdtInput.getText().toString());
                mTvOutput.setText(encryptedData);
                break;
        }
        mLayoutDecryptedData.setVisibility(View.GONE);
        mTvOutput.requestFocus();
        mScrollview.smoothScrollTo(0, (int) mTvOutput.getY());
        hideKeyboard();

        mLayoutOutput.setVisibility(View.VISIBLE);
        mBtnDecrypt.setVisibility(mHomeActivity.mSpnrSecurityType.getSelectedItemPosition() == 3 ? View.GONE : View.VISIBLE);
    }

    private void decryptData() throws Exception {
        if (mTvOutput.getText().toString().equals("")) {
            Toast.makeText(mContext, "Data is blank", Toast.LENGTH_SHORT).show();
            return;
        }
        String decryptedData = null;
        switch (mHomeActivity.mSpnrSecurityType.getSelectedItemPosition()) {
            case 0:
                AES aes128 = (AES) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.AES);
                decryptedData = aes128.decryptToString(HomeActivity.AES_KEY, mTvOutput.getText().toString());
                break;
            case 1:
                AES256JNEncryption aes256JNEncryption = (AES256JNEncryption) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.AES256);
                decryptedData = aes256JNEncryption.decryptToString(HomeActivity.AES256JN_KEY, mTvOutput.getText().toString());
                break;
            case 2:
                KeyStoreSecurity keyStoreSecurity = (KeyStoreSecurity) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.KEY_STORE);
                decryptedData = keyStoreSecurity.decryptToString(mContext, HomeActivity.KEY_STORE_KEY, mTvOutput.getText().toString());
                break;
            case 3:
                Toast.makeText(mContext, "MD5 hash can not be converted back to string", Toast.LENGTH_LONG).show();
                break;
        }

        if (decryptedData != null) {
            setDecryptedData(decryptedData);
            mScrollview.smoothScrollTo(0, (int) mLayoutDecryptedData.getY());
            hideKeyboard();
        }
    }

    private void setDecryptedData(String decryptedData) {
        if (mHomeActivity.mSpnrSecurityType.getSelectedItemPosition() < 3) {
            mLayoutDecryptedData.setVisibility(View.VISIBLE);
            mTvDecryptedData.setText(decryptedData);
        }
    }


    public void resetData(boolean resetInputValue) {
        if (resetInputValue) {
            mEdtInput.setText("");
        }
        hideOutput();
    }

    private void hideOutput() {
        mLayoutOutput.setVisibility(View.GONE);
        mLayoutDecryptedData.setVisibility(View.GONE);
    }

    private void hideKeyboard() {
        View view = mHomeActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
