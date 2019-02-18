package vious.untral.kaku.alarm;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import vious.untral.kaku.alarm.Model.Alarm;

public class AlarmDetailActivity extends AppCompatActivity implements View.OnClickListener {

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
    private TextView txtMissionAlarm, txtTimeRemain, txtRepeat, txtRingtone, txtSnooze, txtLabel;
    private Button btn10m, btnD10m, btn1h, btnd1h, btnCancel, btnDel, btnOk;
    private final int TIME_REMAIN = 1;
    private SeekBar seekVolume;
    private Switch switchVib;
    private ImageView imageView;
    private boolean[] weekkenddays = new boolean[]{true, true, true, true, true, true, true};
    private boolean[] weekdays = new boolean[]{true, true, true, true, true, false, false};
    private final Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TIME_REMAIN) {
                Bundle bundle = msg.getData();
                String s = bundle.getString("update");
                txtTimeRemain.setText(s);
            }
            super.handleMessage(msg);
        }
    };
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);
        setStatusBarGradiant(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mAlarm = bundle.getParcelable("alarm");
        init();
        loadAlarmData(mAlarm);
    }

    private ConstraintLayout missionLay;
    private String TAG = "hieuhk";
    private boolean updateAlarmFlag = false;

    private String getRepeat(boolean[] repeat) {
        String re = "";
        if (Arrays.equals(weekdays, repeat)) {
            re = getResources().getString(R.string.weekdays);
        } else if (Arrays.equals(weekkenddays, repeat)) {
            re = getResources().getString(R.string.weekends);
        } else {
            for (int i = 0; i < 7; i++) {
                if (repeat[i] == true) {
                    switch (i) {
                        case 0:
                            re += getResources().getString(R.string.mondayShort);
                            break;
                        case 1:
                            re += getResources().getString(R.string.tuesdayShort);
                            break;
                        case 2:
                            re += getResources().getString(R.string.wednesdayShort);
                            break;
                        case 3:
                            re += getResources().getString(R.string.thursdayShort);
                            break;
                        case 4:
                            re += getResources().getString(R.string.fridayShort);
                            break;
                        case 5:
                            re += getResources().getString(R.string.saturday);
                            break;
                        case 6:
                            re += getResources().getString(R.string.sundayShort);
                            break;
                    }
                    re += ", ";
                }
            }
        }
        return re.substring(0, (re.length() - 1));
    }

    private Timer t;
    private int MISSION_ALARM_CODE = 1;

    private void init() {
        txtLabel = (TextView) findViewById(R.id.txtLabel);
        txtMissionAlarm = (TextView) findViewById(R.id.txtMissionAlarm);
        txtTimeRemain = (TextView) findViewById(R.id.txtTimeRemain);
        txtRingtone = (TextView) findViewById(R.id.txtRingtone);
        txtSnooze = (TextView) findViewById(R.id.txtSnooze);
        txtRepeat = (TextView) findViewById(R.id.txtRepeat);

        btn10m = (Button) findViewById(R.id.btn10m);
        btn10m.setOnClickListener(this);
        btnD10m = (Button) findViewById(R.id.btnD10m);
        btnD10m.setOnClickListener(this);
        btn1h = (Button) findViewById(R.id.btn1h);
        btn1h.setOnClickListener(this);
        btnd1h = (Button) findViewById(R.id.btnd1h);
        btnd1h.setOnClickListener(this);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnDel = (Button) findViewById(R.id.btnDel);
        btnOk = (Button) findViewById(R.id.btnOk);
        timePicker = (TimePicker) findViewById(R.id.timePicker1);

        seekVolume = (SeekBar) findViewById(R.id.seekVolume);

        switchVib = (Switch) findViewById(R.id.switchVib);

        imageView = (ImageView) findViewById(R.id.imageView2);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                if (updateAlarmFlag) {
                    mAlarm.setHour(timePicker.getCurrentHour());
                    mAlarm.setMinute(timePicker.getCurrentMinute());
                    Log.d("hieuhk ", "onTimeChanged: " + timePicker.getCurrentHour() + " " + timePicker.getCurrentMinute());
                    calTimeRemain(timePicker.getCurrentHour(), timePicker.getCurrentMinute(), mAlarm.getRepeat());
                }
            }
        });

        missionLay = (ConstraintLayout) findViewById(R.id.missionLay);
        missionLay.setOnClickListener(this);
    }

    private void loadAlarmData(Alarm mAlarm) {
        if (mAlarm != null) {
            switch (mAlarm.getMissionAlarm()) {
                case 0:
                    txtMissionAlarm.setText(R.string.Default);
                    imageView.setImageDrawable(getDrawable(R.drawable.alarm));
                    break;
                case 1:
                    txtMissionAlarm.setText(R.string.Picture);
                    imageView.setImageDrawable(getDrawable(R.drawable.ic_camera));
                    break;
                case 2:
                    txtMissionAlarm.setText(R.string.Shake);
                    imageView.setImageDrawable(getDrawable(R.drawable.ic_vibration));
                    break;
                case 3:
                    txtMissionAlarm.setText(R.string.Math);
                    imageView.setImageDrawable(getDrawable(R.drawable.ic_calculator));
                    break;
                case 4:
                    txtMissionAlarm.setText(R.string.Scan);
                    imageView.setImageDrawable(getDrawable(R.drawable.ic_qrcode));
                    break;
            }
        }


        txtTimeRemain.setText(calTimeRemain(mAlarm.getHour(), mAlarm.getMinute(), mAlarm.getRepeat()));
        txtRepeat.setText(getRepeat(mAlarm.getRepeat()));
        if (mAlarm.getRingtone() != null) {
            txtRingtone.setText(mAlarm.getRingtone().getFile());
        }
        txtSnooze.setText(String.valueOf(mAlarm.getSnooze()));
        txtLabel.setText(mAlarm.getLabel());

        updateAlarmFlag = false;
        timePicker.setCurrentHour(mAlarm.getHour());
        timePicker.setCurrentMinute(mAlarm.getMinute());
        updateAlarmFlag = true;
    }

    private void updateUI(String data) {
        Message message = new Message();
        message.what = TIME_REMAIN;
        Bundle bundle = new Bundle();
        bundle.putString("update", data);
        message.setData(bundle);
        uiHandler.sendMessage(message);
    }

    private String calTimeRemain(final int hour, final int minute, final boolean[] repeat) {
        if (t != null) t.cancel();
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
                                      DateTime now = DateTime.now();

                                      DateTime alarmDate = new DateTime(now.year().get(), now.monthOfYear().get(), now.dayOfMonth().get(), hour, minute);

                                      boolean flagStop = true;

                                      while (flagStop) {
                                          for (int i = 0; i < repeat.length; i++) {
                                              if (repeat[i] == true) {
                                                  DateTime nextDay = null;
                                                  switch (i) {
                                                      case 0:
                                                          nextDay = alarmDate.withDayOfWeek(DateTimeConstants.MONDAY);
                                                          break;
                                                      case 1:
                                                          nextDay = alarmDate.withDayOfWeek(DateTimeConstants.TUESDAY);
                                                          break;
                                                      case 2:
                                                          nextDay = alarmDate.withDayOfWeek(DateTimeConstants.WEDNESDAY);
                                                          break;
                                                      case 3:
                                                          nextDay = alarmDate.withDayOfWeek(DateTimeConstants.THURSDAY);
                                                          break;
                                                      case 4:
                                                          nextDay = alarmDate.withDayOfWeek(DateTimeConstants.FRIDAY);
                                                          break;
                                                      case 5:
                                                          nextDay = alarmDate.withDayOfWeek(DateTimeConstants.SATURDAY);
                                                          break;
                                                      case 6:
                                                          nextDay = alarmDate.withDayOfWeek(DateTimeConstants.SUNDAY);
                                                          break;
                                                  }

                                                  if (nextDay.getMillis() > now.getMillis()) {
                                                      long durationBetweenDate = nextDay.getMillis() - now.getMillis();
                                                      long secondsCAL = durationBetweenDate / 1000;
                                                      long minutesCAL = secondsCAL / 60;
                                                      long hoursCAL = minutesCAL / 60;
                                                      long daysCAL = hoursCAL / 24;

                                                      String update = getString(R.string.remain_part1);

                                                      if (daysCAL != 0)
                                                          update += " " + daysCAL + " " + getString(R.string.days);
                                                      if ((hoursCAL - daysCAL * 24) != 0)
                                                          update += " " + (hoursCAL - daysCAL * 24) + " " + getString(R.string.hours);
                                                      if ((minutesCAL - (hoursCAL - daysCAL * 24) * 60 - daysCAL * 60 * 24) != 0)
                                                          update += " " + (minutesCAL - (hoursCAL - daysCAL * 24) * 60 - daysCAL * 60 * 24) + " " + getString(R.string.minutes);

                                                      updateUI(update);
                                                      flagStop = false;
                                                      break;
                                                  }
                                              }
                                          }
                                          if (flagStop) alarmDate = alarmDate.plusWeeks(1);
                                      }

                                  }
                              },
                0,
                30000);
        return "";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1h:
                timePicker.setCurrentHour(timePicker.getCurrentHour() + 1);
                break;
            case R.id.btn10m:
                timePicker.setCurrentMinute(timePicker.getCurrentMinute() + 10);
                break;
            case R.id.btnD10m:
                timePicker.setCurrentMinute(timePicker.getCurrentMinute() - 10);
                break;
            case R.id.btnd1h:
                timePicker.setCurrentHour(timePicker.getCurrentHour() - 1);
                break;
            case R.id.missionLay:
                Log.d(TAG, "onClick: hello");
                startActivityForResult(new Intent(AlarmDetailActivity.this, MissionAlarmActivity.class), MISSION_ALARM_CODE);
                break;
        }
    }
}
