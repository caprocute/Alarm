package vious.untral.kaku.alarm.Tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vious.untral.kaku.alarm.Model.Mission;


public class MyDatabaseHelperMission extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";


    // Phiên bản
    private static final int DATABASE_VERSION = 1;


    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "MISSION_Manager";


    // Tên bảng: MISSION.
    private static final String TABLE_MISSION = "MISSIONCode";

    private static final String COLUMN_MISSION_ID = "MISSION_Id";
    private static final String COLUMN_MISSION_NAME = "MISSION_Name";
    private static final String COLUMN_MISSION_CONTENT = "MISSION_Content";
    private static final String COLUMN_MISSION_MISSION = "MISSION_Mission";
    private static final String COLUMN_MISSION_PIC_URL = "MISSION_Pic_url";
    private static final String COLUMN_MISSION_LEVEL = "MISSION_Level";
    private static final String COLUMN_MISSION_NUMBER = "MISSION_Number";


    public MyDatabaseHelperMission(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Tạo các bảng.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelperMission.onCreate ... ");
        // Script tạo bảng.
        String script = "CREATE TABLE IF NOT EXISTS " + TABLE_MISSION + "("
                + COLUMN_MISSION_ID + " TEXT PRIMARY KEY,"
                + COLUMN_MISSION_NAME + " TEXT,"
                + COLUMN_MISSION_CONTENT + " TEXT,"
                + COLUMN_MISSION_MISSION + " INTEGER,"
                + COLUMN_MISSION_PIC_URL + " TEXT,"
                + COLUMN_MISSION_LEVEL + " INTEGER,"
                + COLUMN_MISSION_NUMBER + " INTEGER"
                + ")";
        // Chạy lệnh tạo bảng.
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG, "MyDatabaseHelperMission.onUpgrade ... ");

        // Hủy (drop) bảng cũ nếu nó đã tồn tại.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MISSION);


        // Và tạo lại.
        onCreate(db);
    }


    public void addMISSION(Mission MISSION) {
        Log.i(TAG, "MyDatabaseHelperMission.addMISSION ... " + MISSION.getId());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MISSION_ID, MISSION.getId());
        values.put(COLUMN_MISSION_NAME, MISSION.getName());
        values.put(COLUMN_MISSION_CONTENT, MISSION.getContent());
        values.put(COLUMN_MISSION_MISSION, MISSION.getMissionType());
        values.put(COLUMN_MISSION_PIC_URL, MISSION.getPictureUrl());
        values.put(COLUMN_MISSION_LEVEL, MISSION.getLevel());
        values.put(COLUMN_MISSION_NUMBER, MISSION.getNumber());


        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_MISSION, null, values);


        // Đóng kết nối database.
        db.close();
    }


    public Mission getMISSION(String id) {
        Log.i(TAG, "MyDatabaseHelperMission.getMISSION ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MISSION, new String[]{
                        COLUMN_MISSION_ID,
                        COLUMN_MISSION_NAME,
                        COLUMN_MISSION_CONTENT,
                        COLUMN_MISSION_MISSION,
                        COLUMN_MISSION_PIC_URL,
                        COLUMN_MISSION_LEVEL,
                        COLUMN_MISSION_NUMBER
                }, COLUMN_MISSION_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Mission MISSION = new Mission(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                Integer.valueOf(cursor.getString(3)),
                cursor.getString(4),
                Integer.valueOf(cursor.getString(5)),
                Integer.valueOf(cursor.getString(6)));
        // return MISSION
        return MISSION;
    }


    public List<Mission> getAllMISSIONs() {
        Log.i(TAG, "MyDatabaseHelperMission.getAllMISSIONs ... ");

        List<Mission> MISSIONList = new ArrayList<Mission>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MISSION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Mission MISSION = new Mission();
                if (cursor.getString(0) != null)
                    MISSION.setId(cursor.getString(0));

                if (cursor.getString(1) != null)
                    MISSION.setName(cursor.getString(1));

                if (cursor.getString(2) != null)
                    MISSION.setContent(cursor.getString(2));

                if (cursor.getString(3) != null)
                    MISSION.setMissionType(Integer.valueOf(cursor.getString(3)));

                if (cursor.getString(4) != null)
                    MISSION.setPictureUrl(cursor.getString(4));

                if (cursor.getString(5) != null)
                    MISSION.setLevel(Integer.valueOf(cursor.getString(5)));

                if (cursor.getString(6) != null)
                    MISSION.setNumber(Integer.valueOf(cursor.getString(6)));

                // Thêm vào danh sách.
                MISSIONList.add(MISSION);
            } while (cursor.moveToNext());
        }

        // return MISSION list
        return MISSIONList;
    }

    public int getMISSIONsCount() {
        Log.i(TAG, "MyDatabaseHelperMission.getMISSIONsCount ... ");

        String countQuery = "SELECT  * FROM " + TABLE_MISSION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public int updateMISSION(Mission MISSION) {
        Log.i(TAG, "MyDatabaseHelperMission.updateMISSION ... " + MISSION.getId());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MISSION_ID, MISSION.getId());
        values.put(COLUMN_MISSION_NAME, MISSION.getName());
        values.put(COLUMN_MISSION_CONTENT, MISSION.getContent());
        values.put(COLUMN_MISSION_MISSION, MISSION.getMissionType());
        values.put(COLUMN_MISSION_PIC_URL, MISSION.getPictureUrl());
        values.put(COLUMN_MISSION_LEVEL, MISSION.getPictureUrl());
        values.put(COLUMN_MISSION_NUMBER, MISSION.getNumber());

        // updating row
        return db.update(TABLE_MISSION, values, COLUMN_MISSION_ID + " = ?",
                new String[]{String.valueOf(MISSION.getId())});
    }

    public void deleteMISSION(Mission MISSION) {
        Log.i(TAG, "MyDatabaseHelperMission.updateMISSION ... " + MISSION.getId());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MISSION, COLUMN_MISSION_ID + " = ?",
                new String[]{String.valueOf(MISSION.getId())});
        db.close();
    }
}