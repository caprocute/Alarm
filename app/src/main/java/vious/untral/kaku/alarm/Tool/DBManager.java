/*
package vious.untral.kaku.alarm.Tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import vious.untral.kaku.alarm.Model.Alarm;


*/
/**
 * Created by chien on 11/13/16.
 *//*


public class DBManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Alarm_list";
    private static final String TABLE_NAME = "Alarm";
    private static final String MISSION = "mission";
    private static final String HOUR = "hour";
    private static final String ID = "id";
    private static final String MIN = "minute";
    private static final String VIBRATE = "vibrate";
    private static final String SNOOZE = "snooze";
    private static final String LABEL = "label";
    private static final String RINGTONE = "ringtone";
    private static final String REPEAT = "repeat";

    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d("DBManager", "DBManager: ");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " integer primary key, " +
                MISSION + " INT, " +
                HOUR + " INT, " +
                MIN + " INT," +
                SNOOZE + " INT," +
                LABEL + " TEXT," +
                RINGTONE + " TEXT," +
                REPEAT + " TEXT," +
                VIBRATE + " BOOLEAN)";
        db.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfylly", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Drop successfylly", Toast.LENGTH_SHORT).show();
    }

    //Add new a Alarm
    public void addAlarm(Alarm Alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, Alarm.getId());
        values.put(MISSION, Alarm.getMissionAlarm());
        values.put(HOUR, Alarm.getHour());
        values.put(MIN, Alarm.getMinute());
        values.put(SNOOZE, Alarm.getSnooze());
        values.put(LABEL, Alarm.getLabel());
        values.put(RINGTONE, Alarm.getRingtone());
        values.put(REPEAT, Alarm.getAddress());
        values.put(VIBRATE, Alarm.getAddress());
        //Neu de null thi khi value bang null thi loi

        db.insert(TABLE_NAME, null, values);

        db.close();
    }
    
    */
/*
    Select a Alarm by ID
     *//*


    public Alarm getSdtudentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID,
                        NAME, EMAIL, NUMBER, ADDRESS}, ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Alarm Alarm = new Alarm(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        cursor.close();
        db.close();
        return Alarm;
    }
    
    */
/*
    Update name of Alarm 
     *//*


    public int Update(Alarm Alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, Alarm.getName());

        return db.update(TABLE_NAME, values, ID + "=?", new String[]{String.valueOf(Alarm.getId())});


    }
 
    */
/*
     Getting All Alarm
      *//*


    public List<Alarm> getAllAlarm() {
        List<Alarm> listAlarm = new ArrayList<Alarm>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Alarm Alarm = new Alarm();
                Alarm.setId(cursor.getInt(0));
                Alarm.setName(cursor.getString(1));
                Alarm.setEmail(cursor.getString(2));
                Alarm.setNumber(cursor.getString(3));
                Alarm.setAddress(cursor.getString(4));
                listAlarm.add(Alarm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listAlarm;
    }

    */
/*
    Delete a Alarm by ID
     *//*

    public void deleteAlarm(Alarm Alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[]{String.valueOf(Alarm.getId())});
        db.close();
    }

    */
/*
    Get Count Alarm in Table Alarm
     *//*

    public int getAlarmsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}*/
