package dictionary.vietnamese.tudien.viet.model.resultApi;

/**
 * Created by manhi on 2/3/2016.
 */
public class HelpResult extends BaseResult {
    public HelpInfo data;

    public static class HelpInfo {
        public String help_url;
        public String info;
    }
}
