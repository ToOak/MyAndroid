package com.cc.xushuailong.javacalljs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView webView, webViewGif, webViewImg, webview2;
    private WebSettings webSettings;
    private TextView textView;
    private Button button;
    private Button buttonWithArg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        viewEvents();
        setWebUserAgent("yzjkandroid");
    }

    private void initViews() {
        webView = (WebView) findViewById(R.id.webview);
        webViewGif = (WebView) findViewById(R.id.webview_gif);
        webViewImg = (WebView) findViewById(R.id.webview_img);
        webview2 = (WebView) findViewById(R.id.webview_2);
        textView = (TextView) findViewById(R.id.arg);
        button = (Button) findViewById(R.id.button);
        buttonWithArg = (Button) findViewById(R.id.buttonWithArg);
    }

    private void viewEvents() {
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        setWebview(webSettings);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/html/index.html");
        webView.addJavascriptInterface(this, "yzjk");


        webview2.getSettings().setJavaScriptEnabled(true);
        webview2.loadUrl("file:///android_asset/index.html");
        webview2.addJavascriptInterface(this,"wst");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview2.loadUrl("javascript:javacalljs()");
            }
        });
        buttonWithArg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview2.loadUrl("javascript:javacalljswitharg(" + "'hello js'" + ")");
            }
        });


        setWebview(webViewGif.getSettings());
        setWebview(webViewImg.getSettings());

        webViewImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
        webViewGif.loadUrl("file:///android_asset/img/ohno.gif");
        webViewImg.loadUrl("file:///android_asset/img/bigman.jpg");
        webViewImg.setScrollContainer(false);

    }

    private void setWebview(WebSettings webSetting) {
        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
         * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
//        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setJavaScriptEnabled(true);

// User settings

        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setUseWideViewPort(true);//关键点
        webSetting.setDisplayZoomControls(false);
        webSetting.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSetting.setAllowFileAccess(true); // 允许访问文件
        webSetting.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSetting.setSupportZoom(false); // 支持缩放
        webSetting.setLoadWithOverviewMode(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        Log.d("maomao", "densityDpi = " + mDensity);
        if (mDensity == 240) {
            webSetting.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSetting.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if(mDensity == 120) {
            webSetting.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
            webSetting.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else if (mDensity == DisplayMetrics.DENSITY_TV){
            webSetting.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else{
            webSetting.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
    }


    /**
     * 设置默认浏览的userAgent
     *
     * @param userAgent
     */
    public void setWebUserAgent(String userAgent) {
        if (webSettings != null) {
            String web = getWebUserAgent();
            webSettings.setUserAgentString(web + ";" + userAgent);
        }
    }

    /**
     * 得到浏览器的useerAgent
     *
     * @return: userAgent
     */
    public String getWebUserAgent() {
        if (webSettings != null) {
            return webSettings.getUserAgentString();
        } else {
            return null;
        }
    }

    /**
     * js调用本地方法
     */
    @JavascriptInterface
    public void enterNativeWithData(String key, String params) {
        Log.d("yyw", "the one key: " + key + "\tparams: " + params);
        Toast.makeText(this," do base64",Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void startFunction() {
        Toast.makeText(this, "js 调用了 Java 方法", Toast.LENGTH_SHORT).show();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(textView.getText().toString() + "js 调用了 java\n");
            }
        });
    }

    @JavascriptInterface
    public void startFunction(final String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(textView.getText().toString() + "js 调用了 java" + str + "\n");
            }
        });
    }
}
