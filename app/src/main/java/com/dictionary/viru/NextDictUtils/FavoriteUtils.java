package com.dictionary.viru.NextDictUtils;

import android.content.Context;
import android.content.SharedPreferences;

import com.dictionary.viru.configuration.Configruation;

import java.util.ArrayList;

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
