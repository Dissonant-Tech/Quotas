package com.dissonant.quotas.db;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dissonant.quotas.model.QuotaModel;

public class QuotaAccess implements DBAcessInterface<QuotaModel> {
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] columns;

    boolean isOpen = false;

    public QuotaAccess(Context context) {
        dbHelper = new DBHelper(context);
        columns = dbHelper.getQuotaColumns();
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        this.isOpen = true;
    }

    public void close() {
        dbHelper.close();
        this.isOpen = false;
    }

    public void add(QuotaModel quota) {
        Log.d("addQouta: ", quota.toString());

        if (!isOpen)
            this.open();

        ContentValues values = QuotaToValues(quota);
        database.insert(dbHelper.TABLE_QUOTAS, null, values);

        if (isOpen)
            this.close();
    }

    public QuotaModel get(int id) {
        Cursor cursor = database.query(DBHelper.TABLE_QUOTAS,
                columns, DBHelper.COLUMN_ID + " = " + id,
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        QuotaModel quota = cursorToQuota(cursor);

        Log.d("getQuota("+id+")", quota.toString());
        return quota;
    }

    public List<QuotaModel> getAll() {
        List<QuotaModel> quotas = new LinkedList<QuotaModel>();

        String query = "SELECT * FROM " + DBHelper.TABLE_QUOTAS;

        Cursor cursor = database.rawQuery(query, null);

        QuotaModel quota = null;
        if (cursor.moveToFirst()) {
            do {
                quota = cursorToQuota(cursor);

                quotas.add(quota);
            } while (cursor.moveToNext());
        }
        return quotas;
    }

    public int update(QuotaModel quota) {
        ContentValues values = QuotaToValues(quota);

        int i = database.update(DBHelper.TABLE_QUOTAS,
                values,
                DBHelper.COLUMN_ID+" = ?",
                new String[] {String.valueOf(quota.getId())});

        return i;
    }

    public void del(QuotaModel quota) {
        database.delete(DBHelper.TABLE_QUOTAS,
                DBHelper.COLUMN_ID+" = ?",
                new String[] {String.valueOf(quota.getId())});

    }

    private QuotaModel cursorToQuota(Cursor cursor) {
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();

        startTime.setTimeInMillis(cursor.getInt(4));
        endTime.setTimeInMillis(cursor.getInt(4));

        QuotaModel quota = new QuotaModel(cursor.getInt(0),
                cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                startTime, endTime, cursor.getInt(6));

        return quota;
    }

    private ContentValues QuotaToValues(QuotaModel quota) {
        ContentValues values = new ContentValues();

        values.put(dbHelper.COLUMN_TITLE, quota.getTitle());
        values.put(dbHelper.COLUMN_DESCRIPTION, quota.getDescription());
        values.put(dbHelper.COLUMN_REPEAT, quota.getRepeat());
        values.put(dbHelper.COLUMN_ISACTIVE, quota.getIsActive());

        values.put(dbHelper.COLUMN_STARTTIME,
                String.valueOf(quota.getStartTime().getTimeInMillis()));
        values.put(dbHelper.COLUMN_ENDTIME,
                String.valueOf(quota.getEndTime().getTimeInMillis()));

        return values;
    }
}
