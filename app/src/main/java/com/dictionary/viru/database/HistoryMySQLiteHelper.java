package com.dictionary.viru.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HistoryMySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_HISTORY = "tblhistory";
    public static final String HISTORY_ID = "history_id";
    public static final String HISTORY_WORD = "history_word";
    public static final String HISTORY_CREAT_AT = "history_creat_at";
    public static final String HISTORY_UPDATE_AT = "history_update_at";

    private static final String DATABASE_NAME = "history";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_HISTORY + "("
            + HISTORY_ID + " text primary key, "
            + HISTORY_WORD + " text,"
            + HISTORY_CREAT_AT + " text, "
            + HISTORY_UPDATE_AT + " text );";

    public HistoryMySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(HistoryMySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

}