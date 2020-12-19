package com.github.williamfzc.webvpm

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

object WvpmInject {
    private val TAG = "WvpmInject"

    fun inject(wv: WebView) {
        Log.d(TAG, "injecting settings ...")
        // settings
        wv.settings?.let {
            // collect data via js
            @SuppressWarnings
            it.javaScriptEnabled = true
            it.domStorageEnabled = true
        }


        Log.d(TAG, "injecting webview client ...")
        // client
        val originPageFinished = wv.webViewClient::onPageFinished
        wv.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                // some webviews may have their own clients
                originPageFinished.invoke(view, url)

                // inject main contents
                applyWhenFinished(view, url)
            }
        }
        Log.d(TAG, "inject finished")
    }

    private fun applyWhenFinished(view: WebView?, url: String?) {
        Log.d(TAG, "now page finished: $view, $url")
    }
}
