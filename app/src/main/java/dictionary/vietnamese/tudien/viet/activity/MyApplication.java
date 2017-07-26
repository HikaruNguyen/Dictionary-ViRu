package dictionary.vietnamese.tudien.viet.activity;

import android.app.Application;
import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

import dictionary.vietnamese.tudien.viet.NextDictUtils.CLog;

/**
 * Created by manhi on 4/1/2016.
 */
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    private TextToSpeech textToSpeechUK;
    private TextToSpeech textToSpeechUS;

    public static MyApplication with(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initVoice();
    }

    public TextToSpeech getTextToSpeechUK() {
        return textToSpeechUK;
    }

    public TextToSpeech getTextToSpeechUS() {
        return textToSpeechUS;
    }

    private void initVoice() {
        try {
            textToSpeechUK = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
//                    if (status != TextToSpeech.ERROR) {
//                        textToSpeechUK.setLanguage(Locale.UK);
//                        textToSpeechUK.setPitch(1.3f);
//                        textToSpeechUK.setSpeechRate(1f);
//                    }
                    if (status == TextToSpeech.SUCCESS) {
                        int result = textToSpeechUK.setLanguage(Locale.UK);
                        if (result == TextToSpeech.LANG_MISSING_DATA ||
                                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            CLog.e("error", "This Language is not supported");
                        } else {
                            textToSpeechUK.setPitch(1.3f);
                            textToSpeechUK.setSpeechRate(1f);
                        }
                    } else
                        CLog.e(TAG, "Initilization UK Failed!");
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            textToSpeechUS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        int result = textToSpeechUS.setLanguage(Locale.US);
                        if (result == TextToSpeech.LANG_MISSING_DATA ||
                                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            CLog.e("error", "This Language is not supported");
                        } else {
                            textToSpeechUS.setPitch(1.0f);
                            textToSpeechUS.setSpeechRate(1f);
                        }
                    } else
                        CLog.e(TAG, "Initilization US Failed!");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}