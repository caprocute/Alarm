package vious.untral.kaku.alarm.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;

public class Alarm implements Parcelable {

    private int missionAlarm;
    private int hour;
    private int id;
    private int minute;
    private boolean[] repeat = new boolean[7];
    private boolean vibrate;
    private int snooze;
    private String label;
    private String ringtone;

    protected Alarm(Parcel in) {
        missionAlarm = in.readInt();
        hour = in.readInt();
        id = in.readInt();
        minute = in.readInt();
        repeat = in.createBooleanArray();
        vibrate = in.readByte() != 0;
        snooze = in.readInt();
        label = in.readString();
        ringtone = in.readString();
    }

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

    public Alarm() {
        missionAlarm = 3;
        hour = 6;
        minute = 30;

        Arrays.fill(repeat, false);
        for (int i = 0; i < 2; i++) {
            repeat[i] = true;
        }
        ringtone = "/sdcard/Download/AmThamBenEm-SonTungMTP-4066476.mp3";
        vibrate = false;
        snooze = 0;
        label = "";
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
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

    public String getRingtone() {
        return ringtone;
    }

    public void setRingtone(String ringtone) {
        this.ringtone = ringtone;
    }

    public int getSnooze() {
        return snooze;
    }

    public boolean getVibrate() {
        return vibrate;
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(missionAlarm);
        dest.writeInt(hour);
        dest.writeInt(id);
        dest.writeInt(minute);
        dest.writeBooleanArray(repeat);
        dest.writeByte((byte) (vibrate ? 1 : 0));
        dest.writeInt(snooze);
        dest.writeString(label);
        dest.writeString(ringtone);
    }
}
