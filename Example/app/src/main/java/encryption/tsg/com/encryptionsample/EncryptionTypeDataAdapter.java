package encryption.tsg.com.encryptionsample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by kiwitech on 18/04/16.
 */
public class EncryptionTypeDataAdapter extends FragmentPagerAdapter {

    private TextDataSecurityFragment textDataSecurityFragment;
    private ImageDataSecurityFragment imageDataSecurityFragment;

    public EncryptionTypeDataAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if (textDataSecurityFragment == null) {
                textDataSecurityFragment = new TextDataSecurityFragment();
            }
            return textDataSecurityFragment;
        } else if (position == 1) {
            if (imageDataSecurityFragment == null) {
                imageDataSecurityFragment = new ImageDataSecurityFragment();
            }
            return imageDataSecurityFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Textual Conversion";
        } else {
            return "Image Conversion";
        }
    }

    public void resetData(boolean resetInputValue) {
        if (textDataSecurityFragment != null)
            textDataSecurityFragment.resetData(resetInputValue);
        if (imageDataSecurityFragment != null)
            imageDataSecurityFragment.resetData(resetInputValue);
    }
}
