package vious.untral.kaku.alarm.UI;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import vious.untral.kaku.alarm.Adapter.QRAdapter;
import vious.untral.kaku.alarm.Model.Mission;
import vious.untral.kaku.alarm.R;
import vious.untral.kaku.alarm.Tool.MyDatabaseHelperMission;
import vious.untral.kaku.alarm.Tool.Unitls;
import vious.untral.kaku.alarm.fragment.AlarmFragment;

public class QrSettingScreen extends AppCompatActivity {
    private List<Mission> missionList;
    private Button btnOkQr, btnCancelQr;
    private FloatingActionButton floatAddQR;
    private RecyclerView listView;
    private TextView txtNone;
    private AlarmFragment.OnListFragmentInteractionListener mListener;
    private QRAdapter qrAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_setting_screen);
        Unitls.setStatusBarGradiant(this);

        btnOkQr = (Button) findViewById(R.id.btnOkQr);
        btnCancelQr = (Button) findViewById(R.id.btnCancelQR);
        floatAddQR = (FloatingActionButton) findViewById(R.id.floatAddQR);

        txtNone = (TextView) findViewById(R.id.txtNoneWarningQR);
        listView = (RecyclerView) findViewById(R.id.listQR);

        loadData();
        floatAddQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator integrator = new IntentIntegrator(QrSettingScreen.this);

                integrator.setPrompt(getResources().getString(R.string.scan_a_bar_code));
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setOrientationLocked(true);
                integrator.setBeepEnabled(true);
                integrator.setCaptureActivity(CaptureQRActivity.class);
                integrator.initiateScan();

            }
        });
        btnCancelQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnOkQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qrAdapter.getCheckedMission() != null) {
                    Log.d("hieuhk", "onClick: " + qrAdapter.getCheckedMission().getName());
                    Intent intent = new Intent();
                    intent.putExtra("mission", qrAdapter.getCheckedMission());
                    setResult(MissionAlarmActivity.REQUEST_SETTING_QR, intent);
                    finish();//finishing activity
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                final TextView txtCode;
                final EditText edtName;
                final Button btnOk, btnCancel;
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_scan_qr, null);

                txtCode = (TextView) dialogView.findViewById(R.id.txtCode);
                edtName = (EditText) dialogView.findViewById(R.id.edtName);

                txtCode.setText(result.getContents());

                btnOk = (Button) dialogView.findViewById(R.id.btnOkSaveQR);
                btnCancel = (Button) dialogView.findViewById(R.id.btnCancelSaveQR);


                dialogBuilder.setView(dialogView);

                final AlertDialog b = dialogBuilder.create();
                b.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                b.show();
                btnCancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        b.dismiss();
                        // TODO Auto-generated method stub
                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edtName.getText().toString() != "") {

                            Mission mission = new Mission(Unitls.MISSION_QR);
                            mission.setName(edtName.getText().toString());
                            mission.setContent(result.getContents());
                            missionList.add(mission);

                            txtNone.setVisibility((missionList.size() == 0) ? View.VISIBLE : View.GONE);
                            listView.setVisibility((missionList.size() == 0) ? View.GONE : View.VISIBLE);

                            MyDatabaseHelperMission myDatabaseHelperMission = new MyDatabaseHelperMission(QrSettingScreen.this);
                            myDatabaseHelperMission.addMISSION(mission);
                            qrAdapter.notifyDataSetChanged();

                            b.dismiss();

                        }
                    }
                });
            }
        }

    }

    private void loadData() {
        MyDatabaseHelperMission myDatabaseHelperMission = new MyDatabaseHelperMission(this);

        missionList = myDatabaseHelperMission.getAllMISSIONs();
        txtNone.setVisibility((missionList.size() == 0) ? View.VISIBLE : View.GONE);
        listView.setVisibility((missionList.size() == 0) ? View.GONE : View.VISIBLE);

        Log.d("hieuhk", "loadData: " + missionList.size());

        qrAdapter = new QRAdapter(this, missionList);
        qrAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                txtNone.setVisibility((itemCount == 0) ? View.VISIBLE : View.GONE);
                listView.setVisibility((itemCount == 0) ? View.GONE : View.VISIBLE);
                super.onItemRangeChanged(positionStart, itemCount);
            }
        });

        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(qrAdapter);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QrSettingScreen.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
