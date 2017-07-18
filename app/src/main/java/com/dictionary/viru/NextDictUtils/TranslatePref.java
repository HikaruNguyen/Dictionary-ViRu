package com.dictionary.viru.NextDictUtils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by manhi on 3/3/2016.
 */
public class TranslatePref {
    private Context context;
    private final String Pref_Trans = "Pref_Trans";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String KEY_SRC_CODE = "src_pref_lang_code";
    private final String KEY_DES_CODE = "des_pref_lang_code";
    private final String KEY_SRC_NAME = "src_pref_lang_name";
    private final String KEY_DES_NAME = "des_pref_lang_name";

    public TranslatePref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Pref_Trans, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLanguage(boolean isSource, String langCode, String langName) {
        String code = isSource ? KEY_SRC_CODE : KEY_DES_CODE;
        String name = isSource ? KEY_SRC_NAME : KEY_DES_NAME;
        editor.putString(code, langCode).apply();
        editor.putString(name, langName).apply();
    }

    public String getLangCode(boolean isSource) {
        String code = isSource ? KEY_SRC_CODE : KEY_DES_CODE;
        return sharedPreferences.getString(code, "");
    }

    public String getLangName(boolean isSource) {
        String name = isSource ? KEY_SRC_NAME : KEY_DES_NAME;
        return sharedPreferences.getString(name, "");
    }
}
