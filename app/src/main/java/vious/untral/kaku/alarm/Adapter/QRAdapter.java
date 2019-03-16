package vious.untral.kaku.alarm.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import vious.untral.kaku.alarm.Model.Alarm;
import vious.untral.kaku.alarm.Model.QrCode;
import vious.untral.kaku.alarm.R;
import vious.untral.kaku.alarm.Tool.MyDatabaseHelperQR;

public class QRAdapter extends RecyclerView.Adapter<QRAdapter.ViewHolder> {

    List<QrCode> qrCodeList;
    private String TAG = "HIEUHK";
    private int lastCheckedPosition = -1;
    private Context mContext;


    public QRAdapter(Context applicationContext, List<QrCode> datas) {
        mContext = applicationContext;
        qrCodeList = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_qr, parent, false);
        Log.d(TAG, "onCreateViewHolder: ");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        holder.radioContent.setText(qrCodeList.get(position).getContent());
        holder.txtName.setText(qrCodeList.get(position).getName());
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView txttitle, txtcontent;
                Button btnOk, btnCancel;
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

                txttitle = (TextView) dialogView.findViewById(R.id.txtTitle);
                txtcontent = (TextView) dialogView.findViewById(R.id.txtContent);

                txttitle.setText("Delete entry");
                txtcontent.setText("Are you sure you want to delete this entry?");

                btnOk = (Button) dialogView.findViewById(R.id.btnOkAlert);
                btnCancel = (Button) dialogView.findViewById(R.id.btnCancelAlert);


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
                        // TODO Auto-generated method stub
                        MyDatabaseHelperQR myDatabaseHelperQR = new MyDatabaseHelperQR(mContext);
                        myDatabaseHelperQR.deleteQR(qrCodeList.get(position));
                        qrCodeList.remove(position);
                        notifyItemRemoved(position); // notify the adapter about the removed item
                        notifyItemRangeChanged(position, qrCodeList.size());
                        b.dismiss();
                    }
                });
            }
        });
        holder.radioContent.setChecked(position == lastCheckedPosition);
        holder.radioContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int copyLastCheckedPosition = lastCheckedPosition;
                lastCheckedPosition = position;
                notifyItemChanged(copyLastCheckedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + qrCodeList.size());
        return qrCodeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView btnDel, btnEdit;
        public final RadioButton radioContent;
        public final TextView txtName;

        public Alarm mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            btnDel = (ImageView) view.findViewById(R.id.btnDel);
            btnEdit = (ImageView) view.findViewById(R.id.btnEdit);
            radioContent = (RadioButton) view.findViewById(R.id.radioContent);
            txtName = (TextView) view.findViewById(R.id.txtName);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}
