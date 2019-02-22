package vious.untral.kaku.alarm.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.sql.Array;
import java.util.Arrays;

import vious.untral.kaku.alarm.Model.Alarm;
import vious.untral.kaku.alarm.R;

import static vious.untral.kaku.alarm.Tool.Unitls.everyday;
import static vious.untral.kaku.alarm.Tool.Unitls.weekdays;
import static vious.untral.kaku.alarm.Tool.Unitls.weekkenddays;

public class RepeatPickerDialog extends AlertDialog implements View.OnClickListener {
    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
    private ToggleButton toggleWeekday, toggleWeekend;
    private Button btnOk, btnCancel;
    private CheckBox checkmon, checktue, checkwed, checkthur, checkfri, checksat, checksun;
    private AlertDialog b;
    private AlarmDetailActivity mAlarmDetailActivity;
    private boolean[] updateRepeat;

    protected RepeatPickerDialog(AlarmDetailActivity context, boolean[] repeat) {
        super(context);
        this.mAlarmDetailActivity = context;
        this.updateRepeat = repeat;
    }

    public AlertDialog createNew() {

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_repeat_picker_dialog, null);

        toggleWeekday = (ToggleButton) dialogView.findViewById(R.id.btnWeekday);
        toggleWeekend = (ToggleButton) dialogView.findViewById(R.id.btnWeekend);

        btnOk = (Button) dialogView.findViewById(R.id.btnOkRepeat);
        btnCancel = (Button) dialogView.findViewById(R.id.btnCancelRepeat);

        checkmon = (CheckBox) dialogView.findViewById(R.id.checkMon);
        checktue = (CheckBox) dialogView.findViewById(R.id.checktue);
        checkwed = (CheckBox) dialogView.findViewById(R.id.checkwed);
        checkthur = (CheckBox) dialogView.findViewById(R.id.checkthur);
        checkfri = (CheckBox) dialogView.findViewById(R.id.checkfri);
        checksat = (CheckBox) dialogView.findViewById(R.id.checksat);
        checksun = (CheckBox) dialogView.findViewById(R.id.checksun);

        toggleWeekday.setOnClickListener(this);
        toggleWeekend.setOnClickListener(this);
        btnOk.setOnClickListener(this);

        checkmon.setOnClickListener(this);
        checkfri.setOnClickListener(this);
        checksat.setOnClickListener(this);
        checksun.setOnClickListener(this);
        checkthur.setOnClickListener(this);
        checktue.setOnClickListener(this);
        checkwed.setOnClickListener(this);

        dialogBuilder.setView(dialogView);

        b = dialogBuilder.create();
        b.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        b.show();
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                b.dismiss();
                // TODO Auto-generated method stub
            }
        });
        loadRepeat(updateRepeat);
        return null;
    }

    private void loadRepeat(boolean[] repeat) {
        if (Arrays.equals(weekkenddays, repeat)) {
            toggleWeekday.setChecked(true);
        } else toggleWeekday.setChecked(false);

        if (Arrays.equals(weekdays, repeat)) {
            toggleWeekend.setChecked(true);
        } else toggleWeekend.setChecked(false);

        if (Arrays.equals(everyday, repeat)) {
            toggleWeekend.setChecked(true);
            toggleWeekday.setChecked(true);
        } else {
            toggleWeekend.setChecked(false);
            toggleWeekday.setChecked(false);
        }


        for (int i = 0; i <= 6; i++) {
            switch (i) {
                case 0:
                    checkmon.setChecked(repeat[i]);
                    break;
                case 1:
                    checktue.setChecked(repeat[i]);
                    break;
                case 2:
                    checkwed.setChecked(repeat[i]);
                    break;
                case 3:
                    checkthur.setChecked(repeat[i]);
                    break;
                case 4:
                    checkfri.setChecked(repeat[i]);
                    break;
                case 5:
                    checksun.setChecked(repeat[i]);
                    break;
                case 6:
                    checksat.setChecked(repeat[i]);
                    break;
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnWeekday:
                checkmon.setChecked(toggleWeekday.isChecked());
                checktue.setChecked(toggleWeekday.isChecked());
                checkwed.setChecked(toggleWeekday.isChecked());
                checkthur.setChecked(toggleWeekday.isChecked());
                checkfri.setChecked(toggleWeekday.isChecked());
                for (int i = 0; i < 5; i++) {
                    updateRepeat[i] = toggleWeekday.isChecked();
                }
                break;
            case R.id.btnWeekend:
                checksat.setChecked(toggleWeekend.isChecked());
                checksun.setChecked(toggleWeekend.isChecked());
                for (int i = 5; i <= 6; i++) {
                    updateRepeat[i] = toggleWeekday.isChecked();
                }
                break;
            case R.id.checkMon:
                updateRepeat[0] = checkmon.isChecked();
                Log.d("hieuhk", "onClick: " + updateRepeat[0]);
                updateStatus(updateRepeat);
                break;
            case R.id.checktue:
                updateRepeat[1] = checktue.isChecked();
                updateStatus(updateRepeat);
                Log.d("hieuhk", "onClick: " + updateRepeat[1]);
                break;
            case R.id.checkwed:
                updateRepeat[2] = checkwed.isChecked();
                updateStatus(updateRepeat);
                Log.d("hieuhk", "onClick: " + updateRepeat[2]);
                break;
            case R.id.checkthur:
                updateRepeat[3] = checkthur.isChecked();
                updateStatus(updateRepeat);
                Log.d("hieuhk", "onClick: " + updateRepeat[3]);
                break;
            case R.id.checkfri:
                updateRepeat[4] = checkfri.isChecked();
                updateStatus(updateRepeat);
                Log.d("hieuhk", "onClick: " + updateRepeat[4]);
                break;
            case R.id.checksat:
                updateRepeat[5] = checksat.isChecked();
                updateStatus(updateRepeat);
                Log.d("hieuhk", "onClick: " + updateRepeat[5]);
                break;
            case R.id.checksun:
                updateRepeat[6] = checksun.isChecked();
                updateStatus(updateRepeat);
                Log.d("hieuhk", "onClick: " + updateRepeat[6]);
                break;

            case R.id.btnOkRepeat:
                if (mAlarmDetailActivity != null) {
                    mAlarmDetailActivity.updateRepeat(updateRepeat);
                }
                b.dismiss();
                break;
        }
    }

    private void updateStatus(boolean[] alarm) {
        int count = 0;
        boolean isdays = false;
        boolean isend = true;
        for (int i = 0; i < 7; i++) {
            if (alarm[i] == true) count++;
            if (i == 4 && count == 5) isdays = true;
            if ((i == 5 || i == 6) && alarm[i] == false) isend = false;
        }

        Log.d("hieuhjk", "updateStatus: " + isdays);
        Log.d("hieuhjk", "updateStatus: " + count);
        if (isdays) toggleWeekday.setChecked(true);
        else toggleWeekday.setChecked(false);
        if (isend) toggleWeekend.setChecked(true);
        else toggleWeekend.setChecked(false);
    }
}
