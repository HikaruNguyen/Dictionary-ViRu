package com.dictionary.viru.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.dictionary.viru.R;
import com.dictionary.viru.configuration.Configruation;
import com.dictionary.viru.database.ManagerDictDatabase;
import com.dictionary.viru.event.ChangeMenuEvent;
import com.dictionary.viru.model.db.ManagerDict;
import com.dictionary.viru.service.ChatHeadService;
import com.dictionary.viru.service.standOut.StandOutWindow;
import com.dictionary.viru.service.standOut.WidgetsWindow;
import com.dictionary.viru.NextDictUtils.Utils;

import java.util.List;

import de.greenrobot.event.EventBus;


public class TestActivity extends AppCompatActivity {
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD = 1234;
    private String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StandOutWindow.closeAll(this, WidgetsWindow.class);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                word = intent.getStringExtra(Intent.EXTRA_TEXT);

            }
        }
        showPopup();
        finish();
    }

    private void showPopup() {
        ManagerDictDatabase managerDictDatabase = new ManagerDictDatabase(TestActivity.this);
        managerDictDatabase.open();
        List<ManagerDict> managerDicts = managerDictDatabase.getAllDictIfChecked();
        if (managerDicts != null && managerDicts.size() > 0) {
            if (Utils.canDrawOverlays(TestActivity.this))
                startChatHead();
            else {
                requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD);
//            startChatHead();
            }
            finish();
        } else {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TestActivity.this);
            builder.setMessage(getString(R.string.dictNotFound));
            builder.setNegativeButton(getString(R.string.late), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton(getString(R.string.downNow), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Utils.hideKeyboard(TestActivity.this);
                    EventBus.getDefault().post(new ChangeMenuEvent(Configruation.HOME_SELECT_DICT));
                }
            });
            builder.setCancelable(false);
            builder.show();
        }


//        Intent intent = new Intent(TestActivity.this, TestActivity.class);
//        startActivity(intent);
//        finish();
    }

    private void startChatHead() {
        SharedPreferences sharedPreferences = getSharedPreferences(Configruation.Pref_Popup, MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(Configruation.KEY_ISPOPUP_OPENING, true).apply();
        Intent i = new Intent(TestActivity.this, ChatHeadService.class);
        i.putExtra("word", word);
        startService(i);

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission(int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD) {
            if (!Utils.canDrawOverlays(TestActivity.this)) {
                needPermissionDialog(requestCode);
            } else {
                startChatHead();
            }

        }

    }

    private void needPermissionDialog(final int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
        builder.setMessage(getString(R.string.needPermission));
        builder.setPositiveButton(getString(R.string.ok),
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        requestPermission(requestCode);
                    }
                });
        builder.setNegativeButton(getString(R.string.cancel), new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
