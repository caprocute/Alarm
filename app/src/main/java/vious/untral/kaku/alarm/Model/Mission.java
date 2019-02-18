package vious.untral.kaku.alarm.Model;

import android.app.Activity;
import android.content.Context;

import vious.untral.kaku.alarm.R;

public class Mission {
    protected int missionID;

    public Mission(Activity context) {
        this.missionID = 0;
    }

    public int getMissionID() {
        return missionID;
    }

    public void setMissionID(int missionID) {
        this.missionID = missionID;
    }
}

