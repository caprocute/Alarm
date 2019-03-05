package vious.untral.kaku.alarm.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ToggleButton;

import vious.untral.kaku.alarm.R;

public class LabelDialog extends AlertDialog implements View.OnClickListener {
    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
    private final AlarmDetailActivity mContext;
    private String label = "";
    private Button btnOk, btnCancel;
    private EditText edtLabel;
    private AlertDialog b;

    protected LabelDialog(AlarmDetailActivity context, String label) {
        super(context);
        this.mContext = context;
        this.label = label;
    }

    public AlertDialog createNew() {
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_label_dialog, null);


        btnOk = (Button) dialogView.findViewById(R.id.btnOkLabel);
        btnCancel = (Button) dialogView.findViewById(R.id.btnCancelLabel);

        edtLabel = (EditText) dialogView.findViewById(R.id.edtLabel);

        btnOk.setOnClickListener(this);


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
        loadRepeat(label);
        return null;
    }

    private void loadRepeat(String label) {
        if (label != null) edtLabel.setText(label);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOkLabel:
                if (mContext != null) {
                    mContext.updateLabel(edtLabel.getText().toString());
                }

                b.dismiss();
                break;
        }
    }
}
