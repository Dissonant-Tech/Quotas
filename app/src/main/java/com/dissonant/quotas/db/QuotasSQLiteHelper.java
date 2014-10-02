package com.dissonant.quotas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuotasSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_QUOTAS = "quotas:";
    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LENGTH_TIME = "length_time";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";
    public static final String COLUMN_DESCRIPTION = "description";

    private static final String DATABASE_NAME = "quotas.db";
    private static final int DATABASE_VERSION = 1;

    private static final String[] COLUMNS = {COLUMN_ID, COLUMN_TITLE,
        COLUMN_DESCRIPTION, COLUMN_START_TIME, COLUMN_END_TIME};

    //Database Creation
    private static final String DATABASE_CREATE = "create table "
        + TABLE_QUOTAS + "(" + COLUMN_ID
        + " integer primary key autoincrement, "
        + " text not null " + COLUMN_TITLE 
        + " text not null " + COLUMN_DESCRIPTION
        + " text not null " + COLUMN_LENGTH_TIME
        + " text not null " + COLUMN_START_TIME
        + " text not null " + COLUMN_END_TIME;

    public QuotasSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(QuotasSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTAS);
        onCreate(db);
    }

    public void addQuota(QuotaModel quota) {
        Log.d("addQouta: ", quota.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, quota.getTitle());
        values.put(COLUMN_DESCRIPTION, quota.getDescription());
        values.put(COLUMN_LENGTH_TIME, quota.getLengthTime().toString());
        values.put(COLUMN_START_TIME, quota.getStartTime().toString());
        values.put(COLUMN_END_TIME, quota.getEndTime().toString());

        db.insert(TABLE_QUOTAS, null, values);
        db.close();
    }

    public void getQuota(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QUOTAS, COLUMNS, " id = ?", 
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        QuotaModel quota = new QuotaModel(cursor.getInt(0), 
                cursor.getString(1), cursor.getString(2), cursor.getString(3), 
                cursor.getString(4), cursor.getString(5), cursor.getInt(6));
    }
}
