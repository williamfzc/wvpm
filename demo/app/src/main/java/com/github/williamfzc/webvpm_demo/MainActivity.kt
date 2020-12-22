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

        // normal
//        WvpmAPI.wrapWebview(mWebview)
//        mWebview.loadUrl(url)

        // if we already have a client
        mWebview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d(TAG, "origin page finished!")
            }
        }

        WvpmAPI.injectOnPageFinished(mWebview, WvpmJsFlag.FLAG_JS_PERF, fun(resp: WvpmResponse) {
            Log.d(TAG, "get js return after page finished in activity: ${resp.data}")
        })
        WvpmAPI.injectOnPageFinished(mWebview, WvpmJsFlag.FLAG_JS_PERF, fun(resp: WvpmResponse) {
            Log.d(TAG, "again: get js return after page finished in activity: ${resp.data}")
        })
        WvpmAPI.injectOnPageStarted(mWebview, WvpmJsFlag.FLAG_JS_PERF, fun(resp: WvpmResponse) {
            Log.d(TAG, "get js return before page started in activity: ${resp.data}")
        })
        mWebview.loadUrl(url)

//        fun execCallback(resp: WvpmResponse) {
//            Log.d(TAG, "execCallback: get js return in activity: ${resp.data}")
//        }
//
//        Log.d(TAG, "execute js: ${WvpmJsFlag.FLAG_JS_PERF}")
//        WvpmAPI.getPerfTiming(mWebview, ::execCallback)

        button.setOnClickListener {
            val intent = Intent(this, JavaActivity::class.java)
            startActivity(intent)
        }
    }
}
