package dictionary.vietnamese.tudien.viet.model.resultApi;

/**
 * Created by manhi on 1/3/2016.
 */
public class TransResult extends BaseResult {
    public TransData data;

    public static class TransData {
        public String language_src;
        public String language_des;
        public String translated_text;
    }
}
