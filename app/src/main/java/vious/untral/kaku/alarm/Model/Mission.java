package vious.untral.kaku.alarm.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.ThreadLocalRandom;

import vious.untral.kaku.alarm.Tool.Unitls;

public class Mission implements Parcelable {
    private String id;
    private String content;
    private String name;

    public static final Creator<Mission> CREATOR = new Creator<Mission>() {
        @Override
        public Mission createFromParcel(Parcel in) {
            return new Mission(in);
        }

        @Override
        public Mission[] newArray(int size) {
            return new Mission[size];
        }
    };
    private int missionType;
    private String pictureUrl;
    private int number;
    private int level;

    public Mission() {
    }

    public Mission(String id, String content, String name, Integer missionType, String pictureUrl, Integer number, Integer level) {
        this.id = id;
        this.content = content;
        this.name = name;
        this.missionType = missionType;
        this.pictureUrl = pictureUrl;
        this.number = number;
        this.level = level;
    }

    protected Mission(Parcel in) {
        id = in.readString();
        content = in.readString();
        name = in.readString();
        missionType = in.readInt();
        pictureUrl = in.readString();
        number = in.readInt();
        level = in.readInt();
    }

    public Mission(int missionType) {
        this.missionType = missionType;
        this.id = randomID(missionType);
        this.content = "demo";
        this.name = "demo";
        this.pictureUrl = "demo";
        this.level = 0;
        this.number = 0;
    }

    public Mission(String id, String content, String name) {
        this.id = id;
        this.content = content;
        this.name = name;
    }

    public int getMissionType() {
        return missionType;
    }

    public void setMissionType(int missionType) {
        this.missionType = missionType;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private String randomID(int missionType) {
        long number = ThreadLocalRandom.current().nextLong(100000, 1000000);
        String text = RandomStringUtils.randomAlphabetic(2);
        switch (missionType) {
            case Unitls.MISSION_DEFAULT:
                return ("DEF" + number + text);
            case Unitls.MISSION_SHAKE:
                return ("SHAKE" + number + text);
            case Unitls.MISSION_CAL:
                return ("CAL" + number + text);
        }
        return ("QR" + number + text);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(content);
        dest.writeString(name);
        dest.writeInt(missionType);
        dest.writeString(pictureUrl);
        dest.writeInt(number);
        dest.writeInt(level);
    }
}
