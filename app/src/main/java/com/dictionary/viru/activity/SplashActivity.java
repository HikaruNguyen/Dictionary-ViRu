package com.dictionary.viru.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dictionary.viru.NextDictUtils.CLog;
import com.dictionary.viru.NextDictUtils.Utils;
import com.dictionary.viru.R;
import com.dictionary.viru.configuration.Configruation;
import com.dictionary.viru.database.DictDatabaseOpenHelper;
import com.dictionary.viru.database.ManagerDictDatabase;
import com.dictionary.viru.model.resultApi.ListDictResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static android.R.attr.name;

public class SplashActivity extends AppCompatActivity {
    private static String TAG = SplashActivity.class.getSimpleName();
    private ManagerDictDatabase managerDictDataSource;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private List<ListDictResult.ListDictInfo> listDictInfos;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        managerDictDataSource = new ManagerDictDatabase(this);
        mSharedPreferences = getSharedPreferences(Configruation.PREFERENCE_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        InitData();
    }

    private void InitData() {
//
        managerDictDataSource.open();
        int countdict = managerDictDataSource.getCountDictExist();
        managerDictDataSource.close();
        if (countdict <= 0) {
            LoadDictAsynTask loadDictAsynTask = new LoadDictAsynTask(this);
            loadDictAsynTask.execute();
        } else {
            new Thread(new Task()).start();
        }
    }

    class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            goToMain();
        }
    }

    private void LoadDictJson() {
        String json = loadJSONFromAsset();
        Gson gson = new Gson();
        ListDictResult result = gson.fromJson(json, ListDictResult.class);
        if (result.success && result.statusCode == 200) {
            if (result.data != null && result.data.items != null && result.data.items.size() > 0) {
                listDictInfos = result.data.items;
                LoadDictFromAsset();

            }
        } else {
            goToMain();
        }
    }

    private void LoadDictFromAsset() {
        managerDictDataSource.open();
        for (int i = 0; i < listDictInfos.size(); i++) {
            if (Utils.assetExists(getAssets(), listDictInfos.get(i).id + ".ndf")) {
                addDict(listDictInfos.get(i));
            }
        }
        managerDictDataSource.close();
        goToMain();
    }

    public void addDict(ListDictResult.ListDictInfo listDictInfo) {
        if (!managerDictDataSource.checkExistsDict(listDictInfo)) {
            if (managerDictDataSource.addDict(listDictInfo)) {
                CLog.d(TAG, "Creat success: " + listDictInfo.id);
                loadSQLite(listDictInfo);
            } else {
                CLog.d(TAG, "Loi creat data: " + name);
            }
        }
    }

    private void loadSQLite(ListDictResult.ListDictInfo listDictInfo) {
        DictDatabaseOpenHelper dictDatabaseOpenHelper = new DictDatabaseOpenHelper(SplashActivity.this);
        dictDatabaseOpenHelper.StoreDatabase(listDictInfo.id + ".ndf");
    }


    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("dict/dict.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void goToMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class LoadDictAsynTask extends AsyncTask<String, String, String> {
        private Context context;

        public LoadDictAsynTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            LoadDictJson();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            goToMain();
        }


    }
}
