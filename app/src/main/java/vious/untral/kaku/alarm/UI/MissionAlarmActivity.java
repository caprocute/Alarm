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
import vious.untral.kaku.alarm.R;
import vious.untral.kaku.alarm.Tool.FlingRecycleView;
import vious.untral.kaku.alarm.Tool.LinePagerIndicatorDecoration;
import vious.untral.kaku.alarm.fragment.MissionFragment;
import vious.untral.kaku.alarm.fragment.MissionFragmentMath;
import vious.untral.kaku.alarm.fragment.MissionFragmentPicutre;
import vious.untral.kaku.alarm.fragment.MissionFragmentQR;
import vious.untral.kaku.alarm.fragment.MissionFragmentShake;

public class MissionAlarmActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Integer> mResId;
    private ImageView mPagerBg;

    private List<MissionFragment> missionFragmentList = new ArrayList<>(5);
    private FlingRecycleView mPagerRecycleView;
    private Button btnCancel, btnOk;

    public static final int REQUEST_SETTING_QR = 5951;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_alarm);
        setStatusBarGradiant(this);

        Bundle bundle = getIntent().getExtras();

        mAlarm = bundle.getParcelable("alarm");

        mPagerRecycleView = (FlingRecycleView) findViewById(R.id.rc_1);

        MissionFragment missionFragment = new MissionFragmentQR();

        missionFragmentList.add(new MissionFragment());
        missionFragmentList.add(new MissionFragmentPicutre());
        missionFragmentList.add(new MissionFragmentShake());
        missionFragmentList.add(new MissionFragmentMath());
        missionFragmentList.add(new MissionFragmentQR());

        mPagerRecycleView.setFlingAble(false);
        mPagerRecycleView.addItemDecoration(new LinePagerIndicatorDecoration());
        GalleryLayoutManager layoutManager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        layoutManager.attach(mPagerRecycleView, 30);
        layoutManager.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                /*Bitmap bmp = BitmapUtils.decodeSampledBitmapFromResource(getResources(), mResId.get(position % mResId.size()), 100, 100);*/
/*
                mPagerBg.setImageBitmap(FastBlur.doBlur(bmp, 20, false));

*/
                mAlarm.setMissionAlarm(position);
            }
        });
        layoutManager.setItemTransformer(new ScaleTransformer());
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        ImageCardAdapter imageAdapter = new ImageCardAdapter(missionFragmentList, (int) (displayMetrics.widthPixels * 0.55f), (int) (displayMetrics.heightPixels * 0.55f), this, mAlarm);
        imageAdapter.setOnItemClickListener(new ImageCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        startActivityForResult(new Intent(MissionAlarmActivity.this, SettingMathScreen.class)
                                .putExtra("alarm", mAlarm), REQUEST_SETTING_QR);
                        break;
                    case 4:
                        startActivityForResult(new Intent(MissionAlarmActivity.this, SettingQrScreen.class)
                                .putExtra("alarm", mAlarm), REQUEST_SETTING_QR);
                        break;
                }
            }
        });
        mPagerRecycleView.setAdapter(imageAdapter);

        if (mAlarm != null) {
            mPagerRecycleView.smoothScrollToPosition(mAlarm.getMissionAlarm());
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
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
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
                Intent returnIntent = new Intent();
                returnIntent.putExtra("alarm", mAlarm);
                setResult(AlarmDetailActivity.MISSION_ALARM_CODE, returnIntent);
                finish();
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
