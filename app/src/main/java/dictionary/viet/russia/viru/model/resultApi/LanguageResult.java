package dictionary.viet.russia.viru.model.resultApi;

import java.util.ArrayList;

/**
 * Created by manhi on 1/3/2016.
 */
public class LanguageResult extends BaseResult {
    public LanguageData data;

    public static class LanguageData {
        public ArrayList<LanguageInfo> language_src;
        public ArrayList<LanguageInfo> language_des;
    }

    public static class LanguageInfo {
        public String code;
        public String name;

        public LanguageInfo(String code, String name) {
            this.code = code;
            this.name = name;
        }
    }
}
