package vious.untral.kaku.alarm.Model;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.URL;

import vious.untral.kaku.alarm.R;

public class MissionPicutre extends Mission {
    private URL imageLink;

    public MissionPicutre() {
        missionID = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mission_picture, container, false);
        missionID = 1;

        // Set the adapter
        if (view instanceof RecyclerView) {
            mContext = view.getContext();
            parentView = (ConstraintLayout) view;
        }
        return view;
    }

}
