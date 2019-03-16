package vious.untral.kaku.alarm.Tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vious.untral.kaku.alarm.Model.QrCode;


public class MyDatabaseHelperQR extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";


    // Phiên bản
    private static final int DATABASE_VERSION = 1;


    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "QR_Manager";


    // Tên bảng: QR.
    private static final String TABLE_QR = "QRCode";

    private static final String COLUMN_QR_ID = "QR_Id";
    private static final String COLUMN_QR_NAME = "QR_Name";
    private static final String COLUMN_QR_CONTENT = "QR_Content";


    public MyDatabaseHelperQR(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Tạo các bảng.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelperQR.onCreate ... ");
        // Script tạo bảng.
        String script = "CREATE TABLE IF NOT EXISTS " + TABLE_QR + "("
                + COLUMN_QR_ID + " TEXT PRIMARY KEY,"
                + COLUMN_QR_NAME + " TEXT,"
                + COLUMN_QR_CONTENT + " TEXT"
                + ")";
        // Chạy lệnh tạo bảng.
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG, "MyDatabaseHelperQR.onUpgrade ... ");

        // Hủy (drop) bảng cũ nếu nó đã tồn tại.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QR);


        // Và tạo lại.
        onCreate(db);
    }


    public void addQR(QrCode QR) {
        Log.i(TAG, "MyDatabaseHelperQR.addQR ... " + QR.getId());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_QR_ID, QR.getId());
        values.put(COLUMN_QR_NAME, QR.getName());
        values.put(COLUMN_QR_CONTENT, QR.getContent());


        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_QR, null, values);


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


    public QrCode getQR(int id) {
        Log.i(TAG, "MyDatabaseHelperQR.getQR ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QR, new String[]{COLUMN_QR_ID,
                        COLUMN_QR_NAME,
                        COLUMN_QR_CONTENT}, COLUMN_QR_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        QrCode QR = new QrCode(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2));
        // return QR
        return QR;
    }


    public List<QrCode> getAllQRs() {
        Log.i(TAG, "MyDatabaseHelperQR.getAllQRs ... ");

        List<QrCode> QRList = new ArrayList<QrCode>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QR;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                QrCode QR = new QrCode();
                QR.setId(cursor.getString(0));
                QR.setName(cursor.getString(1));
                QR.setContent(cursor.getString(2));

                // Thêm vào danh sách.
                QRList.add(QR);
            } while (cursor.moveToNext());
        }

        // return QR list
        return QRList;
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

    public int getQRsCount() {
        Log.i(TAG, "MyDatabaseHelperQR.getQRsCount ... ");

        String countQuery = "SELECT  * FROM " + TABLE_QR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public int updateQR(QrCode QR) {
        Log.i(TAG, "MyDatabaseHelperQR.updateQR ... " + QR.getId());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_QR_ID, QR.getId());
        values.put(COLUMN_QR_NAME, QR.getName());
        values.put(COLUMN_QR_CONTENT, QR.getContent());

        // updating row
        return db.update(TABLE_QR, values, COLUMN_QR_ID + " = ?",
                new String[]{String.valueOf(QR.getId())});
    }

    public void deleteQR(QrCode QR) {
        Log.i(TAG, "MyDatabaseHelperQR.updateQR ... " + QR.getId());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QR, COLUMN_QR_ID + " = ?",
                new String[]{String.valueOf(QR.getId())});
        db.close();
    }
}