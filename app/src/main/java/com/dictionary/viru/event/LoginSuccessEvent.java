package com.dictionary.viru.event;

/**
 * Created by manhi on 27/2/2016.
 */
public class LoginSuccessEvent {
    public static final int TYPE_FACEBOOK = 1;
    public static final int TYPE_NORMAL = 2;
    public int type;

    public LoginSuccessEvent(int type) {
        this.type = type;
    }
}
