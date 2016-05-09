package encryption.tsg.com.encryptionsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.bouncycastle.crypto.InvalidCipherTextException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import security.tsg.com.tsgsecurityframwork.AES;
import security.tsg.com.tsgsecurityframwork.AES256JNEncryption;
import security.tsg.com.tsgsecurityframwork.DataSecurity;
import security.tsg.com.tsgsecurityframwork.DataSecurityFactory;
import security.tsg.com.tsgsecurityframwork.MD5;

/**
 * Created by kiwitech on 18/04/16.
 */
public class ImageDataSecurityFragment extends Fragment implements View.OnClickListener {


    private Context mContext;
    private ImageView mImgOriginal, mImgEncrypted;
    private Button mBtnEncrypt, mBtnDecrypt, mBtnClearAll;
    private LinearLayout mLayoutEncryptedData, mLayoutDecryptedData;
    private HomeActivity mHomeActivity;

    private TextView mTvOutput;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_image_security, null);
        mImgOriginal = (ImageView) view.findViewById(R.id.image_security_original_img_iv);
        mImgEncrypted = (ImageView) view.findViewById(R.id.image_security_decrypted_img_iv);

        mLayoutEncryptedData = (LinearLayout) view.findViewById(R.id.image_security_encryptedData_layout);
        mLayoutDecryptedData = (LinearLayout) view.findViewById(R.id.image_security_decryptedData_layout);

        mTvOutput = (TextView) view.findViewById(R.id.image_security_encryptedData_tv);

        mBtnEncrypt = (Button) view.findViewById(R.id.image_security_encrypt_btn);
        mBtnDecrypt = (Button) view.findViewById(R.id.image_security_decrypt_btn);
        mBtnClearAll = (Button) view.findViewById(R.id.image_security_clearall_btn);

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
                encryptImage();
            } catch (Exception e) {
            }
        } else if (view == mBtnDecrypt) {
            try {
                decryptImage();
            } catch (Exception e) {
            }
        } else if (view == mBtnClearAll) {
            resetData(true);
        }
    }


    private void encryptImage() throws IOException, InvalidCipherTextException, NoSuchAlgorithmException, Exception {

        Bitmap bitmap = ((BitmapDrawable) mImgOriginal.getDrawable()).getBitmap();

        String encryptedData = null;
        switch (mHomeActivity.mSpnrSecurityType.getSelectedItemPosition()) {
            case 0:
                AES AES = (AES) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.AES);
                encryptedData = AES.encrypt(HomeActivity.AES_KEY, bitmap);
                mTvOutput.setText(encryptedData);
                break;
            case 1:
                AES256JNEncryption aes256JNEncryption = (AES256JNEncryption) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.AES256);
                encryptedData = aes256JNEncryption.encrypt(HomeActivity.AES256JN_KEY, bitmap);
                mTvOutput.setText(encryptedData);
                break;
            case 2:
                Toast.makeText(mContext, "Not possible only support for 128 bytes data.", Toast.LENGTH_LONG).show();
                return;
            case 3:
                MD5 md5 = (MD5) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.MD5);
                encryptedData = md5.encrypt(bitmap);
                mTvOutput.setText(encryptedData);
                break;
        }


        mLayoutEncryptedData.setVisibility(View.VISIBLE);
        mLayoutDecryptedData.setVisibility(View.GONE);
        mTvOutput.requestFocus();
        mBtnDecrypt.setVisibility(mHomeActivity.mSpnrSecurityType.getSelectedItemPosition() == 3 ? View.GONE : View.VISIBLE);
    }


    private void decryptImage() throws IOException, InvalidCipherTextException, Exception{
        Bitmap decryptedData = null;
        switch (mHomeActivity.mSpnrSecurityType.getSelectedItemPosition()) {
            case 0:
                AES AES = (AES) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.AES);
                decryptedData = AES.decryptToBitmap(HomeActivity.AES_KEY, mTvOutput.getText().toString());
                break;
            case 1:
                AES256JNEncryption aes256JNEncryption = (AES256JNEncryption) DataSecurityFactory.getAlgoUtility(DataSecurity.TYPE.AES256);
                decryptedData = aes256JNEncryption.decryptToBitmap(HomeActivity.AES256JN_KEY, mTvOutput.getText().toString());
                break;
            case 2:
                Toast.makeText(mContext, "Not possible only support for 128 bytes data.", Toast.LENGTH_LONG).show();
                return;
            case 3:
                Toast.makeText(mContext, "Not Possible", Toast.LENGTH_LONG).show();
                break;
        }

        if (decryptedData != null) {
            if (mHomeActivity.mSpnrSecurityType.getSelectedItemPosition() < 3) {
                mLayoutDecryptedData.setVisibility(View.VISIBLE);
                mImgEncrypted.setImageBitmap(decryptedData);
            }
        }
    }

    public void resetData(boolean resetInputValue) {
        mLayoutEncryptedData.setVisibility(View.GONE);
        mTvOutput.setText("");
    }

}
