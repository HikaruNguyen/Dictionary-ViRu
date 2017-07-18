package com.dictionary.viru.service.standOut;

import android.content.Intent;
import android.content.res.Resources;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.dictionary.viru.NextDictUtils.CLog;
import com.dictionary.viru.R;
import com.dictionary.viru.adapter.MeaningAdapter;
import com.dictionary.viru.configuration.Configruation;
import com.dictionary.viru.event.clickEvent.ClickVoiceEvent;
import com.dictionary.viru.model.db.DictWordObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;

/**
 * Created by manhi on 30/1/2016.
 */
public class WidgetsWindow extends MultiWindow {
    public static final int DATA_CHANGED_TEXT = 0;
    public static RecyclerView rvListMeaning;
    public static MeaningAdapter meaningWordAdapter;
    public static List<DictWordObject> arr;
    private TextToSpeech textToSpeechUK;
    private TextToSpeech textToSpeechUS;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

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
                            textToSpeechUK.setPitch(1.0f);
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
                            textToSpeechUS.setPitch(1.3f);
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

    @Override
    public void createAndAttachView(final int id, FrameLayout frame) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.widgets, frame, true);
        InitUI(view);
        InitData();
    }

    private void InitData() {
        arr = new ArrayList<>();
        initVoice();
    }

    private void InitUI(View view) {
        rvListMeaning = (RecyclerView) view.findViewById(R.id.rvListMeaning);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        rvListMeaning.setLayoutManager(layoutManager1);
        rvListMeaning.setHasFixedSize(true);
        rvListMeaning.setItemAnimator(new DefaultItemAnimator());
        meaningWordAdapter = new MeaningAdapter(this, new ArrayList<DictWordObject>(), Configruation.TYPE_POPUP);
        rvListMeaning.setAdapter(meaningWordAdapter);
    }

    @Override
    public StandOutLayoutParams getParams(int id, Window window) {
        int w = getScreenWidth() * 2 / 3;
        int h = w * 4 / 3;
        return new StandOutLayoutParams(id, w, h,
                StandOutLayoutParams.AUTO_POSITION,
                StandOutLayoutParams.AUTO_POSITION, 100, 100);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    public String getAppName() {
        return getString(R.string.app_name);
    }

    @Override
    public int getThemeStyle() {
        return R.style.AppTheme;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (textToSpeechUK != null) {
            textToSpeechUK.stop();
            textToSpeechUK.shutdown();
        }
        if (textToSpeechUS != null) {
            textToSpeechUS.stop();
            textToSpeechUS.shutdown();
        }
    }

    public void onEvent(ClickVoiceEvent event) {
        if (event.type == Configruation.TYPE_POPUP) {
            if (event.loc.equals(Locale.UK)) {
                textToSpeechUK.speak(event.word, TextToSpeech.QUEUE_FLUSH, null);
            } else if (event.loc.equals(Locale.US)) {
                textToSpeechUS.speak(event.word, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

}