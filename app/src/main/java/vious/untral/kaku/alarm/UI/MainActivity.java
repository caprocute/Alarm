package vious.untral.kaku.alarm.UI;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import vious.untral.kaku.alarm.Model.Alarm;
import vious.untral.kaku.alarm.R;
import vious.untral.kaku.alarm.Tool.Unitls;
import vious.untral.kaku.alarm.fragment.AlarmFragment;
import vious.untral.kaku.alarm.fragment.HistoryFragment;

public class MainActivity extends AppCompatActivity implements HistoryFragment.OnFragmentInteractionListener, AlarmFragment.OnListFragmentInteractionListener {

    private TextView mTextMessage;
    public static int UPDATE_ALARM = 1;
    public static int ADD_ALARM = 2;
    private FloatingActionButton floatAdd;

    private void loadFragment(Fragment fragment) {
        // load fragment
      /*  FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();*/

        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, fragment);
        mFragmentTransaction.commit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.color.witching_hour1);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadFragment(new AlarmFragment());
                    floatAdd.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    floatAdd.setVisibility(View.GONE);
                    loadFragment(new HistoryFragment());
                    return true;
                case R.id.navigation_notifications:
                    floatAdd.setVisibility(View.GONE);
                    loadFragment(new SettingsFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        floatAdd = (FloatingActionButton) findViewById(R.id.floatAdd);

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarm alarm = new Alarm();
                Bundle bundle = new Bundle();
                bundle.putParcelable("alarm", alarm);
                bundle.putBoolean("isNew", true);
                bundle.putInt("postion", -1);
                Intent intent = new Intent(MainActivity.this, AlarmDetailActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, MainActivity.ADD_ALARM);
            }
        });

        loadFragment(new AlarmFragment());

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Alarm item) {

    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.settings);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == UPDATE_ALARM) {
            Boolean isdel = data.getBooleanExtra("isdel", false);
            if (isdel) {
                int mPostion = data.getIntExtra("postion", -1);
                FragmentManager mFragmentManager = getFragmentManager();

                if (mFragmentManager.findFragmentById(R.id.fragment_container) instanceof AlarmFragment) {
                    AlarmFragment alarmFragment = (AlarmFragment) mFragmentManager.findFragmentById(R.id.fragment_container);
                    alarmFragment.deleteAlarm(mPostion);
                }
            } else {
                Alarm mAlarm = data.getParcelableExtra("alarm");
                int mPostion = data.getIntExtra("postion", -1);

                if (mAlarm != null && mPostion != -1) {
                    FragmentManager mFragmentManager = getFragmentManager();

                    if (mFragmentManager.findFragmentById(R.id.fragment_container) instanceof AlarmFragment) {
                        AlarmFragment alarmFragment = (AlarmFragment) mFragmentManager.findFragmentById(R.id.fragment_container);
                        alarmFragment.updateAlarm(mPostion, mAlarm);
                    }
                }
                Toast.makeText(MainActivity.this,
                        Unitls.calTimeRemain(MainActivity.this, mAlarm.getHour(), mAlarm.getMinute(), mAlarm.getRepeat()),
                        Toast.LENGTH_LONG).show();
            }
        } else if (resultCode == ADD_ALARM) {
            Alarm mAlarm = data.getParcelableExtra("alarm");
            if (mAlarm != null) {
                FragmentManager mFragmentManager = getFragmentManager();

                if (mFragmentManager.findFragmentById(R.id.fragment_container) instanceof AlarmFragment) {
                    AlarmFragment alarmFragment = (AlarmFragment) mFragmentManager.findFragmentById(R.id.fragment_container);
                    alarmFragment.addAlarm(mAlarm);
                }
                Toast.makeText(MainActivity.this,
                        Unitls.calTimeRemain(MainActivity.this, mAlarm.getHour(), mAlarm.getMinute(), mAlarm.getRepeat()),
                        Toast.LENGTH_LONG).show();
            }

        }
    }

}
