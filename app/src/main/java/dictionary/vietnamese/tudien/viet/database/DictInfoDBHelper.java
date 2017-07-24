package dictionary.vietnamese.tudien.viet.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import dictionary.vietnamese.tudien.viet.BuildConfig;

/**
 * Created by manhi on 5/1/5016.
 */
public class DictInfoDBHelper {
    public static final String TAG = DictInfoDBHelper.class.getSimpleName();
    public static String fieldObjectId = "id";
    public static String fieldObjectInfo = "ifo";
    public static String DB_PATH = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";

    public static String getInfoDict(String dbName) {
        String s = "";
        SQLiteDatabase database;
//        File file = new File(MyApplication.DICT_ROOT, dbName + ".ndf");
//        if (file.exists()) {
//            String filepath = file.getPath();
        database = SQLiteDatabase.openDatabase(DB_PATH + dbName + ".ndf", null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor;
        String tableName;
        String sql = "";
        tableName = "dictinfo";
        sql += "SELECT * FROM " + tableName;

        cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            s = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();
//        }

//        } else {
//            CLog.d(TAG, "dict not exits");
//        }
        return s;
    }


}
