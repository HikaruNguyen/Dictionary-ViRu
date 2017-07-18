package com.dictionary.viru.widget.customeControl;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.dictionary.viru.NextDictUtils.CLog;

/**
 * Created by manhi on 5/1/2016.
 */
public class CustomAutoCompleteTextChangedListener implements TextWatcher {

    public static final String TAG = "CustomAutoCompleteTextChangedListener.java";
    Context context;
    public OnChangedListener onChangedListener;

    public CustomAutoCompleteTextChangedListener(Context context, OnChangedListener onChangedListener) {
        this.context = context;
        this.onChangedListener = onChangedListener;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {

        // if you want to see in the logcat what the user types
        CLog.e(TAG, "User input: " + userInput);
        onChangedListener.searchWord(userInput.toString().trim());
        // query the database based on the user input
//        HomeFragment.dictWords = DictDBHelper.filterWord(userInput.toString());
//        for (int i = 0; i < HomeFragment.dictWords.size(); i++) {
//            HomeFragment.listword.add(HomeFragment.dictWords.get(i).getWord());
//        }
////
////        // update the adapater
//        HomeFragment.adapter.notifyDataSetChanged();
//        HomeFragment.adapter = new SearchWordAdapter
//                (activity, listword);
//        ACTVSearch.setAdapter(adapter);
//        EventBus.getDefault().post(new ClickHintWordEvent(userInput.toString()));

    }

    public interface OnChangedListener {
        void searchWord(String word);
    }
}
