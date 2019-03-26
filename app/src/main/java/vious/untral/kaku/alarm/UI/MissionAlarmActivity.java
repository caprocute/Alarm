package vious.untral.kaku.alarm.UI;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;
import vious.untral.kaku.alarm.Adapter.ImageCardAdapter;
import vious.untral.kaku.alarm.Model.Alarm;
import vious.untral.kaku.alarm.Model.Mission;
import vious.untral.kaku.alarm.R;
import vious.untral.kaku.alarm.Tool.FlingRecycleView;
import vious.untral.kaku.alarm.Tool.LinePagerIndicatorDecoration;
import vious.untral.kaku.alarm.Tool.MyDatabaseHelperMission;
import vious.untral.kaku.alarm.Tool.Unitls;
import vious.untral.kaku.alarm.fragment.MissionFragment;
import vious.untral.kaku.alarm.fragment.MissionFragmentMath;
import vious.untral.kaku.alarm.fragment.MissionFragmentPicutre;
import vious.untral.kaku.alarm.fragment.MissionFragmentQR;
import vious.untral.kaku.alarm.fragment.MissionFragmentShake;

import static vious.untral.kaku.alarm.UI.AlarmDetailActivity.MISSION_ALARM_CODE;

public class MissionAlarmActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Integer> mResId;
    private ImageView mPagerBg;

    private List<MissionFragment> missionFragmentList = new ArrayList<>(5);
    private FlingRecycleView mPagerRecycleView;
    private Button btnCancel, btnOk;
    private String TAG = "HIEUHK";
    public static final int REQUEST_SETTING_QR = 5951;
    public static final int REQUEST_SETTING_QR_FINISH = 5952;

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

    private Alarm mAlarm;
    private Mission rawMission, edtMission;
    private ImageCardAdapter imageAdapter;
    private boolean firstRUn = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_alarm);
        setStatusBarGradiant(this);

        Bundle bundle = getIntent().getExtras();

        mAlarm = bundle.getParcelable("alarm");

        if (mAlarm.getMissionAlarmId() != null && !mAlarm.getMissionAlarmId().isEmpty()) {
            MyDatabaseHelperMission myDatabaseHelperMission = new MyDatabaseHelperMission(this);
            rawMission = myDatabaseHelperMission.getMISSION(mAlarm.getMissionAlarmId());
        }

        edtMission = rawMission;

        mPagerRecycleView = (FlingRecycleView) findViewById(R.id.rc_1);

        MissionFragment missionFragment = new MissionFragmentQR();

        missionFragmentList.add(new MissionFragment());
        /*missionFragmentList.add(new MissionFragmentPicutre());*/
        missionFragmentList.add(new MissionFragmentShake());
        missionFragmentList.add(new MissionFragmentMath());
        missionFragmentList.add(new MissionFragmentQR());

        mPagerRecycleView.setFlingAble(false);
        mPagerRecycleView.addItemDecoration(new LinePagerIndicatorDecoration());

        GalleryLayoutManager layoutManager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        layoutManager.attach(mPagerRecycleView, 30);


        layoutManager.setItemTransformer(new ScaleTransformer());
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        imageAdapter = new ImageCardAdapter(missionFragmentList, (int) (displayMetrics.widthPixels * 0.55f), (int) (displayMetrics.heightPixels * 0.55f), this, mAlarm);

        imageAdapter.setOnItemClickListener(new ImageCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (missionFragmentList.get(position).getMissionID()) {

                    case Unitls.MISSION_SHAKE:
                        startActivityForResult(new Intent(MissionAlarmActivity.this, SettingMathScreen.class)
                                .putExtra("type", Unitls.MISSION_SHAKE).putExtra("mission", edtMission), REQUEST_SETTING_QR);
                        break;
                    case Unitls.MISSION_CAL:
                        startActivityForResult(new Intent(MissionAlarmActivity.this, SettingMathScreen.class)
                                .putExtra("type", Unitls.MISSION_CAL).putExtra("mission", edtMission), REQUEST_SETTING_QR);
                        break;
                    case Unitls.MISSION_QR:
                        startActivityForResult(new Intent(MissionAlarmActivity.this, SettingQrScreen.class)
                                .putExtra("alarm", mAlarm), REQUEST_SETTING_QR);
                        break;
                }
            }
        });
        mPagerRecycleView.setAdapter(imageAdapter);


        layoutManager.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                if (firstRUn == false) {
                    //todo check here
                    edtMission = null;
                    mAlarm.setMissionAlarm(missionFragmentList.get(position).getMissionID());
                    Log.d(TAG, "onItemSelected: " + missionFragmentList.get(position).getMissionID());
                }
            }
        });
        if (mAlarm != null) {
            mPagerRecycleView.smoothScrollToPosition(mAlarm.getMissionAlarm());
            firstRUn = false;
        }

        btnCancel = (Button) findViewById(R.id.btnCancelMission);
        btnOk = (Button) findViewById(R.id.btnOkMission);

        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_SETTING_QR) {
            // Make sure the request was successful
            if (resultCode == MISSION_ALARM_CODE) {
                edtMission = data.getParcelableExtra("mission");
            }
        } else if (requestCode == REQUEST_SETTING_QR_FINISH) {
            // Make sure the request was successful
            if (resultCode == MISSION_ALARM_CODE) {
                edtMission = data.getParcelableExtra("mission");
                saveMission();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("alarm", mAlarm);
                setResult(MISSION_ALARM_CODE, returnIntent);

                finish();
            }
        }
    }


    private void saveMission() {
        MyDatabaseHelperMission myDatabaseHelperMission = new MyDatabaseHelperMission(this);
        if (rawMission == null) {
            myDatabaseHelperMission.addMISSION(edtMission);
            mAlarm.setMissionAlarmId(edtMission.getId());
        } else {
            rawMission = edtMission;
            myDatabaseHelperMission.updateMISSION(rawMission);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancelMission:
                setResult(Activity.RESULT_CANCELED, null);
                finish();
                break;
            case R.id.btnOkMission:
                if (edtMission != null || mAlarm.getMissionAlarm() == Unitls.MISSION_DEFAULT) {
                    saveMission();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("alarm", mAlarm);
                    setResult(MISSION_ALARM_CODE, returnIntent);
                    finish();
                } else {
                    switch (mAlarm.getMissionAlarm()) {
                        case Unitls.MISSION_SHAKE:
                            startActivityForResult(new Intent(MissionAlarmActivity.this, SettingMathScreen.class)
                                    .putExtra("type", Unitls.MISSION_SHAKE).putExtra("mission", edtMission), REQUEST_SETTING_QR_FINISH);
                            break;
                        case Unitls.MISSION_CAL:
                            startActivityForResult(new Intent(MissionAlarmActivity.this, SettingMathScreen.class)
                                    .putExtra("type", Unitls.MISSION_CAL).putExtra("mission", edtMission), REQUEST_SETTING_QR_FINISH);
                            break;
                        case Unitls.MISSION_QR:
                            startActivityForResult(new Intent(MissionAlarmActivity.this, SettingQrScreen.class)
                                    .putExtra("alarm", mAlarm), REQUEST_SETTING_QR_FINISH);
                            break;
                    }
                }

                break;
        }
    }

    public class ScaleTransformer implements GalleryLayoutManager.ItemTransformer {

        @Override
        public void transformItem(GalleryLayoutManager layoutManager, View item, float fraction) {
            item.setPivotX(item.getWidth() / 2.f);
            item.setPivotY(item.getHeight() / 2.0f);
            float scale = 1 - 0.3f * Math.abs(fraction);
            item.setScaleX(scale);
            item.setScaleY(scale);
        }
    }
}
