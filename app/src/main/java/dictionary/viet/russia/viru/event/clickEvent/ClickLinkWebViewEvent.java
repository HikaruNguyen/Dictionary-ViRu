package dictionary.viet.russia.viru.event.clickEvent;

/**
 * Created by manhi on 31/1/2016.
 */
public class ClickLinkWebViewEvent {
    public int type;
    public String word;

    public ClickLinkWebViewEvent(String word, int type) {
        this.word = word;
        this.type = type;
    }
}
