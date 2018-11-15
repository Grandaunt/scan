package com.bcm.havoc.mylibrary.UI.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bcm.havoc.mylibrary.R;
import com.bcm.havoc.mylibrary.UI.BaseActivity;


/**
 * 详情页
 * Created by Administrator on 2018/1/9 0009.
 */

public class WebActivity extends BaseActivity {
    public WebView webView;
    private ProgressBar progressBar;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        title = getIntent().getStringExtra("title");

        //设置标题
        getTitle_toolbar().setDarkTheme().set_title(title);
        //黑色主题
        // StatusBarUtil.StatusBarLightMode(this);

        setEvent();
    }

    private void setEvent() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        webView = findViewById(R.id.webview);
          /*初始化设置*/
        initWebViewSettings();
        /*初始化webview内事件*/
        initWebViewEvent();
        webView.loadUrl(getIntent().getStringExtra("url"));
    }

    /*初始化webview 的事件*/
    private void initWebViewEvent() {

        webView.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {

                                         view.loadUrl(url);
                                         return true;
                                     }

                                     @Override
                                     public void onPageStarted(WebView view, String url, Bitmap favicon) {

                                     }

                                     @Override
                                     public void onPageFinished(WebView view, String url) {
                                         //设定加载结束的操作

                                     }


                                     @Override
                                     public void onLoadResource(WebView view, String url) {

                                         super.onLoadResource(view, url);

                                     }


                                 }

        );
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (WebActivity.this.title == null)
                    getTitle_toolbar().setDarkTheme().set_title(title);

            }


            //设置加载进度
            @Override
            public void onProgressChanged(final WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setProgress(newProgress);
                }

                super.onProgressChanged(view, newProgress);
            }


        });

    }


    //webView 返回
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (event.getAction() == KeyEvent.ACTION_DOWN)
            if (keyCode == KeyEvent.KEYCODE_BACK) {

                if (webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
            }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    @Override
    public void onPause() {
        super.onPause();

        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(WebActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public void initWebViewSettings() {
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //支持插件
        webSettings.setPluginState(WebSettings.PluginState.ON);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        // webSettings.setSavePassword(false);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 开启database storage API功能
        webSettings.setDatabaseEnabled(true);
        //webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheEnabled(true);

        /*调用支付宝必须设置*/
        // 开启DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
        webSettings.setSupportZoom(true);//支持屏幕缩放
        //webSettings.setBuiltInZoomControls(true);

        webView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                        }
                        break;
                }
                return false;
            }
        });

    }


}
