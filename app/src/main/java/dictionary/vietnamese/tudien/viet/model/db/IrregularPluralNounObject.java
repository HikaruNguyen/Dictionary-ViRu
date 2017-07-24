package dictionary.vietnamese.tudien.viet.model.db;

import java.io.Serializable;

/**
 * Created by manhi on 17/3/2016.
 */
public class IrregularPluralNounObject implements Serializable {
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_NORMAL = 2;

    public Integer id;
    public String singular;
    public String plural;
    public String group;
    public int type;

    public IrregularPluralNounObject(Integer id, String singular, String plural, String group) {
        this.id = id;
        this.singular = singular;
        this.plural = plural;
        this.group = group;
        this.type = TYPE_NORMAL;
    }

    public IrregularPluralNounObject(String group) {
        this.group = group;
        this.type = TYPE_HEADER;
    }
}
