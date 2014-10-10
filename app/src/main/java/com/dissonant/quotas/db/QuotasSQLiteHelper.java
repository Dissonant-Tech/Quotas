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

    private static final String COLUMNS_REFID = "refId";
    private static final String COLUMN_TASKDATE = "taskDate";
    private static final String COLUMN_COMPLETED = "completed";

    private static final String DATABASE_NAME = "quotas.db";
    private static final int DATABASE_VERSION = 1;

    private static final String[] QUOTAS_COLUMNS = {COLUMN_ID, COLUMN_TITLE,
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
        onCreate(db);
    }

    public void addQuota(QuotaModel quota) {
        Log.d("addQouta: ", quota.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, quota.getTitle());
        values.put(COLUMN_DESCRIPTION, quota.getDescription());
        values.put(COLUMN_REPEAT, quota.getRepeat());
        values.put(COLUMN_STARTTIME, quota.getStartTime().toString());
        values.put(COLUMN_ENDTIME, quota.getEndTime().toString());
        values.put(COLUMN_ISACTIVE, quota.getIsActive());

        db.insert(TABLE_QUOTAS, null, values);
        db.close();
    }

    public QuotaModel getQuota(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QUOTAS, QUOTAS_COLUMNS, " id = ?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        QuotaModel quota = new QuotaModel(cursor.getInt(0),
                cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                cursor.getString(4), cursor.getString(5), cursor.getInt(6));

        Log.d("getQuota("+id+")", quota.toString());
        return quota;
    }

    public List<QuotaModel> getAllQuotas() {
        List<QuotaModel> quotas = new LinkedList<QuotaModel>();

        String query = "SELECT * FROM " + TABLE_QUOTAS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        QuotaModel quota = null;
        if (cursor.moveToFirst()) {
            do {
                quota = getQuota(cursor.getInt(0));

                quotas.add(quota);
            } while (cursor.moveToNext());
        }
        return quotas;
    }

    public int updateQuota(QuotaModel quota) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", quota.getTitle());
        values.put("description", quota.getDescription());

        int i = db.update(TABLE_QUOTAS,
                values,
                COLUMN_ID+" = ?",
                new String[] {String.valueOf(quota.getId())});

        db.close();
        return i;
    }

    public void deleteQuota(QuotaModel quota) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_QUOTAS,
                COLUMN_ID+" = ?",
                new String[] {String.valueOf(quota.getId())});

        db.close();
    }
}
