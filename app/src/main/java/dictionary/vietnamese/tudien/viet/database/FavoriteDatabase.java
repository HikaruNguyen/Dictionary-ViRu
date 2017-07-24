
package dictionary.vietnamese.tudien.viet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dictionary.vietnamese.tudien.viet.NextDictUtils.CLog;
import dictionary.vietnamese.tudien.viet.model.db.DictWord;


public class FavoriteDatabase {
    private SQLiteDatabase database;
    private FavoriteMySQLiteHelper dbHelper;
    private String[] allColumns = {
            FavoriteMySQLiteHelper.FAVORITE_ID,
            FavoriteMySQLiteHelper.FAVORITE_WORD,
            FavoriteMySQLiteHelper.FAVORITE_CREAT_AT,
            FavoriteMySQLiteHelper.FAVORITE_UPDATE_AT
    };

    public FavoriteDatabase(Context context) {
        dbHelper = new FavoriteMySQLiteHelper(context);
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
        InsertHelper insertHelper = new InsertHelper(database, FavoriteMySQLiteHelper.TABLE_FAVORITE);

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
                .getColumnIndex(FavoriteMySQLiteHelper.FAVORITE_ID);
        int column_name = insertHelper
                .getColumnIndex(FavoriteMySQLiteHelper.FAVORITE_WORD);
        long dtMili = System.currentTimeMillis();
        int column_creat_at = insertHelper
                .getColumnIndex(FavoriteMySQLiteHelper.FAVORITE_CREAT_AT);
        int column_update_at = insertHelper
                .getColumnIndex(FavoriteMySQLiteHelper.FAVORITE_UPDATE_AT);
        insertHelper.bind(column_name, dictWord.getWord());
        insertHelper.bind(column_creat_at, dtMili + "");
        insertHelper.bind(column_update_at, dtMili + "");

        insertHelper.execute();
    }

    public boolean addWordToFavorite(DictWord managerDict) {
        ContentValues values = new ContentValues();
        long dtMili = System.currentTimeMillis();
        values.put(FavoriteMySQLiteHelper.FAVORITE_WORD,
                managerDict.getWord());
        values.put(FavoriteMySQLiteHelper.FAVORITE_CREAT_AT,
                dtMili + "");
        values.put(FavoriteMySQLiteHelper.FAVORITE_UPDATE_AT,
                dtMili + "");
        long insertId = database.insert(FavoriteMySQLiteHelper.TABLE_FAVORITE, null,
                values);

        if (insertId != -1)
            return true;
        else
            return false;
    }

    public boolean addWordToFavorite(String word) {
        ContentValues values = new ContentValues();
        long dtMili = System.currentTimeMillis();
        values.put(FavoriteMySQLiteHelper.FAVORITE_WORD,
                word);
        values.put(FavoriteMySQLiteHelper.FAVORITE_CREAT_AT,
                dtMili + "");
        values.put(FavoriteMySQLiteHelper.FAVORITE_UPDATE_AT,
                dtMili + "");
        long insertId = database.insert(FavoriteMySQLiteHelper.TABLE_FAVORITE, null,
                values);
        if (insertId != -1)
            return true;
        else
            return false;
    }

    public boolean delFavroritebyWord(DictWord dictWord) {
        ContentValues values = new ContentValues();
//        values.put(FavoriteMySQLiteHelper.MANAGER_DICT_ID,
//                managerDict.getDictName());
        values.put(FavoriteMySQLiteHelper.FAVORITE_WORD,
                dictWord.getWord());
        String word = dictWord.getWord();
        if (word.contains("'")) {
            word = word.replaceAll("'", "''");
        }
        long insertId = database.delete(FavoriteMySQLiteHelper.TABLE_FAVORITE, FavoriteMySQLiteHelper.FAVORITE_WORD + " ='" + word + "'",
                null);

        if (insertId != -1)
            return true;
        else
            return false;
    }

    public boolean delFavroritebyWord(String dictWord) {
        if (dictWord.contains("'")) {
            dictWord = dictWord.replaceAll("'", "''");
        }
        long insertId = database.delete(FavoriteMySQLiteHelper.TABLE_FAVORITE, FavoriteMySQLiteHelper.FAVORITE_WORD + " ='" + dictWord + "'",
                null);

        if (insertId != -1)
            return true;
        else
            return false;
    }

    public boolean deleteAll() {
        int number = database.delete(FavoriteMySQLiteHelper.TABLE_FAVORITE, null, null);
        if (number > 0)
            return true;
        else
            return false;
    }

    public boolean isExistWord(String word) {
        if (word == null) return false;
        word = word.trim();
        if (word.contains("'"))
            word = word.replaceAll("'", "''");
        Cursor cursor = database.query(FavoriteMySQLiteHelper.TABLE_FAVORITE,
                allColumns, FavoriteMySQLiteHelper.FAVORITE_WORD + " = '" + word + "' ", null, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        } else
            return false;
    }

    public boolean updateWord(String word) {
        ContentValues values = new ContentValues();
        long dtMili = System.currentTimeMillis();
        values.put(FavoriteMySQLiteHelper.FAVORITE_UPDATE_AT, dtMili + "");

        long insertId = database.update(FavoriteMySQLiteHelper.TABLE_FAVORITE, values,
                FavoriteMySQLiteHelper.FAVORITE_WORD + " = '" + word + "'", null);

        if (insertId > 0)
            return true;
        else
            return false;
    }

    public List<DictWord> getAll() {
        List<DictWord> dictWords = new ArrayList<DictWord>();

        Cursor cursor = database.query(FavoriteMySQLiteHelper.TABLE_FAVORITE,
                allColumns, null, null, null, null,
                FavoriteMySQLiteHelper.FAVORITE_UPDATE_AT + " DESC");

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
        word = word.replaceAll("'", "\'");
        Cursor cursor = database.query(FavoriteMySQLiteHelper.TABLE_FAVORITE,
                allColumns, FavoriteMySQLiteHelper.FAVORITE_WORD + " = '" + word
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
}
