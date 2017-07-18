package com.dictionary.viru.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.view.MenuItem;

import com.afollestad.materialdialogs.prefs.MaterialListPreference;
import com.dictionary.viru.R;
import com.dictionary.viru.configuration.Configruation;
import com.dictionary.viru.NextDictUtils.CLog;

@SuppressLint("NewApi")
public class SettingActivity extends BaseActivity {

    public static class SettingsFragment extends PreferenceFragment {
        private MaterialListPreference settingFontPref;
        private SwitchPreference clipboardPreference;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting);
            sharedPreferences = getActivity().getSharedPreferences(Configruation.Pref_Setting, MODE_PRIVATE);
            editor = sharedPreferences.edit();
            settingFont();
            settingClipboard();

        }

        private void settingClipboard() {
            clipboardPreference = (SwitchPreference) findPreference(getString(R.string.clipboard));
            clipboardPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    boolean isVibrateOn = (Boolean) newValue;
                    editor.putBoolean(Configruation.Pref_Key_Clipboard, isVibrateOn);
                    editor.apply();
                    return true;
                }
            });
        }

        private void settingFont() {
            settingFontPref = (MaterialListPreference) findPreference(getString(R.string.fontSize));
            settingFontPref.setSummary(settingFontPref.getValue());
            CLog.d("", "FONTSIZE: " + settingFontPref.getValue());
            settingFontPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    settingFontPref.setSummary(newValue.toString());
                    editor.putString(Configruation.Pref_Key_FontSize, newValue.toString());
                    editor.apply();
                    return true;
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitleToolbar(getString(R.string.setting));
        setBackButtonToolbar();
        if (getFragmentManager().findFragmentById(R.id.content_frame) == null) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}