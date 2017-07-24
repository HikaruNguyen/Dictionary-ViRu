package dictionary.vietnamese.tudien.viet.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ManagerDictMySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_DICT = "dict";
    public static final String MANAGER_DICT = "dict";
    public static final String MANAGER_DICT_ID = "dict_id";
    public static final String MANAGER_DICT_NAME = "dict_name";
    public static final String MANAGER_DICT_CHECKED = "dict_checked";
    private static final String DATABASE_NAME = "manager_dict";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_DICT + "("
            + MANAGER_DICT + " text primary key, "
            + MANAGER_DICT_ID + " text, "
            + MANAGER_DICT_NAME + " text, "
            + MANAGER_DICT_CHECKED + "  INTEGER)";

    public ManagerDictMySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ManagerDictMySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICT);
        onCreate(db);
    }

}