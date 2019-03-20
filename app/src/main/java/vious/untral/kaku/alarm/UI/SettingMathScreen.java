package vious.untral.kaku.alarm.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import vious.untral.kaku.alarm.Tool.Unitls;

import vious.untral.kaku.alarm.R;

public class SettingMathScreen extends AppCompatActivity {

    private TextView txtSettingTitle, txtSettingTopNumberMath, txtSettingBottom, txtSettingSample, txtEasy, txtHard;
    private SeekBar seekNUmber;
    private NumberPicker npPIckerNUmber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_math_screen);
        Unitls.setStatusBarGradiant(SettingMathScreen.this);

        txtSettingTitle = (TextView) findViewById(R.id.txtSettingTitle);
        txtSettingTopNumberMath = (TextView) findViewById(R.id.txtSettingTopNumberMath);
        txtSettingBottom = (TextView) findViewById(R.id.txtSettingBottom);
        txtSettingSample = (TextView) findViewById(R.id.txtSettingSample);
        txtEasy = (TextView) findViewById(R.id.txtEasy);
        txtHard = (TextView) findViewById(R.id.txtHard);
        seekNUmber = (SeekBar) findViewById(R.id.seekNUmber);
        npPIckerNUmber = (NumberPicker) findViewById(R.id.npPIckerNUmber);

        npPIckerNUmber.setMaxValue(200);
        npPIckerNUmber.setMinValue(1);
        npPIckerNUmber.setValue(0);

        npPIckerNUmber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                txtSettingTopNumberMath.setText("Do " + newVal + " maths");
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
    }
}
