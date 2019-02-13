package vious.untral.kaku.alarm.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;

public class Alarm implements Parcelable {
    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };
    private int missionAlarm;
    private int hour;
    private int id;
    private int minute;
    private boolean[] repeat = new boolean[7];
    private URL ringtone;
    private int snooze;
    private String label;

    protected Alarm(Parcel in) {
        missionAlarm = in.readInt();
        hour = in.readInt();
        id = in.readInt();
        minute = in.readInt();
        repeat = in.createBooleanArray();
        snooze = in.readInt();
        label = in.readString();
    }

    public Alarm() {
        missionAlarm = 0;
        hour = 6;
        minute = 30;

        Arrays.fill(repeat, false);
        for (int i = 0; i < 7; i++) {
            repeat[i] = true;
        }
        ringtone = null;
        snooze = 0;
        label = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMissionAlarm() {
        return missionAlarm;
    }

    public void setMissionAlarm(int missionAlarm) {
        this.missionAlarm = missionAlarm;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean[] getRepeat() {
        return repeat;
    }

    public void setRepeat(boolean[] repeat) {
        this.repeat = repeat;
    }

    public URL getRingtone() {
        return ringtone;
    }

    public void setRingtone(URL ringtone) {
        this.ringtone = ringtone;
    }

    public int getSnooze() {
        return snooze;
    }

    public void setSnooze(int snooze) {
        this.snooze = snooze;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(missionAlarm);
        parcel.writeInt(hour);
        parcel.writeInt(id);
        parcel.writeInt(minute);
        parcel.writeBooleanArray(repeat);
        parcel.writeInt(snooze);
        parcel.writeString(label);
    }
}
