package vious.untral.kaku.alarm;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;
import vious.untral.kaku.alarm.Model.BitmapUtils;
import vious.untral.kaku.alarm.Model.FlingRecycleView;
import vious.untral.kaku.alarm.Model.ImageCardAdapter;
import vious.untral.kaku.alarm.Model.Mission;
import vious.untral.kaku.alarm.Model.MissionMath;
import vious.untral.kaku.alarm.Model.MissionPicutre;
import vious.untral.kaku.alarm.Model.MissionQR;
import vious.untral.kaku.alarm.Model.MissionShake;

public class MissionAlarmActivity extends AppCompatActivity {
    List<Integer> mResId;
    ImageView mPagerBg;

    List<Mission> missionList = new ArrayList<>(5);
    FlingRecycleView mPagerRecycleView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_alarm);
        setStatusBarGradiant(this);
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
            }
        });
        layoutManager.setItemTransformer(new ScaleTransformer());
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        ImageCardAdapter imageAdapter = new ImageCardAdapter(missionList, (int) (displayMetrics.widthPixels * 0.55f), (int) (displayMetrics.heightPixels * 0.55f), this);
        imageAdapter.setOnItemClickListener(new ImageCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MissionAlarmActivity.this, "click" + missionList.get(position).getMissionID(), Toast.LENGTH_SHORT).show();
                mPagerRecycleView.smoothScrollToPosition(position);
            }
        });
        mPagerRecycleView.setAdapter(imageAdapter);
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
