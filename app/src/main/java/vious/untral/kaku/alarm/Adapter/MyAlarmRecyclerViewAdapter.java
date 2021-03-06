package vious.untral.kaku.alarm.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import vious.untral.kaku.alarm.Model.Alarm;
import vious.untral.kaku.alarm.R;
import vious.untral.kaku.alarm.Tool.Unitls;
import vious.untral.kaku.alarm.UI.AlarmDetailActivity;
import vious.untral.kaku.alarm.UI.MainActivity;
import vious.untral.kaku.alarm.fragment.AlarmFragment.OnListFragmentInteractionListener;

import static vious.untral.kaku.alarm.Tool.Unitls.everyday;
import static vious.untral.kaku.alarm.Tool.Unitls.weekdays;
import static vious.untral.kaku.alarm.Tool.Unitls.weekkenddays;

/**
 * {@link RecyclerView.Adapter} that can display a {@link vious.untral.kaku.alarm.Model.Alarm} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAlarmRecyclerViewAdapter extends RecyclerView.Adapter<MyAlarmRecyclerViewAdapter.ViewHolder> {

    private final List<Alarm> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Activity mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_alarm, parent, false);
        return new ViewHolder(view);
    }


    public MyAlarmRecyclerViewAdapter(List<Alarm> items, OnListFragmentInteractionListener listener, Activity context) {
        mContext = context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        holder.mIdView.setText(mValues.get(position).getHour() + ":" + mValues.get(position).getMinute());
        holder.mContentView.setText(getRepeat(mValues.get(position).getRepeat()));
        if (mValues.get(position).getLabel() != "")
            holder.mLabel.setText("| " + mValues.get(position).getLabel());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                    openSelectedAlarm(holder.mItem, position);
                }
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    Unitls.setAlarmFromNow(mContext, mValues.get(position));

                    holder.imageButton.setBackground(mContext.getDrawable(R.drawable.more));

                    holder.mLabel.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.mContentView.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.mIdView.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.mLabel.setTextColor(mContext.getResources().getColor(R.color.white));

                    switch (mValues.get(position).getMissionAlarm()) {
                        case Unitls.MISSION_DEFAULT:
                            holder.imageView.setImageDrawable(mContext.getDrawable(R.drawable.alarm));
                            break;
                        case Unitls.MISSION_SHAKE:
                            holder.imageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_vibration));
                            break;
                        case Unitls.MISSION_CAL:
                            holder.imageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_calculator));
                            break;
                        case Unitls.MISSION_QR:
                            holder.imageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_qrcode));
                            break;
                    }

                } else {
                    Unitls.cancelAlarm(mContext, mValues.get(position));
                    Unitls.setAlarmFromNow(mContext, mValues.get(position));

                    holder.mLabel.setTextColor(mContext.getResources().getColor(R.color.unselect));
                    holder.mContentView.setTextColor(mContext.getResources().getColor(R.color.unselect));
                    holder.mIdView.setTextColor(mContext.getResources().getColor(R.color.unselect));
                    holder.mLabel.setTextColor(mContext.getResources().getColor(R.color.unselect));
                    holder.imageButton.setBackground(mContext.getDrawable(R.drawable.more_off));

                    switch (mValues.get(position).getMissionAlarm()) {
                        case Unitls.MISSION_DEFAULT:
                            holder.imageView.setImageDrawable(mContext.getDrawable(R.drawable.alarm_off));
                            break;

                        case Unitls.MISSION_SHAKE:
                            holder.imageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_vibration_off));
                            break;
                        case Unitls.MISSION_CAL:
                            holder.imageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_calculator_off));
                            break;
                        case Unitls.MISSION_QR:
                            holder.imageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_qrcode_off));
                            break;
                    }
                }
            }
        });
        holder.checkBox.setChecked(mValues.get(position).isEnable());

        switch (mValues.get(position).getMissionAlarm()) {
            case Unitls.MISSION_DEFAULT:
                holder.imageView.setImageDrawable(mContext.getDrawable(R.drawable.alarm));
                break;

            case Unitls.MISSION_SHAKE:
                holder.imageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_vibration));
                break;
            case Unitls.MISSION_CAL:
                holder.imageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_calculator));
                break;
            case Unitls.MISSION_QR:
                holder.imageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_qrcode));
                break;
        }
    }

    private String getRepeat(boolean[] repeat) {
        String re = "";
        if (Arrays.equals(everyday, repeat)) {
            re = mContext.getResources().getString(R.string.everyday);
            return re;

        } else if (Arrays.equals(weekdays, repeat)) {
            re = mContext.getResources().getString(R.string.weekdays);
            return re;

        } else if (Arrays.equals(weekkenddays, repeat)) {
            re = mContext.getResources().getString(R.string.weekends);
            return re;

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
        return re.substring(0, (re.length() - 2));
    }

    private void openSelectedAlarm(Alarm mItem, int postion) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("alarm", mItem);
        bundle.putInt("postion", postion);
        bundle.putBoolean("isNew", false);
        Intent intent = new Intent(mContext, AlarmDetailActivity.class);
        intent.putExtras(bundle);
        mContext.startActivityForResult(intent, MainActivity.UPDATE_ALARM);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mLabel;
        public final ImageView imageView;
        public Alarm mItem;
        public final Switch checkBox;
        public final ConstraintLayout container_item;
        public final ImageButton imageButton;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mLabel = (TextView) view.findViewById(R.id.txtLabelitem);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            mContentView = (TextView) view.findViewById(R.id.content);
            checkBox = (Switch) view.findViewById(R.id.checkBox);
            container_item = (ConstraintLayout) view.findViewById(R.id.container_item);
            imageButton = (ImageButton) view.findViewById(R.id.imageButton);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
