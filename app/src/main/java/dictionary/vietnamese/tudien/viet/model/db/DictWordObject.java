package dictionary.vietnamese.tudien.viet.model.db;

/**
 * Created by manhi on 6/1/2016.
 */
public class DictWordObject {
    public final static int TYPE_SEARCH_WORD = 1;
    public final static int TYPE_MEANING_WORD = 2;
    public String dictId;
    public String dictName;
    public DictWord dictWord;
    public int type;
    public int formatType;

    public DictWordObject(String dictId, String dictName, DictWord dictWord, int formatType) {
        this.dictId = dictId;
        this.dictName = dictName;
        this.dictWord = dictWord;
        this.formatType = formatType;
    }

}
