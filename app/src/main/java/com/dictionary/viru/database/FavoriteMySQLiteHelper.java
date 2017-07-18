package com.dictionary.viru.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavoriteMySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_FAVORITE = "tblFavorite";
    public static final String FAVORITE_ID = "FAVORITE_ID";
    public static final String FAVORITE_WORD = "FAVORITE_WORD";
    public static final String FAVORITE_CREAT_AT = "FAVORITE_CREAT_AT";
    public static final String FAVORITE_UPDATE_AT = "FAVORITE_UPDATE_AT";

    private static final String DATABASE_NAME = "favorite";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_FAVORITE + "("
            + FAVORITE_ID + " text primary key, "
            + FAVORITE_WORD + " text,"
            + FAVORITE_CREAT_AT + " text, "
            + FAVORITE_UPDATE_AT + " text );";

    public FavoriteMySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(FavoriteMySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(db);
    }

}