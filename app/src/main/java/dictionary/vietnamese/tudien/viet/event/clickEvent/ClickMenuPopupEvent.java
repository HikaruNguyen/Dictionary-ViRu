package dictionary.vietnamese.tudien.viet.event.clickEvent;

/**
 * Created by manhi on 30/1/2016.
 */
public class ClickMenuPopupEvent {
    public int type;
    public static final int TYPE_ZOOM = 1;
    public static final int TYPE_HIDE = 2;

    public ClickMenuPopupEvent(int type) {
        this.type = type;
    }
}
