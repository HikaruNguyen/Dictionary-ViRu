package com.dictionary.viru.event;

public class UpdateDownloadEvent {
    public int progress;
    public int contentId;

    public UpdateDownloadEvent(int progress, int contentId) {
        this.progress = progress;
        this.contentId = contentId;
    }
}
