package com.dictionary.viru.event;

import java.io.File;
import java.util.List;

/**
 * Created by thuc on 6/30/14.
 */
public class DictDownloadEvent {
    public static final int STATUS_DOWNLOAD_NOT_STARTED = -1;
    public static final int STATUS_DOWNLOAD_STARTED = 0;
    public static final int STATUS_DOWNLOAD_IN_PROGRESS = 1;
    public static final int STATUS_DOWNLOAD_SUCCESS = 2;
    public static final int STATUS_DOWNLOAD_FAILED = 3;
    public static final int STATUS_EXTRACT_STARTED = 5;
    public static final int STATUS_EXTRACT_IN_PROGRESS = 6;
    public static final int STATUS_EXTRACT_SUCCESS = 7;
    public static final int STATUS_EXTRACT_FAILED = 9;

    public List<File> extractedFiles = null;
    public String downloadedFile = "";
    public long downloadedSize = 0;
    public long totalSize = 0;
    public Exception exception = null;
    public int status = STATUS_DOWNLOAD_NOT_STARTED;

    public DictDownloadEvent(int status, String path, long downloadedBytes, long totalSize) {
        this.status = status;
        this.downloadedFile = path;
        this.downloadedSize = downloadedBytes;
        this.totalSize = totalSize;
    }

    public DictDownloadEvent(int statusExtractFailed, String path, Exception e) {
        this.status = statusExtractFailed;
        this.downloadedFile = path;
        this.exception = e;
    }

    public DictDownloadEvent(int statusExtract, String originalFile, long length) {
        this.status = statusExtract;
        this.downloadedFile = originalFile;
        this.totalSize = length;
    }

    public DictDownloadEvent(int statusExtract, String originalFile, List<File> extractedFiles) {
        this.status = statusExtract;
        this.extractedFiles = extractedFiles;
    }
}
