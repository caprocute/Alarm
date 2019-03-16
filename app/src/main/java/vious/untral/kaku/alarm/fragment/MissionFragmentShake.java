package vious.untral.kaku.alarm.fragment;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vious.untral.kaku.alarm.R;
import vious.untral.kaku.alarm.Tool.ShakeDetector;

public class MissionFragmentShake extends MissionFragment implements ShakeDetector.Listener {
    private int number;

    public MissionFragmentShake() {
        missionID = 2;
    }

    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private TextView txtShaked;

    /*  private final SensorEventListener mSensorListener = new SensorEventListener() {

          public void onSensorChanged(SensorEvent se) {
              float x = se.values[0];
              float y = se.values[1];
              float z = se.values[2];
              mAccelLast = mAccelCurrent;
              mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
              float delta = mAccelCurrent - mAccelLast;
              mAccel = mAccel * 0.9f + delta; // perform low-cut filter
              if (mAccel > 12) {
                  Log.d("hieuhk", "onSensorChanged: "+mAccel);
                  mNumberShaked += 1;
                  if (mNumberShaked == mNumberShakeNeed) getActivity().onBackPressed();
                  else
                      txtShaked.setText("Shake your phone " + (mNumberShakeNeed - mNumberShaked) + " times");
              }

          }

          @Override
          public void onAccuracyChanged(Sensor sensor, int accuracy) {

          }
      };
  */
    private int mNumberShakeNeed, mNumberShaked;
    private ShakeDetector shakeDetector;

    @Override
    public void onPause() {
        shakeDetector.stop();
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mission_shake, container, false);
        missionID = 2;

        // Set the adapter
        if (view instanceof RecyclerView) {
            mContext = view.getContext();
            parentView = (ConstraintLayout) view;
        }
        txtShaked = (TextView) view.findViewById(R.id.txtshaked);

        mNumberShakeNeed = 15;
        mNumberShaked = 0;
        txtShaked.setText("Shake your phone " + (mNumberShakeNeed - mNumberShaked) + " times");

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
      /*  mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;*/

        shakeDetector = new ShakeDetector(this);
        shakeDetector.setSensitivity(ShakeDetector.SENSITIVITY_LIGHT);
        shakeDetector.start(mSensorManager);

        return view;
    }

    @Override
    public void hearShake() {
        mNumberShaked += 1;
        if (mNumberShaked % 2 == 0) {
            if ((mNumberShaked == (mNumberShakeNeed * 2))) {
                getActivity().onBackPressed();
            } else {
                txtShaked.setText("Shake your phone " + (mNumberShakeNeed - mNumberShaked / 2) + " times");
                Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(500);
                } else {

                    v.vibrate(500);
                }
            }
        }
    }
}
