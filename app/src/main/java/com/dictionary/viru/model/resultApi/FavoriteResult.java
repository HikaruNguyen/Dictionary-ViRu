package com.dictionary.viru.model.resultApi;

import java.util.List;

/**
 * Created by manhi on 6/5/2016.
 */
public class FavoriteResult extends BaseResult {
    public FavoriteData data;
    public static class FavoriteData {
        public List<Word> items;
    }

    public static class Word {
        public String word;
        public long created_at;
    }
}
