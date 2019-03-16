package vious.untral.kaku.alarm.UI;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vious.untral.kaku.alarm.Adapter.QRAdapter;
import vious.untral.kaku.alarm.Model.QrCode;
import vious.untral.kaku.alarm.R;
import vious.untral.kaku.alarm.Tool.MyDatabaseHelperQR;
import vious.untral.kaku.alarm.Tool.Unitls;
import vious.untral.kaku.alarm.fragment.AlarmFragment;

public class QrSettingScreen extends AppCompatActivity {
    private List<QrCode> qrCodeList;
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
                QrCode qrCode = new QrCode();
                qrCodeList.add(qrCode);
                MyDatabaseHelperQR myDatabaseHelperQR = new MyDatabaseHelperQR(QrSettingScreen.this);
                myDatabaseHelperQR.addQR(qrCode);
                qrAdapter.notifyDataSetChanged();
            }
        });

    }

    private void loadData() {
        MyDatabaseHelperQR myDatabaseHelperQR = new MyDatabaseHelperQR(this);
        qrCodeList = myDatabaseHelperQR.getAllQRs();
/*
        txtNone.setVisibility((qrCodeList.size() == 0) ? View.VISIBLE : View.GONE);
        listView.setVisibility((qrCodeList.size() == 0) ? View.GONE : View.VISIBLE);*/
        Log.d("hieuhk", "loadData: " + qrCodeList.size());
        qrAdapter = new QRAdapter(this, qrCodeList);
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
