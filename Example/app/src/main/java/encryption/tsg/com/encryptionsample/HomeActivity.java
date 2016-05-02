package encryption.tsg.com.encryptionsample;

import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class HomeActivity extends AppCompatActivity {

    //    public final static String AES_KEY = "kEyLI1Fy648tzWXGuRcxrg==";
    public final static String AES_KEY = "bbC2H19lkVbQDfakxcrtNMQdd0FloLyw";
    //    public final static String AES256JN_KEY = "V3z1gYur18m3lxS1PqAVf3z8sFoJpleW";
    public final static String AES256JN_KEY = "bbC2H19lkVbQDfakxcrtNMQdd0FloLyw";
    public final static String KEY_CHAIN_KEY = "kEyLI1Fy648tzWXGuRcxrg==";


    public Spinner mSpnrSecurityType;
    private ViewPager mPager;
    private EncryptionTypeDataAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mSpnrSecurityType = (Spinner) findViewById(R.id.home_activity_security_type_spinner);
        mPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new EncryptionTypeDataAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);

        PagerTabStrip strip = (PagerTabStrip) findViewById(R.id.pts_main);
        strip.setDrawFullUnderline(false);

        mSpnrSecurityType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mAdapter.resetData(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

}
