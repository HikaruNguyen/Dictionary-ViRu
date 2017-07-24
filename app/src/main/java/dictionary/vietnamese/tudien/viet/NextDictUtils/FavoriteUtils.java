package dictionary.vietnamese.tudien.viet.NextDictUtils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import dictionary.vietnamese.tudien.viet.configuration.Configruation;

/**
 * Created by manhi on 28/6/2016.
 */
public class FavoriteUtils {
    private static final String TAG = FavoriteUtils.class.getSimpleName();
    private Context context;
    private SharedPreferences sharedPreferences;

    public FavoriteUtils(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Configruation.Pref, Context.MODE_PRIVATE);
    }

    public void deleteWord(ArrayList<String> words) {
    }
}
