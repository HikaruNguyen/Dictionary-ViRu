package dictionary.vietnamese.tudien.viet.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dictionary.vietnamese.tudien.viet.configuration.Configruation;
import dictionary.vietnamese.tudien.viet.model.db.SentencesObject;

/**
 * Created by manhi on 5/1/5016.
 */
public class SentencesDBHelper {

    public static final String TAG = SentencesDBHelper.class.getSimpleName();
    public static String id = "id";
    public static final String dbName = "references.db";
    public static final String tableNameCUMDT = "phrasal_verb";
    public static final String tableNameTHANHNGU = "idiom";
    public static final String tableNameTUCNGU = "proverb";
    public static final String tableNameVIETTAT = "internet_acronym";

    public static List<SentencesObject> getListVerb(Context context, int type) {
        List<SentencesObject> list = new ArrayList<>();
        AssetDatabaseOpenHelper adb = new AssetDatabaseOpenHelper(context);
        SQLiteDatabase database = adb.StoreDatabase();
//        database = SQLiteDatabase.openDatabase(DB_PATH + dbName, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor;
        String sql = "";
        String tableName = "";
        switch (type) {
            case Configruation.TYPE_CAC_TU_VIET_TAT:
                tableName = tableNameVIETTAT;
                break;
            case Configruation.TYPE_THANH_NGU:
                tableName = tableNameTHANHNGU;
                break;
            case Configruation.TYPE_TUC_NGU:
                tableName = tableNameTUCNGU;
                break;
            case Configruation.TYPE_CUM_DT:
                tableName = tableNameCUMDT;
                break;
            default:
                tableName = tableNameCUMDT;
                break;

        }
        sql += "SELECT * FROM " + tableName;
//        sql += " LIMIT " + 100;
        if (database != null) {
            cursor = database.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                switch (type) {
                    case Configruation.TYPE_CAC_TU_VIET_TAT:
                        list.add(new SentencesObject(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
                        break;
                    case Configruation.TYPE_THANH_NGU:
                        list.add(new SentencesObject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
                        break;
                    case Configruation.TYPE_TUC_NGU:
                        list.add(new SentencesObject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), true));
                        break;
                    case Configruation.TYPE_CUM_DT:
                        list.add(new SentencesObject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                        break;
                    default:
                        list.add(new SentencesObject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                        break;
                }

                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }


}
