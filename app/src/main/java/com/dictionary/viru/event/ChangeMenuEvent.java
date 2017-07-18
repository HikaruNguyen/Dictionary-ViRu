package com.dictionary.viru.event;

/**
 * Created by manhi on 29/1/2016.
 */
public class ChangeMenuEvent {
    public int type;
    public boolean isClickFloat;

    public ChangeMenuEvent(int type) {
        this.type = type;
        isClickFloat = false;
    }

    public ChangeMenuEvent(int type, boolean isClickFloat) {
        this.type = type;
        this.isClickFloat = isClickFloat;
    }
}
