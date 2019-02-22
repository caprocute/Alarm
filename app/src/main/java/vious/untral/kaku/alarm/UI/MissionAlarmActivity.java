package vious.untral.kaku.alarm.UI;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import vious.untral.kaku.alarm.Tool.LinePagerIndicatorDecoration;
import vious.untral.kaku.alarm.Model.Alarm;
import vious.untral.kaku.alarm.Tool.FlingRecycleView;
import vious.untral.kaku.alarm.Adapter.ImageCardAdapter;
import vious.untral.kaku.alarm.Model.Mission;
import vious.untral.kaku.alarm.Model.MissionMath;
import vious.untral.kaku.alarm.Model.MissionPicutre;
import vious.untral.kaku.alarm.Model.MissionQR;
import vious.untral.kaku.alarm.Model.MissionShake;
import vious.untral.kaku.alarm.R;

public class MissionAlarmActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Integer> mResId;
    private ImageView mPagerBg;

    private List<Mission> missionList = new ArrayList<>(5);
    private FlingRecycleView mPagerRecycleView;
    private Button btnCancel, btnOk;

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

        Mission mission = new MissionQR(this);

        missionList.add(new Mission(MissionAlarmActivity.this));
        missionList.add(new MissionPicutre(MissionAlarmActivity.this));
        missionList.add(new MissionShake(MissionAlarmActivity.this));
        missionList.add(new MissionMath(MissionAlarmActivity.this));
        missionList.add(new MissionQR(MissionAlarmActivity.this));

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
        ImageCardAdapter imageAdapter = new ImageCardAdapter(missionList, (int) (displayMetrics.widthPixels * 0.55f), (int) (displayMetrics.heightPixels * 0.55f), this, mAlarm);
        imageAdapter.setOnItemClickListener(new ImageCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
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
