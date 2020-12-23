package com.github.williamfzc.webvpm_demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.williamfzc.wvpm.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val url = "http://www.baidu.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // wvpm will not break your custom client if you already have one
        mWebview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d(TAG, "origin page finished!")
            }
        }

        // inject a callback
        WvpmAPI.injectOnPageFinished(
                mWebview,
                WvpmJsFlag.FLAG_JS_PERF_TIMING,
                fun(resp: WvpmResponse) {
                    Log.d(TAG, "get js return after page finished in activity: ${resp.data}")
                }
        )
        // you can inject multi callback functions
        // they will be executed one by one
        WvpmAPI.injectOnPageFinished(
                mWebview,
                WvpmJsFlag.FLAG_JS_PERF_NAVIGATION,
                fun(resp: WvpmResponse) {
                    Log.d(TAG, "again: get js return after page finished in activity: ${resp.data}")
                }
        )

        // inject onPageStarted
        WvpmAPI.injectOnPageStarted(mWebview, WvpmJsFlag.FLAG_JS_PERF_TIMING, fun(resp: WvpmResponse) {
            Log.d(TAG, "get js return before page started in activity: ${resp.data}")
        })

        // load url as usual
        // you will see all your functions have been executed
        mWebview.loadUrl(url)

        // if you are using java, check JavaActivity.java
        button.setOnClickListener {
            val intent = Intent(this, JavaActivity::class.java)
            startActivity(intent)
        }
    }
}
