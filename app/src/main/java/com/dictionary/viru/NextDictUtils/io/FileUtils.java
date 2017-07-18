package com.dictionary.viru.NextDictUtils.io;

import android.content.Context;
import android.os.Environment;


import com.dictionary.viru.NextDictUtils.CLog;

import java.io.File;

public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getExternalStorageDir(String folderName, boolean createIfNotExisted) {
        File file = new File(Environment.getExternalStorageDirectory(), folderName);
        if (file.exists()) {
            CLog.i(TAG, "Directory existed");
        } else if (createIfNotExisted && !file.mkdirs()) {
            CLog.e(TAG, "Directory not created");
        }
        return file;
    }

    /**
     * this function doesn't require WRITE_EXTERNAL_STORAGE permission since Kitkat 4.4 (api 19)
     *
     * @param ctx
     * @param folderName
     * @param createIfNotExisted
     * @return
     */
    public static File getExternalPrivateStorageDir(Context ctx, String folderName, boolean createIfNotExisted) {
        File file = new File(ctx.getExternalFilesDir(null), folderName);
        if (file.exists()) {
            CLog.i(TAG, "Directory existed");
        } else if (createIfNotExisted && !file.mkdirs()) {
            CLog.e(TAG, "Directory not created");
        }
        return file;
    }
}
