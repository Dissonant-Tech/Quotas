package com.dissonant.quotas.db;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dissonant.quotas.model.QuotaModel;

public class QuotaDao implements GenericDao<QuotaModel, Long> {
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] columns;

    boolean isOpen = false;

    public QuotaDao(Context context) {
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

    @Override
    public void add(QuotaModel quota) {
        Log.d("addQouta: ", quota.toString());

        if (!isOpen)
            this.open();

        ContentValues values = QuotaToValues(quota);
        database.insert(dbHelper.TABLE_QUOTAS, null, values);

        if (isOpen)
            this.close();
    }

    @Override
    public QuotaModel get(Long id) {
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

    @Override
    public List<QuotaModel> list() {
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

    @Override
    public Long update(QuotaModel quota) {
        ContentValues values = QuotaToValues(quota);

        int i = database.update(DBHelper.TABLE_QUOTAS,
                values,
                DBHelper.COLUMN_ID+" = ?",
                new String[] {String.valueOf(quota.getId())});

        return Long.valueOf(i);
    }

    @Override
    public void remove(QuotaModel quota) {
        database.delete(DBHelper.TABLE_QUOTAS,
                DBHelper.COLUMN_ID+" = ?",
                new String[] {String.valueOf(quota.getId())});

    }

    private QuotaModel cursorToQuota(Cursor cursor) {
        QuotaModel quota = new QuotaModel(Long.valueOf(cursor.getInt(0)),
                cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                cursor.getLong(4), cursor.getLong(5), cursor.getInt(6));

        return quota;
    }

    private ContentValues QuotaToValues(QuotaModel quota) {
        ContentValues values = new ContentValues();

        values.put(dbHelper.COLUMN_TITLE, quota.getTitle());
        values.put(dbHelper.COLUMN_DESCRIPTION, quota.getDescription());
        values.put(dbHelper.COLUMN_REPEAT, quota.getRepeat());
        values.put(dbHelper.COLUMN_ISACTIVE, quota.getIsActive());
        values.put(dbHelper.COLUMN_STARTTIME, quota.getStartTime());
        values.put(dbHelper.COLUMN_ENDTIME, quota.getEndTime());

        return values;
    }
}
