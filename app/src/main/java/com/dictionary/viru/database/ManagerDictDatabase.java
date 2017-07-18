
package com.dictionary.viru.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dictionary.viru.model.db.ManagerDict;

import java.util.ArrayList;
import java.util.List;


public class ManagerDictDatabase {
    private SQLiteDatabase database;
    private ManagerDictMySQLiteHelper dbHelper;
    private String[] allColumns = {
            ManagerDictMySQLiteHelper.MANAGER_DICT,
            ManagerDictMySQLiteHelper.MANAGER_DICT_ID,
            ManagerDictMySQLiteHelper.MANAGER_DICT_NAME,
            ManagerDictMySQLiteHelper.MANAGER_DICT_PATH,
            ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED,
            ManagerDictMySQLiteHelper.MANAGER_DICT_CREAT_AT,
            ManagerDictMySQLiteHelper.MANAGER_DICT_UPDATE_AT,
            ManagerDictMySQLiteHelper.MANAGER_DICT_SORT
    };

    public ManagerDictDatabase(Context context) {
        dbHelper = new ManagerDictMySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    @SuppressWarnings("deprecation")
    public void addAllDict(List<ManagerDict> list) throws Exception {
        database.beginTransaction();
        boolean check = false;
        InsertHelper insertHelper = new InsertHelper(database, ManagerDictMySQLiteHelper.TABLE_DICT);

        for (ManagerDict obj : list) {
            check = checkExistsDict(obj);
            if (!check) {
                newAddDict(insertHelper, obj);
            }
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    @SuppressWarnings("deprecation")
    public void newAddDict(InsertHelper insertHelper, ManagerDict managerDict) {
        if (managerDict == null) {
            return;
        }
        insertHelper.prepareForInsert();
        int column_id = insertHelper
                .getColumnIndex(ManagerDictMySQLiteHelper.MANAGER_DICT_ID);
        int column_name = insertHelper
                .getColumnIndex(ManagerDictMySQLiteHelper.MANAGER_DICT_NAME);
        int column_path = insertHelper
                .getColumnIndex(ManagerDictMySQLiteHelper.MANAGER_DICT_PATH);
        int column_check = insertHelper
                .getColumnIndex(ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED);
        int column_creat = insertHelper
                .getColumnIndex(ManagerDictMySQLiteHelper.MANAGER_DICT_CREAT_AT);
        int column_update = insertHelper
                .getColumnIndex(ManagerDictMySQLiteHelper.MANAGER_DICT_UPDATE_AT);
        long time = System.currentTimeMillis();
        insertHelper.bind(column_id, managerDict.getDictId());
        insertHelper.bind(column_name, managerDict.getDictName());
        insertHelper.bind(column_path, managerDict.getDictPath());
        insertHelper.bind(column_check, managerDict.getIsChecked());
        insertHelper.bind(column_creat, time + "");
        insertHelper.bind(column_update, time + "");
        insertHelper.execute();
    }

    public boolean addDict(ManagerDict managerDict) {
        ContentValues values = new ContentValues();
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_ID,
                managerDict.getDictId());
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_NAME,
                managerDict.getDictName());
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_PATH,
                managerDict.getDictPath());
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED,
                managerDict.getIsChecked());
        long time = System.currentTimeMillis();
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_CREAT_AT, time + "");
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_UPDATE_AT, time + "");
        long insertId = database.insert(ManagerDictMySQLiteHelper.TABLE_DICT, null,
                values);

        if (insertId != -1)
            return true;
        else
            return false;
    }

    public boolean addDict(ManagerDict managerDict, int position) {
        ContentValues values = new ContentValues();
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_ID,
                managerDict.getDictId());
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_NAME,
                managerDict.getDictName());
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_PATH,
                managerDict.getDictPath());
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED,
                managerDict.getIsChecked());
        long time = System.currentTimeMillis();
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_CREAT_AT, time + "");
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_UPDATE_AT, time + "");
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_SORT, position);
        long insertId = database.insert(ManagerDictMySQLiteHelper.TABLE_DICT, null,
                values);

        if (insertId != -1)
            return true;
        else
            return false;
    }

    public boolean addDict(ManagerDict managerDict, boolean isCheck) {
        ContentValues values = new ContentValues();
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_ID,
                managerDict.getDictId());
        int check = isCheck ? 1 : 0;
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_NAME,
                managerDict.getDictName());
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_PATH,
                managerDict.getDictPath());
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED,
                managerDict.getIsChecked());
        long time = System.currentTimeMillis();
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_CREAT_AT, time + "");
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_UPDATE_AT, time + "");
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED, check + "");
        long insertId = database.insert(ManagerDictMySQLiteHelper.TABLE_DICT, null,
                values);

        if (insertId != -1)
            return true;
        else
            return false;
    }

    public boolean delDict(ManagerDict managerDict) {
        ContentValues values = new ContentValues();
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_ID,
                managerDict.getDictId());
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_NAME,
                managerDict.getDictName());
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_PATH,
                managerDict.getDictPath());
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED,
                managerDict.getIsChecked());
        long insertId = database.delete(ManagerDictMySQLiteHelper.TABLE_DICT, ManagerDictMySQLiteHelper.MANAGER_DICT_ID + " ='" + managerDict.getDictId() + "'",
                null);

        if (insertId != -1)
            return true;
        else
            return false;
    }


    public boolean deleteAll() {
        int number = database.delete(ManagerDictMySQLiteHelper.TABLE_DICT, null, null);
        if (number > 0)
            return true;
        else
            return false;
    }

    public List<ManagerDict> getAllDictIfChecked() {
        String where = ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED + " =1";

        List<ManagerDict> likes = new ArrayList<ManagerDict>();

        Cursor cursor = database.query(ManagerDictMySQLiteHelper.TABLE_DICT,
                allColumns, null, null, null, null,
                ManagerDictMySQLiteHelper.MANAGER_DICT_SORT + " ASC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ManagerDict listLiveChannelForm = cursorToLive(cursor);
            likes.add(listLiveChannelForm);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return likes;
    }

    public boolean checkExistsDict(ManagerDict managerDict) {
        if (managerDict == null)
            return true;
        String id = managerDict.getDictId();

        Cursor cursor = database.query(ManagerDictMySQLiteHelper.TABLE_DICT,
                allColumns, ManagerDictMySQLiteHelper.MANAGER_DICT_ID + " = '" + id
                        + "'", null, null, null, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }

    public boolean checkExistsDict(String id) {
        if (id == null)
            return true;
        Cursor cursor = database.query(ManagerDictMySQLiteHelper.TABLE_DICT,
                allColumns, ManagerDictMySQLiteHelper.MANAGER_DICT_ID + " = '" + id
                        + "'", null, null, null, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }

    public boolean checDictIsCheck(ManagerDict managerDict) {
        int isChecked = managerDict.getIsChecked();
        String id = managerDict.getDictId();
        Cursor cursor = database.query(ManagerDictMySQLiteHelper.TABLE_DICT,
                allColumns, ManagerDictMySQLiteHelper.MANAGER_DICT_ID + " = '" + id + "' AND " + ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED
                        + " = 1", null, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        } else
            return false;

    }

    public boolean checDictIsCheck(String id) {
        Cursor cursor = database.query(ManagerDictMySQLiteHelper.TABLE_DICT,
                allColumns, ManagerDictMySQLiteHelper.MANAGER_DICT_ID + " = '" + id + "' AND " + ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED
                        + " = 1", null, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        } else
            return false;

    }

    public boolean addChecked(String id, int isChecked) {
        ContentValues values = new ContentValues();

        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED, isChecked);

        long insertId = database.update(ManagerDictMySQLiteHelper.TABLE_DICT, values,
                ManagerDictMySQLiteHelper.MANAGER_DICT_ID + " = '" + id + "'", null);

        if (insertId > 0)
            return true;
        else
            return false;
    }

    public boolean addNumberSort(String id, int position) {
        ContentValues values = new ContentValues();

        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_SORT, position);

        long insertId = database.update(ManagerDictMySQLiteHelper.TABLE_DICT, values,
                ManagerDictMySQLiteHelper.MANAGER_DICT_ID + " = '" + id + "'", null);

        if (insertId > 0)
            return true;
        else
            return false;
    }

    private ManagerDict cursorToLive(Cursor cursor) {
        ManagerDict managerDict = new ManagerDict(cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getInt(4));
        return managerDict;
    }

    public int getCountDictExist() {
        String sql = "SELECT * FROM " + ManagerDictMySQLiteHelper.TABLE_DICT;
        Cursor cursor = database.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getCountDictChecked() {
        String sql = "SELECT * FROM " + ManagerDictMySQLiteHelper.TABLE_DICT + " WHERE " + ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED + "=1";
        Cursor cursor = database.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
