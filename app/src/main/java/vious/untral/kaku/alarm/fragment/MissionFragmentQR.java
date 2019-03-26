package vious.untral.kaku.alarm.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import vious.untral.kaku.alarm.R;
import vious.untral.kaku.alarm.Tool.Unitls;

public class MissionFragmentQR extends MissionFragment implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA = 1;
    private String scanResult;
    private ZXingScannerView scannerView;

    public MissionFragmentQR() {
        missionID = Unitls.MISSION_QR;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
/*
        View view = inflater.inflate(R.layout.fragment_mission_qr, container, false);
*/
        missionID = 4;
        View view = inflater.inflate(R.layout.fragment_mission_qr, container, false);
        scannerView = (ZXingScannerView) view.findViewById(R.id.zXingScanner);
        int apiVersion = android.os.Build.VERSION.SDK_INT;
        if (apiVersion >= android.os.Build.VERSION_CODES.M) {
            if (!checkPermission()) {
                requestPermission();
            }
        }
        // Set the adapter
       /* if (view instanceof RecyclerView) {
            mContext = view.getContext();
            parentView = (ConstraintLayout) view;
        }*/
        scannerView.resumeCameraPreview(MissionFragmentQR.this);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
        return view;
    }

    @Override
    public void handleResult(Result result) {
        getActivity().onBackPressed();
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(getActivity().getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                        scannerView.resumeCameraPreview(MissionFragmentQR.this);
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                showMessage("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessage(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
