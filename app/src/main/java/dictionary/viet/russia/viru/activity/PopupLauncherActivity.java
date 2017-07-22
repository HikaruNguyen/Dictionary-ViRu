package dictionary.viet.russia.viru.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import dictionary.viet.russia.viru.NextDictUtils.CLog;
import dictionary.viet.russia.viru.NextDictUtils.Utils;
import dictionary.viet.russia.viru.R;
import dictionary.viet.russia.viru.service.ChatHeadService;

public class PopupLauncherActivity extends AppCompatActivity {
    private static final String TAG = PopupLauncherActivity.class.getSimpleName();
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD = 1234;
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG = 5678;
    public static Button btnStartService, btnShowMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_launcher);

        btnStartService = (Button) findViewById(R.id.btnStartService);
        btnShowMsg = (Button) findViewById(R.id.btnMsg);

        btnStartService.setOnClickListener(lst_StartService);
        btnShowMsg.setOnClickListener(lst_ShowMsg);
    }


    Button.OnClickListener lst_StartService = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            CLog.d(TAG, "lst_StartService -> Utils.canDrawOverlays(Main.this): " + Utils.canDrawOverlays(PopupLauncherActivity.this));

            if (Utils.canDrawOverlays(PopupLauncherActivity.this))
                startChatHead();
            else {
                requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD);
            }
        }

    };


    Button.OnClickListener lst_ShowMsg = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (Utils.canDrawOverlays(PopupLauncherActivity.this))
                showChatHeadMsg();
            else {
                requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG);
            }

        }

    };

    private void startChatHead() {
        startService(new Intent(PopupLauncherActivity.this, ChatHeadService.class));
    }

    private void showChatHeadMsg() {
        java.util.Date now = new java.util.Date();
        String str = "test by henry  " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);

        Intent it = new Intent(PopupLauncherActivity.this, ChatHeadService.class);
        it.putExtra(Utils.EXTRA_MSG, str);
        startService(it);
    }

    private void needPermissionDialog(final int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PopupLauncherActivity.this);
        builder.setMessage("You need to allow permission");
        builder.setPositiveButton("OK",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        requestPermission(requestCode);
                    }
                });
        builder.setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.setCancelable(false);
        builder.show();
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();


    }

    private void requestPermission(int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD) {
            if (!Utils.canDrawOverlays(PopupLauncherActivity.this)) {
                needPermissionDialog(requestCode);
            } else {
                startChatHead();
            }

        } else if (requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG) {
            if (!Utils.canDrawOverlays(PopupLauncherActivity.this)) {
                needPermissionDialog(requestCode);
            } else {
                showChatHeadMsg();
            }

        }

    }
}
