package com.dictionary.viru.model.resultApi;

import com.dictionary.viru.model.LinkObject;
import com.dictionary.viru.model.MetaObject;

import java.util.ArrayList;

/**
 * Created by manhi on 28/12/2015.
 */
public class ListDictResult {
    public boolean success;
    public String message;
    public int statusCode;
    public ListDictData data;

    public static class ListDictData {
        public ArrayList<ListDictInfo> items;
        public MetaObject _meta;
        public LinkObject _links;
    }

    public static class ListDictInfo {
        public String id;
        public String name;
        public String description;
        public String src_lang;
        public String des_lang;
        public long word_count;
        public String author;
        public String email;
        public String dict_type;
        public int date;
        public String created_at;
        public String updated_at;
        public String res_path;
        public String name2;
        public String url;
        public boolean isDownloaded;
        public int isChecked = 0;

        public ListDictInfo() {

        }

        public ListDictInfo(String name) {
            this.name = name;
        }

        public ListDictInfo(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public ListDictInfo(String id, String name, int isChecked) {
            this.id = id;
            this.name = name;
            this.isChecked = isChecked;
        }
    }
}
