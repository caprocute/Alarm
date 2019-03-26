package vious.untral.kaku.alarm.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import vious.untral.kaku.alarm.Model.Alarm;
import vious.untral.kaku.alarm.Model.Mission;
import vious.untral.kaku.alarm.Tool.Unitls;

import vious.untral.kaku.alarm.R;

public class SettingMathScreen extends AppCompatActivity implements View.OnClickListener {

    private TextView txtSettingTitle, txtSettingTopNumberMath, txtSettingBottom, txtSettingSample, txtEasy, txtHard;
    private SeekBar seekNUmber;
    private NumberPicker npPIckerNUmber;
    private Button btnok, btncancel;
    private int mType;
    private Mission mMission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_math_screen);
        Unitls.setStatusBarGradiant(SettingMathScreen.this);

        mMission = getIntent().getParcelableExtra("mission");
        mType = getIntent().getIntExtra("type", -1);

        loadData();

    }

    private void loadData() {

        txtSettingTitle = (TextView) findViewById(R.id.txtSettingTitle);
        txtSettingTopNumberMath = (TextView) findViewById(R.id.txtSettingTopNumberMath);
        txtSettingBottom = (TextView) findViewById(R.id.txtSettingBottom);
        txtSettingSample = (TextView) findViewById(R.id.txtSettingSample);
        txtEasy = (TextView) findViewById(R.id.txtEasy);
        txtHard = (TextView) findViewById(R.id.txtHard);
        seekNUmber = (SeekBar) findViewById(R.id.seekNUmber);
        npPIckerNUmber = (NumberPicker) findViewById(R.id.npPIckerNUmber);

        btncancel = (Button) findViewById(R.id.button2);
        btnok = (Button) findViewById(R.id.button);

        btnok.setOnClickListener(this);
        btncancel.setOnClickListener(this);

        switch (mType) {
            case Unitls.MISSION_CAL:
                txtSettingTitle.setText(R.string.setting_math_title);
                txtSettingBottom.setText(R.string.setting_math_title_bottom);


                npPIckerNUmber.setMaxValue(200);
                npPIckerNUmber.setMinValue(1);
                npPIckerNUmber.setValue(0);

                npPIckerNUmber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        txtSettingTopNumberMath.setText(getString(R.string.do_title) + " " + newVal + " " + getString(R.string.math));
                    }
                });

                seekNUmber.setMax(5);
                /*seekNUmber.incrementProgressBy(1);*/
                seekNUmber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        switch (progress) {
                            case 0:
                                txtSettingSample.setText("9 + 8 = ?");
                                break;
                            case 1:
                                txtSettingSample.setText("15 + 26 = ?");
                                break;
                            case 2:
                                txtSettingSample.setText("43 + 23 + 56  = ?");
                                break;
                            case 3:
                                txtSettingSample.setText("(23 x 9) + 56 = ?");
                                break;
                            case 4:
                                txtSettingSample.setText("(33 x 17)+ 321 = ?");
                                break;
                            case 5:
                                txtSettingSample.setText("(192 x 73) + 8888 = ?");
                                break;
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                npPIckerNUmber.setValue(2);
                seekNUmber.setProgress(1);
                if (mMission != null && mMission.getMissionType() == mType) {
                    npPIckerNUmber.setValue(mMission.getNumber());
                    seekNUmber.setProgress(mMission.getLevel());
                }
                break;
            case Unitls.MISSION_SHAKE:
                txtSettingTitle.setText(R.string.setting_shake_title);
                txtSettingBottom.setText(R.string.setting_shake_title_bottom);


                npPIckerNUmber.setMaxValue(200);
                npPIckerNUmber.setMinValue(1);
                npPIckerNUmber.setValue(0);

                npPIckerNUmber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        txtSettingTopNumberMath.setText(getString(R.string.do_title_shake) + " " + newVal + " " + getString(R.string.do_title_time));
                    }
                });

                seekNUmber.setMax(2);

                seekNUmber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        switch (progress) {
                            case 0:
                                txtSettingSample.setText(getString(R.string.easy));
                                break;
                            case 1:
                                txtSettingSample.setText(getString(R.string.normal));
                                break;
                            case 2:
                                txtSettingSample.setText(getString(R.string.hard));
                                break;
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                npPIckerNUmber.setValue(40);
                seekNUmber.setProgress(1);

                if (mMission != null && mMission.getMissionType() == mType) {
                    npPIckerNUmber.setValue(mMission.getNumber());
                    seekNUmber.setProgress(mMission.getLevel());
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                setResult(Activity.RESULT_CANCELED, null);
                finish();
                break;
            case R.id.button:
                Intent returnIntent = new Intent();

                if (mMission == null && mType == Unitls.MISSION_CAL)
                    mMission = new Mission(Unitls.MISSION_CAL);

                if (mMission == null && mType == Unitls.MISSION_SHAKE)
                    mMission = new Mission(Unitls.MISSION_SHAKE);

                mMission.setMissionType(mType);
                mMission.setLevel(seekNUmber.getProgress());
                mMission.setNumber(npPIckerNUmber.getValue());
                returnIntent.putExtra("mission", mMission);
                setResult(AlarmDetailActivity.MISSION_ALARM_CODE, returnIntent);
                finish();
                break;
        }
    }
}
