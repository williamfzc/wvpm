package com.github.williamfzc.webvpm_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.williamfzc.webvpm.WvpmAPI
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
        mWebview.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d(TAG, "origin page finished!")
            }
        }

        fun callback(jsRet: String) {
            Log.d(TAG, "get js return in activity: $jsRet")
        }

        WvpmAPI.wrapWebview(mWebview, ::callback)
        mWebview.loadUrl(url)
    }
}
