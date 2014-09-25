package com.dissonant.quotas.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuotasSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_QUOTAS = "quotas:";
    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_QUOTA_ID = "quota_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";
    public static final String COLUMN_DESCRIPTION = "description";

    private static final String DATABASE_NAME = "quotas.db";
    private static final int DATABASE_VERSION = 1;

    //Database Creation
    private static final DATABASE_CREATE = "create table "
        + TABLE_QUOTAS + "(" + COLUMN_ID
        + " integer primary key autoincrement, " + COLUMN_QUOTA_ID;

    public QuotasSQLiteHelper(Context context) {
        super(content, DATABASE_NAME, null, DATABASE_VERSION);
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
}
