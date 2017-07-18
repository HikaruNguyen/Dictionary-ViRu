package com.dictionary.viru.event;

/**
 * Created by manhi on 4/1/2016.
 */
public class DownloadDictByPositionEvent {
    public int fromPostion;
    public int toPosition;
    public String url;
    public String file_name;

    public DownloadDictByPositionEvent(int fromPostion, int toPosition, String url, String file_name) {
        this.fromPostion = fromPostion;
        this.toPosition = toPosition;
        this.url = url;
        this.file_name = file_name;
    }
}
