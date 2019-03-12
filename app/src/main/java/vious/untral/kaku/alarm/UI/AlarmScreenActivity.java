package vious.untral.kaku.alarm.UI;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import vious.untral.kaku.alarm.Model.Alarm;
import vious.untral.kaku.alarm.Model.Mission;
import vious.untral.kaku.alarm.Model.MissionMath;
import vious.untral.kaku.alarm.Model.MissionPicutre;
import vious.untral.kaku.alarm.Model.MissionQR;
import vious.untral.kaku.alarm.Model.MissionShake;
import vious.untral.kaku.alarm.R;

public class AlarmScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private Alarm mAlarm;
    private Boolean isDemo;
    private MediaPlayer mediaPlayer;
    private String DEFAULT_RINGTONE_URL = "/sdcard/Download/AmThamBenEm-SonTungMTP-4066476.mp3";
    private Vibrator v;
    private long vibratePattern[] = {1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000};
    private ConstraintLayout moduleVerifyAlarmScreen;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.color.jshine1);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    private void makeVibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createWaveform(vibratePattern, 0));
        } else {

            v.vibrate(vibratePattern, 0);
        }
    }

    private Mission mMission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_screen);
        setStatusBarGradiant(this);

        mAlarm = getIntent().getParcelableExtra("alarm");
        isDemo = getIntent().getBooleanExtra("isdemo", false);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        moduleVerifyAlarmScreen = (ConstraintLayout) findViewById(R.id.container_module_verify_alarm);

        switch (mAlarm.getMissionAlarm()) {
            case 0:
                mMission = new Mission();
                break;
            case 1:
                mMission = new MissionPicutre();
                break;
            case 2:
                mMission = new MissionShake();
                break;
            case 3:
                mMission = new MissionMath();
                break;
            case 4:
                mMission = new MissionQR();
                break;
        }

        if (savedInstanceState == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(moduleVerifyAlarmScreen.getId(), mMission).commit();
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.default_alarm);
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                mediaPlayer.setAudioAttributes(
                        new AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_ALARM)
                                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                .build());
            } else {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            }
            /*mediaPlayer.setDataSource(DEFAULT_RINGTONE_URL);*/
            mediaPlayer.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception of type : " + e.toString());
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                /*mp.start();*/
            }
        });
        if (mAlarm.getVibrate()) makeVibrate();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
        v.cancel();
        this.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        v.cancel();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}
