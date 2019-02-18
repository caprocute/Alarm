package vious.untral.kaku.alarm.Model;

import android.app.Activity;
import android.content.Context;

import vious.untral.kaku.alarm.R;

public class MissionQR extends Mission {
    private String scanResult;

    public MissionQR(Activity context) {
        super(context);
        this.missionID = 4;
    }
}
