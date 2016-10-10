package com.cc.xushuailong.javacalljs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
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
        textView = (TextView) findViewById(R.id.arg);
        button = (Button) findViewById(R.id.button);
        buttonWithArg = (Button) findViewById(R.id.buttonWithArg);
    }

    private void viewEvents() {
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("file:///android_asset/index.html");
        webView.loadUrl("file:///android_asset/html/index.html");
//        webView.addJavascriptInterface(this, "wst");
        webView.addJavascriptInterface(this, "yzjk");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:javacalljs()");
            }
        });
        buttonWithArg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:javacalljswitharg(" + "'hello js'" + ")");
            }
        });
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
                textView.setText(textView.getText().toString() + "\njs 调用了 java\n");
            }
        });
    }

    @JavascriptInterface
    public void startFunction(final String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(textView.getText().toString() + "\njs 调用了 java" + str + "\n");
            }
        });
    }
}
