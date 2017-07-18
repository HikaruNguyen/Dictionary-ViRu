package com.dictionary.viru.widget.customeControl;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dictionary.viru.R;
import com.dictionary.viru.configuration.Configruation;
import com.dictionary.viru.event.clickEvent.ClickLinkWebViewEvent;
import com.dictionary.viru.NextDictUtils.CLog;

import de.greenrobot.event.EventBus;

/**
 * Created by manhi on 31/1/2016.
 */
public class CustomeWebView extends WebView {
    private static final String TAG = CustomeWebView.class.getSimpleName();
    private int type;
    private Context context;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public CustomeWebView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CustomeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CustomeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        this.setWebViewClient(new MyBrowser());
        WebSettings webSettings = this.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");
//        webSettings.setUseWideViewPort(true);
        webSettings.setDefaultTextEncodingName("utf-8");
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setAllowFileAccess(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        this.setHorizontalScrollBarEnabled(false);
//        this.setVerticalScrollBarEnabled(true);
        SharedPreferences sharedPreferences = context.getSharedPreferences(Configruation.Pref_Setting, Context.MODE_PRIVATE);
        String fontSize = sharedPreferences.getString(Configruation.Pref_Key_FontSize, context.getString(R.string.normal));
        CLog.d(TAG, "font size webview: " + fontSize);
        if (fontSize.equals(context.getString(R.string.small))) {
            webSettings.setTextSize(WebSettings.TextSize.SMALLEST);
        } else if (fontSize.equals(context.getString(R.string.normal))) {
//            webSettings.setDefaultFontSize((int) context.getResources().getDimension(R.dimen.font_size));
            webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        } else if (fontSize.equals(context.getString(R.string.large))) {
//            webSettings.setDefaultFontSize((int) context.getResources().getDimension(R.dimen.font_size_large));
            webSettings.setTextSize(WebSettings.TextSize.LARGEST);
        } else if (fontSize.equals(context.getString(R.string.huge))) {
//            webSettings.setDefaultFontSize((int) context.getResources().getDimension(R.dimen.font_size_huge));
            webSettings.setTextSize(WebSettings.TextSize.LARGEST);
        } else {
//            webSettings.setDefaultFontSize((int) context.getResources().getDimension(R.dimen.font_size));
            webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        }

    }

    private class MyBrowser extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
//            prbLoading.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            prbLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public String getOriginalUrl() {
        CLog.d("webview", "getOriginalUrl: " + super.getOriginalUrl());
        return super.getOriginalUrl();
    }

    @Override
    public void loadUrl(String url) {
        if (url.startsWith("kref://")) {
            String link = url.substring(7, url.length());
            CLog.d("webview", "link: " + link);
            EventBus.getDefault().post(new ClickLinkWebViewEvent(link, type));
        } else {
            super.loadUrl(url);
        }
    }

}
