package vious.untral.kaku.alarm.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vious.untral.kaku.alarm.BuildConfig;
import vious.untral.kaku.alarm.R;

public class ImageCardAdapter extends RecyclerView.Adapter<ImageCardAdapter.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "ImageCardAdapter";
    private List<Mission> items;
    private OnItemClickListener mOnItemClickListener;
    private int mWidth;
    private int mHeight;
    private Context context;

    public ImageCardAdapter(List<Mission> items, int width, int height, Context context) {
        this.items = items;
        this.context = context;
        mWidth = width;
        mHeight = height;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onBindViewHolder: position:" + position);
        }
        Mission item = items.get(position);
        switch (item.getMissionID()) {
            case 0:
                holder.image.setImageDrawable(context.getDrawable(R.drawable.alarm));
                holder.textView.setText(context.getText(R.string.Default));
                break;
            case 1:
                holder.image.setImageDrawable(context.getDrawable(R.drawable.ic_camera));
                holder.textView.setText(context.getText(R.string.Picture));
                break;
            case 2:
                holder.image.setImageDrawable(context.getDrawable(R.drawable.ic_vibration));
                holder.textView.setText(context.getText(R.string.Shake));
                break;
            case 3:
                holder.image.setImageDrawable(context.getDrawable(R.drawable.ic_calculator));
                holder.textView.setText(context.getText(R.string.Math));
                break;
            case 4:
                holder.image.setImageDrawable(context.getDrawable(R.drawable.ic_qrcode));
                holder.textView.setText(context.getText(R.string.Scan));
                break;
        }
        holder.itemView.setTag(position);
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
        public TextView textView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView6);
            textView = (TextView) itemView.findViewById(R.id.textView6);
            imageView = (ImageView) itemView.findViewById(R.id.imageView7);
        }
    }

    /**
     *
     */

}