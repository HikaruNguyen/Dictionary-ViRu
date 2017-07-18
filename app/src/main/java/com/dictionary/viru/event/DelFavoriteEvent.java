package com.dictionary.viru.event;

/**
 * Created by manhi on 20/1/2016.
 */
public class DelFavoriteEvent {
    public String word;
    public int position;
    public DelFavoriteEvent(String word, int position) {
        this.word = word;
        this.position = position;
    }
}
