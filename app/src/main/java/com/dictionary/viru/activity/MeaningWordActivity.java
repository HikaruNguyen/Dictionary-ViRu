package com.dictionary.viru.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dictionary.viru.NextDictUtils.AESUtils;
import com.dictionary.viru.NextDictUtils.CLog;
import com.dictionary.viru.NextDictUtils.FavoriteUtils;
import com.dictionary.viru.NextDictUtils.Utils;
import com.dictionary.viru.R;
import com.dictionary.viru.configuration.Configruation;
import com.dictionary.viru.configuration.IntentFilterConfig;
import com.dictionary.viru.database.DictDBHelper;
import com.dictionary.viru.database.DictInfoDBHelper;
import com.dictionary.viru.database.FavoriteDatabase;
import com.dictionary.viru.database.HistoryDatabase;
import com.dictionary.viru.database.ManagerDictDatabase;
import com.dictionary.viru.event.ChangeMenuEvent;
import com.dictionary.viru.event.HideShowKeyBoardEvent;
import com.dictionary.viru.event.clickEvent.ClickLinkWebViewEvent;
import com.dictionary.viru.event.clickEvent.ClickVoiceEvent;
import com.dictionary.viru.model.db.DictInfo;
import com.dictionary.viru.model.db.DictWord;
import com.dictionary.viru.model.db.DictWordObject;
import com.dictionary.viru.model.resultApi.ListDictResult;
import com.dictionary.viru.widget.customeControl.CustomeWebView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class MeaningWordActivity extends BaseActivity {
    private static final String TAG = MeaningWordActivity.class.getSimpleName();
    private String word;
    private Context activity;
    //    private RecyclerView rvListMeaning;
//    private MeaningAdapter meaningWordAdapter;
    private ManagerDictDatabase managerDictDatabase;
    private HistoryDatabase historyDatabase;
    private FavoriteDatabase favoriteDatabase;
    private boolean isFavorited = false;
    private FloatingActionButton fab;
//    private TextToSpeech textToSpeechUK;
//    private TextToSpeech textToSpeechUS;

    private CustomeWebView webView;
    private TextView tvWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_meaning_word);
        activity = MeaningWordActivity.this;
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                word = intent.getStringExtra(Intent.EXTRA_TEXT);
            }
        } else if (action != null && action.equals(IntentFilterConfig.COLORDICT_INTENT_ACTION_SEARCH)) {
            word = intent.getStringExtra(IntentFilterConfig.EXTRA_QUERY);
        } else if (action != null && action.equals(IntentFilterConfig.FLEXIDICT_INTENT_ACTION_LOOKUP)) {
            word = intent.getStringExtra(IntentFilterConfig.EXTRA_QUERY);
        } else if (action != null && action.equals(IntentFilterConfig.MEGADICT_INTENT_ACTION_LOOKUP)) {
            word = intent.getStringExtra(IntentFilterConfig.EXTRA_QUERY);
        } else if (action != null && action.equals(IntentFilterConfig.NEXTDICT_INTENT_ACTION_LOOKUP)) {
            word = intent.getStringExtra(IntentFilterConfig.EXTRA_QUERY);
        } else {
            word = intent.getStringExtra("word");
        }
        if (word == null) {
            word = "";
        }
        InitUI();
        InitData();
    }

    private void InitData() {
//        initVoice();
        managerDictDatabase = new ManagerDictDatabase(activity);
        historyDatabase = new HistoryDatabase(activity);
        favoriteDatabase = new FavoriteDatabase(activity);
        GetMindWordAsynTask getMindWordAsynTask = new GetMindWordAsynTask();
        getMindWordAsynTask.execute();
//        rvListMeaning.setVisibility(View.VISIBLE);
//        meaningWordAdapter.clear();


    }

    private void bindMeanWord(DictWordObject dictWord) {
        if (dictWord != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("<HTML><HEAD><LINK href=\"css/result.css\" type=\"text/css\" rel=\"stylesheet\"/></HEAD><body>");
            String info = "";
            info = DictInfoDBHelper.getInfoDict(dictWord.dictId);
            CLog.d(TAG, "dictinfo: " + info.split("\n").length);
            DictInfo dictInfo = new DictInfo();
            int isEncrypted = 0;
            int format_version = 1;
            for (int i = 0; i < info.split("\n").length; i++) {
                String s = info.split("\n")[i];
                if (s.trim() != null && !s.isEmpty()) {
                    String s1[] = s.split("=");
                    if (s1[0].trim().equals("format_version")) {
                        dictInfo.format_version = s1[1];
                        try {
                            format_version = Integer.parseInt(dictInfo.format_version);
                        } catch (Exception e) {
                            format_version = 1;
                        }
                    } else if (s1[0].trim().equals("encrypted")) {
                        dictInfo.encrypted = s1[1];
                        try {
                            isEncrypted = Integer.parseInt(dictInfo.encrypted);
                        } catch (Exception e) {
                            isEncrypted = 0;
                        }
                    } else if (s1[0].trim().equals("encryptKey")) {
                        dictInfo.encryptKey = s1[1];
                    }
                }
            }
            String s = "";
            if (isEncrypted == 1) {
                if (format_version == 1) {
                    s = dictWord.dictWord.getDefinition().toString();
                    AESUtils aesUtils = new AESUtils();
                    aesUtils.setKey(dictInfo.encryptKey);
                    try {
                        s = aesUtils.decrypt(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    byte[] ss = dictWord.dictWord.getDefinition2();
                    AESUtils aesUtils = new AESUtils();
                    aesUtils.setKey(dictInfo.encryptKey);
                    try {
                        s = aesUtils.decrypt2(ss);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            sb.append(s);
            sb.append("</body></HTML>");
            webView.loadDataWithBaseURL("file:///android_asset/", sb.toString(), "text/html", "UTF-8", null);
            tvWord.setText(dictWord.dictWord.getWord());
        }
    }

//    private void initVoice() {
//        try {
//            textToSpeechUK = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//                @Override
//                public void onInit(int status) {
////                    if (status != TextToSpeech.ERROR) {
////                        textToSpeechUK.setLanguage(Locale.UK);
////                        textToSpeechUK.setPitch(1.3f);
////                        textToSpeechUK.setSpeechRate(1f);
////                    }
//                    if (status == TextToSpeech.SUCCESS) {
//                        int result = textToSpeechUK.setLanguage(Locale.UK);
//                        if (result == TextToSpeech.LANG_MISSING_DATA ||
//                                result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                            CLog.e("error", "This Language is not supported");
//                        } else {
//                            textToSpeechUK.setPitch(1.3f);
//                            textToSpeechUK.setSpeechRate(1f);
//                        }
//                    } else
//                        CLog.e(TAG, "Initilization UK Failed!");
//                }
//
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            textToSpeechUS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//                @Override
//                public void onInit(int status) {
//                    if (status == TextToSpeech.SUCCESS) {
//                        int result = textToSpeechUS.setLanguage(Locale.US);
//                        if (result == TextToSpeech.LANG_MISSING_DATA ||
//                                result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                            CLog.e("error", "This Language is not supported");
//                        } else {
//                            textToSpeechUS.setPitch(1.0f);
//                            textToSpeechUS.setSpeechRate(1f);
//                        }
//                    } else
//                        CLog.e(TAG, "Initilization US Failed!");
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    private void InitUI() {
        EventBus.getDefault().post(new HideShowKeyBoardEvent(HideShowKeyBoardEvent.TYPE_HIDE));
        setTitleToolbar(word);
        setBackButtonToolbar();
//        rvListMeaning = (RecyclerView) findViewById(R.id.rvListMeaning);
//        LinearLayoutManager layoutManager1 = new LinearLayoutManager(activity);
//        rvListMeaning.setLayoutManager(layoutManager1);
//        rvListMeaning.setHasFixedSize(true);
//        rvListMeaning.setItemAnimator(new DefaultItemAnimator());
//        meaningWordAdapter = new MeaningAdapter(activity, new ArrayList<DictWordObject>(), Configruation.TYPE_APP);
//        rvListMeaning.setAdapter(meaningWordAdapter);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Configruation.isClickWordRelated) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Configruation.isClickWordRelated = false;
                    Intent intent = new Intent(MeaningWordActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

        webView = (CustomeWebView) findViewById(R.id.webView);
        webView.setType(Configruation.TYPE_APP);
        tvWord = (TextView) findViewById(R.id.tvWord);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favorite) {
            if (isFavorited) {
                item.setIcon(getResources().getDrawable(R.mipmap.ic_favorite_2));

            } else {
                item.setIcon(getResources().getDrawable(R.mipmap.ic_favorite_active));
            }
            updateFavorite(isFavorited);
            isFavorited = !isFavorited;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateFavorite(boolean isFavorited) {
        favoriteDatabase.open();
        if (isFavorited) {
            favoriteDatabase.delFavroritebyWord(word);
            FavoriteUtils favoriteUtils = new FavoriteUtils(activity);
            ArrayList<String> words = new ArrayList<>();
            words.add(word);
            favoriteUtils.deleteWord(words);
        } else {
            favoriteDatabase.addWordToFavorite(word);
        }
        favoriteDatabase.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meaning_menu, menu);
        MenuItem menuItemFav = menu.findItem(R.id.action_favorite);
        if (isFavorited) {
            menuItemFav.setIcon(getResources().getDrawable(R.mipmap.ic_favorite_active));
        } else {
            menuItemFav.setIcon(getResources().getDrawable(R.mipmap.ic_favorite_2));
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    public void onEvent(ClickLinkWebViewEvent event) {
        if (event.type == Configruation.TYPE_APP) {
            Configruation.isClickWordRelated = true;
            Intent intent = new Intent(activity, MeaningWordActivity.class);
            intent.putExtra("word", event.word);
            startActivity(intent);
//            finish();
        }
    }

    public void onEvent(ClickVoiceEvent event) {
//        if (event.type == Configruation.TYPE_APP) {
//            if (event.loc.equals(Locale.UK)) {
//                textToSpeechUK.speak(event.word, TextToSpeech.QUEUE_FLUSH, null);
//            } else if (event.loc.equals(Locale.US)) {
//                textToSpeechUS.speak(event.word, TextToSpeech.QUEUE_FLUSH, null);
//            }
//        }

    }

    public void onPause() {
//        if (textToSpeechUK != null) {
//            textToSpeechUK.stop();
//            textToSpeechUK.shutdown();
//        }
//        if (textToSpeechUS != null) {
//            textToSpeechUS.stop();
//            textToSpeechUS.shutdown();
//        }
        super.onPause();
    }

    private class GetMindWordAsynTask extends AsyncTask<Void, Void, ArrayList<DictWordObject>> {

        public GetMindWordAsynTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<DictWordObject> doInBackground(Void... params) {

            historyDatabase.open();
            if (!historyDatabase.isExistWord(word)) {
                historyDatabase.addWordToHistory(word);
            } else {
                historyDatabase.updateWord(word);
            }

            historyDatabase.close();

            favoriteDatabase.open();
            if (favoriteDatabase.isExistWord(word)) {
                isFavorited = true;
            } else {
                isFavorited = false;
            }
            favoriteDatabase.close();


            ArrayList<DictWordObject> arr = new ArrayList<>();
            managerDictDatabase.open();
            ListDictResult.ListDictInfo listDictInfo = managerDictDatabase.getDictIfChecked();
            if (listDictInfo != null) {
                CLog.d(TAG, "dict name: " + listDictInfo.name);
                int format_type = Utils.getFormatTypeDict(listDictInfo);
                ArrayList<DictWord> dictWords = DictDBHelper.filterWord(listDictInfo.id, word, false, format_type);
                if (dictWords.size() > 0) {
                    for (int j = 0; j < dictWords.size(); j++) {
                        arr.add(new DictWordObject(listDictInfo.id,listDictInfo.name, dictWords.get(j), format_type));
                    }

                }

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(getString(R.string.dictNotFound));
                builder.setNegativeButton(getString(R.string.late), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton(getString(R.string.downNow), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getDefault().post(new ChangeMenuEvent(Configruation.HOME_SELECT_DICT));
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
            managerDictDatabase.close();
            return arr;
        }


        @Override
        protected void onPostExecute(ArrayList<DictWordObject> arr) {
            super.onPostExecute(arr);
            if (arr.size() == 0) {
                ToastMsg(activity, getString(R.string.notFoundWord));
            }
//        meaningWordAdapter.addAll(arr);
            bindMeanWord(arr.get(0));
        }
    }
}
