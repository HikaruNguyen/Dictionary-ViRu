package dictionary.vietnamese.tudien.viet.event;

/**
 * Created by manhi on 29/1/2016.
 */
public class HideShowKeyBoardEvent {
    public static final int TYPE_SHOW = 1;
    public static final int TYPE_HIDE = 2;
    public int type;

    public HideShowKeyBoardEvent(int type) {
        this.type = type;
    }
}
