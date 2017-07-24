package dictionary.vietnamese.tudien.viet.model.db;

import java.io.Serializable;

/**
 * Created by manhi on 17/3/2016.
 */
public class SentencesObject implements Serializable {
    //Cum dong tu
    public Integer id;
    public String verb;
    public String definition;
    public String example;

    public SentencesObject(Integer id, String verb, String definition, String example) {
        this.id = id;
        this.verb = verb;
        this.definition = definition;
        this.example = example;
    }

    // thanh ngu
    public String idiom;
    public String category;
    public String country;

    public SentencesObject(Integer id, String idiom, String definition, String category, String country) {
        this.id = id;
        this.definition = definition;
        this.idiom = idiom;
        this.category = category;
        this.country = country;
    }

    //tuc ngu
    public String proverb;

    public SentencesObject(Integer id, String proverb, String definition, String category, String country, boolean isProverb) {
        this.id = id;
        this.definition = definition;
        this.proverb = proverb;
        this.category = category;
        this.country = country;
    }

    //tu viet tat
    public String acronym;

    public SentencesObject(Integer id, String acronym, String definition) {
        this.id = id;
        this.acronym = acronym;
        this.definition = definition;
    }
}
