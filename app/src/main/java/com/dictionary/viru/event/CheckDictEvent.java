package com.dictionary.viru.event;

import com.dictionary.viru.model.resultApi.ListDictResult;

/**
 * Created by manhi on 13/1/2016.
 */
public class CheckDictEvent {
    public ListDictResult.ListDictInfo dictWordObject;
    public boolean isCheck;
    public int position;
    public CheckDictEvent(ListDictResult.ListDictInfo dictWordObject, boolean isCheck, int position){
        this.dictWordObject = dictWordObject;
        this.isCheck = isCheck;
        this.position = position;
    }
}
