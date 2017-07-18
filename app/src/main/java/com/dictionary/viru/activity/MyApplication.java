package com.dictionary.viru.activity;

import android.app.Application;

import com.dictionary.viru.NextDictUtils.CLog;
import com.dictionary.viru.NextDictUtils.PackageUtils;
import com.dictionary.viru.NextDictUtils.io.FileUtils;

import java.io.File;

/**
 * Created by manhi on 4/1/2016.
 */
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();

    private static final String DICT_FOLDER = "data";

    public static File DICT_ROOT;
    private static MyApplication _instance = null;

    public static MyApplication getInstance() {
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        DICT_ROOT = FileUtils.getExternalPrivateStorageDir(this, DICT_FOLDER, true);

        CLog.d(TAG, "Application create");
        CLog.d(TAG, "Version code: " + PackageUtils.getVersionCode(this) + ", version name: " + PackageUtils.getVersionName(this));
    }


    @Override
    public void onTerminate() {
        CLog.d(TAG, "Application create");
        super.onTerminate();
    }


}