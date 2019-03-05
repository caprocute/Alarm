package vious.untral.kaku.alarm.UI;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import vious.untral.kaku.alarm.R;

public class SnoozeDialog extends AlertDialog {
    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
    private final AlarmDetailActivity mContext;
    private int snooze;
    private Button btnOk, btnCancel;

    private RadioGroup radioGroup;
    private AlertDialog b;

    public SnoozeDialog(AlarmDetailActivity context, int snooze) {
        super(context);
        this.mContext = context;
        this.snooze = snooze;
    }

    public void createNew() {
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_snooze_dialog, null);


        btnOk = (Button) dialogView.findViewById(R.id.btnOkSnooze);
        btnCancel = (Button) dialogView.findViewById(R.id.btnCancelSnooze);
        radioGroup = (RadioGroup) dialogView.findViewById(R.id.radioGroup);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButton1:
                        snooze = 0;
                        break;
                    case R.id.radioButton2:
                        snooze = 1;
                        break;
                    case R.id.radioButton3:
                        snooze = 2;
                        break;
                    case R.id.radioButton4:
                        snooze = 3;
                        break;
                    case R.id.radioButton5:
                        snooze = 4;
                        break;
                    case R.id.radioButton6:
                        snooze = 5;
                        break;
                    case R.id.radioButton7:
                        snooze = 6;
                        break;
                    case R.id.radioButton8:
                        snooze = 7;
                        break;
                }
                if (mContext != null) {
                    mContext.updateSnooze(snooze);
                }

                b.dismiss();
            }
        });


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
        loadSnooze(snooze);
    }

    private void loadSnooze(int snooze) {
        switch (snooze) {
            case 0:
                radioGroup.check(R.id.radioButton1);
                break;
            case 1:
                radioGroup.check(R.id.radioButton2);
                break;
            case 2:
                radioGroup.check(R.id.radioButton3);
                break;
            case 3:
                radioGroup.check(R.id.radioButton4);
                break;
            case 4:
                radioGroup.check(R.id.radioButton5);
                break;
            case 5:
                radioGroup.check(R.id.radioButton6);
                break;
            case 6:
                radioGroup.check(R.id.radioButton7);
                break;
            case 7:
                radioGroup.check(R.id.radioButton8);
                break;
        }
    }
}