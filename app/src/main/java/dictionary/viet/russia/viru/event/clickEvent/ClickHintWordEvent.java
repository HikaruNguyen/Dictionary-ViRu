package dictionary.viet.russia.viru.event.clickEvent;

import java.util.ArrayList;

import dictionary.viet.russia.viru.model.db.DictWord;
import dictionary.viet.russia.viru.model.db.DictWordObject;

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
