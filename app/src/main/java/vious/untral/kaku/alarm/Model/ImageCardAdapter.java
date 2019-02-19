package vious.untral.kaku.alarm.Model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.List;

import vious.untral.kaku.alarm.AlarmScreenActivity;
import vious.untral.kaku.alarm.BuildConfig;
import vious.untral.kaku.alarm.R;

public class ImageCardAdapter extends RecyclerView.Adapter<ImageCardAdapter.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "ImageCardAdapter";
    private List<Mission> items;
    private OnItemClickListener mOnItemClickListener;
    private int mWidth;
    private int mHeight;
    private Context context;
    private Alarm alarm;

    public ImageCardAdapter(List<Mission> items, int width, int height, Context context, Alarm alarm) {
        this.items = items;
        this.context = context;
        mWidth = width;
        mHeight = height;
        this.alarm = alarm;
    }

    public ImageCardAdapter setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
        return this;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "onCreateViewHolder: type:" + viewType);
        }
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycel_image, parent, false);
        v.setOnClickListener(this);
        v.setLayoutParams(new RecyclerView.LayoutParams(mWidth, mHeight));
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onBindViewHolder: position:" + position);
        }
        final Mission item = items.get(position);
        switch (item.getMissionID()) {
            case 0:
                holder.image.setImageDrawable(context.getDrawable(R.drawable.alarm));
                holder.textView.setText(context.getText(R.string.Default));
                holder.txtSetting.setVisibility(View.GONE);
                break;
            case 1:
                holder.image.setImageDrawable(context.getDrawable(R.drawable.ic_camera));
                holder.txtSetting.setVisibility(View.VISIBLE);
                holder.textView.setText(context.getText(R.string.Picture));
                break;
            case 2:
                holder.image.setImageDrawable(context.getDrawable(R.drawable.ic_vibration));
                holder.txtSetting.setVisibility(View.VISIBLE);
                holder.textView.setText(context.getText(R.string.Shake));
                break;
            case 3:
                holder.image.setImageDrawable(context.getDrawable(R.drawable.ic_calculator));
                holder.txtSetting.setVisibility(View.VISIBLE);
                holder.textView.setText(context.getText(R.string.Math));
                break;
            case 4:
                holder.image.setImageDrawable(context.getDrawable(R.drawable.ic_qrcode));
                holder.txtSetting.setVisibility(View.VISIBLE);
                holder.textView.setText(context.getText(R.string.Scan));
                break;
        }
        holder.itemView.setTag(position);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Helllo bitches", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, AlarmScreenActivity.class)
                        .putExtra("alarm", createDemo(alarm, position))
                        .putExtra("isdemo", true));
            }
        });
    }

    private Alarm createDemo(Alarm alarm, int position) {
        Alarm demoAlarm = alarm;
        demoAlarm.setMissionAlarm(position);

        DateTime dt = new DateTime();  // current time
        int min = dt.getMinuteOfHour();     // gets the current month
        int hours = dt.getHourOfDay(); // gets hour of day

        demoAlarm.setHour(hours);
        demoAlarm.setMinute(min);

        return demoAlarm;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(final View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    /**
     * @author chensuilun
     */
    public interface OnItemClickListener {

        void onItemClick(View view, int position);

    }

    /**
     * @author chensuilun
     */
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView textView, txtSetting;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView6);
            textView = (TextView) itemView.findViewById(R.id.textView6);
            imageView = (ImageView) itemView.findViewById(R.id.imageView7);
            txtSetting = (TextView) itemView.findViewById(R.id.txtSetting);
        }
    }

    /**
     *
     */

}