package vious.untral.kaku.alarm.Model;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vious.untral.kaku.alarm.R;

public class Mission extends Fragment implements View.OnClickListener {
    protected int missionID;
    protected Context mContext;
    protected ConstraintLayout parentView;
    protected Button btnDissmiss;

    public Mission() {
        missionID = 0;
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
}

