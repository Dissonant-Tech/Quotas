package com.dissonant.quotas.db;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class QuotaAccess {
    private SQLiteDatabase database;
    private QuotasSQLiteHelper dbHelper;
    private String[] columns;

    public QuotaAccess(Context context) {
        dbHelper = new QuotasSQLiteHelper(context);
        columns = dbHelper.getQuotaColumns();
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addQuota(QuotaModel quota) {
        Log.d("addQouta: ", quota.toString());

        ContentValues values = QuotaToValues(quota);

        database.insert(dbHelper.TABLE_QUOTAS, null, values);
    }

    public QuotaModel getQuota(int id) {
        Cursor cursor = database.query(QuotasSQLiteHelper.TABLE_QUOTAS,
                columns, QuotasSQLiteHelper.COLUMN_ID + " = " + id,
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        QuotaModel quota = cursorToQuota(cursor);

        Log.d("getQuota("+id+")", quota.toString());
        return quota;
    }

    public List<QuotaModel> getAllQuotas() {
        List<QuotaModel> quotas = new LinkedList<QuotaModel>();

        String query = "SELECT * FROM " + QuotasSQLiteHelper.TABLE_QUOTAS;

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

    public int updateQuota(QuotaModel quota) {
        ContentValues values = QuotaToValues(quota);

        int i = database.update(QuotasSQLiteHelper.TABLE_QUOTAS,
                values,
                QuotasSQLiteHelper.COLUMN_ID+" = ?",
                new String[] {String.valueOf(quota.getId())});

        return i;
    }

    public void deleteQuota(QuotaModel quota) {
        database.delete(QuotasSQLiteHelper.TABLE_QUOTAS,
                QuotasSQLiteHelper.COLUMN_ID+" = ?",
                new String[] {String.valueOf(quota.getId())});

    }

    private QuotaModel cursorToQuota(Cursor cursor) {
        QuotaModel quota = new QuotaModel(cursor.getInt(0),
                cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                cursor.getString(4), cursor.getString(5), cursor.getInt(6));

        return quota;
    }

    private ContentValues QuotaToValues(QuotaModel quota) {
        ContentValues values = new ContentValues();

        values.put(dbHelper.COLUMN_TITLE, quota.getTitle());
        values.put(dbHelper.COLUMN_DESCRIPTION, quota.getDescription());
        values.put(dbHelper.COLUMN_REPEAT, quota.getRepeat());
        values.put(dbHelper.COLUMN_STARTTIME, quota.getStartTime().toString());
        values.put(dbHelper.COLUMN_ENDTIME, quota.getEndTime().toString());
        values.put(dbHelper.COLUMN_ISACTIVE, quota.getIsActive());
        
        return values;
    }
}
