package com.github.williamfzc.webvpm_demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.webkit.WebView;

import com.github.williamfzc.wvpm.WvpmAPI;
import com.github.williamfzc.wvpm.WvpmJsFlag;
import com.github.williamfzc.wvpm.WvpmResponse;

public final class JavaActivity extends AppCompatActivity {
    private final static String TAG = "JavaActivity";
    private final static String url = "http://www.baidu.com";

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        wv = findViewById(R.id.mWebview1);

        WvpmAPI.injectOnPageFinished(wv, WvpmJsFlag.FLAG_JS_PERF_TIMING, (WvpmResponse resp) -> {
            Log.d(TAG, "first page finished: " + resp.getData());
            return null;
        });
        WvpmAPI.injectOnPageFinished(wv, WvpmJsFlag.FLAG_JS_PERF_NAVIGATION, (WvpmResponse resp) -> {
            Log.d(TAG, "second page finished: " + resp.getData());
            return null;
        });
        WvpmAPI.injectOnPageStarted(wv, WvpmJsFlag.FLAG_JS_PERF_TIMING, (WvpmResponse resp) -> {
            Log.d(TAG, "before page started: " + resp.getData());
            return null;
        });
        wv.loadUrl(url);
    }
}
