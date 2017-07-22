
package dictionary.viet.russia.viru.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dictionary.viet.russia.viru.NextDictUtils.CLog;
import dictionary.viet.russia.viru.model.db.DictWord;


public class HistoryDatabase {
    private SQLiteDatabase database;
    private HistoryMySQLiteHelper dbHelper;
    private String[] allColumns = {
            HistoryMySQLiteHelper.HISTORY_ID,
            HistoryMySQLiteHelper.HISTORY_WORD,
            HistoryMySQLiteHelper.HISTORY_CREAT_AT,
            HistoryMySQLiteHelper.HISTORY_UPDATE_AT
    };

    public HistoryDatabase(Context context) {
        dbHelper = new HistoryMySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    @SuppressWarnings("deprecation")
    public void addAllHistory(List<DictWord> list) throws Exception {
        database.beginTransaction();
        boolean check = false;
        InsertHelper insertHelper = new InsertHelper(database, HistoryMySQLiteHelper.TABLE_HISTORY);

        for (DictWord obj : list) {
            check = checkExistsDict(obj);
            if (!check) {
                newAddDict(insertHelper, obj);
            }
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    @SuppressWarnings("deprecation")
    public void newAddDict(InsertHelper insertHelper, DictWord dictWord) {
        if (dictWord == null) {
            return;
        }
        insertHelper.prepareForInsert();
        int column_id = insertHelper
                .getColumnIndex(HistoryMySQLiteHelper.HISTORY_ID);
        int column_name = insertHelper
                .getColumnIndex(HistoryMySQLiteHelper.HISTORY_WORD);
        long dtMili = System.currentTimeMillis();
        int column_creat_at = insertHelper
                .getColumnIndex(HistoryMySQLiteHelper.HISTORY_CREAT_AT);
        int column_update_at = insertHelper
                .getColumnIndex(HistoryMySQLiteHelper.HISTORY_UPDATE_AT);
//        insertHelper.bind(column_id, managerDict.getDictName());
        insertHelper.bind(column_name, dictWord.getWord());
        insertHelper.bind(column_creat_at, dtMili + "");
        insertHelper.bind(column_update_at, dtMili + "");

        insertHelper.execute();
    }

    public boolean addWordToHistory(DictWord managerDict) {
        ContentValues values = new ContentValues();
//        values.put(HistoryMySQLiteHelper.MANAGER_DICT_ID,
//                managerDict.getDictName());
        long dtMili = System.currentTimeMillis();
        values.put(HistoryMySQLiteHelper.HISTORY_WORD,
                managerDict.getWord());
        values.put(HistoryMySQLiteHelper.HISTORY_CREAT_AT,
                dtMili + "");
        values.put(HistoryMySQLiteHelper.HISTORY_UPDATE_AT,
                dtMili + "");
        long insertId = database.insert(HistoryMySQLiteHelper.TABLE_HISTORY, null,
                values);

        if (insertId != -1)
            return true;
        else
            return false;
    }

    public boolean addWordToHistory(String word) {
        ContentValues values = new ContentValues();
        long dtMili = System.currentTimeMillis();
        values.put(HistoryMySQLiteHelper.HISTORY_WORD,
                word);
        values.put(HistoryMySQLiteHelper.HISTORY_CREAT_AT,
                dtMili + "");
        values.put(HistoryMySQLiteHelper.HISTORY_UPDATE_AT,
                dtMili + "");
        long insertId = database.insert(HistoryMySQLiteHelper.TABLE_HISTORY, null,
                values);
        if (insertId != -1)
            return true;
        else
            return false;
    }

    public boolean delHistorybyWord(DictWord dictWord) {
        ContentValues values = new ContentValues();
//        values.put(HistoryMySQLiteHelper.MANAGER_DICT_ID,
//                managerDict.getDictName());
        String word = dictWord.getWord();
        if (word.contains("'")) {
            word = word.replaceAll("'", "''");
        }
        values.put(HistoryMySQLiteHelper.HISTORY_WORD,
                word);
        long insertId = database.delete(HistoryMySQLiteHelper.TABLE_HISTORY, HistoryMySQLiteHelper.HISTORY_WORD + " ='" + word + "'",
                null);

        if (insertId != -1)
            return true;
        else
            return false;
    }


    public boolean deleteAll() {
        int number = database.delete(HistoryMySQLiteHelper.TABLE_HISTORY, null, null);
        if (number > 0)
            return true;
        else
            return false;
    }

    public boolean isExistWord(String word) {
        if (word.contains("'")) {
            word = word.replaceAll("'", "''");
        }
        Cursor cursor = database.query(HistoryMySQLiteHelper.TABLE_HISTORY,
                allColumns, HistoryMySQLiteHelper.HISTORY_WORD + " = '" + word + "' ", null, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        } else
            return false;
    }

    public boolean updateWord(String word) {
        ContentValues values = new ContentValues();
        long dtMili = System.currentTimeMillis();
        values.put(HistoryMySQLiteHelper.HISTORY_UPDATE_AT, dtMili + "");
        if (word.contains("'")) {
            word = word.replaceAll("'", "''");
        }
        long insertId = database.update(HistoryMySQLiteHelper.TABLE_HISTORY, values,
                HistoryMySQLiteHelper.HISTORY_WORD + " = '" + word + "'", null);

        if (insertId > 0)
            return true;
        else
            return false;
    }

    public List<DictWord> getAll() {

        List<DictWord> dictWords = new ArrayList<DictWord>();

        Cursor cursor = database.query(HistoryMySQLiteHelper.TABLE_HISTORY,
                allColumns, null, null, null, null,
                HistoryMySQLiteHelper.HISTORY_UPDATE_AT + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DictWord listLiveChannelForm = cursorToLive(cursor);
            dictWords.add(listLiveChannelForm);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return dictWords;
    }

    public boolean checkExistsDict(DictWord managerDict) {
        if (managerDict == null)
            return true;
        String word = managerDict.getWord();

        Cursor cursor = database.query(HistoryMySQLiteHelper.TABLE_HISTORY,
                allColumns, HistoryMySQLiteHelper.HISTORY_WORD + " = '" + word
                        + "'", null, null, null, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }


    private DictWord cursorToLive(Cursor cursor) {
        DictWord dictWord = new DictWord();
        dictWord.setWord(cursor.getString(1));
        CLog.d("", "get word: " + dictWord.getWord());
        return dictWord;
    }

    public int getCountHistory() {
        String sql = "SELECT * FROM " + HistoryMySQLiteHelper.TABLE_HISTORY;
        Cursor cursor = database.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
