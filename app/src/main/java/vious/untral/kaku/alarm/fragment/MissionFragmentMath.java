package vious.untral.kaku.alarm.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

import vious.untral.kaku.alarm.R;

public class MissionFragmentMath extends MissionFragment implements OnClickListener, View.OnLongClickListener {
    int numProblems;
    int difficult;
    private Button btn_cal_1, btn_cal_2, btn_cal_3, btn_cal_4, btn_cal_5, btn_cal_6, btn_cal_7, btn_cal_8, btn_cal_9, btn_cal_0, btn_cal_del, btn_cal_ok, btnSnooze;
    private EditText edtInput;
    private TextView txtMath;
    private ImageView imgWrong;
    private int mLevel, mNumberMath = 2;
    private int randomNumber1, randomNumber2, randomNumber3;
    private int result, randomPostion;

    public MissionFragmentMath() {
        missionID = 3;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_mission_math, container, false);

        btn_cal_0 = (Button) view.findViewById(R.id.btn_cal_0);
        btn_cal_1 = (Button) view.findViewById(R.id.btn_cal_1);
        btn_cal_2 = (Button) view.findViewById(R.id.btn_cal_2);
        btn_cal_3 = (Button) view.findViewById(R.id.btn_cal_3);
        btn_cal_4 = (Button) view.findViewById(R.id.btn_cal_4);
        btn_cal_5 = (Button) view.findViewById(R.id.btn_cal_5);
        btn_cal_6 = (Button) view.findViewById(R.id.btn_cal_6);
        btn_cal_7 = (Button) view.findViewById(R.id.btn_cal_7);
        btn_cal_8 = (Button) view.findViewById(R.id.btn_cal_8);
        btn_cal_9 = (Button) view.findViewById(R.id.btn_cal_9);
        btn_cal_0 = (Button) view.findViewById(R.id.btn_cal_0);
        btn_cal_del = (Button) view.findViewById(R.id.btn_cal_del);
        btn_cal_ok = (Button) view.findViewById(R.id.btn_cal_ok);
        btnSnooze = (Button) view.findViewById(R.id.btn_cal_snooze);
        edtInput = (EditText) view.findViewById(R.id.edt_input);
        txtMath = (TextView) view.findViewById(R.id.txt_math);
        imgWrong = (ImageView) view.findViewById(R.id.imgWrong);

        btn_cal_0.setOnClickListener(this);
        btn_cal_1.setOnClickListener(this);
        btn_cal_2.setOnClickListener(this);
        btn_cal_3.setOnClickListener(this);
        btn_cal_4.setOnClickListener(this);
        btn_cal_5.setOnClickListener(this);
        btn_cal_6.setOnClickListener(this);
        btn_cal_7.setOnClickListener(this);
        btn_cal_8.setOnClickListener(this);
        btn_cal_9.setOnClickListener(this);
        btn_cal_0.setOnClickListener(this);
        btn_cal_del.setOnClickListener(this);
        btn_cal_ok.setOnClickListener(this);
        btn_cal_del.setOnLongClickListener(this);


        missionID = 3;
        // Set the adapter
        if (view instanceof RecyclerView) {
            mContext = view.getContext();
            parentView = (ConstraintLayout) view;
        }
        mLevel = 2;
        createMath(mLevel);
        return view;

    }

    private void createMath(int mLevel) {
        edtInput.setText("");
        imgWrong.setVisibility(View.GONE);
        switch (mLevel) {
            case 0:
                randomNumber1 = ThreadLocalRandom.current().nextInt(1, 10);
                randomNumber2 = ThreadLocalRandom.current().nextInt(1, 10);

                result = randomNumber1 + randomNumber2;
                txtMath.setText(randomNumber1 + " + " + randomNumber2 + " = ?");

                break;
            case 1:
                randomNumber1 = ThreadLocalRandom.current().nextInt(10, 100);
                randomNumber2 = ThreadLocalRandom.current().nextInt(10, 100);

                result = randomNumber1 + randomNumber2;
                txtMath.setText(randomNumber1 + " + " + randomNumber2 + " = ?");
                break;
            case 2:
                randomNumber1 = ThreadLocalRandom.current().nextInt(10, 100);
                randomNumber2 = ThreadLocalRandom.current().nextInt(10, 100);
                randomNumber3 = ThreadLocalRandom.current().nextInt(10, 100);

                result = randomNumber1 + randomNumber2 + randomNumber3;
                txtMath.setText(randomNumber1 + " + " + randomNumber2 + " + " + randomNumber3 + " = ?");
                break;
            case 3:
                randomPostion = ThreadLocalRandom.current().nextInt(0, 2);
                randomNumber1 = ThreadLocalRandom.current().nextInt(10, 100);
                randomNumber2 = ThreadLocalRandom.current().nextInt(1, 10);
                randomNumber3 = ThreadLocalRandom.current().nextInt(10, 100);

                if (randomPostion == 0) {
                    result = (randomNumber1 * randomNumber2) + randomNumber3;
                    txtMath.setText("(" + randomNumber1 + " x " + randomNumber2 + ") + " + randomNumber3 + " = ?");
                } else {
                    result = randomNumber1 + (randomNumber2 * randomNumber3);
                    txtMath.setText(randomNumber1 + " + (" + randomNumber2 + " x " + randomNumber3 + ") = ?");
                }

                break;
            case 4:
                randomPostion = ThreadLocalRandom.current().nextInt(0, 2);


                if (randomPostion == 0) {
                    randomNumber1 = ThreadLocalRandom.current().nextInt(10, 100);
                    randomNumber2 = ThreadLocalRandom.current().nextInt(10, 10);
                    randomNumber3 = ThreadLocalRandom.current().nextInt(100, 1000);
                    result = (randomNumber1 * randomNumber2) + randomNumber3;
                    txtMath.setText("(" + randomNumber1 + " x " + randomNumber2 + ") + " + randomNumber3 + " = ?");
                } else {
                    randomNumber1 = ThreadLocalRandom.current().nextInt(100, 1000);
                    randomNumber2 = ThreadLocalRandom.current().nextInt(10, 10);
                    randomNumber3 = ThreadLocalRandom.current().nextInt(10, 100);
                    result = randomNumber1 + (randomNumber2 * randomNumber3);
                    txtMath.setText(randomNumber1 + " + (" + randomNumber2 + " x " + randomNumber3 + ") = ?");
                }
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cal_0:
                edtInput.setText(edtInput.getText() + "0");
                break;
            case R.id.btn_cal_1:
                edtInput.setText(edtInput.getText() + "1");
                break;
            case R.id.btn_cal_2:
                edtInput.setText(edtInput.getText() + "2");
                break;
            case R.id.btn_cal_3:
                edtInput.setText(edtInput.getText() + "3");
                break;
            case R.id.btn_cal_4:
                edtInput.setText(edtInput.getText() + "4");
                break;
            case R.id.btn_cal_5:
                edtInput.setText(edtInput.getText() + "5");
                break;
            case R.id.btn_cal_6:
                edtInput.setText(edtInput.getText() + "6");
                break;
            case R.id.btn_cal_7:
                edtInput.setText(edtInput.getText() + "7");
                break;
            case R.id.btn_cal_8:
                edtInput.setText(edtInput.getText() + "8");
                break;
            case R.id.btn_cal_9:
                edtInput.setText(edtInput.getText() + "9");
                break;
            case R.id.btn_cal_del:
                if (edtInput.getText().length() > 0)
                    edtInput.setText(edtInput.getText().subSequence(0, edtInput.getText().length() - 1));
                break;
            case R.id.btn_cal_ok:
                String res = edtInput.getText().toString();
                if (res != null && res != "") {
                    try {
                        if (Integer.valueOf(res) == result) {
                            mNumberMath = mNumberMath - 1;
                            if (mNumberMath == 0) {
                                getActivity().onBackPressed();
                            } else createMath(mLevel);
                        } else imgWrong.setVisibility(View.VISIBLE);
                    } catch (NumberFormatException e) {
                        imgWrong.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.btn_cal_snooze:
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.btn_cal_del) {
            edtInput.setText("");

        }
        return true;
    }
}
