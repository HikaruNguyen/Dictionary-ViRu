package dictionary.vietnamese.tudien.viet.model.resultApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import dictionary.vietnamese.tudien.viet.model.LinkObject;
import dictionary.vietnamese.tudien.viet.model.MetaObject;


/**
 * Created by manhi on 28/12/2015.
 */
public class ListDictResult {
    @SerializedName("success")
    public boolean success;
    @SerializedName("message")
    public String message;
    @SerializedName("statusCode")
    public int statusCode;
    @SerializedName("data")
    public ListDictData data;

    public static class ListDictData {
        @SerializedName("items")
        public ArrayList<ListDictInfo> items;
        @SerializedName("_meta")
        public MetaObject _meta;
        @SerializedName("_links")
        public LinkObject _links;
    }

    public static class ListDictInfo {
        @SerializedName("id")
        public String id;
        @SerializedName("name")
        public String name;
        @SerializedName("description")
        public String description;
        @SerializedName("src_lang")
        public String src_lang;
        @SerializedName("des_lang")
        public String des_lang;
        @SerializedName("word_count")
        public long word_count;
        @SerializedName("author")
        public String author;
        @SerializedName("email")
        public String email;
        @SerializedName("dict_type")
        public String dict_type;
        @SerializedName("date")
        public int date;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("res_path")
        public String res_path;
        @SerializedName("name2")
        public String name2;
        @SerializedName("url")
        public String url;
        @SerializedName("isDownloaded")
        public boolean isDownloaded;
        @SerializedName("isChecked")
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
