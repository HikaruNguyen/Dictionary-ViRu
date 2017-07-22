package dictionary.viet.russia.viru.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dictionary.viet.russia.viru.model.db.IrregularPluralNounObject;
import dictionary.viet.russia.viru.model.db.IrregularVerbObject;

/**
 * Created by manhi on 5/1/5016.
 */
public class IrregularDBHelper {

    public static final String TAG = IrregularDBHelper.class.getSimpleName();
    public static String id = "id";
    public static String infinitive = "infinitive";
    public static String past_simple = "past_simple";
    public static String past_participle = "past_participle";
    public static String third_person_singular = "third_person_singular";
    public static String present_participle = "present_participle";
    public static String group = "group";
    public static String definition = "definition";
    public static final String dbName = "references.db";
    public static final String tableNameDongTu = "irregular_verb";
    public static final String tableNameDanhTu = "irregular_plural_noun";
    private static String DB_PATH = "/data/data/com.nextdict.appandroid/databases/";

    public static List<IrregularVerbObject> getListIrregualrVerbk(Context context, String tableName) {
        List<IrregularVerbObject> list = new ArrayList<>();
        AssetDatabaseOpenHelper adb = new AssetDatabaseOpenHelper(context);
        SQLiteDatabase database = adb.StoreDatabase();
//        database = SQLiteDatabase.openDatabase(DB_PATH + dbName, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor;
        String sql = "";
        sql += "SELECT * FROM " + tableName;
//        sql += " LIMIT " + 100;
        if (database != null) {
            cursor = database.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                if (tableName.equals(tableNameDongTu)) {
                    list.add(new IrregularVerbObject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7)));
                } else {
                    list.add(new IrregularVerbObject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                }

                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public static List<IrregularVerbObject> getListIrregualrVerbk(Context context, String tableName, int groupNum) {
        List<IrregularVerbObject> list = new ArrayList<>();
        AssetDatabaseOpenHelper adb = new AssetDatabaseOpenHelper(context);
        SQLiteDatabase database = adb.StoreDatabase();
//        database = SQLiteDatabase.openDatabase(DB_PATH + dbName, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor;
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE [group] = " + groupNum;
        if (database != null) {
            cursor = database.rawQuery(sql, null);
//            cursor = database.query(tableName, allColumnsDongTu, group + " = " + groupNum, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                if (tableName.equals(tableNameDongTu)) {
                    list.add(new IrregularVerbObject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7)));
                } else {
                    list.add(new IrregularVerbObject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                }

                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public static List<IrregularPluralNounObject> getListIrregualrNoun(Context context, String tableName) {
        List<IrregularPluralNounObject> list = new ArrayList<>();
        AssetDatabaseOpenHelper adb = new AssetDatabaseOpenHelper(context);
        SQLiteDatabase database = adb.StoreDatabase();
//        database = SQLiteDatabase.openDatabase(DB_PATH + dbName, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor;
        String sql = "";
        sql += "SELECT * FROM " + tableName;
//        sql += " LIMIT " + 100;
        if (database != null) {
            cursor = database.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(new IrregularPluralNounObject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));

                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public static List<IrregularPluralNounObject> getListIrregualrNoun(Context context, String tableName, String groupName) {
        List<IrregularPluralNounObject> list = new ArrayList<>();
        AssetDatabaseOpenHelper adb = new AssetDatabaseOpenHelper(context);
        SQLiteDatabase database = adb.StoreDatabase();
//        database = SQLiteDatabase.openDatabase(DB_PATH + dbName, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor;
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE [group] = '" + groupName + "'";
        if (database != null) {
            cursor = database.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(new IrregularPluralNounObject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));

                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public static List<IrregularPluralNounObject> getGroupNoun(Context context, String tableName) {
        List<IrregularPluralNounObject> list = new ArrayList<>();
        AssetDatabaseOpenHelper adb = new AssetDatabaseOpenHelper(context);
        SQLiteDatabase database = adb.StoreDatabase();
//        database = SQLiteDatabase.openDatabase(DB_PATH + dbName, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor;
        String sql = "";
        sql += "SELECT distinct [group] FROM " + tableName;
//        sql += " LIMIT " + 100;
        if (database != null) {
            cursor = database.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(new IrregularPluralNounObject(cursor.getString(0)));

                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
}
