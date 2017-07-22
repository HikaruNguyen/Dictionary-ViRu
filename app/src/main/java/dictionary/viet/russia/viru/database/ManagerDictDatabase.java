
package dictionary.viet.russia.viru.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import dictionary.viet.russia.viru.model.resultApi.ListDictResult;

import java.util.ArrayList;
import java.util.List;


public class ManagerDictDatabase {
    private SQLiteDatabase database;
    private ManagerDictMySQLiteHelper dbHelper;
    private String[] allColumns = {
            ManagerDictMySQLiteHelper.MANAGER_DICT,
            ManagerDictMySQLiteHelper.MANAGER_DICT_ID,
            ManagerDictMySQLiteHelper.MANAGER_DICT_NAME,
            ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED,
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
    public void addAllDict(List<ListDictResult.ListDictInfo> list) throws Exception {
        database.beginTransaction();
        boolean check = false;
        InsertHelper insertHelper = new InsertHelper(database, ManagerDictMySQLiteHelper.TABLE_DICT);

        for (ListDictResult.ListDictInfo obj : list) {
            check = checkExistsDict(obj);
            if (!check) {
                newAddDict(insertHelper, obj);
            }
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    @SuppressWarnings("deprecation")
    public void newAddDict(InsertHelper insertHelper, ListDictResult.ListDictInfo listDictInfo) {
        if (listDictInfo == null) {
            return;
        }
        insertHelper.prepareForInsert();
        int column_id = insertHelper
                .getColumnIndex(ManagerDictMySQLiteHelper.MANAGER_DICT_ID);
        int column_name = insertHelper
                .getColumnIndex(ManagerDictMySQLiteHelper.MANAGER_DICT_NAME);
        int column_check = insertHelper
                .getColumnIndex(ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED);
        long time = System.currentTimeMillis();
        insertHelper.bind(column_id, listDictInfo.id);
        insertHelper.bind(column_name, listDictInfo.name);
        insertHelper.bind(column_check, listDictInfo.isChecked);
        insertHelper.execute();
    }

    public boolean addDict(ListDictResult.ListDictInfo listDictInfo) {
        ContentValues values = new ContentValues();
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_ID,
                listDictInfo.id);
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_NAME,
                listDictInfo.name);
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED,
                listDictInfo.isChecked);
        long insertId = database.insert(ManagerDictMySQLiteHelper.TABLE_DICT, null,
                values);

        if (insertId != -1)
            return true;
        else
            return false;
    }

    public boolean addDict(ListDictResult.ListDictInfo listDictInfo, int position) {
        ContentValues values = new ContentValues();
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_ID,
                listDictInfo.id);
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_NAME,
                listDictInfo.name);
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED,
                listDictInfo.isChecked);
        long insertId = database.insert(ManagerDictMySQLiteHelper.TABLE_DICT, null,
                values);

        if (insertId != -1)
            return true;
        else
            return false;
    }

    public boolean addDict(ListDictResult.ListDictInfo listDictInfo, boolean isCheck) {
        ContentValues values = new ContentValues();
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_ID,
                listDictInfo.id);
        int check = isCheck ? 1 : 0;
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_NAME,
                listDictInfo.name);
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED,
                check);
        long insertId = database.insert(ManagerDictMySQLiteHelper.TABLE_DICT, null,
                values);

        if (insertId != -1)
            return true;
        else
            return false;
    }

    public boolean delDict(ListDictResult.ListDictInfo listDictInfo) {
        ContentValues values = new ContentValues();
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_ID,
                listDictInfo.id);
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_NAME,
                listDictInfo.name);
        values.put(ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED,
                listDictInfo.isChecked);
        long insertId = database.delete(
                ManagerDictMySQLiteHelper.TABLE_DICT,
                ManagerDictMySQLiteHelper.MANAGER_DICT_ID + " ='" + listDictInfo.id + "'",
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

    public List<ListDictResult.ListDictInfo> getAllDict() {

        List<ListDictResult.ListDictInfo> likes = new ArrayList<>();

        Cursor cursor = database.query(ManagerDictMySQLiteHelper.TABLE_DICT,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ListDictResult.ListDictInfo listLiveChannelForm = cursorToLive(cursor);
            likes.add(listLiveChannelForm);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return likes;
    }

    public List<ListDictResult.ListDictInfo> getAllDictIfChecked() {
        String where = ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED + " =1";

        List<ListDictResult.ListDictInfo> likes = new ArrayList<>();

        Cursor cursor = database.query(ManagerDictMySQLiteHelper.TABLE_DICT,
                allColumns, where, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ListDictResult.ListDictInfo listLiveChannelForm = cursorToLive(cursor);
            likes.add(listLiveChannelForm);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return likes;
    }

    public ListDictResult.ListDictInfo getDictIfChecked() {
        String where = ManagerDictMySQLiteHelper.MANAGER_DICT_CHECKED + " =1";

        List<ListDictResult.ListDictInfo> likes = new ArrayList<>();

        Cursor cursor = database.query(ManagerDictMySQLiteHelper.TABLE_DICT,
                allColumns, where, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ListDictResult.ListDictInfo listLiveChannelForm = cursorToLive(cursor);
            likes.add(listLiveChannelForm);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        if (likes == null || likes.size() <= 0) {
            return null;
        }
        return likes.get(0);
    }

    public boolean checkExistsDict(ListDictResult.ListDictInfo info) {
        if (info == null)
            return false;
        String id = info.id;

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

    public boolean checDictIsCheck(ListDictResult.ListDictInfo listDictInfo) {
        String id = listDictInfo.id;
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

    private ListDictResult.ListDictInfo cursorToLive(Cursor cursor) {
        ListDictResult.ListDictInfo managerDict = new ListDictResult.ListDictInfo(cursor.getString(1), cursor.getString(2), cursor.getInt(3));
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
