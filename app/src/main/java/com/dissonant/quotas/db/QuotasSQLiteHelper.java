package com.dissonant.quotas.db;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuotasSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_QUOTAS = "quotas:";
    public static final String TABLE_TASKS = "tasks:";

    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_REPEAT = "repeat";
    public static final String COLUMN_STARTTIME = "start_time";
    public static final String COLUMN_ENDTIME = "end_time";
    public static final String COLUMN_ISACTIVE = "isActive";

    public static final String COLUMNS_REFID = "refId";
    public static final String COLUMN_TASKDATE = "taskDate";
    public static final String COLUMN_COMPLETED = "completed";

    public static final String DATABASE_NAME = "quotas.db";
    public static final int DATABASE_VERSION = 1;

    public static final String[] QUOTAS_COLUMNS = {COLUMN_ID, COLUMN_TITLE,
        COLUMN_DESCRIPTION, COLUMN_REPEAT, COLUMN_STARTTIME, COLUMN_ENDTIME,
        COLUMN_ISACTIVE
    };

    // Databse tables
    private static final String CREATE_QUOTAS_TABLE = "create table "
        + TABLE_QUOTAS + "(" + COLUMN_ID
        + " integer primary key autoincrement, "
        + COLUMN_TITLE          + " text not null "
        + COLUMN_REPEAT         + " text not null "
        + COLUMN_DESCRIPTION    + " text not null "
        + COLUMN_STARTTIME      + " text not null "
        + COLUMN_ENDTIME        + " text not null "
        + COLUMN_ISACTIVE       + " integer default 0 "
        + ");";

    private static final String CREATE_TASKS_TABLE = "create table "
        + TABLE_TASKS + "(" + COLUMN_ID
        + " integer primary key autoincrement, "
        + COLUMNS_REFID         + " integer "
        + COLUMN_TASKDATE       + " text not null "
        + COLUMN_COMPLETED      + " integer default 0 "
        + ");";

    public QuotasSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUOTAS_TABLE);
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(QuotasSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public String[] getQuotaColumns() {
        return QUOTAS_COLUMNS;
    }
}
