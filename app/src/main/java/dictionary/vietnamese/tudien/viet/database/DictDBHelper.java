package dictionary.vietnamese.tudien.viet.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import dictionary.vietnamese.tudien.viet.NextDictUtils.CLog;
import dictionary.vietnamese.tudien.viet.NextDictUtils.WordHash;
import dictionary.vietnamese.tudien.viet.model.db.DictWord;

import static dictionary.vietnamese.tudien.viet.database.DictInfoDBHelper.DB_PATH;

/**
 * Created by manhi on 5/1/5016.
 */
public class DictDBHelper {
    public static final String TAG = DictDBHelper.class.getSimpleName();
    public static String fieldObjectId = "id";
    public static String fieldObjectWord = "word";
    public static String fieldObjectDefinition = "definition";

    public static boolean isCharNormal(String a) {
        if (a.matches("[a-zA-Z.? ]*")) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<DictWord> filterWord(String dbName, String word, boolean isLike, int type) {
        if (word.contains("'")) {
            word = word.replaceAll("'", "''");
        }
        if (word != null)
            word = word.toLowerCase();
        ArrayList<DictWord> arr = new ArrayList<>();
        if (word.length() > 0) {
            String first_word = WordHash.hash(word.charAt(0)) + "";
            SQLiteDatabase database;

            database = SQLiteDatabase.openDatabase(DB_PATH + dbName + ".ndf", null, SQLiteDatabase.CREATE_IF_NECESSARY);
            Cursor cursor;
            String tableName;
            String sql = "";
            if (word.trim().isEmpty()) {
                sql += "SELECT * FROM a_t";
                sql += " WHERE LOWER(" + fieldObjectWord + ") LIKE '" + word + "%'";
                sql += " ORDER BY " + fieldObjectId + " ASC";
                sql += " LIMIT " + 50;
            } else {
                tableName = first_word + "_t";
                if (isLike) {
                    sql += "SELECT * FROM " + tableName;
                    sql += " WHERE LOWER(" + fieldObjectWord + ") LIKE '" + word + "%'";
                    sql += " ORDER BY " + fieldObjectId + " ASC";
                    sql += " LIMIT " + 50;
                } else {
                    sql += "SELECT * FROM " + tableName;
                    sql += " WHERE LOWER(" + fieldObjectWord + ") = '" + word + "'";
                    sql += " ORDER BY " + fieldObjectId + " ASC";
                    sql += " LIMIT " + 50;
                }

            }
            try {
                cursor = database.rawQuery(sql, null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    if (type == 1) {
                        try {
                            arr.add(new DictWord(cursor.getInt(0), cursor.getString(1), new String(cursor.getBlob(2), "UTF-8")));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        arr.add(new DictWord(cursor.getInt(0), cursor.getString(1), cursor.getBlob(2)));
                    }
                    cursor.moveToNext();
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            CLog.d(TAG, "array dict: " + arr.size());
        }

//        if (word.length() > 0 && isCharNormal(word.charAt(0) + "")) {
//
//
//        }

        return arr;
    }

    public static ArrayList<DictWord> filterWord(String dbName, String word, boolean isLike, int limit, int type) {
        if (word.contains("'")) {
            word = word.replaceAll("'", "''");
        }
        if (word != null)
            word = word.toLowerCase();
        ArrayList<DictWord> arr = new ArrayList<>();
        if (word.length() > 0) {
            String first_word = WordHash.hash(word.charAt(0)) + "";
//            DictDatabaseOpenHelper adb = new DictDatabaseOpenHelper(context);
//            SQLiteDatabase database = adb.StoreDatabase(dbName + ".ndf");
            SQLiteDatabase database;
            database = SQLiteDatabase.openDatabase(DB_PATH + dbName + ".ndf", null, SQLiteDatabase.CREATE_IF_NECESSARY);
            Cursor cursor;
            String tableName;
            String sql = "";
            if (word.trim().isEmpty()) {
                sql += "SELECT * FROM a_t";
                sql += " WHERE LOWER(" + fieldObjectWord + ") LIKE '" + word + "%'";
                sql += " ORDER BY " + fieldObjectId + " ASC";
                sql += " LIMIT " + limit;
            } else {
                tableName = first_word + "_t";
                if (isLike) {
                    sql += "SELECT * FROM " + tableName;
                    sql += " WHERE LOWER(" + fieldObjectWord + ") LIKE '" + word + "%'";
                    sql += " ORDER BY " + fieldObjectId + " ASC";
                    sql += " LIMIT " + limit;
                } else {
                    sql += "SELECT * FROM " + tableName;
                    sql += " WHERE LOWER(" + fieldObjectWord + ") = '" + word + "'";
                    sql += " ORDER BY " + fieldObjectId + " ASC";
                    sql += " LIMIT " + limit;
                }

            }
            try {
                cursor = database.rawQuery(sql, null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    if (type == 1) {
                        try {
                            arr.add(new DictWord(cursor.getInt(0), cursor.getString(1), new String(cursor.getBlob(2), "UTF-8")));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        arr.add(new DictWord(cursor.getInt(0), cursor.getString(1), cursor.getBlob(2)));
                    }
                    cursor.moveToNext();
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            CLog.d(TAG, "array dict: " + arr.size());
        }

//        if (word.length() > 0 && isCharNormal(word.charAt(0) + "")) {
//
//
//        }

        return arr;
    }

}
