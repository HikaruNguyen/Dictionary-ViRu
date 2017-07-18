package com.dictionary.viru.event.clickEvent;

import java.util.Locale;

/**
 * Created by manhi on 5/3/2016.
 */
public class ClickVoiceEvent {
    public Locale loc;
    public String word;
    public int type;
    public ClickVoiceEvent(Locale loc, String word, int type) {
        this.loc = loc;
        this.word = word;
        this.type = type;
    }
}
