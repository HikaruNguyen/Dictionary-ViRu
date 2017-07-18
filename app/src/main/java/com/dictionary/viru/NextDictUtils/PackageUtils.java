package com.dictionary.viru.NextDictUtils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PackageUtils {
    public static final int getVersionCode(Context ctx) {
        PackageInfo pInfo;
        try {
            pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            return pInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static final String getVersionName(Context ctx) {
        PackageInfo pInfo;
        try {
            pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            return pInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            CLog.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                CLog.e("Key Hash=", key);
            }
        } catch (NameNotFoundException e1) {
            CLog.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            CLog.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            CLog.e("Exception", e.toString());
        }

        return key;
    }
}
