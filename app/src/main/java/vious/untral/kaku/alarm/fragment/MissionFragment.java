package vious.untral.kaku.alarm.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vious.untral.kaku.alarm.R;

public class MissionFragment extends Fragment implements View.OnClickListener, Parcelable {
    protected int missionID;
    protected Context mContext;
    protected ConstraintLayout parentView;
    protected Button btnDissmiss;

    public static final Creator<MissionFragment> CREATOR = new Creator<MissionFragment>() {
        @Override
        public MissionFragment createFromParcel(Parcel in) {
            return new MissionFragment(in);
        }

        @Override
        public MissionFragment[] newArray(int size) {
            return new MissionFragment[size];
        }
    };

    public MissionFragment() {
        missionID = 0;
    }

    @SuppressLint("ValidFragment")
    protected MissionFragment(Parcel in) {
        missionID = in.readInt();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mission_default, container, false);

        btnDissmiss = (Button) view.findViewById(R.id.btnDissmiss);
        btnDissmiss.setOnClickListener(this);

        missionID = 0;
        // Set the adapter
        if (view instanceof RecyclerView) {
            mContext = view.getContext();
            parentView = (ConstraintLayout) view;
        }
        return view;
    }

    public int getMissionID() {
        return missionID;
    }

    public void setMissionID(int missionID) {
        this.missionID = missionID;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDissmiss:
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(missionID);
    }
}

