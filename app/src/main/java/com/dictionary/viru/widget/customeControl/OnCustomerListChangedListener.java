package com.dictionary.viru.widget.customeControl;

import com.dictionary.viru.model.resultApi.ListDictResult;

import java.util.ArrayList;

/**
 * Created by manhi on 15/1/2016.
 */
public interface OnCustomerListChangedListener {
    void onNoteListChanged(ArrayList<ListDictResult.ListDictInfo> customers);
}
