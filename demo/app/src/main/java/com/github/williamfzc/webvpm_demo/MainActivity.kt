package com.github.williamfzc.webvpm_demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.williamfzc.wvpm.*
import com.github.williamfzc.wvpm.js.WvpmJsContent
import com.github.williamfzc.wvpm.js.WvpmJsManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val url = "http://www.baidu.com"

    // custom hook if you need
    enum class MyJsFlag: WvpmJsFlagBase { FLAG_NEW }
    val MyJsContent = object: WvpmJsContent(path = "perf.js") {}

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

        // custom hook
        WvpmJsManager.addJs(MyJsFlag.FLAG_NEW, MyJsContent)
        WvpmAPI.injectOnPageFinished(mWebview, MyJsFlag.FLAG_NEW) {
            Log.d(TAG, "custom js: ${it.data}")
        }

        WvpmAPI.injectOnPageFinished(mWebview, WvpmJsFlag.FLAG_JS_PERF_TIMING, fun(resp: WvpmResponse) {
            Log.d(TAG, "get js return after page finished in activity: ${resp.data}")
        })
        WvpmAPI.injectOnPageFinished(mWebview, WvpmJsFlag.FLAG_JS_PERF_NAVIGATION, fun(resp: WvpmResponse) {
            Log.d(TAG, "again: get js return after page finished in activity: ${resp.data}")
        })
        WvpmAPI.injectOnPageStarted(mWebview, WvpmJsFlag.FLAG_JS_PERF_TIMING, fun(resp: WvpmResponse) {
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
