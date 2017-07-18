package com.dictionary.viru.model.resultApi;

import java.util.List;

/**
 * Created by manhi on 29/2/2016.
 */
public class FriendFacebookResult {

    public List<FriendFacebookInfo> data;

    public static class FriendFacebookInfo {
        public String id;
        public String name;
        public FriendFacebookImage picture;

    }

    public static class FriendFacebookImage {
        public ImageData data;

        public static class ImageData {
            public boolean is_silhouette;
            public String url;
        }
    }
}
