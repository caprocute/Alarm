package vious.untral.kaku.alarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import vious.untral.kaku.alarm.AlarmFragment.OnListFragmentInteractionListener;
import vious.untral.kaku.alarm.Model.Alarm;

import java.util.Arrays;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link vious.untral.kaku.alarm.Model.Alarm} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAlarmRecyclerViewAdapter extends RecyclerView.Adapter<MyAlarmRecyclerViewAdapter.ViewHolder> {

    private final List<Alarm> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;
    private boolean[] weekdays = new boolean[]{true, true, true, true, true, false, false};

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_alarm, parent, false);
        return new ViewHolder(view);
    }

    private boolean[] weekkenddays = new boolean[]{true, true, true, true, true, true, true};

    public MyAlarmRecyclerViewAdapter(List<Alarm> items, OnListFragmentInteractionListener listener, Context context) {
        mContext = context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getHour() + ":" + mValues.get(position).getMinute());
        holder.mContentView.setText(getRepeat(mValues.get(position).getRepeat()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                    openSelectedAlarm(holder.mItem);
                }
            }
        });

        switch (mValues.get(position).getMissionAlarm()) {
            case 0:
                holder.imageView.setImageDrawable(mContext.getDrawable(R.drawable.alarm));
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    private String getRepeat(boolean[] repeat) {
        String re = "";
        if (Arrays.equals(weekdays, repeat)) {
            re = mContext.getResources().getString(R.string.weekdays);
        } else if (Arrays.equals(weekkenddays, repeat)) {
            re = mContext.getResources().getString(R.string.weekends);
        } else {
            for (int i = 0; i < 7; i++) {
                if (repeat[i] == true) {
                    switch (i) {
                        case 0:
                            re += mContext.getResources().getString(R.string.mondayShort);
                            break;
                        case 1:
                            re += mContext.getResources().getString(R.string.tuesdayShort);
                            break;
                        case 2:
                            re += mContext.getResources().getString(R.string.wednesdayShort);
                            break;
                        case 3:
                            re += mContext.getResources().getString(R.string.thursdayShort);
                            break;
                        case 4:
                            re += mContext.getResources().getString(R.string.fridayShort);
                            break;
                        case 5:
                            re += mContext.getResources().getString(R.string.saturday);
                            break;
                        case 6:
                            re += mContext.getResources().getString(R.string.sundayShort);
                            break;
                    }
                    re += ", ";
                }
            }
        }
        return re.substring(0, (re.length() - 1));
    }

    private void openSelectedAlarm(Alarm mItem) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("alarm", mItem);

        Intent intent = new Intent(mContext, AlarmDetailActivity.class);
        mContext.startActivity(intent, bundle);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView imageView;
        public Alarm mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
