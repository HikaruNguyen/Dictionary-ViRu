package com.dictionary.viru.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Thuc on 1/1/2015.
 */
public class JsonHelper {
    private static final String TAG = JsonHelper.class.getSimpleName();

    public static <T> T decode(String data, final Class<T> type) {
        try {
            if(data == null)
                return null;
            JSONObject obj = new JSONObject(data);
            return gsonDecode(obj.toString(), type);
        } catch (JSONException ex) {
            try {
                JSONArray ar = new JSONArray(data);
                return gsonDecode(ar.toString(), type);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "JSONException: " + ex.getMessage());
                Log.e(TAG, "JSONException, error parsing: " + data);
            }
        }

        return null;
    }

    private static <T> T gsonDecode(String data, final Class<T> type) {
        try {
            Gson gson = new Gson();
            final T res = gson.fromJson(data, type);
            return res;
        } catch (JsonParseException ex) {
            ex.printStackTrace();
            Log.e(TAG, "JsonParseException: " + ex.getMessage());
//            Log.e(TAG, "JsonParseException, error parsing: " + data);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            Log.e(TAG, "RuntimeException: " + ex.getMessage());
            Log.e(TAG, "RuntimeException, error parsing: " + data);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e(TAG, "Exception: " + ex.getMessage());
            Log.e(TAG, "Exception, error parsing: " + data);
        }

        return null;
    }
}
