package com.dictionary.viru.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dictionary.viru.NextDictUtils.CLog;
import com.dictionary.viru.NextDictUtils.KeyboardUtils;
import com.dictionary.viru.NextDictUtils.Utils;
import com.dictionary.viru.R;
import com.dictionary.viru.activity.MainActivity;
import com.dictionary.viru.activity.MeaningWordActivity;
import com.dictionary.viru.adapter.BaseRecyclerAdapter;
import com.dictionary.viru.adapter.ListWordAdapter;
import com.dictionary.viru.configuration.Configruation;
import com.dictionary.viru.database.DictDBHelper;
import com.dictionary.viru.database.HistoryDatabase;
import com.dictionary.viru.database.ManagerDictDatabase;
import com.dictionary.viru.event.ChangeMenuEvent;
import com.dictionary.viru.event.HideShowKeyBoardEvent;
import com.dictionary.viru.model.db.DictWord;
import com.dictionary.viru.model.db.ManagerDict;
import com.dictionary.viru.widget.DividerItemDecoration;
import com.dictionary.viru.widget.DrawableClickListener;
import com.dictionary.viru.widget.customeControl.CustomEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, BaseRecyclerAdapter.OnItemClickListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    //    private CustomAutoCompleteView ACTVSearch;
    private ArrayList<String> listword = new ArrayList<>();
    private ImageView imgSearchVoice;
    private static final int RECOGNIZER_REQ_CODE = 1234;
    //    private static final int CODE_MEANING = 1;
    //    private SearchWordAdapter adapter;
    private ArrayList<DictWord> dictWords;
    //    private TextView tvDefinition;
    public static CustomEditText edSearch;
    private RecyclerView rvListWord;
    private RecyclerView rvListMeaning;
    private ListWordAdapter adapter;
    //    private CardView cvWord;
    private boolean isClickItem = false;
    //    private ManagerDictDatabase databaseHandler;
    private ManagerDictDatabase managerDictDatabase;
    private HistoryDatabase historyDatabase;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private static final String ARG_PARAM_WORD = "WORD";
    private String wordShare;
    private QuerryHintWord querryHintWord;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String word) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_WORD, word);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            wordShare = getArguments().getString(ARG_PARAM_WORD);
            if (wordShare != null && wordShare.isEmpty()) {
                managerDictDatabase.open();
                List<ManagerDict> managerDicts = managerDictDatabase.getAllDictIfChecked();
                if (managerDicts != null && managerDicts.size() > 0) {
                    if (adapter.getItemCount() == 0) {
                        ToastMsg(activity, getString(R.string.notFoundWord));
                    } else {
                        searchHintWord(wordShare);
                    }
                } else {
                    showPopupNoDict();
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        InitUI(view);
        InitData();
        InitEvent();
        return view;
    }

    @Override
    public void onResume() {
        edSearch = (CustomEditText) getActivity().findViewById(R.id.edSearch);
        edSearch.requestFocus();
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(edSearch, InputMethodManager.SHOW_IMPLICIT);
        super.onResume();

    }

    private void InitEvent() {
        imgSearchVoice.setOnClickListener(this);
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String text = edSearch.getText().toString().trim();
                    if (Utils.countWords(text) > 3) {
//                        Intent intent = new Intent(activity, TranslateActivity.class);
//                        intent.putExtra("trans", text);
//                        startActivity(intent);
                    } else {
                        managerDictDatabase.open();
                        List<ManagerDict> managerDicts = managerDictDatabase.getAllDictIfChecked();
                        if (managerDicts != null && managerDicts.size() > 0) {
                            if (adapter.getItemCount() == 0) {
                                if (activity != null)
                                    ToastMsg(activity, activity.getResources().getString(R.string.notFoundWord));
                                return false;
                            } else {
                                String word = adapter.getItembyPostion(0).getWord().trim();
                                searchHintWord(word);
                            }
                        } else {
                            if (getActivity() != null)
                                showPopupNoDict();
                        }

                    }


                    return true;
                }
                return false;
            }
        });

        edSearch.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                if (target == DrawablePosition.RIGHT) {
                    adapter.clear();
                    edSearch.setText("");
//                    Utils.openKeyboard(activity);
                    edSearch.setFocusable(true);
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edSearch, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isClickItem) {
                    try {
//                        showHintWord(s.toString().trim());
                        if (querryHintWord != null) {
                            querryHintWord.cancel(true);
                        }
                        querryHintWord = new QuerryHintWord(s.toString().trim());
                        querryHintWord.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
//        edSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (((MainActivity) getActivity()).drawer.isDrawerOpen(GravityCompat.START)) {
//                    ((MainActivity) getActivity()).drawer.closeDrawer(GravityCompat.START);
//                }
//            }
//        });
        adapter.setOnItemClickListener(this);
//        edSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                edSearch.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.showSoftInput(edSearch, InputMethodManager.SHOW_IMPLICIT);
//                    }
//                });
//            }
//        });
//        edSearch.requestFocus();
    }

    private void InitData() {
        mSharedPreferences = activity.getSharedPreferences(Configruation.PREFERENCE_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        managerDictDatabase = new ManagerDictDatabase(activity);
        historyDatabase = new HistoryDatabase(activity);

    }

    private void showPopupNoDict() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(activity.getResources().getString(R.string.dictNotFound));
        builder.setNegativeButton(activity.getResources().getString(R.string.late), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(activity.getResources().getString(R.string.downNow), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.hideKeyboard(activity);
                EventBus.getDefault().post(new ChangeMenuEvent(Configruation.HOME_SELECT_DICT));
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    //    private void showHintWord(String word) {
//        word = word.toLowerCase();
//        managerDictDatabase.open();
//        List<ManagerDict> managerDicts = managerDictDatabase.getAllDictIfChecked();
//        ArrayList<DictWord> wordArrayList = new ArrayList<>();
//        if (managerDicts != null && managerDicts.size() > 0) {
//            CLog.d(TAG, "managedict sizeL " + managerDicts.size());
//            adapter.clear();
//            for (int i = 0; i < managerDicts.size(); i++) {
//                int format_version = Utils.getFormatTypeDict(managerDicts.get(i));
//                dictWords = DictDBHelper.filterWord(managerDicts.get(i).getDictId(), word, true, format_version);
//                if (dictWords.size() > 0) {
//                    for (int j = 0; j < dictWords.size(); j++) {
//                        boolean isSame = false;
//                        for (int k = 0; k < wordArrayList.size(); k++) {
//                            if (wordArrayList.get(k).getWord().trim().equals(dictWords.get(j).getWord().trim())) {
//                                isSame = true;
//                                break;
//                            }
//                        }
//                        if (!isSame) {
//                            wordArrayList.add(dictWords.get(j));
//                        }
//                    }
//                }
//
//            }
//            Collections.sort(wordArrayList, new Comparator<DictWord>() {
//                @Override
//                public int compare(DictWord lhs, DictWord rhs) {
//                    return (lhs.getWord().toUpperCase().compareTo(rhs.getWord().toUpperCase()));
//                }
//            });
//            adapter.addAll(wordArrayList);
//
//        } else {
////            ToastMsg(activity, getString(R.string.dictNotFound));
//
//
//        }
//        managerDictDatabase.close();
//    }
    private ArrayList<DictWord> showHintWord(String word) {
        word = word.toLowerCase();
        managerDictDatabase.open();
        List<ManagerDict> managerDicts = managerDictDatabase.getAllDictIfChecked();
        ArrayList<DictWord> wordArrayList = new ArrayList<>();
        if (managerDicts != null && managerDicts.size() > 0) {
            CLog.d(TAG, "managedict sizeL " + managerDicts.size());
            for (int i = 0; i < managerDicts.size(); i++) {
                int format_version = Utils.getFormatTypeDict(managerDicts.get(i));
                dictWords = DictDBHelper.filterWord(managerDicts.get(i).getDictId(), word, true, format_version);
                if (dictWords.size() > 0) {
                    for (int j = 0; j < dictWords.size(); j++) {
                        boolean isSame = false;
                        for (int k = 0; k < wordArrayList.size(); k++) {
                            if (wordArrayList.get(k).getWord().trim().equals(dictWords.get(j).getWord().trim())) {
                                isSame = true;
                                break;
                            }
                        }
                        if (!isSame) {
                            wordArrayList.add(dictWords.get(j));
                        }
                    }
                }

            }
            Collections.sort(wordArrayList, new Comparator<DictWord>() {
                @Override
                public int compare(DictWord lhs, DictWord rhs) {
                    return (lhs.getWord().toUpperCase().compareTo(rhs.getWord().toUpperCase()));
                }
            });
        } else {
//            ToastMsg(activity, getString(R.string.dictNotFound));
        }
        managerDictDatabase.close();
        return wordArrayList;
    }

    private void InitUI(View view) {
        imgSearchVoice = (ImageView) view.findViewById(R.id.imgSearchVoice);
        edSearch = (CustomEditText) view.findViewById(R.id.edSearch);
        checkExistDict();
        /*InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);*/

        rvListWord = (RecyclerView) view.findViewById(R.id.rvListWord);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvListWord.setLayoutManager(layoutManager);
        rvListWord.setHasFixedSize(true);
        rvListWord.setItemAnimator(new DefaultItemAnimator());
        rvListWord.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
        adapter = new ListWordAdapter(activity, new ArrayList<DictWord>());
        rvListWord.setAdapter(adapter);
    }

    private void checkExistDict() {
        ManagerDictDatabase database = new ManagerDictDatabase(activity);
        database.open();
        int e = database.getCountDictExist();
        int c = database.getCountDictChecked();
        database.close();
        if (e == 0 || c == 0) {

        } else {
            KeyboardUtils.showDelayedKeyboard(activity, edSearch);
//            edSearch.requestFocus();
//            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(edSearch, InputMethodManager.SHOW_IMPLICIT);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgSearchVoice:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, activity.getApplication().getPackageName());
                try {
                    startActivityForResult(intent, RECOGNIZER_REQ_CODE);
                } catch (Exception e) {
                    ToastMsg(activity, getString(R.string.noSupportSpeechVoice));
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RECOGNIZER_REQ_CODE:
                if (resultCode == Activity.RESULT_OK && null != data) {
                    String yourResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
                    edSearch.setText(yourResult);
                }
                break;
            case MainActivity.REQUEST_MEANING:
                if (resultCode == Activity.RESULT_OK) {
                    edSearch.setFocusable(true);
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edSearch, InputMethodManager.SHOW_IMPLICIT);
                }

                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDetach() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDetach();
    }

//    public void onEvent(ClickHintWordEvent event) {
//        searchHintWord(event.dictWord.getWord());
//    }

    public void onEvent(HideShowKeyBoardEvent event) {
//        edSearch.setFocusable(false);
        if (event.type == HideShowKeyBoardEvent.TYPE_HIDE) {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } else {
            edSearch.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edSearch, InputMethodManager.SHOW_IMPLICIT);
        }

    }

    public void searchHintWord(String word) {
        Intent intent = new Intent(activity, MeaningWordActivity.class);
        intent.putExtra("word", word);
        activity.startActivityForResult(intent, MainActivity.REQUEST_MEANING);
    }

    @Override
    public void onItemClick(int position) {
        searchHintWord(adapter.getItembyPostion(position).getWord());
    }

    public class QuerryHintWord extends AsyncTask<String, Void, ArrayList<DictWord>> {
        private String word;

        public QuerryHintWord(String word) {
            this.word = word;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<DictWord> doInBackground(String... params) {
            return showHintWord(word);
        }

        @Override
        protected void onPostExecute(ArrayList<DictWord> dictWords) {
            super.onPostExecute(dictWords);
            adapter.clear();
            adapter.addAll(dictWords);
        }


    }


}
