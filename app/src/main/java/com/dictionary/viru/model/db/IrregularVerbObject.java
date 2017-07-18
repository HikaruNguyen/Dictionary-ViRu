package com.dictionary.viru.model.db;

import java.io.Serializable;

/**
 * Created by manhi on 14/4/2016.
 */
public class IrregularVerbObject implements Serializable {
    public Integer id;
    public String infinitive;
    public String past_simple;
    public String past_participle;
    public String third_person_singular;
    public String present_participle;
    public Integer group;
    public String definition;

    public IrregularVerbObject() {
    }

    public IrregularVerbObject(Integer id, String infinitive, String past_simple, String past_participle) {
        this.id = id;
        this.infinitive = infinitive;
        this.past_simple = past_simple;
        this.past_participle = past_participle;
    }

    public IrregularVerbObject(Integer id, String infinitive, String past_simple, String past_participle, String third_person_singular, String present_participle, Integer group, String definition) {
        this.id = id;
        this.infinitive = infinitive;
        this.past_simple = past_simple;
        this.past_participle = past_participle;
        this.third_person_singular = third_person_singular;
        this.present_participle = present_participle;
        this.group = group;
        this.definition = definition;
    }
}