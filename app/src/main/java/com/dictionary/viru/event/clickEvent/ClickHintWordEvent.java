package com.dictionary.viru.event.clickEvent;

import com.dictionary.viru.model.db.DictWord;
import com.dictionary.viru.model.db.DictWordObject;

import java.util.ArrayList;

/**
 * Created by manhi on 5/1/2016.
 */
public class ClickHintWordEvent {
    public String word;
    public DictWord dictWord;
    public ArrayList<DictWordObject> DictWordObject;

    public ClickHintWordEvent(String word) {
        this.word = word;
    }

    public ClickHintWordEvent(DictWord dictWord) {
        this.dictWord = dictWord;
    }

    public ClickHintWordEvent(ArrayList<DictWordObject> dictWordObject) {
        this.DictWordObject = dictWordObject;
    }
}
