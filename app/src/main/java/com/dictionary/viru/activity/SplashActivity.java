package com.dictionary.viru.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.dictionary.viru.NextDictUtils.CLog;
import com.dictionary.viru.NextDictUtils.Utils;
import com.dictionary.viru.NextDictUtils.io.CompressUtils;
import com.dictionary.viru.R;
import com.dictionary.viru.configuration.Configruation;
import com.dictionary.viru.database.DictDatabaseOpenHelper;
import com.dictionary.viru.database.ManagerDictDatabase;
import com.dictionary.viru.model.db.ManagerDict;
import com.dictionary.viru.model.resultApi.ListDictResult;

import org.apache.commons.compress.archivers.ArchiveException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
                addDict(listDictInfos.get(i).id, listDictInfos.get(i).name);
            }
        }
        managerDictDataSource.close();
        goToMain();
    }

    private void LoadDict() {
        File file = new File(MyApplication.DICT_ROOT, listDictInfos.get(index).id + ".ndf");
        if (file.exists()) {
            for (int index = 0; index < listDictInfos.size(); index++) {
                managerDictDataSource.open();
//            addDict(listDictInfos.get(index).id, listDictInfos.get(index).name);
                if (!managerDictDataSource.checkExistsDict(listDictInfos.get(index).id)) {
                    if (managerDictDataSource.addDict(new ManagerDict(listDictInfos.get(index).id, listDictInfos.get(index).name), listDictInfos.get(index).isChecked)) {
                        CLog.d(TAG, "CREAT SUCCESS " + listDictInfos.get(index).id);
                    } else {
                        CLog.d(TAG, "CREAT FAIL " + listDictInfos.get(index).id);
                    }
                }
                managerDictDataSource.close();
            }
            goToMain();
        } else {
            try {
                InputStream stream = getAssets().open("dict/" + listDictInfos.get(index).id);
                File output = new File(MyApplication.DICT_ROOT, listDictInfos.get(index).id + ".tar.gz");
                FileOutputStream outputStream =
                        new FileOutputStream(output.getPath());
                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = stream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
//            int source;
//            switch (index) {
//                case 1:
//                    index = R.raw.concise_en_en;
//                    break;
//                case 2:
//                    index = R.raw.en_vi;
//                    break;
//                case 3:
//                    index = R.raw.vi_en;
//                    break;
//            }
                extractDict(output.getPath(), listDictInfos.get(index).id);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public void addDict(String id, String name) {
        ManagerDict managerDict = new ManagerDict(id, name, "", 1);
        if (!managerDictDataSource.checkExistsDict(managerDict)) {
            if (managerDictDataSource.addDict(managerDict)) {
                CLog.d(TAG, "Creat success: " + id);
                loadSQLite(managerDict);
            } else {
                CLog.d(TAG, "Loi creat data: " + name);
            }
        }
    }

    private void extractDict(String filepath, String dictName) {
        File file = new File(filepath);
        if (!file.exists()) {
            CLog.d(TAG, "file not exist");
            return;
        } else {
            CLog.d(TAG, "file exist exist");
            ExtractDictAsynTask extractDictAsynTask = new ExtractDictAsynTask(SplashActivity.this, file, dictName);
            extractDictAsynTask.execute();
        }

    }

    private class ExtractDictAsynTask extends AsyncTask<String, String, Integer> {
        private File file;
        private Context context;
        private ProgressDialog progressDialog;
        private String dictName;
        private long contentLength;

        public ExtractDictAsynTask(Context context, File file, String dictName) {
            this.file = file;
            this.context = context;
            this.dictName = dictName;
            contentLength = file.length();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(context, "", getString(R.string.extracting));
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(getString(R.string.extracting) + " " + dictName);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            return extractDict(file, dictName, new PublishProgressExtractCallBack() {
                @Override
                public void callback(long progress, int type) {
                    if (type == 1) {
                        publishProgress("" + (int) ((progress * 50.0) / contentLength));
                    } else if (type == 2) {
                        publishProgress("" + (int) ((progress * 50.0) / contentLength) + 50);
                    }

                }
            });

        }

        @Override
        protected void onProgressUpdate(String... progress) {
//            super.onProgressUpdate(values);
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Toast.makeText(SplashActivity.this, getString(R.string.extracSuccess) + " " + dictName, Toast.LENGTH_SHORT).show();
            managerDictDataSource.open();
//            addDict(listDictInfos.get(index).id, listDictInfos.get(index).name);
            if (!managerDictDataSource.checkExistsDict(listDictInfos.get(index).id)) {
                if (managerDictDataSource.addDict(new ManagerDict(listDictInfos.get(index).id, listDictInfos.get(index).name), listDictInfos.get(index).isChecked)) {
                    CLog.d(TAG, "CREAT SUCCESS " + listDictInfos.get(index).id);
                } else {
                    CLog.d(TAG, "CREAT FAIL " + listDictInfos.get(index).id);
                }
            }
            managerDictDataSource.close();
            index++;
            if (index < listDictInfos.size()) {
                LoadDict();
            } else {
                goToMain();
            }

        }

    }

    private int extractDict(File compressedFile, String dictName, final PublishProgressExtractCallBack extractCallBack) {
        String fileName = compressedFile.getName();
        if (!fileName.endsWith(".bz2") && !fileName.endsWith(".gz")) {
            CLog.d(TAG, "Not bz2 or gz compressed file, no need to extract");
            return -1;
        }
        try {
            File uncompressedFile = null;
            if (compressedFile.exists()) {
                if (fileName.endsWith(".bz2")) {
                    uncompressedFile = CompressUtils.unBzip2(compressedFile, MyApplication.DICT_ROOT);
                } else {
                    CLog.d(TAG, "compressedFile: " + compressedFile);
                    uncompressedFile = CompressUtils.unGzip(compressedFile, MyApplication.DICT_ROOT, new CompressUtils.SetProgressCallback() {
                        @Override
                        public void setProgress(long progress) {
                            extractCallBack.callback(progress, 1);
                        }
                    });
                }
                CLog.d(TAG, "extractDict size: " + uncompressedFile.length());
                List<File> finals = new ArrayList<File>();

                if (fileName.endsWith(".tar.bz2") || fileName.endsWith(".tar.gz")) {
                    CLog.d(TAG, "Tar file detected, untarring...");
                    finals = CompressUtils.unTar(uncompressedFile, MyApplication.DICT_ROOT, dictName, new CompressUtils.SetProgressCallback() {
                        @Override
                        public void setProgress(long progress) {
                            extractCallBack.callback(progress, 2);
                        }
                    });
                    for (File file : finals) {
                        CLog.d(TAG, "Uncompressed file: " + file.getAbsolutePath());
                    }
                    // remove tar files
                    uncompressedFile.delete();
                } else {
                    finals.add(uncompressedFile);
                }

                // remove compressed files
                compressedFile.delete();
            }


            return 1;
        } catch (IOException e) {
            e.printStackTrace();

            return 2;
        } catch (ArchiveException e) {
            e.printStackTrace();
            return 2;
        }
    }

    public interface PublishProgressExtractCallBack {
        void callback(long progress, int type);
    }

    private void loadSQLite(ManagerDict managerDict) {
        DictDatabaseOpenHelper dictDatabaseOpenHelper = new DictDatabaseOpenHelper(SplashActivity.this);
        dictDatabaseOpenHelper.StoreDatabase(managerDict.getDictId() + ".ndf");
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
