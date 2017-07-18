package com.dictionary.viru.model.db;

/**
 * Created by manhi on 5/1/2016.
 */
public class DictWord {
    private int id;
    private String word;
    private String definition;
    private byte[] definition2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public byte[] getDefinition2() {
        return definition2;
    }

    public void setDefinition2(byte[] definition2) {
        this.definition2 = definition2;
    }

    public DictWord() {

    }

    public DictWord(String word) {
        this.word = word;
    }

    public DictWord(int id, String word) {
        this.id = id;
        this.word = word;
    }

    public DictWord(int id, String word, String definition) {
        this.id = id;
        this.word = word;
        this.definition = definition;
    }

    public DictWord(int id, String word, byte[] definition2) {
        this.id = id;
        this.word = word;
        this.definition2 = definition2;
    }
}
