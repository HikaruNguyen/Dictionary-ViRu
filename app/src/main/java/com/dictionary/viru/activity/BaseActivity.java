package com.dictionary.viru.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dictionary.viru.R;

/**
 * Created by Nguyen Duc Manh on 14/8/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private Toolbar mActionBarToolbar;
    private ImageView statusBar;

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);
        getToolbar();
//        getStatusbar();

    }

    public Toolbar getToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);

        }
        return mActionBarToolbar;
    }

    public void SetMarginToolbar() {
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = getStatusBarHeight();
        mActionBarToolbar.setLayoutParams(params);

    }

    public void setTitleToolbar(String title) {
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle(title);
        setSupportActionBar(mActionBarToolbar);
    }

    public void setBackButtonToolbar() {
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back);
    }

    public void setMenuToggerToolbar() {
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setT
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu);
    }

    public void setHomeToolbar() {
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setT
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_home);
    }

    public void hideToolBar() {
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setVisibility(View.GONE);
    }

    public void showToolBar() {
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setVisibility(View.VISIBLE);
    }

//    public ImageView getStatusbar() {
//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
//            if (statusBar == null) {
//                statusBar = (ImageView) findViewById(R.id.statusBar);
////                SetHightStatusBar();
//            }
//        }
//
//        return statusBar;
//    }

//    private void SetHightStatusBar() {
//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
//            statusBar.getLayoutParams().height = getStatusBarHeight();
//        } else {
//            statusBar.getLayoutParams().height = 0;
//        }
//    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void startActivityForBundle(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startActivityForBundle(Context context, Class<?> cls) {
        startActivityForBundle(context, cls, null);
    }

    public void ToastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void ToastErrorConnect(Context context) {
        Toast.makeText(context, context.getString(R.string.error_connect), Toast.LENGTH_SHORT).show();
    }

    public void ToastSnackBar(Context context, String msg, boolean isTop) {
//        if (!isTop) {
//            SnackbarManager.show(
//                    Snackbar.with(context)
//                            .text(msg));
//        } else {
//            SnackbarManager.show(
//                    Snackbar.with(context).position(Snackbar.SnackbarPosition.TOP)
//                            .text(msg));
//        }

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
