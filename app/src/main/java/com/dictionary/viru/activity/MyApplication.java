package com.dictionary.viru.activity;

import android.app.Application;

import com.dictionary.viru.NextDictUtils.CLog;
import com.dictionary.viru.NextDictUtils.PackageUtils;

/**
 * Created by manhi on 4/1/2016.
 */
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();


    private static MyApplication _instance = null;

    public static MyApplication getInstance() {
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;

        CLog.d(TAG, "Application create");
        CLog.d(TAG, "Version code: " + PackageUtils.getVersionCode(this) + ", version name: " + PackageUtils.getVersionName(this));
    }


    @Override
    public void onTerminate() {
        CLog.d(TAG, "Application create");
        super.onTerminate();
    }


}