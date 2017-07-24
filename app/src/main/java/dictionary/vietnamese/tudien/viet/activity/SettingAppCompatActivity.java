package dictionary.vietnamese.tudien.viet.activity;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.view.MenuItem;

import com.afollestad.materialdialogs.prefs.MaterialListPreference;

import dictionary.vietnamese.tudien.viet.NextDictUtils.CLog;
import dictionary.vietnamese.tudien.viet.R;
import dictionary.vietnamese.tudien.viet.configuration.Configruation;

@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
public class SettingAppCompatActivity extends android.preference.PreferenceActivity {
    private MaterialListPreference settingFont;
    SharedPreferences sharedPreferences = getSharedPreferences(Configruation.Pref, MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        settingFont = (MaterialListPreference) findPreference(getString(R.string.fontSize));
        settingFont.setSummary(settingFont.getValue());
        CLog.d("", "FONTSIZE: " + settingFont.getValue());
        settingFont.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                settingFont.setSummary(newValue.toString());

                editor.putString(Configruation.Pref_Key_FontSize, newValue.toString());
                editor.apply();
                return true;
            }
        });
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