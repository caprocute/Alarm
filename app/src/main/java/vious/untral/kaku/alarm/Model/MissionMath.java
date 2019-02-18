package vious.untral.kaku.alarm.Model;

import android.app.Activity;
import android.content.Context;

import vious.untral.kaku.alarm.R;

public class MissionMath extends Mission {
    int numProblems;
    int difficult;

    public MissionMath(Activity context) {
        super(context);
        this.numProblems = 3;
        this.difficult = 1;
        this.missionID = 3;
    }
}
