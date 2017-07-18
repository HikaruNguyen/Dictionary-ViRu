package com.dictionary.viru.model.resultApi;

/**
 * Created by manhi on 7/3/2016.
 */
public class AppInfoResult extends BaseResult {
    public AppInfoData data;

    public class AppInfoData {
        public String name;
        public String package_name;
        public String description;
        public String link_download;
        public Integer version_code;
        public String version_name;
        public String what_new;
    }
}
