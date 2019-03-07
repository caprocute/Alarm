package vious.untral.kaku.alarm.Tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vious.untral.kaku.alarm.Model.Alarm;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";


    // Phiên bản
    private static final int DATABASE_VERSION = 1;


    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "Alarm_Manager";


    // Tên bảng: Alarm.
    private static final String TABLE_ALARM = "Alarm";

    private static final String COLUMN_ALARM_ID = "Alarm_Id";
    private static final String COLUMN_ALARM_HOUR = "Alarm_Hour";
    private static final String COLUMN_ALARM_REPEAT_ID = "Alarm_Repeat_Id";
    private static final String COLUMN_ALARM_MISSION = "Alarm_Mission";
    private static final String COLUMN_ALARM_MINUTE = "Alarm_Minute";
    private static final String COLUMN_ALARM_VIBRATE = "Alarm_Vibrate";
    private static final String COLUMN_ALARM_SNOOZE = "Alarm_Snooze";
    private static final String COLUMN_ALARM_LABEL = "Alarm_Label";
    private static final String COLUMN_ALARM_ISENABLE = "Alarm_Enable";
    private static final String COLUMN_ALARM_RINGTONE = "Alarm_Ringtone";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Tạo các bảng.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script tạo bảng.
        String script = "CREATE TABLE IF NOT EXISTS " + TABLE_ALARM + "("
                + COLUMN_ALARM_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_ALARM_MISSION + " INTEGER,"
                + COLUMN_ALARM_HOUR + " INTEGER,"
                + COLUMN_ALARM_MINUTE + " INTEGER,"
                + COLUMN_ALARM_REPEAT_ID + " INTEGER,"
                + COLUMN_ALARM_VIBRATE + " INTEGER,"
                + COLUMN_ALARM_SNOOZE + " INTEGER,"
                + COLUMN_ALARM_LABEL + " TEXT,"
                + COLUMN_ALARM_RINGTONE + " TEXT,"
                + COLUMN_ALARM_ISENABLE + " INTEGER"
                + ")";
        // Chạy lệnh tạo bảng.
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");

        // Hủy (drop) bảng cũ nếu nó đã tồn tại.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARM);


        // Và tạo lại.
        onCreate(db);
    }

    // Nếu trong bảng Alarm chưa có dữ liệu,
    // Trèn vào mặc định 2 bản ghi.
    public void createDefaultAlarmsIfNeed() {
        int count = this.getAlarmsCount();
        if (count == 0) {
            Alarm Alarm1 = new Alarm();
            Alarm Alarm2 = new Alarm();
            this.addAlarm(Alarm1);
            this.addAlarm(Alarm2);
        }
    }


    public void addAlarm(Alarm Alarm) {
        Log.i(TAG, "MyDatabaseHelper.addAlarm ... " + Alarm.getId());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ALARM_ID, Alarm.getId());
        values.put(COLUMN_ALARM_MISSION, Alarm.getMissionAlarm());
        values.put(COLUMN_ALARM_HOUR, Alarm.getHour());
        values.put(COLUMN_ALARM_MINUTE, Alarm.getMinute());
        values.put(COLUMN_ALARM_REPEAT_ID, createRepeatString(Alarm.getRepeat()));
        values.put(COLUMN_ALARM_VIBRATE, Alarm.getVibrate());
        values.put(COLUMN_ALARM_SNOOZE, Alarm.getSnooze());
        values.put(COLUMN_ALARM_LABEL, Alarm.getLabel());
        values.put(COLUMN_ALARM_RINGTONE, Alarm.getRingtone());
        values.put(COLUMN_ALARM_ISENABLE, Alarm.isEnable());


        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_ALARM, null, values);


        // Đóng kết nối database.
        db.close();
    }

    private String createRepeatString(boolean[] repeat) {
        String s = "";
        for (int i = 0; i < repeat.length; i++) {
            if (repeat[i] == true) s += "1";
            else s += 0;
        }
        return s;
    }


    public Alarm getAlarm(int id) {
        Log.i(TAG, "MyDatabaseHelper.getAlarm ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ALARM, new String[]{COLUMN_ALARM_ID,
                        COLUMN_ALARM_MISSION,
                        COLUMN_ALARM_HOUR,
                        COLUMN_ALARM_MINUTE,
                        COLUMN_ALARM_REPEAT_ID,
                        COLUMN_ALARM_VIBRATE,
                        COLUMN_ALARM_SNOOZE,
                        COLUMN_ALARM_LABEL,
                        COLUMN_ALARM_RINGTONE,
                        COLUMN_ALARM_ISENABLE}, COLUMN_ALARM_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Alarm Alarm = new Alarm(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),
                convertTorepeat(cursor.getString(4)),
                Boolean.parseBoolean(cursor.getString(5)),
                Integer.parseInt(cursor.getString(6)),
                cursor.getString(7),
                Boolean.parseBoolean(cursor.getString(8)),
                cursor.getString(9));
        // return Alarm
        return Alarm;
    }


    public List<Alarm> getAllAlarms() {
        Log.i(TAG, "MyDatabaseHelper.getAllAlarms ... ");

        List<Alarm> AlarmList = new ArrayList<Alarm>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ALARM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Alarm Alarm = new Alarm();
                Alarm.setId(Integer.parseInt(cursor.getString(0)));
                Alarm.setMissionAlarm(Integer.parseInt(cursor.getString(1)));
                Alarm.setHour(Integer.parseInt(cursor.getString(2)));
                Alarm.setMinute(Integer.parseInt(cursor.getString(3)));
                Alarm.setRepeat(convertTorepeat(cursor.getString(4)));
                Alarm.setVibrate(Boolean.parseBoolean(cursor.getString(5)));
                Alarm.setSnooze(Integer.parseInt(cursor.getString(6)));
                Alarm.setLabel(cursor.getString(7));
                Alarm.setRingtone(cursor.getString(8));
                Alarm.setEnable(Boolean.parseBoolean(cursor.getString(9)));

                // Thêm vào danh sách.
                AlarmList.add(Alarm);
            } while (cursor.moveToNext());
        }

        // return Alarm list
        return AlarmList;
    }

    private boolean[] convertTorepeat(String string) {
        boolean[] repeat = new boolean[string.length()];
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c == '1') {
                repeat[i] = true;
            } else repeat[i] = false;
        }
        return repeat;
    }

    public int getAlarmsCount() {
        Log.i(TAG, "MyDatabaseHelper.getAlarmsCount ... ");

        String countQuery = "SELECT  * FROM " + TABLE_ALARM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public int updateAlarm(Alarm Alarm) {
        Log.i(TAG, "MyDatabaseHelper.updateAlarm ... " + Alarm.getId());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ALARM_ID, Alarm.getId());
        values.put(COLUMN_ALARM_MISSION, Alarm.getMissionAlarm());
        values.put(COLUMN_ALARM_HOUR, Alarm.getHour());
        values.put(COLUMN_ALARM_MINUTE, Alarm.getMinute());
        values.put(COLUMN_ALARM_REPEAT_ID, createRepeatString(Alarm.getRepeat()));
        values.put(COLUMN_ALARM_VIBRATE, Alarm.getVibrate());
        values.put(COLUMN_ALARM_SNOOZE, Alarm.getSnooze());
        values.put(COLUMN_ALARM_LABEL, Alarm.getLabel());
        values.put(COLUMN_ALARM_RINGTONE, Alarm.getRingtone());
        values.put(COLUMN_ALARM_ISENABLE, Alarm.isEnable());

        // updating row
        return db.update(TABLE_ALARM, values, COLUMN_ALARM_ID + " = ?",
                new String[]{String.valueOf(Alarm.getId())});
    }

    public void deleteAlarm(Alarm Alarm) {
        Log.i(TAG, "MyDatabaseHelper.updateAlarm ... " + Alarm.getId());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARM, COLUMN_ALARM_ID + " = ?",
                new String[]{String.valueOf(Alarm.getId())});
        db.close();
    }
}